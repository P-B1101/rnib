package com.inr.rnib.sample.ui

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.inr.rnib.sample.R
import timber.log.Timber

class DRNIResultTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    init {
        setCustomFont(context, "SubwayNovella.ttf")
    }

    private fun setCustomFont(ctx: Context, asset: String): Boolean {
        return try {
            val typeface = Typeface.createFromAsset(ctx.assets, asset)
            setTypeface(typeface)
            true
        } catch (e: Exception) {
            Timber.e(e, "Unable to load typeface: ${e.message}")
            false
        }
    }

    fun update(isdrni: Boolean) {
        if (isdrni) {
            drni()
        } else {
            notdrni()
        }
    }

    private fun drni() {
        setText(R.string.result_drni)
        setTextColor(ContextCompat.getColor(context, R.color.fail))
    }

    private fun notdrni() {
        setText(R.string.result_not_drni)
        setTextColor(ContextCompat.getColor(context, R.color.pass))
    }
}
