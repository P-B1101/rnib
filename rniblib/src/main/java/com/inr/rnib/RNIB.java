package com.inr.rnib;

import android.content.Context;
import android.content.pm.PackageManager;

import com.inr.rnib.util.QLog;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static com.inr.rnib.Const.BINARY_BUSYBOX;
import static com.inr.rnib.Const.BINARY_SU;


public class RNIB {

    private final Context mContext;
    private boolean loggingEnabled = true;

    public RNIB(Context context) {
        mContext = context;
    }


    public boolean isDRNI() {

        return detectRNIManagementApps() || detectPotentiallyDangerousApps() || checkForBinary(BINARY_SU)
                || checkForDangerousProps() || checkForRWPaths()
                || detectTestKeys() || checkSuExists() || checkForRNINative() || checkForMagiskBinary();
    }


    @Deprecated
    public boolean isDRNIWithoutBusyBoxCheck() {
        return isDRNI();
    }


    public boolean isDRNIWithBusyBoxCheck() {

        return detectRNIManagementApps() || detectPotentiallyDangerousApps() || checkForBinary(BINARY_SU)
                || checkForBinary(BINARY_BUSYBOX) || checkForDangerousProps() || checkForRWPaths()
                || detectTestKeys() || checkSuExists() || checkForRNINative() || checkForMagiskBinary();
    }


    public boolean detectTestKeys() {
        String buildTags = android.os.Build.TAGS;

        return buildTags != null && buildTags.contains("test-keys");
    }

    public boolean detectRNIManagementApps() {
        return detectRNIManagementApps(null);
    }

    public boolean detectRNIManagementApps(String[] additionalRNIManagementApps) {

        // Create a list of package names to iterate over from constants any others provided
        ArrayList<String> packages = new ArrayList<>(Arrays.asList(Const.knownRNIAppsPackages));
        if (additionalRNIManagementApps!=null && additionalRNIManagementApps.length>0){
            packages.addAll(Arrays.asList(additionalRNIManagementApps));
        }

        return isAnyPackageFromListInstalled(packages);
    }

    public boolean detectPotentiallyDangerousApps() {
        return detectPotentiallyDangerousApps(null);
    }


    public boolean detectPotentiallyDangerousApps(String[] additionalDangerousApps) {

        // Create a list of package names to iterate over from constants any others provided
        ArrayList<String> packages = new ArrayList<>();
        packages.addAll(Arrays.asList(Const.knownDangerousAppsPackages));
        if (additionalDangerousApps!=null && additionalDangerousApps.length>0){
            packages.addAll(Arrays.asList(additionalDangerousApps));
        }

        return isAnyPackageFromListInstalled(packages);
    }


    public boolean detectRNICloakingApps() {
        return detectRNICloakingApps(null) || canLoadNativeLibrary() && !checkForNativeLibraryReadAccess();
    }


    public boolean detectRNICloakingApps(String[] additionalRNICloakingApps) {

        // Create a list of package names to iterate over from constants any others provided
        ArrayList<String> packages = new ArrayList<>(Arrays.asList(Const.knownRNICloakingPackages));
        if (additionalRNICloakingApps!=null && additionalRNICloakingApps.length>0){
            packages.addAll(Arrays.asList(additionalRNICloakingApps));
        }
        return isAnyPackageFromListInstalled(packages);
    }


    public boolean checkForSuBinary(){
        return checkForBinary(BINARY_SU);
    }

    public boolean checkForMagiskBinary(){ return checkForBinary("magisk"); }

    public boolean checkForBusyBoxBinary(){
        return checkForBinary(BINARY_BUSYBOX);
    }

    public boolean checkForBinary(String filename) {

        String[] pathsArray = Const.getPaths();

        boolean result = false;

        for (String path : pathsArray) {
            String completePath = path + filename;
            File f = new File(path, filename);
            boolean fileExists = f.exists();
            if (fileExists) {
                QLog.v(completePath + " binary detected!");
                result = true;
            }
        }

        return result;
    }


    public void setLogging(boolean logging) {
        loggingEnabled = logging;
        QLog.LOGGING_LEVEL = logging ? QLog.ALL : QLog.NONE;
    }

    private String[] propsReader() {
        try {
            InputStream inputstream = Runtime.getRuntime().exec("getprop").getInputStream();
            if (inputstream == null) return null;
            String propVal = new Scanner(inputstream).useDelimiter("\\A").next();
            return propVal.split("\n");
        } catch (IOException | NoSuchElementException e) {
            QLog.e(e);
            return null;
        }
    }

    private String[] mountReader() {
        try {
            InputStream inputstream = Runtime.getRuntime().exec("mount").getInputStream();
            if (inputstream == null) return null;
            String propVal = new Scanner(inputstream).useDelimiter("\\A").next();
            return propVal.split("\n");
        } catch (IOException | NoSuchElementException e) {
            QLog.e(e);
            return null;
        }
    }


    private boolean isAnyPackageFromListInstalled(List<String> packages){
        boolean result = false;

        PackageManager pm = mContext.getPackageManager();

        for (String packageName : packages) {
            try {
                pm.getPackageInfo(packageName, 0);
                QLog.e(packageName + " RNI management app detected!");
                result = true;
            } catch (PackageManager.NameNotFoundException e) {
                // Exception thrown, package is not installed into the system
            }
        }

        return result;
    }

    public boolean checkForDangerousProps() {

        final Map<String, String> dangerousProps = new HashMap<>();
        dangerousProps.put("ro.debuggable", "1");
        dangerousProps.put("ro.secure", "0");

        boolean result = false;

        String[] lines = propsReader();

        if (lines == null){
            // Could not read, assume false;
            return false;
        }

        for (String line : lines) {
            for (String key : dangerousProps.keySet()) {
                if (line.contains(key)) {
                    String badValue = dangerousProps.get(key);
                    badValue = "[" + badValue + "]";
                    if (line.contains(badValue)) {
                        QLog.v(key + " = " + badValue + " detected!");
                        result = true;
                    }
                }
            }
        }
        return result;
    }

    public boolean checkForRWPaths() {

        boolean result = false;
       
        //Run the command "mount" to retrieve all mounted directories
        String[] lines = mountReader();

        if (lines == null){
            // Could not read, assume false;
            return false;
        }

        //The SDK version of the software currently running on this hardware device.
        int sdkVersion = android.os.Build.VERSION.SDK_INT;
        
           /**
             *
             *  In devices that are running Android 6 and less, the mount command line has an output as follow:
             *
             *   <fs_spec_path> <fs_file> <fs_spec> <fs_mntopts>
             *
             *   where :
             *   - fs_spec_path: describes the path of the device or remote filesystem to be mounted.
             *   - fs_file: describes the mount point for the filesystem.
             *   - fs_spec describes the block device or remote filesystem to be mounted.
             *   - fs_mntopts: describes the mount options associated with the filesystem. (E.g. "rw,nosuid,nodev" )
             *
             */

            /** In devices running Android which is greater than Marshmallow, the mount command output is as follow:
             *
             *      <fs_spec> <ON> <fs_file> <TYPE> <fs_vfs_type> <(fs_mntopts)>
             *
             * where :
             *   - fs_spec describes the block device or remote filesystem to be mounted.
             *   - fs_file: describes the mount point for the filesystem.
             *   - fs_vfs_type: describes the type of the filesystem.
             *   - fs_mntopts: describes the mount options associated with the filesystem. (E.g. "(rw,seclabel,nosuid,nodev,relatime)" )
             */
        
        for (String line : lines) {

            // Split lines into parts
            String[] args = line.split(" ");

            if ((sdkVersion <= android.os.Build.VERSION_CODES.M && args.length < 4)
                    || (sdkVersion > android.os.Build.VERSION_CODES.M && args.length < 6)) {
                // If we don't have enough options per line, skip this and log an error
                QLog.e("Error formatting mount line: "+line);
                continue;
            }

            String mountPoint;
            String mountOptions;

            /**
             * To check if the device is running Android version higher than Marshmallow or not
             */
            if (sdkVersion > android.os.Build.VERSION_CODES.M) {
                mountPoint = args[2];
                mountOptions = args[5];
            } else {
                mountPoint = args[1];
                mountOptions = args[3];
            }

            for(String pathToCheck: Const.pathsThatShouldNotBeWritable) {
                if (mountPoint.equalsIgnoreCase(pathToCheck)) {

                       /**
                         * If the device is running an Android version above Marshmallow,
                         * need to remove parentheses from options parameter;
                         */
                        if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.M) {
                            mountOptions = mountOptions.replace("(", "");
                            mountOptions = mountOptions.replace(")", "");

                        }
                    
                    // Split options out and compare against "rw" to avoid false positives
                    for (String option : mountOptions.split(",")){

                        if (option.equalsIgnoreCase("rw")){
                            QLog.v(pathToCheck+" path is mounted with rw permissions! "+line);
                            result = true;
                            break;
                        }
                    }
                }
            }
        }

        return result;
    }


    /**
     * A variation on the checking for SU, this attempts a 'which su'
     * @return true if su found
     */
    public boolean checkSuExists() {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(new String[] { "which", BINARY_SU });
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            return in.readLine() != null;
        } catch (Throwable t) {
            return false;
        } finally {
            if (process != null) process.destroy();
        }
    }


    public boolean checkForNativeLibraryReadAccess() {
        RNIBNative rnibNative = new RNIBNative();
        try {
            rnibNative.setLogDebugMessages(loggingEnabled);
            return true;
        } catch (UnsatisfiedLinkError e) {
            return false;
        }
    }

    /**
     * Checks if it is possible to load our native library
     * @return true if we can | false if not
     */
    public boolean canLoadNativeLibrary(){
        return new RNIBNative().wasNativeLibraryLoaded();
    }


    public boolean checkForRNINative() {

        if (!canLoadNativeLibrary()){
            QLog.e("We could not load the native library to test for rni");
            return false;
        }

        String[] paths = Const.getPaths();

        String[] checkPaths = new String[paths.length];
        for (int i = 0; i < checkPaths.length; i++) {
            checkPaths[i] = paths[i]+ BINARY_SU;
        }

        RNIBNative rnibNative = new RNIBNative();
        try {
            rnibNative.setLogDebugMessages(loggingEnabled);
            return rnibNative.checkForRNI(checkPaths) > 0;
        } catch (UnsatisfiedLinkError e) {
            return false;
        }
    }



}
