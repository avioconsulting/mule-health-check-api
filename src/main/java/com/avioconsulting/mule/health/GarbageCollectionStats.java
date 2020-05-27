package com.avioconsulting.mule.health;

import java.util.ArrayList;

public class GarbageCollectionStats {
	
	private ArrayList<IndividualGCStats> garbageCollection;

	public ArrayList<IndividualGCStats> getGarbageCollection() {
		return garbageCollection;
	}

	public void setGarbageCollection(ArrayList<IndividualGCStats> garbageCollection) {
		this.garbageCollection = garbageCollection;
	}

	

}
