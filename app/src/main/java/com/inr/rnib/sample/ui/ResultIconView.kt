package com.inr.rnib.sample.ui

import android.content.Context
import android.util.AttributeSet
import com.inr.rnib.sample.R

class ResultIconView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatImageView(context, attrs, defStyleAttr) {

    fun showPass() = setImageResource(R.drawable.ic_check_circle_green_24dp)

    fun showFail() = setImageResource(R.drawable.ic_error_circle_outline_red_24dp)

    fun update(isdrni: Boolean) {
        if (isdrni) {
            showFail()
        } else {
            showPass()
        }
    }
}
