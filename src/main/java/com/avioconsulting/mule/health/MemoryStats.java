package com.avioconsulting.mule.health;

public class MemoryStats {
	private long heapMemoryUsageInit;
	private long heapMemoryUsageUsed;
	private long heapMemoryUsageCommitted;
	private long heapMemoryUsageMax;
	
	private long nonHeapMemoryUsageInit;
	private long nonHeapMemoryUsageUsed;
	private long nonHeapMemoryUsageCommitted;
	private long nonHeapMemoryUsageMax;
	
	public long getHeapMemoryUsageInit() {
		return heapMemoryUsageInit;
	}
	public void setHeapMemoryUsageInit(long heapMemoryUsageInit) {
		this.heapMemoryUsageInit = heapMemoryUsageInit;
	}
	public long getHeapMemoryUsageUsed() {
		return heapMemoryUsageUsed;
	}
	public void setHeapMemoryUsageUsed(long heapMemoryUsageUsed) {
		this.heapMemoryUsageUsed = heapMemoryUsageUsed;
	}
	public long getHeapMemoryUsageCommitted() {
		return heapMemoryUsageCommitted;
	}
	public void setHeapMemoryUsageCommitted(long heapMemoryUsageCommitted) {
		this.heapMemoryUsageCommitted = heapMemoryUsageCommitted;
	}
	public long getHeapMemoryUsageMax() {
		return heapMemoryUsageMax;
	}
	public void setHeapMemoryUsageMax(long heapMemoryUsageMax) {
		this.heapMemoryUsageMax = heapMemoryUsageMax;
	}
	public long getNonHeapMemoryUsageInit() {
		return nonHeapMemoryUsageInit;
	}
	public void setNonHeapMemoryUsageInit(long nonHeapMemoryUsageInit) {
		this.nonHeapMemoryUsageInit = nonHeapMemoryUsageInit;
	}
	public long getNonHeapMemoryUsageUsed() {
		return nonHeapMemoryUsageUsed;
	}
	public void setNonHeapMemoryUsageUsed(long nonHeapMemoryUsageUsed) {
		this.nonHeapMemoryUsageUsed = nonHeapMemoryUsageUsed;
	}
	public long getNonHeapMemoryUsageCommitted() {
		return nonHeapMemoryUsageCommitted;
	}
	public void setNonHeapMemoryUsageCommitted(long nonHeapMemoryUsageCommitted) {
		this.nonHeapMemoryUsageCommitted = nonHeapMemoryUsageCommitted;
	}
	public long getNonHeapMemoryUsageMax() {
		return nonHeapMemoryUsageMax;
	}
	public void setNonHeapMemoryUsageMax(long nonHeapMemoryUsageMax) {
		this.nonHeapMemoryUsageMax = nonHeapMemoryUsageMax;
	}

}
