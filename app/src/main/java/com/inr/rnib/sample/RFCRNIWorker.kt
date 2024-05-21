package com.inr.rnib.sample

import android.content.Context
import com.inr.rnib.RNIB
import com.inr.rnib.util.Utils

class RFCRNIWorker(context: Context) {

    private val rnib = RNIB(context)

    suspend operator fun invoke(): List<RNIItemResult> = getRNIResults()

    private fun getRNIResults() = listOf(
        RNIItemResult("RNI Management Apps", rnib.detectRNIManagementApps()),
        RNIItemResult("Potentially Dangerous Apps", rnib.detectPotentiallyDangerousApps()),
        RNIItemResult("RNI Cloaking Apps", rnib.detectRNICloakingApps()),
        RNIItemResult("TestKeys", rnib.detectTestKeys()),
        RNIItemResult("BusyBoxBinary", rnib.rfcBusyBoxBinary()),
        RNIItemResult("SU Binary", rnib.rfcSuBinary()),
        RNIItemResult("2nd SU Binary rkg", rnib.rkgSuExists()),
        RNIItemResult("For RW Paths", rnib.rfcRWPaths()),
        RNIItemResult("Dangerous Props", rnib.rfcDangerousProps()),
        RNIItemResult("RNI via native rkg", rnib.rfcRNINative()),
        RNIItemResult("SE linux Flag Is Enabled", !Utils.isSelinuxFlagInEnabled()),
        RNIItemResult("Magisk specific rkgs", rnib.rfcMagiskBinary())
    )
}

