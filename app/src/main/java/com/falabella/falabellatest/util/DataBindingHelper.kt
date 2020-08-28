package com.falabella.falabellatest.util

import android.widget.TextView
import androidx.databinding.BindingAdapter

object DataBindingHelper {
    @BindingAdapter("setDouble")
    @JvmStatic
    fun setDouble(textView: TextView , data: Double?) {
        data?.let {
            textView.text = data.toString()
            }
        }
}
