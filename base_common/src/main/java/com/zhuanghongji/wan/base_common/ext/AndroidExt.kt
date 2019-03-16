package com.zhuanghongji.wan.base_common.ext

import android.app.Activity
import android.content.Context
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.just.agentweb.AgentWeb
import com.just.agentweb.DefaultWebClient
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

fun String.getAgentWeb(
    activity: Activity,
    webContent: ViewGroup,
    layoutParams: ViewGroup.LayoutParams,
    webView: WebView,
    webChromeClient: WebChromeClient?,
    webViewClient: WebViewClient
) = AgentWeb.with(activity)//传入Activity or Fragment
    .setAgentWebParent(webContent, 1, layoutParams)//传入AgentWeb 的父控件
    .useDefaultIndicator()// 使用默认进度条
    .setWebView(webView)
    .setWebChromeClient(webChromeClient)
    .setWebViewClient(webViewClient)
    .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
    .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，弹窗咨询用户是否前往其他应用
    .createAgentWeb()//
    .ready()
    .go(this)
