package com.cnki.paotui.utils;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorUtil {

	private static final SynchronousQueue<Runnable> synrun = new SynchronousQueue<Runnable>();

	private static final ThreadPoolExecutor executorService = new ThreadPoolExecutor(
			6, Integer.MAX_VALUE, 30L, TimeUnit.SECONDS, synrun,
			new DiscardOldestPolicys());

	public static void doTask(Runnable run) {
		executorService.execute(run);
	}

	public static ThreadPoolExecutor getThreadPoolExecutor() {
		return executorService;
	}

	public static class DiscardOldestPolicys implements
			RejectedExecutionHandler {

		public DiscardOldestPolicys() {
		}

		public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
			if (!e.isShutdown()) {
				e.getQueue().poll();
				e.execute(r);
			}
		}
	}
}
