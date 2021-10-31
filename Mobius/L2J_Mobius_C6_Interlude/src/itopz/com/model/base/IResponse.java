/*
 * Copyright (c) 2021 iTopZ
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package itopz.com.model.base;

import itopz.com.Configurations;
import itopz.com.util.Json;
import itopz.com.util.Logs;
import itopz.com.vote.VDSystem;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author Nightwolf
 * iToPz Discord: https://discord.gg/KkPms6B5aE
 * @Author Rationale
 * Base structure credits goes on Rationale Discord: Rationale#7773
 * <p>
 * Vote Donation System
 * Script website: https://itopz.com/
 * Script version: 1.4
 * Pack Support: Mobius C6 Interlude
 * <p>
 * Personal Donate Panels: https://www.denart-designs.com/
 * Free Donate panel: https://itopz.com/
 */
public abstract class IResponse
{
	private static final Logs _log = new Logs(IResponse.class.getSimpleName());

	private final String _url;

	public IResponse(final String url)
	{
		_url = url;
	}

	public abstract void onFetch(final String TOPSITE, final int responseCode, final Json response);

	public abstract String replaceURL(final String retailURL);

	/**
	 * Return connection
	 *
	 * @return IResponse object
	 */
	public IResponse connect(String TOPSITE, VDSystem.VoteType TYPE)
	{
		// initialize new connection on topsite
		HttpURLConnection connection = null;
		int responseCode;
		try
		{
			// set url type of topsite
			connection = (HttpURLConnection) new URL(replaceURL(_url)).openConnection();
			// user agent
			connection.addRequestProperty("User-Agent", TOPSITE + " Server Manager");
			// do fast timeout
			connection.setConnectTimeout(3000);
			// another stupid idea to send POST request in order to get an int... presented by:
			if (TOPSITE.equals("L2NETWORK"))
			{
				String urlParameters = "apiKey=" + Configurations.L2NETWORK_API_KEY + "&type=1&player=";
				byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
				int postDataLength = postData.length;
				connection.setRequestMethod("POST");
				connection.setRequestProperty("Content-Length", Integer.toString(postDataLength));
				connection.setDoOutput(true);
				try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream()))
				{
					wr.write(postData);
					// bypass manually the checks
					responseCode = 200;
				}
			}
			// and because of the stupid idea with the POST request we have to stop checking the response code because of IllegalStateException
			else
			{
				// get response code from topsite
				responseCode = connection.getResponseCode();
			}
			// read input
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8)))
			{
				// return response code and content
				onFetch(TOPSITE, responseCode, new Json(reader.lines().collect(Collectors.joining()), TOPSITE, TYPE));
			}
			return this;
		} catch (final SocketTimeoutException sex)
		{
			
			if (Configurations.DEBUG)
				_log.error("Socket:" + sex.getMessage(), sex);
		} catch (final FileNotFoundException fnfe)
		{
			
			if (Configurations.DEBUG)
				_log.error("Socket:" + fnfe.getMessage(), fnfe);
		} catch (final Exception ex)
		{
			
			if (Configurations.DEBUG)
				_log.error("Exception:" + ex.getMessage(), ex);
		} finally
		{
			// close connection
			Optional.ofNullable(connection).ifPresent(HttpURLConnection::disconnect);
		}
		return null;
	}
}