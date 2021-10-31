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
package itopz.com.global;

import itopz.com.Configurations;
import itopz.com.gui.Gui;
import itopz.com.model.GlobalResponse;
import itopz.com.util.*;
import itopz.com.vote.VDSystem;
import org.l2jmobius.gameserver.datatables.ItemTable;
import org.l2jmobius.gameserver.model.World;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.items.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
public class Global
{
	// logger
	private static final Logs _log = new Logs(Global.class.getSimpleName());

	// global server vars
	private static int storedVotes, serverVotes, serverRank, serverNeededVotes, serverNextRank;
	private static int responseCode;

	// ip array list
	private final List<String> FINGERPRINT = new ArrayList<>();

	/**
	 * Global reward main function
	 */
	public Global()
	{
		// check if allowed the ITOPZ reward to start
		if (Configurations.ITOPZ_GLOBAL_REWARD)
		{
			VDSThreadPool.scheduleAtFixedRate(() -> execute("ITOPZ"), 100, Configurations.ITOPZ_VOTE_CHECK_DELAY * 1000);
			_log.info(Global.class.getSimpleName() + ": ITOPZ reward started.");
		}

		// check if allowed the HOPZONE reward to start
		if (Configurations.HOPZONE_GLOBAL_REWARD)
		{
			VDSThreadPool.scheduleAtFixedRate(() -> execute("HOPZONE"), 100, Configurations.HOPZONE_VOTE_CHECK_DELAY * 1000);
			_log.info(Global.class.getSimpleName() + ": HOPZONE reward started.");
		}

		// check if allowed the L2TOPGAMESERVER reward to start
		if (Configurations.L2TOPGAMESERVER_GLOBAL_REWARD)
		{
			VDSThreadPool.scheduleAtFixedRate(() -> execute("L2TOPGAMESERVER"), 100, Configurations.L2TOPGAMESERVER_VOTE_CHECK_DELAY * 1000);
			_log.info(Global.class.getSimpleName() + ": L2TOPGAMESERVER reward started.");
		}

		// check if allowed the L2JBRASIL reward to start
		if (Configurations.L2JBRASIL_GLOBAL_REWARD)
		{
			VDSThreadPool.scheduleAtFixedRate(() -> execute("L2JBRASIL"), 100, Configurations.L2JBRASIL_VOTE_CHECK_DELAY * 1000);
			_log.info(Global.class.getSimpleName() + ": L2JBRASIL reward started.");
		}

		// check if allowed the L2NETWORK reward to start
		if (Configurations.L2NETWORK_GLOBAL_REWARD)
		{
			VDSThreadPool.scheduleAtFixedRate(() -> execute("L2NETWORK"), 100, Configurations.L2NETWORK_VOTE_CHECK_DELAY * 1000);
			_log.info(Global.class.getSimpleName() + ": L2NETWORK reward started.");
		}

		// check if allowed the L2TOPSERVERS reward to start
		if (Configurations.L2TOPSERVERS_GLOBAL_REWARD)
		{
			VDSThreadPool.scheduleAtFixedRate(() -> execute("L2TOPSERVERS"), 100, Configurations.L2TOPSERVERS_VOTE_CHECK_DELAY * 1000);
			_log.info(Global.class.getSimpleName() + ": L2TOPSERVERS reward started.");
		}

		// check if allowed the L2VOTES reward to start
		if (Configurations.L2VOTES_GLOBAL_REWARD)
		{
			VDSThreadPool.scheduleAtFixedRate(() -> execute("L2VOTES"), 100, Configurations.L2VOTES_VOTE_CHECK_DELAY * 1000);
			_log.info(Global.class.getSimpleName() + ": L2VOTES reward started.");
		}
	}

	/**
	 * set server information vars
	 */
	public void execute(String TOPSITE)
	{
		// get response from topsite about this ip address
		Optional.ofNullable(GlobalResponse.OPEN(Url.from(TOPSITE + "_GLOBAL_URL").toString()).connect(TOPSITE, VDSystem.VoteType.GLOBAL)).ifPresent(response ->
		{
			// set variables
			responseCode = response.getResponseCode();
			serverNeededVotes = response.getServerNeededVotes();
			serverNextRank = response.getServerNextRank();
			serverRank = response.getServerRank();
			serverVotes = response.getServerVotes();
		});
		// check topsite response
		if (responseCode != 200 || serverVotes == -2)
		{
			// write on console
			Gui.getInstance().ConsoleWrite(TOPSITE + " Not responding maybe its the end of the world.");
			if (Configurations.DEBUG)
				Gui.getInstance().ConsoleWrite(TOPSITE + " RESPONSE:" + responseCode + " VOTES:" + serverVotes);
			return;
		}

		// write console info from response
		switch (TOPSITE)
		{
			case "HOPZONE" -> {
				Gui.getInstance().ConsoleWrite(TOPSITE + " Server Votes: " + serverVotes + " votes.");
				Gui.getInstance().UpdateHopzoneStats(serverVotes);
			}
			case "ITOPZ" -> {
				Gui.getInstance().ConsoleWrite(TOPSITE + " Server Votes:" + serverVotes + " Rank:" + serverRank + " Next Rank(" + serverNextRank + ") need: " + serverNeededVotes + "votes.");
				Gui.getInstance().UpdateItopzStats(serverVotes, serverRank, serverNextRank, serverNeededVotes);
			}
			case "L2JBRASIL" -> {
				Gui.getInstance().ConsoleWrite(TOPSITE + " Server Votes: " + serverVotes + " votes.");
				Gui.getInstance().UpdateBrasilStats(serverVotes);
			}
			case "L2NETWORK" -> {
				Gui.getInstance().ConsoleWrite(TOPSITE + " Server Votes: " + serverVotes + " votes.");
				Gui.getInstance().UpdateNetworkStats(serverVotes);
			}
			case "L2TOPGAMESERVER" -> {
				Gui.getInstance().ConsoleWrite(TOPSITE + " Server Votes: " + serverVotes + " votes.");
				Gui.getInstance().UpdateTopGameServerStats(serverVotes);
			}
			case "L2TOPSERVERS" -> {
				Gui.getInstance().ConsoleWrite(TOPSITE + " Server Votes: " + serverVotes + " votes.");
				Gui.getInstance().UpdateTopServersStats(serverVotes);
			}
			case "L2VOTES" -> {
				Gui.getInstance().ConsoleWrite(TOPSITE + " Server Votes: " + serverVotes + " votes.");
				Gui.getInstance().UpdateVotesStats(serverVotes);
			}
		}
		storedVotes = Utilities.selectGlobalVar(TOPSITE, "votes");

		// check if default return value is -1 (failed)
		if (storedVotes == -1)
		{
			// re-set server votes
			Gui.getInstance().ConsoleWrite(TOPSITE + " recover votes.");
			// save votes
			Utilities.saveGlobalVar(TOPSITE, "votes", serverVotes);
			return;
		}

		// check stored votes are lower than server votes
		if (storedVotes < serverVotes)
		{
			// write on console
			Gui.getInstance().ConsoleWrite(TOPSITE + " update database");
			// save votes
			Utilities.saveGlobalVar(TOPSITE, "votes", storedVotes);
		}

		// monthly reset
		if (storedVotes > serverVotes)
		{
			// write on console
			Gui.getInstance().ConsoleWrite(TOPSITE + " monthly reset");
			// save votes
			Utilities.saveGlobalVar(TOPSITE, "votes", serverVotes);
		}

		// announce current votes
		switch (TOPSITE)
		{
			case "HOPZONE" -> {
				if (Configurations.HOPZONE_ANNOUNCE_STATISTICS)
					Gui.getInstance().UpdateHopzoneStats(serverVotes);
				// check for vote step reward
				if (serverVotes >= storedVotes + Configurations.HOPZONE_VOTE_STEP)
				{
					// reward all online players
					reward(TOPSITE);
				}
				// announce next reward
				Utilities.announce(TOPSITE, "Next reward at " + (storedVotes + Configurations.HOPZONE_VOTE_STEP) + " votes!");
			}
			case "ITOPZ" -> {
				if (Configurations.ITOPZ_ANNOUNCE_STATISTICS)
					Utilities.announce(TOPSITE, "Server Votes:" + serverVotes + " Rank:" + serverRank + " Next Rank(" + serverNextRank + ") need:" + serverNeededVotes + "votes");
				// check for vote step reward
				if (serverVotes >= storedVotes + Configurations.ITOPZ_VOTE_STEP)
				{
					// reward all online players
					reward(TOPSITE);
				}
				// announce next reward
				Utilities.announce(TOPSITE, "Next reward at " + (storedVotes + Configurations.ITOPZ_VOTE_STEP) + " votes!");
			}
			case "L2JBRASIL" -> {
				if (Configurations.L2JBRASIL_ANNOUNCE_STATISTICS)
					Gui.getInstance().UpdateBrasilStats(serverVotes);
				if (serverVotes >= storedVotes + Configurations.L2JBRASIL_VOTE_STEP)
				{
					// reward all online players
					reward(TOPSITE);
				}
				// announce next reward
				Utilities.announce(TOPSITE, "Next reward at " + (storedVotes + Configurations.L2JBRASIL_VOTE_STEP) + " votes!");
			}
			case "L2NETWORK" -> {
				if (Configurations.L2NETWORK_ANNOUNCE_STATISTICS)
					Gui.getInstance().UpdateNetworkStats(serverVotes);
				// check for vote step reward
				if (serverVotes >= storedVotes + Configurations.L2NETWORK_VOTE_STEP)
				{
					// reward all online players
					reward(TOPSITE);
				}
				// announce next reward
				Utilities.announce(TOPSITE, "Next reward at " + (storedVotes + Configurations.L2NETWORK_VOTE_STEP) + " votes!");
			}
			case "L2TOPGAMESERVER" -> {
				if (Configurations.L2TOPGAMESERVER_ANNOUNCE_STATISTICS)
					Gui.getInstance().UpdateTopGameServerStats(serverVotes);
				// check for vote step reward
				if (serverVotes >= storedVotes + Configurations.L2TOPGAMESERVER_VOTE_STEP)
				{
					// reward all online players
					reward(TOPSITE);
				}
				// announce next reward
				Utilities.announce(TOPSITE, "Next reward at " + (storedVotes + Configurations.L2TOPGAMESERVER_VOTE_STEP) + " votes!");
			}
			case "L2TOPSERVERS" -> {
				if (Configurations.L2TOPSERVERS_ANNOUNCE_STATISTICS)
					Gui.getInstance().UpdateTopServersStats(serverVotes);
				// check for vote step reward
				if (serverVotes >= storedVotes + Configurations.L2TOPSERVERS_VOTE_STEP)
				{
					// reward all online players
					reward(TOPSITE);
				}
				// announce next reward
				Utilities.announce(TOPSITE, "Next reward at " + (storedVotes + Configurations.L2TOPSERVERS_VOTE_STEP) + " votes!");
			}
			case "L2VOTES" -> {
				if (Configurations.L2VOTES_ANNOUNCE_STATISTICS)
					Gui.getInstance().UpdateVotesStats(serverVotes);
				// check for vote step reward
				if (serverVotes >= storedVotes + Configurations.L2VOTES_VOTE_STEP)
				{
					// reward all online players
					reward(TOPSITE);
				}
				// announce next reward
				Utilities.announce(TOPSITE, "Next reward at " + (storedVotes + Configurations.L2VOTES_VOTE_STEP) + " votes!");
			}
		}
	}

	/**
	 * reward players
	 */
	private void reward(String TOPSITE)
	{
		// iterate through all players
		for (PlayerInstance player : World.getInstance().getAllPlayers().stream().filter(Objects::nonNull).collect(Collectors.toList()))
		{
			// set player signature key
			String key = "";
			try
			{
				key = Objects.requireNonNull(player.getClient().getIpAddress(), player.getName());
			} catch (Exception e)
			{
				Gui.getInstance().ConsoleWrite(e.getMessage());
				continue;
			}

			// if key exists ignore player
			if (FINGERPRINT.contains(key))
			{
				continue;
			}
			// add the key on ignore list
			FINGERPRINT.add(key);

			for (final int itemId : Rewards.from(TOPSITE + "_GLOBAL_REWARDS").keys())
			{
				// check if the item id exists
				final Item item = ItemTable.getInstance().getTemplate(itemId);
				if (Objects.nonNull(item))
				{
					// get config values
					final Integer[] values = Rewards.from(TOPSITE + "_GLOBAL_REWARDS").get(itemId);
					// set min count value of received item
					int min = values[0];
					// set max count value of received item
					int max = values[1];
					// set chances of getting the item
					int chance = values[2];
					// set count of each item
					int count = Random.get(min, max);
					// chance for each item
					if (Random.get(100) > chance || chance >= 100)
					{
						// reward item
						player.addItem(TOPSITE, itemId, count, player, true);
						// write info on console
						Gui.getInstance().ConsoleWrite(TOPSITE + ": player " + player.getName() + " received x" + count + " " + item.getName());
					}
				}
			}
		}

		FINGERPRINT.clear();

		// announce the reward
		Utilities.announce(TOPSITE, "Thanks for voting! Players rewarded!");
		// save votes
		Utilities.saveGlobalVar(TOPSITE, "votes", serverVotes);
		// write on console
		Gui.getInstance().ConsoleWrite(TOPSITE + ": Players rewarded!");
	}

	public static Global getInstance()
	{
		return SingletonHolder._instance;
	}

	private static class SingletonHolder
	{
		protected static final Global _instance = new Global();
	}
}
