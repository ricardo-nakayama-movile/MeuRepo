package com.betatest.canalkidsbeta.util;

import android.os.Debug;
import android.util.Log;

public class MemoryUsageHelper {

	protected void displayMemoryUsage(String message) {
	    int usedKBytes = (int) (Debug.getNativeHeapAllocatedSize() / 1024L);
	    String usedMegsString = String.format("%s - usedMemory = Memory Used: %d KB", message, usedKBytes);
	    Log.d("HELPER", usedMegsString);
	}
	
}
