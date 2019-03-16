package com.zhuanghongji.wan.common.setting

import android.os.Bundle
import android.view.View
import com.zhuanghongji.wan.R
import com.zhuanghongji.wan.base_common.base.BaseFragment

class SettingFragment : BaseFragment() {

    companion object {
        fun getInstance(bundle: Bundle): SettingFragment {
            val fragment = SettingFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayoutResID(): Int = R.layout.fragment_setting

    override fun initView(view: View) {
    }

    override fun lazyLoad() {
    }
}