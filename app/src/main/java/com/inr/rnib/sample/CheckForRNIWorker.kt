package com.inr.rnib.sample

import android.content.Context
import com.inr.rnib.RNIB
import com.inr.rnib.util.Utils

class CheckForRNIWorker(context: Context) {

    private val rnib = RNIB(context)

    suspend operator fun invoke(): List<RNIItemResult> = getRNIResults()

    private fun getRNIResults() = listOf(
        RNIItemResult("RNI Management Apps", rnib.detectRNIManagementApps()),
        RNIItemResult("Potentially Dangerous Apps", rnib.detectPotentiallyDangerousApps()),
        RNIItemResult("RNI Cloaking Apps", rnib.detectRNICloakingApps()),
        RNIItemResult("TestKeys", rnib.detectTestKeys()),
        RNIItemResult("BusyBoxBinary", rnib.checkForBusyBoxBinary()),
        RNIItemResult("SU Binary", rnib.checkForSuBinary()),
        RNIItemResult("2nd SU Binary check", rnib.checkSuExists()),
        RNIItemResult("For RW Paths", rnib.checkForRWPaths()),
        RNIItemResult("Dangerous Props", rnib.checkForDangerousProps()),
        RNIItemResult("RNI via native check", rnib.checkForRNINative()),
        RNIItemResult("SE linux Flag Is Enabled", !Utils.isSelinuxFlagInEnabled()),
        RNIItemResult("Magisk specific checks", rnib.checkForMagiskBinary())
    )
}

