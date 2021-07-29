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
import itopz.com.model.IndividualResponse;
import itopz.com.util.*;
import itopz.com.vote.VDSystem;
import l2.gameserver.data.xml.holder.ItemHolder;
import l2.gameserver.handler.voicecommands.IVoicedCommandHandler;
import l2.gameserver.model.Player;
import l2.gameserver.model.instances.NpcInstance;
import l2.gameserver.network.l2.s2c.ExShowScreenMessage;
import l2.gameserver.scripts.Functions;
import l2.gameserver.templates.StatsSet;
import l2.gameserver.templates.item.ItemTemplate;

import java.time.Duration;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author Nightwolf
 * iToPz Discord: https://discord.gg/KkPms6B5aE
 * @Author Rationale
 * Base structure credits goes on Rationale Discord: Rationale#7773
 * <p>
 * Vote Donation System
 * Script website: https://itopz.com/
 * Script version: 1.3
 * Pack Support: Lucera
 * <p>
 * Personal Donate Panels: https://www.denart-designs.com/
 * Free Donate panel: https://itopz.com/
 */
public class VoteCMD extends Functions implements IVoicedCommandHandler
{
	// local variables
	private String _IPAddress;

	// 12 hour reuse
	private final Duration VOTE_REUSE = Duration.ofHours(12);

	// flood protector
	private static final Duration FLOOD_REUSE = Duration.ofSeconds(20);
	private static final Map<Integer, AtomicLong> FLOOD_PROTECTOR = new ConcurrentHashMap<>();
	private static final Map<String, AtomicLong> FLOOD_PROTECTOR_IP = new ConcurrentHashMap<>();

	// commands
	public final static String[] COMMANDS =
	{
		"vote","itopz", "hopzone", "l2jbrasil", "l2network", "l2topgameserver", "l2topservers", "l2votes"
	};

	@Override
	public boolean useVoicedCommand(String command, Player player, String s1)
	{
		openWindow(player);
		player.sendActionFailed();
		return false;
	}

	public void check(String[] params)
	{
		String TOPSITE = String.valueOf(params[0]).toUpperCase();
		Player player = this.getSelf();

		// check if allowed the individual command to run
		if (TOPSITE.equals("ITOPZ") && !Configurations.ITOPZ_INDIVIDUAL_REWARD)
			return;
		if (TOPSITE.equals("HOPZONE") && !Configurations.HOPZONE_INDIVIDUAL_REWARD)
			return;
		if (TOPSITE.equals("L2TOPGAMESERVER") && !Configurations.L2TOPGAMESERVER_INDIVIDUAL_REWARD)
			return;
		if (TOPSITE.equals("L2NETWORK") && !Configurations.L2NETWORK_INDIVIDUAL_REWARD)
			return;
		if (TOPSITE.equals("L2JBRASIL") && !Configurations.L2JBRASIL_INDIVIDUAL_REWARD)
			return;
		if (TOPSITE.equals("L2TOPSERVERS") && !Configurations.L2TOPSERVERS_INDIVIDUAL_REWARD)
			return;
		if (TOPSITE.equals("L2VOTES") && !Configurations.L2VOTES_INDIVIDUAL_REWARD)
			return;

		if (FLOOD_PROTECTOR.computeIfAbsent(player.getObjectId(), k -> new AtomicLong()).get() > System.currentTimeMillis())
		{
			sendMsg(player, "You can't use the command so fast.");
			return;
		}

		FLOOD_PROTECTOR.get(player.getObjectId()).set(System.currentTimeMillis() + FLOOD_REUSE.toMillis());

		if (FLOOD_PROTECTOR_IP.computeIfAbsent(player.getIP(), k -> new AtomicLong()).get() > System.currentTimeMillis())
		{
			sendMsg(player, "You can't vote fast from same IP Address.");
			return;
		}

		FLOOD_PROTECTOR_IP.get(player.getIP()).set(System.currentTimeMillis() + FLOOD_REUSE.toMillis());

		// check player eligibility
		if (!playerChecksFail(player, TOPSITE))
		{
			VDSThreadPool.schedule(() -> Execute(player, TOPSITE), Random.get(1000, 10000));
		}
		openWindow(player);
	}

	/**
	 * Show Vote Window
	 * @param player object
	 */
	private void openWindow(Player player)
	{
		StringBuilder html = new StringBuilder("<html>");
		html.append("<head>");
		html.append("<title>Vote for us!</title>");
		html.append("</head>");
		html.append("<body>");
		html.append("<center>");
		html.append("<br><font color=\"cc9900\"><img src=\"L2UI_CH3.herotower_deco\" width=256 height=32></font><br>");
		html.append("<img src=\"L2UI.SquareWhite\" width=\"300\" height=\"1\">");
		html.append("<table width=300 align=center>");
		html.append("<tr><td align=center>Please select the topsite you voted</td></tr>");
		if (Configurations.ITOPZ_INDIVIDUAL_REWARD)
			html.append("<tr><td align=center width=33%><a action=\"bypass -h scripts_itopz.com.command.VoteCMD:check itopz\">iToPz</a></td></tr>");
		if (Configurations.HOPZONE_INDIVIDUAL_REWARD)
			html.append("<tr><td align=center width=33%><a action=\"bypass -h scripts_itopz.com.command.VoteCMD:check hopzone\">Hopzone</a></td></tr>");
		if (Configurations.L2TOPGAMESERVER_INDIVIDUAL_REWARD)
			html.append("<tr><td align=center width=33%><a action=\"bypass -h scripts_itopz.com.command.VoteCMD:check l2topgameserver\">L2TopGameServer</a></td></tr>");
		if (Configurations.L2NETWORK_INDIVIDUAL_REWARD)
			html.append("<tr><td align=center width=33%><a action=\"bypass -h scripts_itopz.com.command.VoteCMD:check l2network\">L2Network</a></td></tr>");
		if (Configurations.L2JBRASIL_INDIVIDUAL_REWARD)
			html.append("<tr><td align=center width=33%><a action=\"bypass -h scripts_itopz.com.command.VoteCMD:check l2jbrasil\">L2JBrasil</a></td></tr>");
		if (Configurations.L2TOPSERVERS_INDIVIDUAL_REWARD)
			html.append("<tr><td align=center width=33%><a action=\"bypass -h scripts_itopz.com.command.VoteCMD:check l2topservers\">L2TopServers</a></td></tr>");
		if (Configurations.L2VOTES_INDIVIDUAL_REWARD)
			html.append("<tr><td align=center width=33%><a action=\"bypass -h scripts_itopz.com.command.VoteCMD:check l2votes\">L2Votes</a></td></tr>");
		html.append("</table>");
		html.append("<img src=\"L2UI.SquareWhite\" width=300 height=1>");
		html.append("<img src=\"Sek.cbui371\" width=\"300\" height=\"1\">");
		html.append("<font color=\"cc9900\"><img src=\"L2UI_CH3.herotower_deco\" width=256 height=32></font><br1>");
		html.append("<img src=l2ui.bbs_lineage2 height=16 width=80>");
		html.append("</center>");
		html.append("</body>");
		html.append("</html>");
		Functions.show(html.toString(), player, (NpcInstance) null);
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
		final long voteTimer = Utilities.selectIndividualVar(player, TOPSITE, "can_vote");
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
				Utilities.saveIndividualVar(player, TOPSITE, "can_vote", System.currentTimeMillis() + VOTE_REUSE.toMillis(), _IPAddress);
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
			sendMsg(player, TOPSITE + " server is not responding try again later.");
			return false;
		}

		// server returned error
		if (_responseError != null)
		{
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
				long count = Random.get(min, max);
				// chance for each item
				if (Random.get(100) < chance || chance >= 100)
				{
					// reward item
					Functions.addItem(player, itemId, count);
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