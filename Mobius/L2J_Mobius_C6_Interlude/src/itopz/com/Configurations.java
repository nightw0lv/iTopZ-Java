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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

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
	public static Map<Integer, List<Integer[]>> ITOPZ_GLOBAL_REWARDS = new HashMap<>();
	// set itopz individual variables
	public static boolean ITOPZ_INDIVIDUAL_REWARD;
	public static Map<Integer, List<Integer[]>> ITOPZ_INDIVIDUAL_REWARDS = new HashMap<>();

	// set hopzone global reward variables
	public static boolean HOPZONE_GLOBAL_REWARD;
	public static String HOPZONE_SERVER_API_KEY;
	public static long HOPZONE_VOTE_CHECK_DELAY;
	public static boolean HOPZONE_ANNOUNCE_STATISTICS;
	public static int HOPZONE_VOTE_STEP;
	public static Map<Integer, List<Integer[]>> HOPZONE_GLOBAL_REWARDS = new HashMap<>();
	// set hopzone individual variables
	public static boolean HOPZONE_INDIVIDUAL_REWARD;
	public static Map<Integer, List<Integer[]>> HOPZONE_INDIVIDUAL_REWARDS = new HashMap<>();

	// set l2topgameserver global reward variables
	public static boolean L2TOPGAMESERVER_GLOBAL_REWARD;
	public static String L2TOPGAMESERVER_API_KEY;
	public static int L2TOPGAMESERVER_VOTE_CHECK_DELAY;
	public static boolean L2TOPGAMESERVER_ANNOUNCE_STATISTICS;
	public static int L2TOPGAMESERVER_VOTE_STEP;
	public static Map<Integer, List<Integer[]>> L2TOPGAMESERVER_GLOBAL_REWARDS = new HashMap<>();
	// set l2topgameserver individual variables
	public static boolean L2TOPGAMESERVER_INDIVIDUAL_REWARD;
	public static Map<Integer, List<Integer[]>> L2TOPGAMESERVER_INDIVIDUAL_REWARDS = new HashMap<>();

	// set l2jbrasil global reward variables
	public static boolean L2JBRASIL_GLOBAL_REWARD;
	public static String L2JBRASIL_USER_NAME;
	public static int L2JBRASIL_VOTE_CHECK_DELAY;
	public static boolean L2JBRASIL_ANNOUNCE_STATISTICS;
	public static int L2JBRASIL_VOTE_STEP;
	public static Map<Integer, List<Integer[]>> L2JBRASIL_GLOBAL_REWARDS = new HashMap<>();
	// set l2jbrasil individual variables
	public static boolean L2JBRASIL_INDIVIDUAL_REWARD;
	public static Map<Integer, List<Integer[]>> L2JBRASIL_INDIVIDUAL_REWARDS = new HashMap<>();

	// set l2network global reward variables
	public static boolean L2NETWORK_GLOBAL_REWARD;
	public static String L2NETWORK_API_KEY;
	public static String L2NETWORK_USER_NAME;
	public static int L2NETWORK_VOTE_CHECK_DELAY;
	public static boolean L2NETWORK_ANNOUNCE_STATISTICS;
	public static int L2NETWORK_VOTE_STEP;
	public static Map<Integer, List<Integer[]>> L2NETWORK_GLOBAL_REWARDS = new HashMap<>();
	// set l2network individual variables
	public static boolean L2NETWORK_INDIVIDUAL_REWARD;
	public static Map<Integer, List<Integer[]>> L2NETWORK_INDIVIDUAL_REWARDS = new HashMap<>();

	// set l2topservers global reward variables
	public static boolean L2TOPSERVERS_GLOBAL_REWARD;
	public static String L2TOPSERVERS_API_KEY;
	public static String L2TOPSERVERS_SERVER_ID;
	public static String L2TOPSERVERS_SERVER_NAME;
	public static int L2TOPSERVERS_VOTE_CHECK_DELAY;
	public static boolean L2TOPSERVERS_ANNOUNCE_STATISTICS;
	public static int L2TOPSERVERS_VOTE_STEP;
	public static Map<Integer, List<Integer[]>> L2TOPSERVERS_GLOBAL_REWARDS = new HashMap<>();
	// set l2topservers individual variables
	public static boolean L2TOPSERVERS_INDIVIDUAL_REWARD;
	public static Map<Integer, List<Integer[]>> L2TOPSERVERS_INDIVIDUAL_REWARDS = new HashMap<>();

	// set l2votes global reward variables
	public static boolean L2VOTES_GLOBAL_REWARD;
	public static String L2VOTES_API_KEY;
	public static int L2VOTES_VOTE_CHECK_DELAY;
	public static boolean L2VOTES_ANNOUNCE_STATISTICS;
	public static int L2VOTES_VOTE_STEP;
	public static Map<Integer, List<Integer[]>> L2VOTES_GLOBAL_REWARDS = new HashMap<>();
	// set l2topservers individual variables
	public static boolean L2VOTES_INDIVIDUAL_REWARD;
	public static Map<Integer, List<Integer[]>> L2VOTES_INDIVIDUAL_REWARDS = new HashMap<>();

	/**
	 * load config variables
	 */
	public static void load()
	{
		// load configuration file
		Properties ep = initProperties("./config/VDSystem.properties");

		// debug messages
		DEBUG = Boolean.parseBoolean(ep.getProperty("VDS_DEBUG", "false"));

		// set console variables
		ITOPZ_CONSOLE_ENABLE = Boolean.parseBoolean(ep.getProperty("ConsoleEnable", "true"));
		ITOPZ_CONSOLE_FONT = ep.getProperty("ConsoleFont", "Arial");
		ITOPZ_CONSOLE_SIZE = Integer.parseInt(ep.getProperty("ConsoleFontSize", "12"));
		ITOPZ_CONSOLE_COLOR_R = Integer.parseInt(ep.getProperty("ConsoleColorR", "204"));
		ITOPZ_CONSOLE_COLOR_G = Integer.parseInt(ep.getProperty("ConsoleColorG", "238"));
		ITOPZ_CONSOLE_COLOR_B = Integer.parseInt(ep.getProperty("ConsoleColorB", "241"));
		ITOPZ_CONSOLE_WIDTH = Integer.parseInt(ep.getProperty("ConsoleWidth", "400"));
		ITOPZ_CONSOLE_HEIGHT = Integer.parseInt(ep.getProperty("ConsoleHeight", "350"));

		// set donate manager variables
		ITOPZ_DONATE_MANAGER = Boolean.parseBoolean(ep.getProperty("DonateManager", "true"));

		// set itopz global reward variables
		ITOPZ_GLOBAL_REWARD = Boolean.parseBoolean(ep.getProperty("iTopZGlobalVoteReward", "false"));
		ITOPZ_SERVER_ID = Integer.parseInt(ep.getProperty("ServerID", "325339"));
		ITOPZ_SERVER_API_KEY = ep.getProperty("iTopZApiKey", "DEMO");
		ITOPZ_ANNOUNCE_STATISTICS = Boolean.parseBoolean(ep.getProperty("iTopZAnnounceStatistics", "false"));
		ITOPZ_VOTE_CHECK_DELAY = Long.parseLong(ep.getProperty("iTopZCheckDelay", "10"));
		ITOPZ_VOTE_STEP = Integer.parseInt(ep.getProperty("iTopZVoteStep", "20"));
		for (String global : ep.getProperty("iTopZGlobalRewards", "57,20000000-50000000-100;57,20000000-50000000-100").split(";"))
		{
			String[] global_split = global.split(";");
			for (String split : global_split)
			{
				String[] parts = split.split(",");
				// Start Join the map
				List<Integer[]> temp = new ArrayList<>();
				// Min-Max-Chance
				temp.add(new Integer[]
				{
				Integer.parseInt(parts[1].split("-")[0]),
				Integer.parseInt(parts[1].split("-")[1]),
				Integer.parseInt(parts[1].split("-")[2]),
				});
				ITOPZ_GLOBAL_REWARDS.put(Integer.parseInt(parts[0]), temp);
			}
		}
		// set itopz individual variables
		ITOPZ_INDIVIDUAL_REWARD = Boolean.parseBoolean(ep.getProperty("iTopZIndividualReward", "false"));
		for (String individual : ep.getProperty("iTopZIndividualRewards", "57,20000000-50000000-100;57,20000000-50000000-100").split(";"))
		{
			String[] individual_split = individual.split(";");
			for (String split : individual_split)
			{
				String[] parts = split.split(",");
				// Start Join the map
				List<Integer[]> temp = new ArrayList<>();
				// Min-Max-Chance
				temp.add(new Integer[]
				{
				Integer.parseInt(parts[1].split("-")[0]),
				Integer.parseInt(parts[1].split("-")[1]),
				Integer.parseInt(parts[1].split("-")[2]),
				});
				ITOPZ_INDIVIDUAL_REWARDS.put(Integer.parseInt(parts[0]), temp);
			}
		}

		// set hopzone global reward variables
		HOPZONE_GLOBAL_REWARD = Boolean.parseBoolean(ep.getProperty("HopzoneGlobalVoteReward", "false"));
		HOPZONE_SERVER_API_KEY = ep.getProperty("HopzoneApiKey", "DEMO");
		HOPZONE_ANNOUNCE_STATISTICS = Boolean.parseBoolean(ep.getProperty("HopzoneAnnounceStatistics", "false"));
		HOPZONE_VOTE_CHECK_DELAY = Long.parseLong(ep.getProperty("HopzoneCheckDelay", "10"));
		HOPZONE_VOTE_STEP = Integer.parseInt(ep.getProperty("HopzoneVoteStep", "20"));
		for (String global : ep.getProperty("HopzoneGlobalRewards", "57,20000000-50000000-100;57,20000000-50000000-100").split(";"))
		{
			String[] global_split = global.split(";");
			for (String split : global_split)
			{
				String[] parts = split.split(",");
				// Start Join the map
				List<Integer[]> temp = new ArrayList<>();
				// Min-Max-Chance
				temp.add(new Integer[]
				{
				Integer.parseInt(parts[1].split("-")[0]),
				Integer.parseInt(parts[1].split("-")[1]),
				Integer.parseInt(parts[1].split("-")[2]),
				});
				HOPZONE_GLOBAL_REWARDS.put(Integer.parseInt(parts[0]), temp);
			}
		}
		// set hopzone individual variables
		HOPZONE_INDIVIDUAL_REWARD = Boolean.parseBoolean(ep.getProperty("HopzoneIndividualReward", "false"));
		for (String individual : ep.getProperty("HopzoneIndividualRewards", "57,20000000-50000000-100;57,20000000-50000000-100").split(";"))
		{
			String[] individual_split = individual.split(";");
			for (String split : individual_split)
			{
				String[] parts = split.split(",");
				// Start Join the map
				List<Integer[]> temp = new ArrayList<>();
				// Min-Max-Chance
				temp.add(new Integer[]
				{
				Integer.parseInt(parts[1].split("-")[0]),
				Integer.parseInt(parts[1].split("-")[1]),
				Integer.parseInt(parts[1].split("-")[2]),
				});
				HOPZONE_INDIVIDUAL_REWARDS.put(Integer.parseInt(parts[0]), temp);
			}
		}

		// set l2topgameserver global reward variables
		L2TOPGAMESERVER_GLOBAL_REWARD = Boolean.parseBoolean(ep.getProperty("L2TopGSGlobalVoteReward", "false"));
		L2TOPGAMESERVER_API_KEY = ep.getProperty("L2TopGSApiKey", "DEMO");
		L2TOPGAMESERVER_ANNOUNCE_STATISTICS = Boolean.parseBoolean(ep.getProperty("L2TopGSAnnounceStatistics", "false"));
		L2TOPGAMESERVER_VOTE_CHECK_DELAY = Integer.parseInt(ep.getProperty("L2TopGSCheckDelay", "10"));
		L2TOPGAMESERVER_VOTE_STEP = Integer.parseInt(ep.getProperty("L2TopGSVoteStep", "20"));
		for (String global : ep.getProperty("L2TopGSGlobalRewards", "57,20000000-50000000-100;57,20000000-50000000-100").split(";"))
		{
			String[] global_split = global.split(";");
			for (String split : global_split)
			{
				String[] parts = split.split(",");
				// Start Join the map
				List<Integer[]> temp = new ArrayList<>();
				// Min-Max-Chance
				temp.add(new Integer[]
				{
				Integer.parseInt(parts[1].split("-")[0]),
				Integer.parseInt(parts[1].split("-")[1]),
				Integer.parseInt(parts[1].split("-")[2]),
				});
				L2TOPGAMESERVER_GLOBAL_REWARDS.put(Integer.parseInt(parts[0]), temp);
			}
		}
		// set l2topgameserver individual variables
		L2TOPGAMESERVER_INDIVIDUAL_REWARD = Boolean.parseBoolean(ep.getProperty("L2TopGSIndividualReward", "false"));
		for (String individual : ep.getProperty("L2TopGSIndividualRewards", "57,20000000-50000000-100;57,20000000-50000000-100").split(";"))
		{
			String[] individual_split = individual.split(";");
			for (String split : individual_split)
			{
				String[] parts = split.split(",");
				// Start Join the map
				List<Integer[]> temp = new ArrayList<>();
				// Min-Max-Chance
				temp.add(new Integer[]
				{
				Integer.parseInt(parts[1].split("-")[0]),
				Integer.parseInt(parts[1].split("-")[1]),
				Integer.parseInt(parts[1].split("-")[2]),
				});
				L2TOPGAMESERVER_INDIVIDUAL_REWARDS.put(Integer.parseInt(parts[0]), temp);
			}
		}

		// set l2jbrasil global reward variables
		L2JBRASIL_GLOBAL_REWARD = Boolean.parseBoolean(ep.getProperty("L2JBrasilGlobalVoteReward", "false"));
		L2JBRASIL_USER_NAME = ep.getProperty("L2JBrasilUserName", "DEMO");
		L2JBRASIL_ANNOUNCE_STATISTICS = Boolean.parseBoolean(ep.getProperty("L2JBrasilAnnounceStatistics", "false"));
		L2JBRASIL_VOTE_CHECK_DELAY = Integer.parseInt(ep.getProperty("L2JBrasilCheckDelay", "10"));
		L2JBRASIL_VOTE_STEP = Integer.parseInt(ep.getProperty("L2JBrasilVoteStep", "20"));
		for (String global : ep.getProperty("L2JBrasilGlobalRewards", "57,20000000-50000000-100;57,20000000-50000000-100").split(";"))
		{
			String[] global_split = global.split(";");
			for (String split : global_split)
			{
				String[] parts = split.split(",");
				// Start Join the map
				List<Integer[]> temp = new ArrayList<>();
				// Min-Max-Chance
				temp.add(new Integer[]
				{
				Integer.parseInt(parts[1].split("-")[0]),
				Integer.parseInt(parts[1].split("-")[1]),
				Integer.parseInt(parts[1].split("-")[2]),
				});
				L2JBRASIL_GLOBAL_REWARDS.put(Integer.parseInt(parts[0]), temp);
			}
		}
		// set l2jbrasil individual variables
		L2JBRASIL_INDIVIDUAL_REWARD = Boolean.parseBoolean(ep.getProperty("L2JBrasilIndividualReward", "false"));
		for (String individual : ep.getProperty("L2JBrasilIndividualRewards", "57,20000000-50000000-100;57,20000000-50000000-100").split(";"))
		{
			String[] individual_split = individual.split(";");
			for (String split : individual_split)
			{
				String[] parts = split.split(",");
				// Start Join the map
				List<Integer[]> temp = new ArrayList<>();
				// Min-Max-Chance
				temp.add(new Integer[]
				{
				Integer.parseInt(parts[1].split("-")[0]),
				Integer.parseInt(parts[1].split("-")[1]),
				Integer.parseInt(parts[1].split("-")[2]),
				});
				L2JBRASIL_INDIVIDUAL_REWARDS.put(Integer.parseInt(parts[0]), temp);
			}
		}

		// set l2network global reward variables
		L2NETWORK_GLOBAL_REWARD = Boolean.parseBoolean(ep.getProperty("L2NetworkGlobalVoteReward", "false"));
		L2NETWORK_API_KEY = ep.getProperty("L2NetworkApiKey", "Hi");
		L2NETWORK_USER_NAME = ep.getProperty("L2NetworkUserName", "Nope");
		L2NETWORK_ANNOUNCE_STATISTICS = Boolean.parseBoolean(ep.getProperty("L2NetworkAnnounceStatistics", "false"));
		L2NETWORK_VOTE_CHECK_DELAY = Integer.parseInt(ep.getProperty("L2NetworkCheckDelay", "10"));
		L2NETWORK_VOTE_STEP = Integer.parseInt(ep.getProperty("L2NetworkVoteStep", "20"));
		for (String global : ep.getProperty("L2NetworkGlobalRewards", "57,20000000-50000000-100;57,20000000-50000000-100").split(";"))
		{
			String[] global_split = global.split(";");
			for (String split : global_split)
			{
				String[] parts = split.split(",");
				// Start Join the map
				List<Integer[]> temp = new ArrayList<>();
				// Min-Max-Chance
				temp.add(new Integer[]
				{
				Integer.parseInt(parts[1].split("-")[0]),
				Integer.parseInt(parts[1].split("-")[1]),
				Integer.parseInt(parts[1].split("-")[2]),
				});
				L2NETWORK_GLOBAL_REWARDS.put(Integer.parseInt(parts[0]), temp);
			}
		}
		// set l2network individual variables
		L2NETWORK_INDIVIDUAL_REWARD = Boolean.parseBoolean(ep.getProperty("L2NetworkIndividualReward", "false"));
		for (String individual : ep.getProperty("L2NetworkIndividualRewards", "57,20000000-50000000-100;57,20000000-50000000-100").split(";"))
		{
			String[] individual_split = individual.split(";");
			for (String split : individual_split)
			{
				String[] parts = split.split(",");
				// Start Join the map
				List<Integer[]> temp = new ArrayList<>();
				// Min-Max-Chance
				temp.add(new Integer[]
				{
				Integer.parseInt(parts[1].split("-")[0]),
				Integer.parseInt(parts[1].split("-")[1]),
				Integer.parseInt(parts[1].split("-")[2]),
				});
				L2NETWORK_INDIVIDUAL_REWARDS.put(Integer.parseInt(parts[0]), temp);
			}
		}

		// set l2topservers global reward variables
		L2TOPSERVERS_GLOBAL_REWARD = Boolean.parseBoolean(ep.getProperty("L2TopServersGlobalVoteReward", "false"));
		L2TOPSERVERS_API_KEY = ep.getProperty("L2TopServersApiKey", "Hi");
		L2TOPSERVERS_SERVER_ID = ep.getProperty("L2TopServersServerId", "Hi");
		L2TOPSERVERS_SERVER_NAME = ep.getProperty("L2TopServersServerName", "Hi");
		L2TOPSERVERS_ANNOUNCE_STATISTICS = Boolean.parseBoolean(ep.getProperty("L2TopServersAnnounceStatistics", "false"));
		L2TOPSERVERS_VOTE_CHECK_DELAY = Integer.parseInt(ep.getProperty("L2TopServersCheckDelay", "10"));
		L2TOPSERVERS_VOTE_STEP = Integer.parseInt(ep.getProperty("L2TopServersVoteStep", "20"));
		for (String global : ep.getProperty("L2TopServersGlobalRewards", "57,20000000-50000000-100;57,20000000-50000000-100").split(";"))
		{
			String[] global_split = global.split(";");
			for (String split : global_split)
			{
				String[] parts = split.split(",");
				// Start Join the map
				List<Integer[]> temp = new ArrayList<>();
				// Min-Max-Chance
				temp.add(new Integer[]
				{
				Integer.parseInt(parts[1].split("-")[0]),
				Integer.parseInt(parts[1].split("-")[1]),
				Integer.parseInt(parts[1].split("-")[2]),
				});
				L2TOPSERVERS_GLOBAL_REWARDS.put(Integer.parseInt(parts[0]), temp);
			}
		}
		// set l2network individual variables
		L2TOPSERVERS_INDIVIDUAL_REWARD = Boolean.parseBoolean(ep.getProperty("L2TopServersIndividualReward", "false"));
		for (String individual : ep.getProperty("L2TopServersIndividualRewards", "57,20000000-50000000-100;57,20000000-50000000-100").split(";"))
		{
			String[] individual_split = individual.split(";");
			for (String split : individual_split)
			{
				String[] parts = split.split(",");
				// Start Join the map
				List<Integer[]> temp = new ArrayList<>();
				// Min-Max-Chance
				temp.add(new Integer[]
				{
				Integer.parseInt(parts[1].split("-")[0]),
				Integer.parseInt(parts[1].split("-")[1]),
				Integer.parseInt(parts[1].split("-")[2]),
				});
				L2TOPSERVERS_INDIVIDUAL_REWARDS.put(Integer.parseInt(parts[0]), temp);
			}
		}

		// set l2votes global reward variables
		L2VOTES_GLOBAL_REWARD = Boolean.parseBoolean(ep.getProperty("L2VotesGlobalVoteReward", "false"));
		L2VOTES_API_KEY = ep.getProperty("L2VotesApiKey", "Hi");
		L2VOTES_ANNOUNCE_STATISTICS = Boolean.parseBoolean(ep.getProperty("L2VotesAnnounceStatistics", "false"));
		L2VOTES_VOTE_CHECK_DELAY = Integer.parseInt(ep.getProperty("L2VotesCheckDelay", "10"));
		L2VOTES_VOTE_STEP = Integer.parseInt(ep.getProperty("L2VotesVoteStep", "20"));
		for (String global : ep.getProperty("L2VotesGlobalRewards", "57,20000000-50000000-100;57,20000000-50000000-100").split(";"))
		{
			String[] global_split = global.split(";");
			for (String split : global_split)
			{
				String[] parts = split.split(",");
				// Start Join the map
				List<Integer[]> temp = new ArrayList<>();
				// Min-Max-Chance
				temp.add(new Integer[]
				{
				Integer.parseInt(parts[1].split("-")[0]),
				Integer.parseInt(parts[1].split("-")[1]),
				Integer.parseInt(parts[1].split("-")[2]),
				});
				L2VOTES_GLOBAL_REWARDS.put(Integer.parseInt(parts[0]), temp);
			}
		}
		// set l2votes individual variables
		L2VOTES_INDIVIDUAL_REWARD = Boolean.parseBoolean(ep.getProperty("L2VotesIndividualReward", "false"));
		for (String individual : ep.getProperty("L2VotesIndividualRewards", "57,20000000-50000000-100;57,20000000-50000000-100").split(";"))
		{
			String[] individual_split = individual.split(";");
			for (String split : individual_split)
			{
				String[] parts = split.split(",");
				// Start Join the map
				List<Integer[]> temp = new ArrayList<>();
				// Min-Max-Chance
				temp.add(new Integer[]
				{
				Integer.parseInt(parts[1].split("-")[0]),
				Integer.parseInt(parts[1].split("-")[1]),
				Integer.parseInt(parts[1].split("-")[2]),
				});
				L2VOTES_INDIVIDUAL_REWARDS.put(Integer.parseInt(parts[0]), temp);
			}
		}

		_log.info(Configurations.class.getSimpleName() + ": loaded.");
	}

	/**
	 * try to load itopz.properties
	 *
	 * @return result String
	 */
	private static Properties initProperties(String file)
	{
		Properties result = new Properties();
		try (InputStream is = new FileInputStream(new File(file)))
		{
			result.load(is);
		} catch (final IOException e)
		{
			_log.warn(Configurations.class.getSimpleName() + ": Error loading " + file + " config.");
		}

		return result;
	}
}