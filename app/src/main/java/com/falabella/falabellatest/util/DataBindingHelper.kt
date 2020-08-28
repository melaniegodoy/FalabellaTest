package com.falabella.falabellatest.util

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.*

object DataBindingHelper {
    @BindingAdapter("setDouble")
    @JvmStatic
    fun setDouble(textView: TextView, data: Double?) {
        data?.let {
            textView.text = String.format("con un valor de %s", data.toString())
            }
        }



    @SuppressLint("SimpleDateFormat")
    @BindingAdapter("setDate")
    @JvmStatic
    fun setDate(textView: TextView, data: String) {

        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val outputFormat = SimpleDateFormat("dd-MM-yyyy")
        val date: Date = inputFormat.parse(data)
        val formattedDate: String = outputFormat.format(date)
        println(formattedDate) // prints 10-04-2018
        textView.text = String.format("A la fecha %s", formattedDate)
    }

}
