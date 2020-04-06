package com.avio.monitoring;

import java.util.ArrayList;
import java.lang.management.ManagementFactory;
import java.lang.management.GarbageCollectorMXBean;
import com.sun.management.OperatingSystemMXBean;


public class JVMStats {

	private OSStats osStats; 
	private MemoryStats memoryStats; 
	private ThreadStats threadStats; 
	private GarbageCollectionStats garbageCollectionStats;
	//Garbage Collection:	
	private ArrayList<IndividualGCStats> gcStatsCollection ;	
	private IndividualGCStats individualGcStats;

	public JVMStats() {		
		osStats=new OSStats();
		memoryStats = new MemoryStats();
		threadStats = new ThreadStats();
		garbageCollectionStats = new GarbageCollectionStats();
	}


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


	public JVMStats getJVMStats() {

		try {

			//OS
			osStats.setPid(ManagementFactory.getRuntimeMXBean().getName());
			osStats.setSystemLoadAverage(ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class).getSystemLoadAverage());
			osStats.setAvailableProcessors(ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class).getAvailableProcessors());
			osStats.setFreePhysicalMemorySize(ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class).getFreePhysicalMemorySize());
			osStats.setProcessCpuTime(ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class).getProcessCpuTime());
			osStats.setSystemCpuLoad(ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class).getSystemCpuLoad());
			osStats.setProcessCpuLoad(ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class).getProcessCpuLoad());		

			//Memory Usage		
			//Heap
			memoryStats.setHeapMemoryUsageInit(ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getInit());
			memoryStats.setHeapMemoryUsageUsed(ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed());
			memoryStats.setHeapMemoryUsageCommitted(ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getCommitted());
			memoryStats.setHeapMemoryUsageMax(ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getMax());
			//Non Heap
			memoryStats.setNonHeapMemoryUsageInit(ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage().getInit());
			memoryStats.setNonHeapMemoryUsageUsed(ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage().getUsed());
			memoryStats.setNonHeapMemoryUsageCommitted(ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage().getCommitted());
			memoryStats.setNonHeapMemoryUsageMax(ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage().getMax());

			//Thread
			threadStats.setActiveThreadCount(ManagementFactory.getThreadMXBean().getThreadCount());
			threadStats.setDaemonThreadCount(ManagementFactory.getThreadMXBean().getDaemonThreadCount());

			//Garbage Collection:
			gcStatsCollection = new ArrayList<IndividualGCStats>();
			for (GarbageCollectorMXBean gcBean : ManagementFactory.getGarbageCollectorMXBeans() ) {

				individualGcStats = new IndividualGCStats();			
				individualGcStats.setGcName(gcBean.getName());
				individualGcStats.setCollectionCount(gcBean.getCollectionCount());
				individualGcStats.setCollectionTime(gcBean.getCollectionTime());
				gcStatsCollection.add(individualGcStats);		


			}
			
			garbageCollectionStats.setGarbageCollection(gcStatsCollection);

			this.setOsStats(osStats);
			this.setMemoryStats(memoryStats);
			this.setThreadStats(threadStats);
			this.setGarbageCollectionStats(garbageCollectionStats);
			return this;


		}catch (Exception e) {
			e.printStackTrace();		
			this.getOsStats().setPid("There was an error retreiving JVM Stats from the machine , please check logs for the stacktrace. \n The error ="+e.getMessage());
			this.setOsStats(osStats);
			return this;
		}
	}
	
	public static OSStats getOSStats() {
		//OS
		OSStats stats = new OSStats();
		OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
		stats.setPid(ManagementFactory.getRuntimeMXBean().getName());
		stats.setSystemLoadAverage(osBean.getSystemLoadAverage());
		stats.setAvailableProcessors(osBean.getAvailableProcessors());
		stats.setFreePhysicalMemorySize(osBean.getFreePhysicalMemorySize());
		stats.setProcessCpuTime(osBean.getProcessCpuTime());
		stats.setSystemCpuLoad(osBean.getSystemCpuLoad());
		stats.setProcessCpuLoad(osBean.getProcessCpuLoad());	
		return stats;
	}


}
