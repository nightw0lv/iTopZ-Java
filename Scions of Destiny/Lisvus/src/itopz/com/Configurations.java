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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.logging.Logger;

/**
 * @Author Nightwolf
 * iToPz Discord: https://discord.gg/KkPms6B5aE
 * @Author Rationale
 * Base structure credits goes on Rationale Discord: Rationale#7773
 *
 * Vote Donation System
 * Script website: https://itopz.com/
 * Script version: 1.0
 * Pack Support: Lisvus Scions of Destiny rev 728
 *
 * Personal Donate Panels: https://www.denart-designs.com/
 * Free Donate panel: https://itopz.com/
 */
public class Configurations
{
    // logger
    private static final Logger _log = Logger.getLogger(Configurations.class.getName());

    // set console variables
    public static String ITOPZ_CONSOLE_FONT;
    public static int ITOPZ_CONSOLE_SIZE;
    public static int ITOPZ_CONSOLE_COLOR_R;
    public static int ITOPZ_CONSOLE_COLOR_G;
    public static int ITOPZ_CONSOLE_COLOR_B;
    public static int ITOPZ_CONSOLE_WIDTH;
    public static int ITOPZ_CONSOLE_HEIGHT;

    // set global reward variables
    public static boolean ITOPZ_GLOBAL_REWARD;
    public static int ITOPZ_SERVER_ID;
    public static String ITOPZ_SERVER_API_KEY;
    public static long ITOPZ_VOTE_CHECK_DELAY;
    public static boolean ITOPZ_ANNOUNCE_STATISTICS;
    public static int ITOPZ_VOTE_STEP;
    public static Map<Integer, List<Integer[]>> ITOPZ_GLOBAL_REWARDS = new HashMap<>();

    // set individual variables
    public static boolean ITOPZ_INDIVIDUAL_REWARD;
    public static Map<Integer, List<Integer[]>> ITOPZ_INDIVIDUAL_REWARDS = new HashMap<>();

    // set donate manager variables
    public static boolean ITOPZ_DONATE_MANAGER;

    /**
     * load config variables
     */
    public static void load()
    {
        // load configuration file
        Properties ep = initProperties("./config/VDSystem.properties");

        // set console variables
        ITOPZ_CONSOLE_FONT = ep.getProperty("ConsoleFont", "Arial");
        ITOPZ_CONSOLE_SIZE = Integer.parseInt(ep.getProperty("ConsoleFontSize", "12"));
        ITOPZ_CONSOLE_COLOR_R = Integer.parseInt(ep.getProperty("ConsoleColorR", "204"));
        ITOPZ_CONSOLE_COLOR_G = Integer.parseInt(ep.getProperty("ConsoleColorG", "238"));
        ITOPZ_CONSOLE_COLOR_B = Integer.parseInt(ep.getProperty("ConsoleColorB", "241"));
        ITOPZ_CONSOLE_WIDTH = Integer.parseInt(ep.getProperty("ConsoleWidth", "400"));
        ITOPZ_CONSOLE_HEIGHT = Integer.parseInt(ep.getProperty("ConsoleHeight", "350"));

        // set global reward variables
        ITOPZ_GLOBAL_REWARD = Boolean.parseBoolean(ep.getProperty("iTopZGlobalVoteReward", "false"));
        ITOPZ_SERVER_ID = Integer.parseInt(ep.getProperty("ServerID", "325339"));
        ITOPZ_SERVER_API_KEY = ep.getProperty("ApiKey", "DEMO");
        ITOPZ_ANNOUNCE_STATISTICS = Boolean.parseBoolean(ep.getProperty("AnnounceStatistics", "false"));
        ITOPZ_VOTE_CHECK_DELAY = Integer.parseInt(ep.getProperty("CheckDelay", "10"));
        ITOPZ_VOTE_STEP = Integer.parseInt(ep.getProperty("VoteStep", "20"));
        for (String global : ep.getProperty("GlobalRewards", "57,20000000-50000000-100;57,20000000-50000000-100").split(";"))
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

        // set individual variables
        ITOPZ_INDIVIDUAL_REWARD = Boolean.parseBoolean(ep.getProperty("IndividualReward", "false"));
        for (String individual : ep.getProperty("IndividualRewards", "57,20000000-50000000-100;57,20000000-50000000-100").split(";"))
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

        // set donate manager variables
        ITOPZ_DONATE_MANAGER = Boolean.parseBoolean(ep.getProperty("DonateManager", "true"));

        _log.info(Configurations.class.getSimpleName() + ": loaded.");
    }

    /**
     * try to load itopz.properties
     *
     * @param filename String
     * @return result String
     */
    private static Properties initProperties(String filename)
    {
        Properties result = new Properties();
        try (InputStream is = new FileInputStream(new File(filename)))
        {
            result.load(is);
        }
        catch (final IOException e)
        {
            _log.warning(Configurations.class.getSimpleName() + ": Error loading " + filename + " config.");
        }

        return result;
    }
}
