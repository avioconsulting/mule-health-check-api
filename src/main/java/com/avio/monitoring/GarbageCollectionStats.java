package com.avio.monitoring;

import java.util.ArrayList;
import java.util.Map;

public class GarbageCollectionStats {
	
	private ArrayList<IndividualGCStats> garbageCollection;

	public ArrayList<IndividualGCStats> getGarbageCollection() {
		return garbageCollection;
	}

	public void setGarbageCollection(ArrayList<IndividualGCStats> garbageCollection) {
		this.garbageCollection = garbageCollection;
	}

	

}
