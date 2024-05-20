package com.inr.rnib;

import android.content.Context;
import android.content.pm.PackageManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class RNIBTest {

    @Test
    public void testIsdrni() {

        RNIB rnib = Mockito.mock(RNIB.class);

        when(rnib.isdrni()).thenCallRealMethod();

        when(rnib.detectRNIManagementApps()).thenReturn(false);
        when(rnib.detectPotentiallyDangerousApps()).thenReturn(false);
        when(rnib.checkForBinary(Const.BINARY_SU)).thenReturn(false);
        when(rnib.checkForDangerousProps()).thenReturn(false);
        when(rnib.checkForRWPaths()).thenReturn(false);
        when(rnib.detectTestKeys()).thenReturn(false);
        when(rnib.checkSuExists()).thenReturn(false);
        when(rnib.checkForrniNative()).thenReturn(false);

        // Test we return false when all methods return false
        assertFalse(rnib.isdrni());

        when(rnib.checkForrniNative()).thenReturn(true);

        // Test we return true when just one returns true
        assertTrue(rnib.isdrni());
    }

    @Test
    public void testIsdrniWithBusyBoxCheck() {

        RNIB rnib = Mockito.mock(RNIB.class);

        when(rnib.isdrni()).thenCallRealMethod();
        when(rnib.detectrniManagementApps()).thenReturn(false);
        when(rnib.detectPotentiallyDangerousApps()).thenReturn(false);
        when(rnib.checkForBinary(Const.BINARY_BUSYBOX)).thenReturn(true);
        when(rnib.checkForBinary(Const.BINARY_SU)).thenReturn(false);
        when(rnib.checkForDangerousProps()).thenReturn(false);
        when(rnib.checkForRWPaths()).thenReturn(false);
        when(rnib.detectTestKeys()).thenReturn(false);
        when(rnib.checkSuExists()).thenReturn(false);
        when(rnib.checkForrniNative()).thenReturn(false);

        // Test we return false as busybox binary presence is ignored
        assertFalse(rnib.isdrni());

        // Check busybox present is detected
        assertTrue(rnib.checkForBinary(Const.BINARY_BUSYBOX));
    }

    @Test
    public void testDetectAppsReturnsFalseWhenNoneFound() throws Exception {

        Context context = Mockito.mock(Context.class);
        PackageManager packageManager = Mockito.mock(PackageManager.class);

        RNIB rnib = new RNIB(context);
        rnib.setLogging(false);

        when(context.getPackageManager()).thenReturn(packageManager);

        // Return exception for every package installed
        when(packageManager.getPackageInfo(anyString(), anyInt())).thenThrow(new PackageManager.NameNotFoundException());

        // Should be false as no packages detected
        assertFalse(rnib.detectPotentiallyDangerousApps());
        assertFalse(rnib.detectrniCloakingApps());
        assertFalse(rnib.detectrniManagementApps());
    }

    /**
     * @param packageNameToFind - We will pretend packagemanager has this package installed, can be null for no packages installed
     * @return - Mocked Context with mocked Packagemanager
     * @throws PackageManager.NameNotFoundException
     */
    private Context getMockedContext(String packageNameToFind) throws PackageManager.NameNotFoundException {
        Context context = Mockito.mock(Context.class);
        PackageManager packageManager = Mockito.mock(PackageManager.class);
        when(context.getPackageManager()).thenReturn(packageManager);

        if (packageManager == null) {
            // Return exception for all packages
            when(packageManager.getPackageInfo(anyString(), anyInt())).thenThrow(new PackageManager.NameNotFoundException());
        } else {
            // Return exception for every package other than one we should detect
            when(packageManager.getPackageInfo(not(eq(packageNameToFind)), anyInt())).thenThrow(new PackageManager.NameNotFoundException());
        }
        return context;
    }

    @Test
    public void testDetectPotentiallyDangerousApps() throws Exception {

        RNIB rnib = new RNIB(getMockedContext(null));
        rnib.setLogging(false);

        // Should be false as no packages detected
        assertFalse(rnib.detectPotentiallyDangerousApps());

        rnib = new RNIB(getMockedContext(Const.knownDangerousAppsPackages[0]));
        rnib.setLogging(false);

        // Should be true as package detected
        assertTrue(rnib.detectPotentiallyDangerousApps());

    }

    @Test
    public void testDetectrniManagementApps() throws Exception {

        RNIB rnib = new RNIB(getMockedContext(null));
        rnib.setLogging(false);

        // Should be false as no packages detected
        assertFalse(rnib.detectrniManagementApps());

        rnib = new RNIB(getMockedContext(Const.knownrniAppsPackages[0]));
        rnib.setLogging(false);

        // Should be true as package detected
        assertTrue(rnib.detectrniManagementApps());

    }

    @Test
    public void testDetectrniCloakingApps() throws Exception {

        RNIB rnib = new RNIB(getMockedContext(null));
        rnib.setLogging(false);

        // Should be false as no packages detected
        assertFalse(rnib.detectrniCloakingApps());

        rnib = new RNIB(getMockedContext(Const.knownrniCloakingPackages[0]));
        rnib.setLogging(false);

        // Should be true as package detected
        assertTrue(rnib.detectrniCloakingApps());
    }

    @Test
    public void testAllSuPathsEndWithSlash() {
        for (String path : Const.getPaths()) {
            assertTrue(path.endsWith("/"));
        }
    }

}
