package com.avio.monitoring;

public class IndividualGCStats {
	private String gcName;
	private long collectionCount;
	private long collectionTime;
	public String getGcName() {
		return gcName;
	}
	public void setGcName(String gcName) {
		this.gcName = gcName;
	}
	public long getCollectionCount() {
		return collectionCount;
	}
	public void setCollectionCount(long collectionCount) {
		this.collectionCount = collectionCount;
	}
	public long getCollectionTime() {
		return collectionTime;
	}
	public void setCollectionTime(long collectionTime) {
		this.collectionTime = collectionTime;
	}

}
