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
package itopz.com.model.entity;

import itopz.com.gui.Gui;
import itopz.com.Configurations;
import itopz.com.model.GlobalResponse;
import itopz.com.util.URL;
import itopz.com.util.Utilities;
import org.l2jmobius.commons.util.Rnd;
import org.l2jmobius.gameserver.datatables.ItemTable;
import org.l2jmobius.gameserver.instancemanager.GlobalVariablesManager;
import org.l2jmobius.gameserver.model.World;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.items.Item;

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
 * Pack Support: Mobius CT 2.4 Epilogue
 *
 * Personal Donate Panels: https://www.denart-designs.com/
 * Free Donate panel: https://itopz.com/
 */
public class ITOPZ_GLOBAL implements Runnable
{
	// logger
	private static final Logger _log = Logger.getLogger(ITOPZ_GLOBAL.class.getName());

	// global server vars
	private static int storedVotes, serverVotes, serverRank, serverNeededVotes, serverNextRank;
	private int responseCode;

	// ip array list
	private final List<String> fingerprint = new ArrayList<>();

	@Override
	public void run()
	{
		load();
	}

	/**
	 * Global reward main function
	 */
	public void load()
	{
		// set variables
		if (!getServerInfo())
		{
			// write on console
			Gui.getInstance().ConsoleWrite("ITOPZ Not responding maybe its the end of the world.");
			return;
		}

		// write console info from response
		Gui.getInstance().ConsoleWrite("Server Votes:" + serverVotes + " Rank:" + serverRank + " Next Rank(" + serverNextRank + ") need: " + serverNeededVotes + "votes.");
		Gui.getInstance().UpdateStats(serverVotes, serverRank, serverNextRank, serverNeededVotes);
		storedVotes = GlobalVariablesManager.getInstance().getInt("itopz_votes", -1);
		// check if default return value is -1 (failed)
		if (storedVotes == -1)
		{
			// re-set server votes
			Gui.getInstance().ConsoleWrite("ITOPZ recover votes.");
			// save votes
			GlobalVariablesManager.getInstance().set("itopz_votes", serverVotes);
			return;
		}

		// check stored votes are lower than server votes
		if (storedVotes < serverVotes)
		{
			// write on console
			Gui.getInstance().ConsoleWrite("ITOPZ update database");
			// save votes
			GlobalVariablesManager.getInstance().set("itopz_votes", storedVotes);
		}

		// monthly reset
		if (storedVotes > serverVotes)
		{
			// write on console
			Gui.getInstance().ConsoleWrite("ITOPZ monthly reset");
			// save votes
			GlobalVariablesManager.getInstance().set("itopz_votes", serverVotes);
		}

		// announce current votes
		if (Configurations.ITOPZ_ANNOUNCE_STATISTICS)
			Utilities.announce("Server Votes:" + serverVotes + " Rank:" + serverRank + " Next Rank(" + serverNextRank + ") need:" + serverNeededVotes + "votes");

		// check for vote step reward
		if (serverVotes >= storedVotes + Configurations.ITOPZ_VOTE_STEP)
		{
			// reward all online players
			reward();
			// announce the reward
			Utilities.announce("Thanks for voting! Players rewarded!");
			// save votes
			GlobalVariablesManager.getInstance().set("itopz_votes", serverVotes);
			// write on console
			Gui.getInstance().ConsoleWrite("Votes: Players rewarded!");
		}
		// announce next reward
		Utilities.announce("Next reward at " + (storedVotes + Configurations.ITOPZ_VOTE_STEP) + " votes!");
	}

	/**
	 * reward players
	 */
	private void reward()
	{
		// iterate through all players
		for (PlayerInstance player : World.getInstance().getPlayers())
		{
			// ignore null players
			if (player == null)
			{
				continue;
			}

			// set player signature key
			final String key = Objects.requireNonNullElse(player.getClient().getConnectionAddress().getHostAddress(), Objects.requireNonNull(player.getClient().getConnectionAddress().getAddress().toString(), player.getName()));
			// if key exists ignore player
			if (fingerprint.contains(key))
			{
				continue;
			}
			// add the key on ignore list
			fingerprint.add(key);

			for (final int itemId : Configurations.ITOPZ_GLOBAL_REWARDS.keySet())
			{
				// check if the item id exists
				final Item item = ItemTable.getInstance().getTemplate(itemId);
				if (Objects.nonNull(item))
				{
					// get config values
					final Long[] values = Configurations.ITOPZ_GLOBAL_REWARDS.get(itemId).get(0);
					// set min count value of received item
					long min = values[0];
					// set max count value of received item
					long max = values[1];
					// set chances of getting the item
					long chance = values[2];
					// set count of each item
					long count = Rnd.get(min, max);
					// chance for each item
					if (Rnd.get(100) > chance || chance >= 100)
					{
						// reward item
						player.addItem("iTopZ", itemId, count, player, true);
						// write info on console
						Gui.getInstance().ConsoleWrite("Vote: player " + player.getName() + " received x" + count + " " + item.getName());
					}
				}
			}
		}

		fingerprint.clear();
	}

	/**
	 * set server information vars
	 * if response is 200 return true
	 *
	 * @return boolean
	 */
	private boolean getServerInfo()
	{
		new Thread(() ->
		{
			// get response from itopz about this ip address
			GlobalResponse response = GlobalResponse.OPEN(URL.ITOPZ_GLOBAL_URL.toString()).connect();
			// set variables
			responseCode = response.getResponseCode();
			serverNeededVotes = response.getServerNeededVotes();
			serverNextRank = response.getServerNextRank();
			serverRank = response.getServerRank();
			serverVotes = response.getServerVotes();
		}).run();

		// check itopz response
		return responseCode == 200 && serverVotes != -2;
	}
}
