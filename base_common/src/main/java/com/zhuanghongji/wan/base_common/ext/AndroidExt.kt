package com.zhuanghongji.wan.base_common.ext

import android.app.Activity
import android.content.Context
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.zhuanghongji.wan.base_common.R

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(message: String) {
    Toast.makeText(this.activity?.applicationContext, message, Toast.LENGTH_SHORT).show()
}

fun Context.toastLong(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Fragment.toastLong(message: String) {
    Toast.makeText(this.activity?.applicationContext, message, Toast.LENGTH_LONG).show()
}

fun Activity.showSnackbar(text: String) {
    val snackbar = Snackbar.make(this.window.decorView, text, Snackbar.LENGTH_SHORT)
    val tvText = snackbar.view.findViewById<TextView>(R.id.snackbar_text)
    tvText.setTextColor(ContextCompat.getColor(this, R.color.white))
    snackbar.show()
}

fun Fragment.showSnackbar(text: String) {
    this.activity ?: return
    val snackbar = Snackbar.make(this.activity!!.window.decorView, text, Snackbar.LENGTH_SHORT)
    val tvText = snackbar.view.findViewById<TextView>(R.id.snackbar_text)
    tvText.setTextColor(ContextCompat.getColor(this.activity!!, R.color.white))
    snackbar.show()
}