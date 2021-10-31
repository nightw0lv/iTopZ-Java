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
package itopz.com.vote;

import itopz.com.Configurations;
import itopz.com.command.VoteCMD;
import itopz.com.donate.DonateTaskManager;
import itopz.com.global.Global;
import itopz.com.util.Logs;
import itopz.com.util.VDSThreadPool;
import org.l2jmobius.gameserver.handler.VoicedCommandHandler;

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
public class VDSystem
{
	// logger
	private static final Logs _log = new Logs(VDSystem.class.getSimpleName());

	public enum VoteType
	{
		GLOBAL, INDIVIDUAL;
	}

	/**
	 * Constructor
	 */
	public VDSystem()
	{
		onLoad();
	}

	/**
	 * Vod function on load
	 */
	public void onLoad()
	{
		// check if allowed the donation system to run
		if (Configurations.ITOPZ_DONATE_MANAGER)
		{
			// start donation manager
			VDSThreadPool.scheduleAtFixedRate(new DonateTaskManager(), 100, 5000);

			// initiate Donation reward
			_log.info(DonateTaskManager.class.getSimpleName() + ": started.");
		}

		// register individual reward command
		VoicedCommandHandler.getInstance().registerVoicedCommandHandler(new VoteCMD());

		// load global system rewards
		Global.getInstance();

		_log.info(VDSystem.class.getSimpleName() + ": System initialized.");
	}

	public static VDSystem getInstance()
	{
		return SingletonHolder._instance;
	}

	private static class SingletonHolder
	{
		protected static final VDSystem _instance = new VDSystem();
	}
}
