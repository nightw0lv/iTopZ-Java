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
import org.l2jmobius.commons.database.DatabaseFactory;
import org.l2jmobius.gameserver.model.entity.Announcements;

import java.awt.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @Author Nightwolf
 * iToPz Discord: https://discord.gg/KkPms6B5aE
 * @Author Rationale
 * Base structure credits goes on Rationale Discord: Rationale#7773
 *
 * Vote Donation System
 * Script website: https://itopz.com/
 * Script version: 1.0
 * Pack Support: Mobius C4 Scions of Destiny
 *
 * Personal Donate Panels: https://www.denart-designs.com/
 * Free Donate panel: https://itopz.com/
 */
public class Utilities
{
    private static final String CREATE_TABLE = "CREATE TABLE donate_holder (" +
            "  no int(11) NOT NULL AUTO_INCREMENT," +
            "  id int(11) NOT NULL," +
            "  count int(11) NOT NULL," +
            "  playername varchar(255) CHARACTER SET utf8mb4 NOT NULL," +
            "  order_status varchar(255) DEFAULT '1'," +
            "  PRIMARY KEY (no)" +
            ") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;";
    private static final String DELETE_TABLE = "DROP TABLE IF EXISTS donate_holder;";

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
        try (Connection con = DatabaseFactory.getConnection();
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
        try (Connection con = DatabaseFactory.getConnection();
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
     * Return date time thanks Rationale for pm/am fix
     *
     * @param millisecond long
     * @return SimpleDateFormat object
     */
    public static String formatMillisecond(final long millisecond)
    {
        return new SimpleDateFormat("hh:mm:ss a", Locale.ENGLISH).format(new Date(millisecond));
    }
}
