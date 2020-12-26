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
import itopz.com.donate.DonateTaskManager;

import itopz.com.model.entity.ITOPZ_GLOBAL;
import itopz.com.model.entity.ITOPZ_INDIVIDUAL;
import l2s.gameserver.ThreadPoolManager;
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
public class iTopZ
{
    /**
     * Constructor
     */
    public iTopZ()
    {
        onLoad();
    }

    /**
     * Vod function on load
     */
    public void onLoad()
    {
        // check if allowed the reward to start
        if (Configurations.ITOPZ_GLOBAL_REWARD)
        {
            // start schedule of the global vote reward
            ThreadPoolManager.getInstance().scheduleAtFixedRate(new ITOPZ_GLOBAL(), 100, Configurations.ITOPZ_VOTE_CHECK_DELAY * 1000);

            // initiate global reward
            LogUtils.log(ITOPZ_GLOBAL.class.getSimpleName() + ": reward started.");
        }
        // check if allowed the individual reward to run
        if (Configurations.ITOPZ_INDIVIDUAL_REWARD)
        {
            // register individual reward command
            ITOPZ_INDIVIDUAL.registerCommand();

            // initiate individual reward
            LogUtils.log(ITOPZ_GLOBAL.class.getSimpleName() + ": reward started.");
        }
        // check if allowed the donation system to run
        if (Configurations.ITOPZ_DONATE_MANAGER)
        {
            // start donation manager
            ThreadPoolManager.getInstance().scheduleAtFixedRate(new DonateTaskManager(), 100, 5000);

            // initiate Donation reward
            LogUtils.log(ITOPZ_GLOBAL.class.getSimpleName() + ": started.");
        }
        LogUtils.log(ITOPZ_GLOBAL.class.getSimpleName() + ": System initialized.");
    }

    public static iTopZ getInstance()
    {
        return SingletonHolder._instance;
    }

    private static class SingletonHolder
    {
        protected static final iTopZ _instance = new iTopZ();
    }
}
