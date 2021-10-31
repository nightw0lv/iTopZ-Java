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
package itopz.com.donate;

import itopz.com.gui.Gui;
import itopz.com.util.Logs;
import itopz.com.util.Utilities;
import org.l2jmobius.commons.database.DatabaseFactory;
import org.l2jmobius.gameserver.datatables.ItemTable;
import org.l2jmobius.gameserver.model.World;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.holders.ItemHolder;
import org.l2jmobius.gameserver.model.items.Item;
import org.l2jmobius.gameserver.network.serverpackets.ActionFailed;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

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
public class DonateTaskManager implements Runnable
{
	// logger
	private static final Logs _log = new Logs(DonateTaskManager.class.getSimpleName());

	private final String DELETE = "DELETE FROM donate_holder WHERE no=? LIMIT 1";
	private final String SELECT = "SELECT no, id, count, playername FROM donate_holder";

	@Override
	public void run()
	{
		start();
	}

	/**
	 * reward player if donation is received
	 */
	private void start()
	{
		try (Connection con = DatabaseFactory.getConnection();
		     PreparedStatement statement = con.prepareStatement(SELECT);
		     ResultSet rset = statement.executeQuery())
		{
			while (rset.next())
			{
				final PlayerInstance player = World.getInstance().getPlayer(rset.getString("playername"));
				final int no = rset.getInt("no");
				final int id = rset.getInt("id");
				final int count = rset.getInt("count");

				Optional.ofNullable(player).ifPresent(s ->
				{
					if (removeDonation(no))
					{
						final Item item = ItemTable.getInstance().getTemplate(no);

						if (Objects.nonNull(item))
						{
							Gui.getInstance().ConsoleWrite("Donation: " + player.getName() + " received " + count + "x " + item.getName());
							player.addItem("Donation", id, count, player, true);
							player.sendPacket(ActionFailed.STATIC_PACKET);
						}
					}
				});
			}
		} catch (final Exception e)
		{
			String error = e.getMessage();
			_log.warn("Check donate items failed. " + error);

			if (error.contains("doesn't exist") && error.contains("donate_holder"))
			{
				Utilities.deleteTable(Utilities.DELETE_DONATE_TABLE, "Donate");
				Utilities.createTable(Utilities.CREATE_DONATE_TABLE, "Donate");
			}
		}
	}

	/**
	 * Remove donation from database
	 *
	 * @param id int
	 * @return boolean
	 */
	private boolean removeDonation(int id)
	{
		try (Connection con = DatabaseFactory.getConnection();
		     PreparedStatement statement = con.prepareStatement(DELETE))
		{
			statement.setInt(1, id);
			statement.execute();
			return true;
		} catch (SQLException e)
		{
			_log.warn("Failed to remove donation from database of donation id: " + id);
			_log.warn(e.getMessage());
		}

		return false;
	}
}