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
package itopz.com.model;

import itopz.com.model.base.IResponse;
import itopz.com.util.Json;

/**
 * @Author Nightwolf
 * iToPz Discord: https://discord.gg/KkPms6B5aE
 * @Author Rationale
 * Base structure credits goes on Rationale Discord: Rationale#7773
 *
 * Vote Donation System
 * Script website: https://itopz.com/
 * Script version: 1.0
 * Pack Support: Mobius CT 2.6 High Five
 *
 * Personal Donate Panels: https://www.denart-designs.com/
 * Free Donate panel: https://itopz.com/
 */
public class IndividualResponse extends IResponse
{
	// local variables
	private boolean _hasVoted;
	private long _voteTime;
	private long _serverTime;
	private String _voteError;
	private int _responseCode;
	private final String _IPAddress;

	/**
	 * Constructor
	 *
	 * @param url string
	 * @param IPAddress string
	 */
	public IndividualResponse(final String url, final String IPAddress)
	{
		super(url);
		_IPAddress = IPAddress;
	}

	/**
	 * on fetch data set local variables
	 *
	 * @param responseCode int
	 * @param response Json object
	 */
	@Override
	public void onFetch(final int responseCode, final Json response)
	{
		//TODO parse the result for individual with StringJoiner str = new StringJoiner();
		_responseCode = responseCode;
		_hasVoted = response.getBoolean("isVoted");
		_serverTime = response.getLong("serverTime");
		_voteTime = response.getLong("voteTime");
		_voteError = response.getString("error");
	}

	/**
	 * Replace the url address
	 *
	 * @param retailURL string
	 * @return retailURL string
	 */
	@Override
	public String replaceURL(final String retailURL)
	{
		return retailURL.replace("%IP%", _IPAddress);
	}

	/**
	 * Connection
	 *
	 * @return super.connect() object
	 */
	@Override
	public IndividualResponse connect()
	{
		return (IndividualResponse) super.connect();
	}

	/**
	 * Returns response code
	 *
	 * @return _responseCode int
	 */
	public int getResponseCode()
	{
		return _responseCode;
	}

	/**
	 * Returns last error
	 *
	 * @return string
	 */
	public String getError()
	{
		return _voteError;
	}

	/**
	 * Returns voted value
	 *
	 * @return hasVoted boolean
	 */
	public boolean hasVoted()
	{
		return _hasVoted;
	}

	/**
	 * Returns server time value
	 *
	 * @return Time long
	 */
	public long getServerTime()
	{
		return _serverTime;
	}

	/**
	 * Returns vote time value
	 *
	 * @return VoteTime long
	 */
	public long getVoteTime()
	{
		return _voteTime;
	}

	/**
	 * Return response
	 *
	 * @param url string
	 * @param IPAddress string
	 * @return IndividualResponse object
	 */
	public static IndividualResponse OPEN(final String url, final String IPAddress)
	{
		return new IndividualResponse(url, IPAddress);
	}
}