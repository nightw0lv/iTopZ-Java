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
package itopz.com.util;

import itopz.com.Configurations;

import java.util.Arrays;

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
public enum Url
{
	ITOPZ_GLOBAL_URL("https://itopz.com/check/" + Configurations.ITOPZ_SERVER_API_KEY + "/" + Configurations.ITOPZ_SERVER_ID + "/"),
	ITOPZ_INDIVIDUAL_URL("https://itopz.com/check/" + Configurations.ITOPZ_SERVER_API_KEY + "/" + Configurations.ITOPZ_SERVER_ID + "/%IP%"),
	ITOPZ_SERVER_URL("https://itopz.com/info/" + Configurations.ITOPZ_SERVER_ID),
	ITOPZ_URL("https://itopz.com/"),
	HOPZONE_INDIVIDUAL_URL("https://api.hopzone.net/lineage2/vote?token=" + Configurations.HOPZONE_SERVER_API_KEY + "&ip_address=%IP%"),
	HOPZONE_GLOBAL_URL("https://api.hopzone.net/lineage2/votes?token=" + Configurations.HOPZONE_SERVER_API_KEY),
	HOPZONE_URL("https://hopzone.net/"),
	L2TOPGAMESERVER_INDIVIDUAL_URL("http://l2.topgameserver.net/lineage/VoteApi/API_KEY=" + Configurations.L2TOPGAMESERVER_API_KEY + "/getData/%IP%"),
	L2TOPGAMESERVER_GLOBAL_URL("http://l2.topgameserver.net/lineage/VoteApi/API_KEY=" + Configurations.L2TOPGAMESERVER_API_KEY + "/getData"),
	L2TOPGAMESERVER_URL("https://l2.topgameserver.net/"),
	L2NETWORK_INDIVIDUAL_URL("https://l2network.eu/index.php?a=in&u=" + Configurations.L2NETWORK_USER_NAME + "&ipc=%IP%"),
	L2NETWORK_GLOBAL_URL("https://l2network.eu/api.php"),
	L2NETWORK_URL("https://l2network.eu/"),
	L2JBRASIL_INDIVIDUAL_URL("https://top.l2jbrasil.com/votesystem/index.php?username=" + Configurations.L2JBRASIL_USER_NAME + "&ip=%IP%&type=json"),
	L2JBRASIL_GLOBAL_URL("https://top.l2jbrasil.com/votesystem/index.php?username=" + Configurations.L2JBRASIL_USER_NAME + "&type=json"),
	L2JBRASIL_URL("https://top.l2jbrasil.com/"),
	L2TOPSERVERS_INDIVIDUAL_URL("https://l2topservers.com/votes?token=" + Configurations.L2TOPSERVERS_API_KEY + "&ip=%IP%"),
	L2TOPSERVERS_GLOBAL_URL("https://l2topservers.com/l2top/" + Configurations.L2TOPSERVERS_SERVER_ID + "/" + Configurations.L2TOPSERVERS_SERVER_NAME),
	L2TOPSERVERS_URL("https://l2topservers.com/"),
	L2VOTES_INDIVIDUAL_URL("https://l2votes.com/api.php?apiKey=" + Configurations.L2VOTES_API_KEY + "&ip=%IP%"),
	L2VOTES_GLOBAL_URL("https://l2votes.com/api.php?apiKey=" + Configurations.L2VOTES_API_KEY),
	L2VOTES_URL("https://l2votes.com.com/"),
	DISCORD("https://discord.gg/KkPms6B5aE"),
	DENART_DESIGNS("https://www.denart-designs.com"),
	GITHUB_URL("https://github.com/nightw0lv/iTopZ-Java");

	private final String _text;
	private static final Url[] _urls = values();

	/**
	 * @param text String
	 */
	Url(final String text)
	{
		_text = text;
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see Enum#toString()
	 */
	@Override
	public String toString()
	{
		return _text;
	}

	/**
	 * returns enum from string
	 *
	 * @param TOPSITE string
	 * @return Individual
	 */
	public static Url from(String TOPSITE)
	{
		return Arrays.stream(_urls).filter(s -> s.name().equalsIgnoreCase(TOPSITE)).findFirst().orElse(ITOPZ_URL);
	}
}
