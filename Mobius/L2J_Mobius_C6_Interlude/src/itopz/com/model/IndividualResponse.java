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

import itopz.com.gui.Gui;
import itopz.com.model.base.IResponse;
import itopz.com.util.Json;
import itopz.com.util.Utilities;
import itopz.com.vote.VDSystem;

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
	 * @param url       string
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
	 * @param response     Json object
	 */
	@Override
	public void onFetch(final String TOPSITE, final int responseCode, final Json response)
	{
		_responseCode = responseCode == 200 ? 200 : -1;
		_hasVoted = response.getBoolean(TOPSITE.toLowerCase() + "_voted");
		_voteTime = -1;
		_serverTime = -1;
		_voteError = "NONE";

		switch (TOPSITE)
		{
			case "ITOPZ":
				_voteTime = response.getLong(TOPSITE.toLowerCase() + "_vote_time") * 1000;
				_serverTime = response.getLong(TOPSITE.toLowerCase() + "serverTime") * 1000;
				_voteError = response.getString("error");
				break;
			case "HOPZONE":
				_voteTime = _hasVoted ? Utilities.millisecondsFromString(response.getString(TOPSITE.toLowerCase() + "_vote_time"), "US/Arizona") : -1;
				_serverTime = Utilities.millisecondsFromString(response.getString(TOPSITE.toLowerCase() + "_server_time"), "US/Arizona");
				_voteError = response.getString("errorMsg");
				break;
			case "L2JBRASIL":
				_voteTime = Utilities.millisecondsFromString(response.getString(TOPSITE.toLowerCase() + "_vote_time"), "America/Sao_Paulo");
				_serverTime = Utilities.millisecondsFromString(response.getString(TOPSITE.toLowerCase() + "_server_time"), "America/Sao_Paulo");
				break;
			case "L2TOPGAMESERVER":
				_voteTime = Utilities.millisecondsFromString(response.getString(TOPSITE.toLowerCase() + "_vote_time"), "Europe/Berlin");
				_serverTime = Utilities.millisecondsFromString(response.getString(TOPSITE.toLowerCase() + "_server_time"), "Europe/Berlin");
				break;
			case "L2TOPSERVERS":
				_voteTime = Utilities.millisecondsFromString(response.getString(TOPSITE.toLowerCase() + "_vote_time"), "Europe/Athens");
				_serverTime = Utilities.millisecondsFromString(response.getString(TOPSITE.toLowerCase() + "_server_time"), "Europe/Athens");
				break;
			case "L2NETWORK":
			case "L2VOTES":
				break;
			default:
				_hasVoted = false;
				Gui.getInstance().ConsoleWrite("Defaulted: " + TOPSITE);
				break;
		}
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
	public IndividualResponse connect(final String TOPSITE, final VDSystem.VoteType TYPE)
	{
		return (IndividualResponse) super.connect(TOPSITE, TYPE);
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
	 * @param url       string
	 * @param IPAddress string
	 * @return IndividualResponse object
	 */
	public static IndividualResponse OPEN(final String url, final String IPAddress)
	{
		return new IndividualResponse(url, IPAddress);
	}
}