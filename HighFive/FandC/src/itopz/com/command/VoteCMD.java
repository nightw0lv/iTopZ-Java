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
package itopz.com.command;

import itopz.com.Configurations;
import itopz.com.gui.Gui;
import itopz.com.model.IndividualResponse;
import itopz.com.util.*;
import itopz.com.vote.VDSystem;
import l2f.gameserver.data.xml.holder.ItemHolder;
import l2f.gameserver.handler.voicecommands.IVoicedCommandHandler;
import l2f.gameserver.model.Player;
import l2f.gameserver.network.serverpackets.ExShowScreenMessage;
import l2f.gameserver.scripts.Functions;
import l2f.gameserver.templates.StatsSet;
import l2f.gameserver.templates.item.ItemTemplate;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Objects;

/**
 * @Author Nightwolf
 * iToPz Discord: https://discord.gg/KkPms6B5aE
 * @Author Rationale
 * Base structure credits goes on Rationale Discord: Rationale#7773
 * <p>
 * Vote Donation System
 * Script website: https://itopz.com/
 * Script version: 1.4
 * Pack Support: FandC H5
 * <p>
 * Personal Donate Panels: https://www.denart-designs.com/
 * Free Donate panel: https://itopz.com/
 */
public class VoteCMD implements IVoicedCommandHandler
{
	// local variables
	private String _IPAddress;

	// 12 hour reuse
	private final Duration VOTE_REUSE = Duration.ofHours(12);

	// vote site list
	public static enum VoteSite
	{
		ITOPZ,
		HOPZONE,
		L2TOPGAMESERVER,
		L2NETWORK,
		L2JBRASIL,
		L2TOPSERVERS,
		L2VOTES,
	}

	// flood protector list
	private static final List<FloodProtectorHolder> FLOOD_PROTECTOR = Collections.synchronizedList(new ArrayList<>());

	// returns protector holder
	public FloodProtectorHolder getFloodProtector(final Player player, final VoteSite site)
	{
		return FLOOD_PROTECTOR.stream().filter(s -> s.getSite() == site && (s.getIP().equalsIgnoreCase(player.getIP()) || s.getHWID().equalsIgnoreCase(player.getNetConnection().getHWID()))).findFirst().orElseGet(() ->
		{
			final FloodProtectorHolder holder = new FloodProtectorHolder(site, player);
			FLOOD_PROTECTOR.add(holder);
			return holder;
		});
	}

	/**
	 * Protector holder class
	 */
	private static class FloodProtectorHolder
	{
		public static final Duration EXTENSION = Duration.ofSeconds(10);

		private final VoteSite _site;

		private final String _IP;
		private final String _HWID;

		private long _lastAction;

		public FloodProtectorHolder(final VoteSite site, final Player player)
		{
			_site = site;
			_IP = player.getIP();
			_HWID = player.getNetConnection().getHWID();
		}

		public VoteSite getSite()
		{
			return _site;
		}

		public String getIP()
		{
			return _IP;
		}

		public String getHWID()
		{
			return _HWID;
		}

		public long getLastAction()
		{
			return _lastAction;
		}

		public void updateLastAction()
		{
			_lastAction = System.currentTimeMillis() + EXTENSION.toMillis();
		}
	}

	// commands
	public final static String[] COMMANDS =
	{
		"itopz", "hopzone", "l2jbrasil", "l2network", "l2topgameserver", "l2topservers", "l2votes"
	};

	@Override
	public boolean useVoicedCommand(String command, Player player, String s1)
	{
		final String TOPSITE = command.replace(".", "").toUpperCase();

		// check if allowed the individual command to run
		if (TOPSITE.equals("ITOPZ") && !Configurations.ITOPZ_INDIVIDUAL_REWARD)
			return false;
		if (TOPSITE.equals("HOPZONE") && !Configurations.HOPZONE_INDIVIDUAL_REWARD)
			return false;
		if (TOPSITE.equals("L2TOPGAMESERVER") && !Configurations.L2TOPGAMESERVER_INDIVIDUAL_REWARD)
			return false;
		if (TOPSITE.equals("L2NETWORK") && !Configurations.L2NETWORK_INDIVIDUAL_REWARD)
			return false;
		if (TOPSITE.equals("L2JBRASIL") && !Configurations.L2JBRASIL_INDIVIDUAL_REWARD)
			return false;
		if (TOPSITE.equals("L2TOPSERVERS") && !Configurations.L2TOPSERVERS_INDIVIDUAL_REWARD)
			return false;
		if (TOPSITE.equals("L2VOTES") && !Configurations.L2VOTES_INDIVIDUAL_REWARD)
			return false;

		// check topsite for flood actions
		final FloodProtectorHolder holder = getFloodProtector(player, VoteSite.valueOf(TOPSITE));
		if (holder.getLastAction() > System.currentTimeMillis())
		{
			sendMsg(player, "You can't use this command so fast!");
			return false;
		}
		holder.updateLastAction();

		// check player eligibility
		if (!playerChecksFail(player, TOPSITE))
		{
			VDSThreadPool.schedule(() -> Execute(player, TOPSITE), Random.get(1000, 10000));
		}

		player.sendActionFailed();
		return false;
	}


	/**
	 * Validate user
	 *
	 * @param player  object
	 * @param TOPSITE string
	 * @return boolean
	 */
	private boolean playerChecksFail(final Player player, final String TOPSITE)
	{
		// check for private network (website will not accept it)
		if (!Configurations.DEBUG && (player.getIP() == null || player.getIP().equals("127.0.0.1") || player.getIP().equals("localhost")))
		{
			sendMsg(player, "Private networks are not allowed.");
			return true;
		}

		// check if 12 hours has pass from last vote
		final long voteTimer = Utilities.selectIndividualVar(TOPSITE, "can_vote", Configurations.DEBUG ? Utilities.getMyIP() : player.getIP());
		if (voteTimer > System.currentTimeMillis())
		{
			String dateFormatted = Utilities.formatMillisecond(voteTimer);
			sendMsg(player, "You already voted on " + TOPSITE + " try again after " + dateFormatted + ".");
			return true;
		}

		// restrict players from same IP to vote again
		final boolean ipVoted = Utilities.selectIndividualIP(TOPSITE, "can_vote", Configurations.DEBUG ? Utilities.getMyIP() : player.getIP());
		if (ipVoted)
		{
			sendMsg(player, "Someone already voted on " + TOPSITE + " from your IP.");
			return true;
		}

		// ignore failures for debug
		if (Configurations.DEBUG)
		{
			_IPAddress = Utilities.getMyIP();
			return false;
		}

		_IPAddress = player.getIP();
		return false;
	}

	/**
	 * Execute individual response and reward player on success
	 *
	 * @param player  object
	 * @param TOPSITE string
	 */
	private void Execute(final Player player, final String TOPSITE)
	{
		// get response from itopz about this ip address
		Optional.ofNullable(IndividualResponse.OPEN(Url.from(TOPSITE + "_INDIVIDUAL_URL").toString(), _IPAddress).connect(TOPSITE, VDSystem.VoteType.INDIVIDUAL)).ifPresent(response ->
		{
			// set variables
			final StatsSet set = new StatsSet();
			set.set("response_code", response.getResponseCode());
			set.set("has_voted", response.hasVoted());
			set.set("vote_time", response.getVoteTime());
			set.set("server_time", response.getServerTime());
			set.set("response_error", response.getError());

			// player can get reward?
			if (isEligible(player, TOPSITE, set))
			{
				sendMsg(player, "Successfully voted in " + TOPSITE + "!" + (Configurations.DEBUG ? "(DEBUG ON)" : ""));
				reward(player, TOPSITE);
				// set can vote: 12 hours (in ms).
				Utilities.saveIndividualVar(TOPSITE, "can_vote", System.currentTimeMillis() + VOTE_REUSE.toMillis(), _IPAddress);
				player.sendActionFailed();
			}
		});
	}

	/**
	 * Return true if player is eligible to get a reward
	 *
	 * @param player object
	 * @return boolean
	 */
	private boolean isEligible(final Player player, final String TOPSITE, final StatsSet set)
	{
		final int _responseCode = set.getInteger("response_code");
		final boolean _hasVoted = set.getBool("has_voted");
		final long _voteTime = set.getLong("vote_time");
		final long _serverTime = set.getLong("server_time");
		final String _responseError = set.getString("response_error");

		// check if response was not ok
		if (_responseCode != 200)
		{
			if (Configurations.DEBUG)
				Gui.getInstance().ConsoleWrite(TOPSITE + " Response Code:" + _responseCode);
			sendMsg(player, TOPSITE + " server is not responding try again later.");
			return false;
		}

		// server returned error
		if (!_responseError.equals("NONE"))
		{
			if (Configurations.DEBUG)
				Gui.getInstance().ConsoleWrite(TOPSITE + " Response Error:" + _responseError);
			sendMsg(player, "Response error:" + _responseError + ".");
			return false;
		}

		// player has not voted
		if (!_hasVoted)
		{
			sendMsg(player, "You didn't vote at " + TOPSITE + ".");
			return false;
		}

		// check 12hours on server time pass
		if ((_serverTime > 0 && _voteTime > 0) && (_voteTime + VOTE_REUSE.toMillis() < _serverTime))
		{
			if (Configurations.DEBUG)
			{
				sendMsg(player, "Dates " + (_voteTime + VOTE_REUSE.toMillis()) + "<" + _serverTime);
				Gui.getInstance().ConsoleWrite(TOPSITE + "Dates " + (_voteTime + VOTE_REUSE.toMillis()) + "<" + _serverTime);
			}
			sendMsg(player, "The reward has expired, vote again.");
			return false;
		}

		// the player is eligible to receive reward
		return true;
	}

	/**
	 * reward player
	 *
	 * @param player object
	 */
	private void reward(final Player player, final String TOPSITE)
	{
		// iterate on item values
		for (final int itemId : Rewards.from(TOPSITE + "_INDIVIDUAL_REWARDS").keys())
		{
			// check if the item id exists
			final ItemTemplate item = ItemHolder.getInstance().getTemplate(itemId);
			if (Objects.nonNull(item))
			{
				// get config values
				final Integer[] values = Rewards.from(TOPSITE + "_INDIVIDUAL_REWARDS").get(itemId);
				// set min count value of received item
				int min = values[0];
				// set max count value of received item
				int max = values[1];
				// set chances of getting the item
				int chance = values[2];
				// set count of each item
				int count = Random.get(min, max);
				// chance for each item
				if (Random.get(100) < chance || chance >= 100)
				{
					// reward item
					Functions.addItem(player, itemId, count, true, TOPSITE);
					// write info on console
					Gui.getInstance().ConsoleWrite(TOPSITE + ": player " + player.getName() + " received x" + count + " " + item.getName());
				}
			}
		}

	}

	/**
	 * Send message to player
	 *
	 * @param player object
	 * @param s      string
	 */
	private void sendMsg(final Player player, final String s)
	{
		player.sendPacket(new ExShowScreenMessage(s, 3000, ExShowScreenMessage.ScreenMessageAlign.MIDDLE_CENTER, true));
		player.sendMessage(s);
	}

	@Override
	public String[] getVoicedCommandList()
	{
		return COMMANDS;
	}
}