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

import itopz.com.Configurations;
import itopz.com.command.VoteCMD;
import itopz.com.gui.Gui;
import l2s.commons.util.Rnd;
import l2s.gameserver.data.xml.holder.ItemHolder;
import l2s.gameserver.handler.voicecommands.VoicedCommandHandler;
import l2s.gameserver.model.Player;
import l2s.gameserver.templates.item.ItemTemplate;
import l2s.gameserver.utils.ItemFunctions;
import smartguard.core.utils.LogUtils;

/**
 * @Author Nightwolf
 * iToPz Discord: https://discord.gg/KkPms6B5aE
 * @Author Rationale
 * Base structure credits goes on Rationale Discord: Rationale#7773
 *
 * Vote Donation System
 * Script website: https://itopz.com/
 * Script version: 1.0
 * Pack Support: Remorse (l2-scripts) classic 196 pack
 *
 * Personal Donate Panels: https://www.denart-designs.com/
 * Free Donate panel: https://itopz.com/
 */
public class ITOPZ_INDIVIDUAL
{
	/**
	 * register voiced command
	 */
	public static void registerCommand()
	{
		VoicedCommandHandler.getInstance().registerVoicedCommandHandler(new VoteCMD());
		LogUtils.log(ITOPZ_INDIVIDUAL.class.getSimpleName() + ": registered .itopz command.");
	}

	/**
	 * reward player
	 *
	 * @param player object
	 * @return boolean
	 */
	public static boolean reward(Player player)
	{
		// iterate on item values
		for (final int _itemId : Configurations.ITOPZ_INDIVIDUAL_REWARDS.keySet())
		{
			// check if the item id exists
			final ItemTemplate item = ItemHolder.getInstance().getTemplate(_itemId);
			if (item == null)
			{
				LogUtils.log(ITOPZ_INDIVIDUAL.class.getSimpleName() + ": Failed to find reward item.");
				continue;
			}

			// load reward values from item id
			final Long[] values = Configurations.ITOPZ_INDIVIDUAL_REWARDS.get(_itemId).get(0);
			long min = values[0];
			long max = values[1];
			long chance = values[2];
			// chance for each item
			if (Rnd.get(100) < chance || chance >= 100)
			{
				// set count of each item
				long _count = Rnd.get(min, max);
				// reward item
				ItemFunctions.addItem(player, _itemId, (int) _count, 0, false);
				// write info on console
				Gui.getInstance().ConsoleWrite("Vote: player " + player.getName() + " received x" + _count + " " + item.getName());
			}
		}

		return true;
	}
}
