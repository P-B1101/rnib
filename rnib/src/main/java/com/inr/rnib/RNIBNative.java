package com.inr.rnib;

import com.inr.rnib.util.QLog;

public class RNIBNative {

    private static boolean libraryLoaded = false;

    /**
     * Loads the C/C++ libraries statically
     */
    static {
        try {
            System.loadLibrary("toolChecker");
            libraryLoaded = true;
        } catch (UnsatisfiedLinkError e) {
            QLog.e(e);
        }
    }

    public boolean wasNativeLibraryLoaded() {
        return libraryLoaded;
    }

    public native int checkForRNI(Object[] pathArray);

    public native int setLogDebugMessages(boolean logDebugMessages);

}
