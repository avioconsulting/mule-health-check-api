package com.avioconsulting.mule.health;

import java.util.ArrayList;
import java.util.List;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import java.lang.management.GarbageCollectorMXBean;
import com.sun.management.OperatingSystemMXBean;



public class JvmInfo {

	public static JvmStats getJvmStats() throws Exception {

		try {
				
			MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
			ThreadMXBean threadBead = ManagementFactory.getThreadMXBean();
			List<GarbageCollectorMXBean> gcBeans = ManagementFactory.getGarbageCollectorMXBeans();
			JvmStats stats = new JvmStats();		
			
			//Heap
			MemoryStats memStats = new MemoryStats();
			MemoryUsage heap = memoryBean.getHeapMemoryUsage();
			memStats.setHeapMemoryUsageInit(heap.getInit());
			memStats.setHeapMemoryUsageUsed(heap.getUsed());
			memStats.setHeapMemoryUsageCommitted(heap.getCommitted());
			memStats.setHeapMemoryUsageMax(heap.getMax());
			
			//Non Heap
			MemoryUsage nonHeap = memoryBean.getNonHeapMemoryUsage();
			memStats.setNonHeapMemoryUsageInit(nonHeap.getInit());
			memStats.setNonHeapMemoryUsageUsed(nonHeap.getUsed());
			memStats.setNonHeapMemoryUsageCommitted(nonHeap.getCommitted());
			memStats.setNonHeapMemoryUsageMax(nonHeap.getMax());

			//Thread
			ThreadStats threadStats = new ThreadStats();
			threadStats.setActiveThreadCount(threadBead.getThreadCount());
			threadStats.setDaemonThreadCount(threadBead.getDaemonThreadCount());

			//Garbage Collection:
			ArrayList<IndividualGCStats> gcStatsCollection = new ArrayList<IndividualGCStats>();
			for (GarbageCollectorMXBean gcBean : gcBeans ) {
				IndividualGCStats individualGcStats = new IndividualGCStats();			
				individualGcStats.setGcName(gcBean.getName());
				individualGcStats.setCollectionCount(gcBean.getCollectionCount());
				individualGcStats.setCollectionTime(gcBean.getCollectionTime());
				gcStatsCollection.add(individualGcStats);		
			}
			
			GarbageCollectionStats gcStats = new GarbageCollectionStats();
			gcStats.setGarbageCollection(gcStatsCollection);


			stats.setMemoryStats(memStats);
			stats.setThreadStats(threadStats);
			stats.setGarbageCollectionStats(gcStats);
			
			OSStats osStats = getOsStats();
			stats.setOsStats(osStats);
			return stats;


		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public static OSStats getOsStats() {
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
