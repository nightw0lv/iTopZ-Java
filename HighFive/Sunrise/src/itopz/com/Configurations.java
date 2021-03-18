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
package itopz.com;

import itopz.com.util.Logs;
import l2r.util.PropertiesParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Nightwolf
 * iToPz Discord: https://discord.gg/KkPms6B5aE
 * @Author Rationale
 * Base structure credits goes on Rationale Discord: Rationale#7773
 * <p>
 * Vote Donation System
 * Script website: https://itopz.com/
 * Script version: 1.1
 * Pack Support: Sunrise
 * <p>
 * Personal Donate Panels: https://www.denart-designs.com/
 * Free Donate panel: https://itopz.com/
 */
public class Configurations
{
	// logger
	private static final Logs _log = new Logs(Configurations.class.getSimpleName());

	public static boolean DEBUG;

	// set donate manager variables
	public static boolean ITOPZ_DONATE_MANAGER;

	// set console variables
	public static boolean ITOPZ_CONSOLE_ENABLE;
	public static String ITOPZ_CONSOLE_FONT;
	public static int ITOPZ_CONSOLE_SIZE;
	public static int ITOPZ_CONSOLE_COLOR_R;
	public static int ITOPZ_CONSOLE_COLOR_G;
	public static int ITOPZ_CONSOLE_COLOR_B;
	public static int ITOPZ_CONSOLE_WIDTH;
	public static int ITOPZ_CONSOLE_HEIGHT;

	// set itopz global reward variables
	public static boolean ITOPZ_GLOBAL_REWARD;
	public static int ITOPZ_SERVER_ID;
	public static String ITOPZ_SERVER_API_KEY;
	public static long ITOPZ_VOTE_CHECK_DELAY;
	public static boolean ITOPZ_ANNOUNCE_STATISTICS;
	public static int ITOPZ_VOTE_STEP;
	public static Map<Integer, List<Long[]>> ITOPZ_GLOBAL_REWARDS = new HashMap<>();
	// set itopz individual variables
	public static boolean ITOPZ_INDIVIDUAL_REWARD;
	public static Map<Integer, List<Long[]>> ITOPZ_INDIVIDUAL_REWARDS = new HashMap<>();

	// set hopzone global reward variables
	public static boolean HOPZONE_GLOBAL_REWARD;
	public static String HOPZONE_SERVER_API_KEY;
	public static long HOPZONE_VOTE_CHECK_DELAY;
	public static boolean HOPZONE_ANNOUNCE_STATISTICS;
	public static int HOPZONE_VOTE_STEP;
	public static Map<Integer, List<Long[]>> HOPZONE_GLOBAL_REWARDS = new HashMap<>();
	// set hopzone individual variables
	public static boolean HOPZONE_INDIVIDUAL_REWARD;
	public static Map<Integer, List<Long[]>> HOPZONE_INDIVIDUAL_REWARDS = new HashMap<>();

	// set l2topgameserver global reward variables
	public static boolean L2TOPGAMESERVER_GLOBAL_REWARD;
	public static String L2TOPGAMESERVER_API_KEY;
	public static int L2TOPGAMESERVER_VOTE_CHECK_DELAY;
	public static boolean L2TOPGAMESERVER_ANNOUNCE_STATISTICS;
	public static int L2TOPGAMESERVER_VOTE_STEP;
	public static Map<Integer, List<Long[]>> L2TOPGAMESERVER_GLOBAL_REWARDS = new HashMap<>();
	// set l2topgameserver individual variables
	public static boolean L2TOPGAMESERVER_INDIVIDUAL_REWARD;
	public static Map<Integer, List<Long[]>> L2TOPGAMESERVER_INDIVIDUAL_REWARDS = new HashMap<>();

	// set l2jbrasil global reward variables
	public static boolean L2JBRASIL_GLOBAL_REWARD;
	public static String L2JBRASIL_USER_NAME;
	public static int L2JBRASIL_VOTE_CHECK_DELAY;
	public static boolean L2JBRASIL_ANNOUNCE_STATISTICS;
	public static int L2JBRASIL_VOTE_STEP;
	public static Map<Integer, List<Long[]>> L2JBRASIL_GLOBAL_REWARDS = new HashMap<>();
	// set l2jbrasil individual variables
	public static boolean L2JBRASIL_INDIVIDUAL_REWARD;
	public static Map<Integer, List<Long[]>> L2JBRASIL_INDIVIDUAL_REWARDS = new HashMap<>();

	// set l2network global reward variables
	public static boolean L2NETWORK_GLOBAL_REWARD;
	public static String L2NETWORK_API_KEY;
	public static String L2NETWORK_USER_NAME;
	public static int L2NETWORK_VOTE_CHECK_DELAY;
	public static boolean L2NETWORK_ANNOUNCE_STATISTICS;
	public static int L2NETWORK_VOTE_STEP;
	public static Map<Integer, List<Long[]>> L2NETWORK_GLOBAL_REWARDS = new HashMap<>();
	// set l2network individual variables
	public static boolean L2NETWORK_INDIVIDUAL_REWARD;
	public static Map<Integer, List<Long[]>> L2NETWORK_INDIVIDUAL_REWARDS = new HashMap<>();

	// set l2topservers global reward variables
	public static boolean L2TOPSERVERS_GLOBAL_REWARD;
	public static String L2TOPSERVERS_API_KEY;
	public static String L2TOPSERVERS_SERVER_ID;
	public static String L2TOPSERVERS_SERVER_NAME;
	public static int L2TOPSERVERS_VOTE_CHECK_DELAY;
	public static boolean L2TOPSERVERS_ANNOUNCE_STATISTICS;
	public static int L2TOPSERVERS_VOTE_STEP;
	public static Map<Integer, List<Long[]>> L2TOPSERVERS_GLOBAL_REWARDS = new HashMap<>();
	// set l2topservers individual variables
	public static boolean L2TOPSERVERS_INDIVIDUAL_REWARD;
	public static Map<Integer, List<Long[]>> L2TOPSERVERS_INDIVIDUAL_REWARDS = new HashMap<>();

	// set l2votes global reward variables
	public static boolean L2VOTES_GLOBAL_REWARD;
	public static String L2VOTES_API_KEY;
	public static int L2VOTES_VOTE_CHECK_DELAY;
	public static boolean L2VOTES_ANNOUNCE_STATISTICS;
	public static int L2VOTES_VOTE_STEP;
	public static Map<Integer, List<Long[]>> L2VOTES_GLOBAL_REWARDS = new HashMap<>();
	// set l2topservers individual variables
	public static boolean L2VOTES_INDIVIDUAL_REWARD;
	public static Map<Integer, List<Long[]>> L2VOTES_INDIVIDUAL_REWARDS = new HashMap<>();

	/**
	 * load config variables
	 */
	public static void load()
	{
		// load configuration file
		PropertiesParser ep = new PropertiesParser("./config/VDSystem.properties");

		// debug messages
		DEBUG = ep.getBoolean("VDS_DEBUG", false);

		// set console variables
		ITOPZ_CONSOLE_ENABLE = ep.getBoolean("ConsoleEnable", true);
		ITOPZ_CONSOLE_FONT = ep.getString("ConsoleFont", "Arial");
		ITOPZ_CONSOLE_SIZE = ep.getInt("ConsoleFontSize", 12);
		ITOPZ_CONSOLE_COLOR_R = ep.getInt("ConsoleColorR", 204);
		ITOPZ_CONSOLE_COLOR_G = ep.getInt("ConsoleColorG", 238);
		ITOPZ_CONSOLE_COLOR_B = ep.getInt("ConsoleColorB", 241);
		ITOPZ_CONSOLE_WIDTH = ep.getInt("ConsoleWidth", 400);
		ITOPZ_CONSOLE_HEIGHT = ep.getInt("ConsoleHeight", 350);

		// set donate manager variables
		ITOPZ_DONATE_MANAGER = ep.getBoolean("DonateManager", true);

		// set itopz global reward variables
		ITOPZ_GLOBAL_REWARD = ep.getBoolean("iTopZGlobalVoteReward", false);
		ITOPZ_SERVER_ID = ep.getInt("ServerID", 325339);
		ITOPZ_SERVER_API_KEY = ep.getString("iTopZApiKey", "DEMO");
		ITOPZ_ANNOUNCE_STATISTICS = ep.getBoolean("iTopZAnnounceStatistics", false);
		ITOPZ_VOTE_CHECK_DELAY = ep.getInt("iTopZCheckDelay", 10);
		ITOPZ_VOTE_STEP = ep.getInt("iTopZVoteStep", 20);
		for (String global : ep.getString("iTopZGlobalRewards", "57,20000000-50000000-100;57,20000000-50000000-100").split(";"))
		{
			String[] global_split = global.split(";");
			for (String split : global_split)
			{
				String[] parts = split.split(",");
				// Start Join the map
				List<Long[]> temp = new ArrayList<>();
				// Min-Max-Chance
				temp.add(new Long[]
				{
					Long.parseLong(parts[1].split("-")[0]),
					Long.parseLong(parts[1].split("-")[1]),
					Long.parseLong(parts[1].split("-")[2]),
				});
				ITOPZ_GLOBAL_REWARDS.put(Integer.parseInt(parts[0]), temp);
			}
		}
		// set itopz individual variables
		ITOPZ_INDIVIDUAL_REWARD = ep.getBoolean("iTopZIndividualReward", false);
		for (String individual : ep.getString("iTopZIndividualRewards", "57,20000000-50000000-100;57,20000000-50000000-100").split(";"))
		{
			String[] individual_split = individual.split(";");
			for (String split : individual_split)
			{
				String[] parts = split.split(",");
				// Start Join the map
				List<Long[]> temp = new ArrayList<>();
				// Min-Max-Chance
				temp.add(new Long[]
				{
					Long.parseLong(parts[1].split("-")[0]),
					Long.parseLong(parts[1].split("-")[1]),
					Long.parseLong(parts[1].split("-")[2]),
				});
				ITOPZ_INDIVIDUAL_REWARDS.put(Integer.parseInt(parts[0]), temp);
			}
		}

		// set hopzone global reward variables
		HOPZONE_GLOBAL_REWARD = ep.getBoolean("HopzoneGlobalVoteReward", false);
		HOPZONE_SERVER_API_KEY = ep.getString("HopzoneApiKey", "DEMO");
		HOPZONE_ANNOUNCE_STATISTICS = ep.getBoolean("HopzoneAnnounceStatistics", false);
		HOPZONE_VOTE_CHECK_DELAY = ep.getInt("HopzoneCheckDelay", 10);
		HOPZONE_VOTE_STEP = ep.getInt("HopzoneVoteStep", 20);
		for (String global : ep.getString("HopzoneGlobalRewards", "57,20000000-50000000-100;57,20000000-50000000-100").split(";"))
		{
			String[] global_split = global.split(";");
			for (String split : global_split)
			{
				String[] parts = split.split(",");
				// Start Join the map
				List<Long[]> temp = new ArrayList<>();
				// Min-Max-Chance
				temp.add(new Long[]
				{
					Long.parseLong(parts[1].split("-")[0]),
					Long.parseLong(parts[1].split("-")[1]),
					Long.parseLong(parts[1].split("-")[2]),
				});
				HOPZONE_GLOBAL_REWARDS.put(Integer.parseInt(parts[0]), temp);
			}
		}
		// set hopzone individual variables
		HOPZONE_INDIVIDUAL_REWARD = ep.getBoolean("HopzoneIndividualReward", false);
		for (String individual : ep.getString("HopzoneIndividualRewards", "57,20000000-50000000-100;57,20000000-50000000-100").split(";"))
		{
			String[] individual_split = individual.split(";");
			for (String split : individual_split)
			{
				String[] parts = split.split(",");
				// Start Join the map
				List<Long[]> temp = new ArrayList<>();
				// Min-Max-Chance
				temp.add(new Long[]
				{
					Long.parseLong(parts[1].split("-")[0]),
					Long.parseLong(parts[1].split("-")[1]),
					Long.parseLong(parts[1].split("-")[2]),
				});
				HOPZONE_INDIVIDUAL_REWARDS.put(Integer.parseInt(parts[0]), temp);
			}
		}

		// set l2topgameserver global reward variables
		L2TOPGAMESERVER_GLOBAL_REWARD = ep.getBoolean("L2TopGSGlobalVoteReward", false);
		L2TOPGAMESERVER_API_KEY = ep.getString("L2TopGSApiKey", "DEMO");
		L2TOPGAMESERVER_ANNOUNCE_STATISTICS = ep.getBoolean("L2TopGSAnnounceStatistics", false);
		L2TOPGAMESERVER_VOTE_CHECK_DELAY = ep.getInt("L2TopGSCheckDelay", 10);
		L2TOPGAMESERVER_VOTE_STEP = ep.getInt("L2TopGSVoteStep", 20);
		for (String global : ep.getString("L2TopGSGlobalRewards", "57,20000000-50000000-100;57,20000000-50000000-100").split(";"))
		{
			String[] global_split = global.split(";");
			for (String split : global_split)
			{
				String[] parts = split.split(",");
				// Start Join the map
				List<Long[]> temp = new ArrayList<>();
				// Min-Max-Chance
				temp.add(new Long[]
				{
					Long.parseLong(parts[1].split("-")[0]),
					Long.parseLong(parts[1].split("-")[1]),
					Long.parseLong(parts[1].split("-")[2]),
				});
				L2TOPGAMESERVER_GLOBAL_REWARDS.put(Integer.parseInt(parts[0]), temp);
			}
		}
		// set l2topgameserver individual variables
		L2TOPGAMESERVER_INDIVIDUAL_REWARD = ep.getBoolean("L2TopGSIndividualReward", false);
		for (String individual : ep.getString("L2TopGSIndividualRewards", "57,20000000-50000000-100;57,20000000-50000000-100").split(";"))
		{
			String[] individual_split = individual.split(";");
			for (String split : individual_split)
			{
				String[] parts = split.split(",");
				// Start Join the map
				List<Long[]> temp = new ArrayList<>();
				// Min-Max-Chance
				temp.add(new Long[]
				{
					Long.parseLong(parts[1].split("-")[0]),
					Long.parseLong(parts[1].split("-")[1]),
					Long.parseLong(parts[1].split("-")[2]),
				});
				L2TOPGAMESERVER_INDIVIDUAL_REWARDS.put(Integer.parseInt(parts[0]), temp);
			}
		}

		// set l2jbrasil global reward variables
		L2JBRASIL_GLOBAL_REWARD = ep.getBoolean("L2JBrasilGlobalVoteReward", false);
		L2JBRASIL_USER_NAME = ep.getString("L2JBrasilUserName", "DEMO");
		L2JBRASIL_ANNOUNCE_STATISTICS = ep.getBoolean("L2JBrasilAnnounceStatistics", false);
		L2JBRASIL_VOTE_CHECK_DELAY = ep.getInt("L2JBrasilCheckDelay", 10);
		L2JBRASIL_VOTE_STEP = ep.getInt("L2JBrasilVoteStep", 20);
		for (String global : ep.getString("L2JBrasilGlobalRewards", "57,20000000-50000000-100;57,20000000-50000000-100").split(";"))
		{
			String[] global_split = global.split(";");
			for (String split : global_split)
			{
				String[] parts = split.split(",");
				// Start Join the map
				List<Long[]> temp = new ArrayList<>();
				// Min-Max-Chance
				temp.add(new Long[]
				{
					Long.parseLong(parts[1].split("-")[0]),
					Long.parseLong(parts[1].split("-")[1]),
					Long.parseLong(parts[1].split("-")[2]),
				});
				L2JBRASIL_GLOBAL_REWARDS.put(Integer.parseInt(parts[0]), temp);
			}
		}
		// set l2jbrasil individual variables
		L2JBRASIL_INDIVIDUAL_REWARD = ep.getBoolean("L2JBrasilIndividualReward", false);
		for (String individual : ep.getString("L2JBrasilIndividualRewards", "57,20000000-50000000-100;57,20000000-50000000-100").split(";"))
		{
			String[] individual_split = individual.split(";");
			for (String split : individual_split)
			{
				String[] parts = split.split(",");
				// Start Join the map
				List<Long[]> temp = new ArrayList<>();
				// Min-Max-Chance
				temp.add(new Long[]
				{
					Long.parseLong(parts[1].split("-")[0]),
					Long.parseLong(parts[1].split("-")[1]),
					Long.parseLong(parts[1].split("-")[2]),
				});
				L2JBRASIL_INDIVIDUAL_REWARDS.put(Integer.parseInt(parts[0]), temp);
			}
		}

		// set l2network global reward variables
		L2NETWORK_GLOBAL_REWARD = ep.getBoolean("L2NetworkGlobalVoteReward", false);
		L2NETWORK_API_KEY = ep.getString("L2NetworkApiKey", "Hi");
		L2NETWORK_USER_NAME = ep.getString("L2NetworkUserName", "Nope");
		L2NETWORK_ANNOUNCE_STATISTICS = ep.getBoolean("L2NetworkAnnounceStatistics", false);
		L2NETWORK_VOTE_CHECK_DELAY = ep.getInt("L2NetworkCheckDelay", 10);
		L2NETWORK_VOTE_STEP = ep.getInt("L2NetworkVoteStep", 20);
		for (String global : ep.getString("L2NetworkGlobalRewards", "57,20000000-50000000-100;57,20000000-50000000-100").split(";"))
		{
			String[] global_split = global.split(";");
			for (String split : global_split)
			{
				String[] parts = split.split(",");
				// Start Join the map
				List<Long[]> temp = new ArrayList<>();
				// Min-Max-Chance
				temp.add(new Long[]
				{
					Long.parseLong(parts[1].split("-")[0]),
					Long.parseLong(parts[1].split("-")[1]),
					Long.parseLong(parts[1].split("-")[2]),
				});
				L2NETWORK_GLOBAL_REWARDS.put(Integer.parseInt(parts[0]), temp);
			}
		}
		// set l2network individual variables
		L2NETWORK_INDIVIDUAL_REWARD = ep.getBoolean("L2NetworkIndividualReward", false);
		for (String individual : ep.getString("L2NetworkIndividualRewards", "57,20000000-50000000-100;57,20000000-50000000-100").split(";"))
		{
			String[] individual_split = individual.split(";");
			for (String split : individual_split)
			{
				String[] parts = split.split(",");
				// Start Join the map
				List<Long[]> temp = new ArrayList<>();
				// Min-Max-Chance
				temp.add(new Long[]
				{
					Long.parseLong(parts[1].split("-")[0]),
					Long.parseLong(parts[1].split("-")[1]),
					Long.parseLong(parts[1].split("-")[2]),
				});
				L2NETWORK_INDIVIDUAL_REWARDS.put(Integer.parseInt(parts[0]), temp);
			}
		}

		// set l2topservers global reward variables
		L2TOPSERVERS_GLOBAL_REWARD = ep.getBoolean("L2TopServersGlobalVoteReward", false);
		L2TOPSERVERS_API_KEY = ep.getString("L2TopServersApiKey", "Hi");
		L2TOPSERVERS_SERVER_ID = ep.getString("L2TopServersServerId", "Hi");
		L2TOPSERVERS_SERVER_NAME = ep.getString("L2TopServersServerName", "Hi");
		L2TOPSERVERS_ANNOUNCE_STATISTICS = ep.getBoolean("L2TopServersAnnounceStatistics", false);
		L2TOPSERVERS_VOTE_CHECK_DELAY = ep.getInt("L2TopServersCheckDelay", 10);
		L2TOPSERVERS_VOTE_STEP = ep.getInt("L2TopServersVoteStep", 20);
		for (String global : ep.getString("L2TopServersGlobalRewards", "57,20000000-50000000-100;57,20000000-50000000-100").split(";"))
		{
			String[] global_split = global.split(";");
			for (String split : global_split)
			{
				String[] parts = split.split(",");
				// Start Join the map
				List<Long[]> temp = new ArrayList<>();
				// Min-Max-Chance
				temp.add(new Long[]
				{
					Long.parseLong(parts[1].split("-")[0]),
					Long.parseLong(parts[1].split("-")[1]),
					Long.parseLong(parts[1].split("-")[2]),
				});
				L2TOPSERVERS_GLOBAL_REWARDS.put(Integer.parseInt(parts[0]), temp);
			}
		}
		// set l2network individual variables
		L2TOPSERVERS_INDIVIDUAL_REWARD = ep.getBoolean("L2TopServersIndividualReward", false);
		for (String individual : ep.getString("L2TopServersIndividualRewards", "57,20000000-50000000-100;57,20000000-50000000-100").split(";"))
		{
			String[] individual_split = individual.split(";");
			for (String split : individual_split)
			{
				String[] parts = split.split(",");
				// Start Join the map
				List<Long[]> temp = new ArrayList<>();
				// Min-Max-Chance
				temp.add(new Long[]
				{
					Long.parseLong(parts[1].split("-")[0]),
					Long.parseLong(parts[1].split("-")[1]),
					Long.parseLong(parts[1].split("-")[2]),
				});
				L2TOPSERVERS_INDIVIDUAL_REWARDS.put(Integer.parseInt(parts[0]), temp);
			}
		}

		// set l2votes global reward variables
		L2VOTES_GLOBAL_REWARD = ep.getBoolean("L2VotesGlobalVoteReward", false);
		L2VOTES_API_KEY = ep.getString("L2VotesApiKey", "Hi");
		L2VOTES_ANNOUNCE_STATISTICS = ep.getBoolean("L2VotesAnnounceStatistics", false);
		L2VOTES_VOTE_CHECK_DELAY = ep.getInt("L2VotesCheckDelay", 10);
		L2VOTES_VOTE_STEP = ep.getInt("L2VotesVoteStep", 20);
		for (String global : ep.getString("L2VotesGlobalRewards", "57,20000000-50000000-100;57,20000000-50000000-100").split(";"))
		{
			String[] global_split = global.split(";");
			for (String split : global_split)
			{
				String[] parts = split.split(",");
				// Start Join the map
				List<Long[]> temp = new ArrayList<>();
				// Min-Max-Chance
				temp.add(new Long[]
				{
					Long.parseLong(parts[1].split("-")[0]),
					Long.parseLong(parts[1].split("-")[1]),
					Long.parseLong(parts[1].split("-")[2]),
				});
				L2VOTES_GLOBAL_REWARDS.put(Integer.parseInt(parts[0]), temp);
			}
		}
		// set l2votes individual variables
		L2VOTES_INDIVIDUAL_REWARD = ep.getBoolean("L2VotesIndividualReward", false);
		for (String individual : ep.getString("L2VotesIndividualRewards", "57,20000000-50000000-100;57,20000000-50000000-100").split(";"))
		{
			String[] individual_split = individual.split(";");
			for (String split : individual_split)
			{
				String[] parts = split.split(",");
				// Start Join the map
				List<Long[]> temp = new ArrayList<>();
				// Min-Max-Chance
				temp.add(new Long[]
				{
					Long.parseLong(parts[1].split("-")[0]),
					Long.parseLong(parts[1].split("-")[1]),
					Long.parseLong(parts[1].split("-")[2]),
				});
				L2VOTES_INDIVIDUAL_REWARDS.put(Integer.parseInt(parts[0]), temp);
			}
		}

		_log.info(Configurations.class.getSimpleName() + ": loaded.");
	}
}