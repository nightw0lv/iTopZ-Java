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
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.entity.Announcements;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Locale;

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
public class Utilities
{
	public static final String CREATE_DONATE_TABLE = "CREATE TABLE donate_holder (" +
	"  no int(11) NOT NULL AUTO_INCREMENT," +
	"  id int(11) NOT NULL," +
	"  count bigint(20) NOT NULL," +
	"  playername varchar(255) NOT NULL," +
	"  order_status varchar(255) DEFAULT '1'," +
	"  PRIMARY KEY (no)" +
	") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;";
	public static final String CREATE_INDIVIDUAL_TABLE = "CREATE TABLE vds_individual (" +
	"id int(11) NOT NULL AUTO_INCREMENT," +
	"topsite enum('ITOPZ','HOPZONE','L2NETWORK','L2JBRASIL','L2TOPGAMESERVER','L2VOTES','L2TOPSERVERS') NOT NULL," +
	"var varchar(255) NOT NULL," +
	"value bigint(20) NOT NULL," +
	"ip varchar(65) NOT NULL," +
	"PRIMARY KEY (id)" +
	") ENGINE=InnoDB DEFAULT CHARSET=latin1;";
	public static final String CREATE_GLOBAL_TABLE = "CREATE TABLE vds_global (" +
	"topsite enum('ITOPZ','HOPZONE','L2NETWORK','L2JBRASIL','L2TOPGAMESERVER','L2VOTES','L2TOPSERVERS') NOT NULL," +
	"var varchar(255) NOT NULL," +
	"value bigint(20) NOT NULL," +
	"PRIMARY KEY (topsite)" +
	") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;";
	public static final String DELETE_DONATE_TABLE = "DROP TABLE IF EXISTS donate_holder;";
	private static final String DELETE_INDIVIDUAL_TABLE = "DROP TABLE IF EXISTS vds_individual;";
	private static final String DELETE_GLOBAL_TABLE = "DROP TABLE IF EXISTS vds_global;";
	private static final String INDIVIDUAL_INSERT = "INSERT INTO vds_individual (topsite, var, value, ip) VALUES (?,?,?,?);";
	private static final String INDIVIDUAL_VAR_SELECT = "SELECT value FROM vds_individual WHERE topsite=? AND var=? AND ip=?;";
	private static final String INDIVIDUAL_IP_SELECT = "SELECT topsite,var,value,ip FROM vds_individual WHERE topsite=? AND var=? AND ip=? AND value > (UNIX_TIMESTAMP() * 1000);";
	private static final String GLOBAL_VAR_SELECT = "SELECT value FROM vds_global WHERE topsite=? AND var=?";
	private static final String GLOBAL_VAR_REPLACE = "INSERT INTO vds_global (topsite,var,value) VALUES (?,?,?) ON DUPLICATE KEY UPDATE value=VALUES(value)";

	/**
	 * announce to all players
	 *
	 * @param message String
	 */
	public static void announce(String topsite, String message)
	{
		Announcements.getInstance().announceToAll("[" + topsite + "]" + message);
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
		} catch (IOException | URISyntaxException error)
		{
			error.printStackTrace();
		}
	}

	/**
	 * Delete Donate Table
	 */
	public static void deleteTable(final String QUERY, final String TABLE)
	{
		try (Connection con = DatabaseFactory.getConnection();
		     PreparedStatement statement = con.prepareStatement(QUERY))
		{
			statement.execute();
		} catch (SQLException e)
		{
			Gui.getInstance().ConsoleWrite("Delete " + TABLE + " Table Failed: " + e.getMessage());
		}

		Gui.getInstance().ConsoleWrite("Delete " + TABLE + " Table successfully!");
	}

	/**
	 * Create Donate Table
	 */
	public static void createTable(final String QUERY, final String TABLE)
	{
		try (Connection con = DatabaseFactory.getConnection();
		     PreparedStatement statement = con.prepareStatement(QUERY))
		{
			statement.execute();
		} catch (SQLException e)
		{
			Gui.getInstance().ConsoleWrite("Installed " + TABLE + " Table Failed: " + e.getMessage());
		}

		Gui.getInstance().ConsoleWrite("Installed " + TABLE + " Table successfully!");
	}

	/**
	 * create individual variable in database
	 *
	 * @param topsite string
	 * @param var     string
	 * @param value   long
	 * @param IP      string
	 */
	public static void saveIndividualVar(final String topsite, final String var, final long value, final String IP)
	{
		try (Connection con = DatabaseFactory.getConnection();
		     PreparedStatement statement = con.prepareStatement(INDIVIDUAL_INSERT))
		{
			statement.setString(1, topsite);
			statement.setString(2, var);
			statement.setString(3, String.valueOf(value));
			statement.setString(4, IP);
			statement.execute();
		} catch (Exception e)
		{
			final String error = e.getMessage();
			if (error != null)
			{
				Gui.getInstance().ConsoleWrite("could not insert char var: " + error);

				if (error.contains("doesn't exist") && error.contains("vds_individual"))
				{
					deleteTable(DELETE_INDIVIDUAL_TABLE, "vds_individual");
					createTable(CREATE_INDIVIDUAL_TABLE, "vds_individual");
				}
			}
		}
	}

	/**
	 * select individual ip from database
	 *
	 * @param topsite string
	 * @param var     string
	 * @param IP      string
	 * @return long
	 */
	public static boolean selectIndividualIP(final String topsite, final String var, final String IP)
	{
		boolean found = false;
		try (Connection con = DatabaseFactory.getConnection();
		     PreparedStatement statement = con.prepareStatement(INDIVIDUAL_IP_SELECT))
		{
			statement.setString(1, topsite);
			statement.setString(2, var);
			statement.setString(3, IP);
			statement.execute();
			try (ResultSet rs = statement.executeQuery())
			{
				while (rs.next())
				{
					found = true;
				}
			}
		} catch (Exception e)
		{
			final String error = e.getMessage();
			if (error != null)
			{
				Gui.getInstance().ConsoleWrite("could not select char var: " + error);

				if ((error.contains("doesn't exist") && error.contains("vds_individual")) || error.contains("Unknown column 'ip'"))
				{
					deleteTable(DELETE_INDIVIDUAL_TABLE, "vds_individual");
					createTable(CREATE_INDIVIDUAL_TABLE, "vds_individual");
				}
			}
		}
		return found;
	}

	/**
	 * select individual variable from database
	 *
	 * @param topsite string
	 * @param var     string
     * @param IP      string
	 * @return long
	 */
	public static long selectIndividualVar(final String topsite, final String var, final String IP)
	{
		long value = -1;
		try (Connection con = DatabaseFactory.getConnection();
		     PreparedStatement statement = con.prepareStatement(INDIVIDUAL_VAR_SELECT))
		{
			statement.setString(1, topsite);
			statement.setString(2, var);
			statement.setString(3, IP);
			statement.execute();
			try (ResultSet rs = statement.executeQuery())
			{
				while (rs.next())
				{
					value = rs.getLong("value");
				}
			}
		} catch (Exception e)
		{
			final String error = e.getMessage();
			if (error != null)
			{
				Gui.getInstance().ConsoleWrite("could not select char var: " + error);

				if (error.contains("doesn't exist") && error.contains("vds_individual"))
				{
					deleteTable(DELETE_INDIVIDUAL_TABLE, "vds_individual");
					createTable(CREATE_INDIVIDUAL_TABLE, "vds_individual");
				}
			}
		}
		return value;
	}

	/**
	 * save global variable
	 *
	 * @param topsite string
	 * @param var     string
	 * @param value   int
	 */
	public static void saveGlobalVar(final String topsite, final String var, final int value)
	{
		try (Connection con = DatabaseFactory.getConnection();
		     PreparedStatement statement = con.prepareStatement(GLOBAL_VAR_REPLACE))
		{
			statement.setString(1, topsite);
			statement.setString(2, var);
			statement.setString(3, String.valueOf(value));
			statement.executeUpdate();
		} catch (Exception e)
		{
			final String error = e.getMessage();
			if (error != null)
			{
				Gui.getInstance().ConsoleWrite("could not insert global variable:" + error);

				if (error.contains("doesn't exist") && error.contains("vds_global"))
				{
					deleteTable(DELETE_GLOBAL_TABLE, "vds_global");
					createTable(CREATE_GLOBAL_TABLE, "vds_global");
				}
			}
		}
	}

	/**
	 * select global variable
	 *
	 * @param topsite string
	 * @param var     string
	 * @return int
	 */
	public static int selectGlobalVar(final String topsite, final String var)
	{
		int result = -1;
		try (Connection con = DatabaseFactory.getConnection();
		     PreparedStatement statement = con.prepareStatement(GLOBAL_VAR_SELECT))
		{
			statement.setString(1, topsite);
			statement.setString(2, var);
			try (ResultSet rs = statement.executeQuery())
			{
				if (rs.first())
					result = rs.getInt("value");
			}
		} catch (Exception e)
		{
			final String error = e.getMessage();
			if (error != null)
			{
				Gui.getInstance().ConsoleWrite("could not load global variable:" + error);

				if (error.contains("doesn't exist") && error.contains("vds_global"))
				{
					deleteTable(DELETE_GLOBAL_TABLE, "vds_global");
					createTable(CREATE_GLOBAL_TABLE, "vds_global");
				}
			}
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

	/**
	 * Convert string datetime to long milliseconds
	 *
	 * @param ServerTime string
	 * @param TimeZone   string
	 * @return long
	 */
	public static long millisecondsFromString(String ServerTime, String TimeZone)
	{
		if (ServerTime == null || TimeZone == null)
			return -4;
		try
		{
			LocalDateTime localDateTime = LocalDateTime.parse(ServerTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
			return localDateTime.atZone(ZoneId.of(TimeZone)).toInstant().toEpochMilli();
		} catch (DateTimeParseException dtpe)
		{
			dtpe.getMessage();
		}
		return -3;
	}

	/**
	 * get external ip for vote debugging
	 *
	 * @return ip string
	 */
	public static String getMyIP()
	{
		String ip = Random.get(0, 254) + "." + Random.get(0, 254) + "." + Random.get(0, 254) + "." + Random.get(0, 254);
		try (BufferedReader in = new BufferedReader(new InputStreamReader(new URL("http://checkip.amazonaws.com").openStream())))
		{
			ip = in.readLine();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return ip;
	}
}