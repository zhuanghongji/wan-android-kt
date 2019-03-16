package com.zhuanghongji.wan.setting

//import android.app.Fragment
//import android.app.FragmentTransaction
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.afollestad.materialdialogs.color.ColorChooserDialog
import com.zhuanghongji.wan.R
import com.zhuanghongji.wan.base_common.base.BaseActivity
import com.zhuanghongji.wan.common.setting.SettingFragment
import com.zhuanghongji.wan.event.ColorEvent
import kotlinx.android.synthetic.main.toolbar.*
import org.greenrobot.eventbus.EventBus

class SettingActivity : BaseActivity(), ColorChooserDialog.ColorCallback {

    override fun initEvent() {

    }

    override fun initElse() {

    }

    companion object {
        private const val EXTRA_SHOW_FRAGMENT = "show_fragment"
        private const val EXTRA_SHOW_FRAGMENT_ARGUMENTS = "show_fragment_args"
        private const val EXTRA_SHOW_FRAGMENT_TITLE = "show_fragment_title"
    }



    override fun getLayoutResID(): Int = R.layout.activity_setting

    override fun initData() {
    }

    override fun initView() {
        val initFragment: String = intent.getStringExtra(EXTRA_SHOW_FRAGMENT) ?: ""
        val initArguments: Bundle = intent.getBundleExtra(EXTRA_SHOW_FRAGMENT_ARGUMENTS) ?: Bundle()
        val initTitle: String = intent.getStringExtra(EXTRA_SHOW_FRAGMENT_TITLE) ?: "设置"

        if (initFragment.isEmpty()) {
            setupFragment(SettingFragment::class.java.name, initArguments)
        } else {
            setupFragment(initFragment, initArguments)
        }

        toolbar.run {
            title = initTitle
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun start() {
    }

    private fun setupFragment(fragmentName: String, args: Bundle) {
        val fragment = Fragment.instantiate(this, fragmentName, args)
        val transaction = supportFragmentManager.beginTransaction()
        // TRANSIT_FRAGMENT_FADE
        transaction.setTransition(FragmentTransaction.TRANSIT_NONE)
        transaction.replace(R.id.container, fragment)
        transaction.commitAllowingStateLoss()
    }

    private fun onBuildStartFragmentIntent(fragmentName: String, args: Bundle?, title: String?): Intent {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.setClass(this, javaClass)
        intent.putExtra(EXTRA_SHOW_FRAGMENT, fragmentName)
        intent.putExtra(EXTRA_SHOW_FRAGMENT_ARGUMENTS, args)
        intent.putExtra(EXTRA_SHOW_FRAGMENT_TITLE, title)
        return intent
    }

    fun startWithFragment(fragmentName: String, args: Bundle?,
                          resultTo: Fragment?, resultRequestCode: Int, title: String?) {
        val intent = onBuildStartFragmentIntent(fragmentName, args, title)
        if (resultTo == null) {
            startActivity(intent)
        } else {
            resultTo.startActivityForResult(intent, resultRequestCode)
        }
    }

    override fun onColorChooserDismissed(dialog: ColorChooserDialog) {
    }

    override fun onColorSelection(dialog: ColorChooserDialog, selectedColor: Int) {
//        if (!dialog.isAccentMode) {
//            SettingUtil.setColor(selectedColor)
//        }
//        initColor()
        EventBus.getDefault().post(ColorEvent(true))
    }

}