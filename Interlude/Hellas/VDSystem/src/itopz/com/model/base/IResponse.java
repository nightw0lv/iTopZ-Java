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

import itopz.com.util.Json;

import java.io.BufferedReader;
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
 *
 * Vote Donation System
 * Script website: https://itopz.com/
 * Script version: 1.0
 * Pack Support: L2JHellas 562 https://app.assembla.com/spaces/l2hellas/subversion/source
 *
 * Personal Donate Panels: https://www.denart-designs.com/
 * Free Donate panel: https://itopz.com/
 */
public abstract class IResponse
{
	private final String _url;

	public IResponse(final String url)
	{
		_url = url;
	}

	public abstract void onFetch(final int responseCode, final Json response);

	public abstract String replaceURL(final String retailURL);

	/**
	 * Return connection
	 *
	 * @return IResponse object
	 */
	public IResponse connect()
	{
		// initialize new connection on itopz
		HttpURLConnection connection = null;

		try
		{
			// set url type on itopz
			connection = (HttpURLConnection) new URL(replaceURL(_url)).openConnection();
			// user agent
			connection.addRequestProperty("User-Agent", "ITOPZ Server Manager");
			// do fast timeout
			connection.setConnectTimeout(3000);
			// get response code from itopz.com
			int responseCode = connection.getResponseCode();
			// read input
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8)))
			{
				// return response code and content
				onFetch(responseCode, new Json(reader.lines().collect(Collectors.joining())));
			}
			return this;
		}
		catch(final SocketTimeoutException sex)
		{
			System.out.println(sex.getMessage());
		}
		catch(final Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		finally
		{
			// close connection
			Optional.ofNullable(connection).ifPresent(HttpURLConnection::disconnect);
		}
		return null;
	}
}