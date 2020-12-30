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

import itopz.com.gui.Gui;
import net.sf.l2j.L2DatabaseFactory;
import net.sf.l2j.gameserver.Announcements;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;

import java.awt.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
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
public class Utilities
{
    // logger
    private static final Logger _log = Logger.getLogger(Utilities.class.getName());

    private static final String CREATE_TABLE = "CREATE TABLE donate_holder (" +
            "  no int(11) NOT NULL AUTO_INCREMENT," +
            "  id int(11) NOT NULL," +
            "  count int(11) NOT NULL," +
            "  playername varchar(255) CHARACTER SET utf8mb4 NOT NULL," +
            "  order_status varchar(255) DEFAULT '1'," +
            "  PRIMARY KEY (no)" +
            ") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;";
    private static final String DELETE_TABLE = "DROP TABLE IF EXISTS donate_holder;";
    private static final String INDIVIDUAL_VAR_INSERT = "REPLACE INTO character_quests (char_id,name,var,value) VALUES (?,?,?,?)";
    private static final String INDIVIDUAL_VAR_SELECT = "SELECT char_id,name,var,value FROM character_quests WHERE char_id=? AND name=?";
    private static final String GLOBAL_VAR_SELECT = "SELECT value FROM quest_global_data WHERE quest_name = ? AND var = ?";
    private static final String GLOBAL_VAR_REPLACE = "REPLACE INTO quest_global_data (quest_name,var,value) VALUES (?,?,?)";

    /**
     * announce to all players
     *
     * @param message String
     */
    public static void announce(String message)
    {
        Announcements.getInstance().announceToAll("[iTopZ]" + message);
    }

    /**
     * open new url on browser
     *
     * @param URL string
     */
    public static void openUrl(String URL)
    {
        final Desktop desktop = Desktop.getDesktop();

        try
        {
            desktop.browse(new URI(URL));
        }
        catch (IOException | URISyntaxException error)
        {
            error.printStackTrace();
        }
    }

    /**
     * Delete Donate Table
     */
    public static void deleteTable()
    {
        try (Connection con = L2DatabaseFactory.getInstance().getConnection();
             PreparedStatement statement = con.prepareStatement(DELETE_TABLE))
        {
            statement.execute();
        }
        catch (SQLException e)
        {
            Gui.getInstance().ConsoleWrite("Delete Donate Table Failed: " + e.getMessage());
        }

        Gui.getInstance().ConsoleWrite("Delete Donate Table successfully!");
    }

    /**
     * Create Donate Table
     */
    public static void createTable()
    {
        try (Connection con = L2DatabaseFactory.getInstance().getConnection();
             PreparedStatement statement = con.prepareStatement(CREATE_TABLE))
        {
            statement.execute();
        }
        catch (SQLException e)
        {
            Gui.getInstance().ConsoleWrite("Installed Donate Table Failed: " + e.getMessage());
        }

        Gui.getInstance().ConsoleWrite("Installed Donate Table successfully!");
    }

    /**
     * create individual variable in database
     *
     * @param player object
     * @param site string
     * @param var string
     * @param value long
     */
    public static void saveIndividualVar(L2PcInstance player, String site, String var, Long value)
    {
        try (Connection con = L2DatabaseFactory.getInstance().getConnection();
             PreparedStatement statement = con.prepareStatement(INDIVIDUAL_VAR_INSERT))
        {
            statement.setInt(1, player.getObjectId());
            statement.setString(2, site);
            statement.setString(3, var);
            statement.setString(4, String.valueOf(value));
            statement.execute();
        }
        catch (Exception e)
        {
            _log.warning("could not insert char var: " + e.getMessage());
        }
    }

    /**
     * select individual variable from database
     *
     * @param player object
     * @param var string
     * @return long
     */
    public static long selectIndividualVar(L2PcInstance player, String var)
    {
        long value = -1;
        try (Connection con = L2DatabaseFactory.getInstance().getConnection();
             PreparedStatement statement = con.prepareStatement(INDIVIDUAL_VAR_SELECT))
        {
            statement.setInt(1, player.getObjectId());
            statement.setString(2, var);
            statement.execute();
            try (ResultSet rs = statement.executeQuery())
            {
                while (rs.next())
                {
                    value = rs.getLong("value");
                }
            }
        }
        catch (Exception e)
        {
            _log.warning("could not select char var: " + e.getMessage());
        }
        return value;
    }

    /**
     * save global variable
     *
     * @param name string
     * @param var string
     * @param value int
     */
    public static void saveGlobalVar(String name, String var, int value)
    {
        try (Connection con = L2DatabaseFactory.getInstance().getConnection();
             PreparedStatement statement = con.prepareStatement(GLOBAL_VAR_REPLACE))
        {
            statement.setString(1, name);
            statement.setString(2, var);
            statement.setString(3, String.valueOf(value));
            statement.executeUpdate();
        }
        catch (Exception e)
        {
            _log.warning("could not insert global variable:" + e.getMessage());
        }
    }

    /**
     * select global variable
     *
     * @param name string
     * @param var string
     * @return int
     */
    public static int selectGlobalVar(String name, String var)
    {
        int result = -1;
        try (Connection con = L2DatabaseFactory.getInstance().getConnection();
             PreparedStatement statement = con.prepareStatement(GLOBAL_VAR_SELECT))
        {
            statement.setString(1, name);
            statement.setString(2, var);
            try (ResultSet rs = statement.executeQuery())
            {
                if (rs.first())
                    result = rs.getInt("value");
            }
        }
        catch (Exception e)
        {
            _log.warning("could not load global variable:" + e.getMessage());
        }
        return result;
    }

    /**
     * Return date time thanks Rationale for pm/am fix
     *
     * @param millisecond long
     * @return SimpleDateFormat object
     */
    public static String formatMillisecond(final long millisecond)
    {
        return new SimpleDateFormat("hh:mm:ss a", Locale.ENGLISH).format(new Date(millisecond));
    }

    /**
     * Check if the address given is local
     *
     * @param address string
     * @return boolean
     */
    public static boolean localIp(InetAddress address)
    {
        return address == null || address.isLinkLocalAddress() || address.isLoopbackAddress() || address.isAnyLocalAddress() || address.isSiteLocalAddress();
    }
}
