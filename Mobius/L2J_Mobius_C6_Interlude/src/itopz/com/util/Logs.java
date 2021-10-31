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

import java.util.logging.Level;
import java.util.logging.Logger;

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
public class Logs
{
	private final Logger _logger;

	public Logs(String name)
	{
		_logger = Logger.getLogger(name);
	}

	public void log(String str)
	{
		String s = "=[ " + str + " ]";
		while (s.length() < 61)
		{
			s = "-" + s;
		}
		info(s);
	}

	private void log0(Level level, StackTraceElement caller, Object message, Throwable exception)
	{
		if (!_logger.isLoggable(level))
			return;

		if (caller == null)
			caller = new Throwable().getStackTrace()[2];

		_logger.logp(level, caller.getClassName(), caller.getMethodName(), String.valueOf(message), exception);
	}

	/**
	 * Logs a message with Level.INFO.
	 *
	 * @param message : The object to log.
	 */
	public void info(Object message)
	{
		log0(Level.INFO, null, message, null);
	}

	/**
	 * Logs a message with Level.WARNING.
	 *
	 * @param message : The object to log.
	 */
	public void warn(Object message)
	{
		log0(Level.WARNING, null, message, null);
	}

	/**
	 * Logs a message with Level.SEVERE.
	 *
	 * @param message : The object to log.
	 */
	public void error(Object message)
	{
		log0(Level.SEVERE, null, message, null);
	}

	/**
	 * Logs a message with Level.SEVERE.
	 *
	 * @param message   : The object to log.
	 * @param exception : Log the caught exception.
	 */
	public void error(Object message, Throwable exception)
	{
		log0(Level.SEVERE, null, message, exception);
	}
}