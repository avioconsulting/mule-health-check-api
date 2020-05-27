package com.avioconsulting.mule.health;

import java.util.ArrayList;


public class JvmStats {

	private OSStats osStats; 
	private MemoryStats memoryStats; 
	private ThreadStats threadStats; 
	private GarbageCollectionStats garbageCollectionStats;
	private ArrayList<IndividualGCStats> gcStatsCollection ;
	
	public OSStats getOsStats() {
		return osStats;
	}
	public void setOsStats(OSStats osStats) {
		this.osStats = osStats;
	}
	public MemoryStats getMemoryStats() {
		return memoryStats;
	}
	public void setMemoryStats(MemoryStats memoryStats) {
		this.memoryStats = memoryStats;
	}
	public ThreadStats getThreadStats() {
		return threadStats;
	}
	public void setThreadStats(ThreadStats threadStats) {
		this.threadStats = threadStats;
	}
	public GarbageCollectionStats getGarbageCollectionStats() {
		return garbageCollectionStats;
	}
	public void setGarbageCollectionStats(GarbageCollectionStats garbageCollectionStats) {
		this.garbageCollectionStats = garbageCollectionStats;
	}
	public ArrayList<IndividualGCStats> getGcStatsCollection() {
		return gcStatsCollection;
	}
	public void setGcStatsCollection(ArrayList<IndividualGCStats> gcStatsCollection) {
		this.gcStatsCollection = gcStatsCollection;
	}	



}
