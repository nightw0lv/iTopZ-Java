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

import java.util.concurrent.*;

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
public class VDSThreadPool
{
	protected static final Logs _log = new Logs(VDSThreadPool.class.getSimpleName());
	private static final long MAX_DELAY = TimeUnit.NANOSECONDS.toMillis(Long.MAX_VALUE - System.nanoTime()) / 2;
	private static int _threadPoolRandomizer;
	protected static ScheduledThreadPoolExecutor[] _scheduledPools;
	protected static ThreadPoolExecutor[] _instantPools;

	public static void init()
	{
		// Feed scheduled pool.
		int poolCount = Runtime.getRuntime().availableProcessors();

		_scheduledPools = new ScheduledThreadPoolExecutor[poolCount];
		for (int i = 0;
		     i < poolCount;
		     i++)
		{ _scheduledPools[i] = new ScheduledThreadPoolExecutor(4); }

		// Feed instant pool.
		poolCount = Runtime.getRuntime().availableProcessors();

		_instantPools = new ThreadPoolExecutor[poolCount];
		for (int i = 0;
		     i < poolCount;
		     i++)
		{ _instantPools[i] = new ThreadPoolExecutor(2, 2, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(100000)); }

		// Pre-start core threads.
		for (ScheduledThreadPoolExecutor threadPool : _scheduledPools)
		{ threadPool.prestartAllCoreThreads(); }

		for (ThreadPoolExecutor threadPool : _instantPools)
		{ threadPool.prestartAllCoreThreads(); }

		// Launch purge task.
		scheduleAtFixedRate(() ->
		{
			for (ScheduledThreadPoolExecutor threadPool : _scheduledPools)
			{ threadPool.purge(); }

			for (ThreadPoolExecutor threadPool : _instantPools)
			{ threadPool.purge(); }
		}, 600000, 600000);

		_log.info("Initializing Threads.");
	}

	public static ScheduledFuture<?> scheduleAtFixedRate(Runnable r, long delay, long period)
	{
		try
		{
			return getPool(_scheduledPools).scheduleAtFixedRate(new TaskWrapper(r), validate(delay), validate(period), TimeUnit.MILLISECONDS);
		} catch (Exception e)
		{
			return null;
		}
	}

	public static ScheduledFuture<?> schedule(Runnable r, long delay)
	{
		try
		{
			return getPool(_scheduledPools).schedule(new TaskWrapper(r), validate(delay), TimeUnit.MILLISECONDS);
		} catch (Exception e)
		{
			return null;
		}
	}

	private static <T> T getPool(T[] threadPools)
	{
		return threadPools[_threadPoolRandomizer++ % threadPools.length];
	}

	private static long validate(long delay)
	{
		return Math.max(0, Math.min(MAX_DELAY, delay));
	}

	public static final class TaskWrapper implements Runnable
	{
		private final Runnable _runnable;

		public TaskWrapper(Runnable runnable)
		{
			_runnable = runnable;
		}

		@Override
		public void run()
		{
			try
			{
				_runnable.run();
			} catch (RuntimeException e)
			{
				_log.error("VDS: Exception on Thread.", e);
			}
		}
	}
}
