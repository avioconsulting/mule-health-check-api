package com.avio.monitoring;

public class ThreadStats {
	private int activeThreadCount;
	private int daemonThreadCount;
	public int getActiveThreadCount() {
		return activeThreadCount;
	}
	public void setActiveThreadCount(int activeThreadCount) {
		this.activeThreadCount = activeThreadCount;
	}
	public int getDaemonThreadCount() {
		return daemonThreadCount;
	}
	public void setDaemonThreadCount(int daemonThreadCount) {
		this.daemonThreadCount = daemonThreadCount;
	}

}
