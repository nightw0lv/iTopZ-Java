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
package itopz.com;

import itopz.com.gui.Gui;
import itopz.com.vote.iTopZ;

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
 * Pack Support: Mobius 2.4 Secret of Empire
 *
 * Personal Donate Panels: https://www.denart-designs.com/
 * Free Donate panel: https://itopz.com/
 */
public class VDSystemManager
{
	// logger
	private static final Logger _log = Logger.getLogger(VDSystemManager.class.getName());

	public VDSystemManager()
	{
		log("VDS Manager");
		// load configurations
		Configurations.load();

		// load gui console
		Gui.getInstance();

		// load iTopz
		iTopZ.getInstance();
	}

	/**
	 * Logger
	 *
	 * @param str string
	 */
	private void log(String str)
	{
		String s = "=[ " + str + " ]";
		while (s.length() < 61)
		{
			s = "-" + s;
		}
		_log.info(s);
	}

	public static VDSystemManager getInstance()
	{
		return VDSystemManager.SingletonHolder._instance;
	}

	private static class SingletonHolder
	{
		protected static final VDSystemManager _instance = new VDSystemManager();
	}
}