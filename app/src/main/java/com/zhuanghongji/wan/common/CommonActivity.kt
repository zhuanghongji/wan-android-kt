package com.zhuanghongji.wan.common

import com.zhuanghongji.wan.R
import com.zhuanghongji.wan.base_common.base.BaseActivity
import com.zhuanghongji.wan.base_common.constants.PreferenceConstant
import com.zhuanghongji.wan.common.about.AboutFragment
import com.zhuanghongji.wan.common.add.todo.AddTodoFragment
import com.zhuanghongji.wan.common.collect.CollectFragment
import com.zhuanghongji.wan.common.search.list.SearchListFragment
import com.zhuanghongji.wan.common.setting.SettingFragment
import kotlinx.android.synthetic.main.toolbar.*

class CommonActivity : BaseActivity() {

    override fun beforeInit() {

    }

    override fun initEvent() {

    }

    override fun initElse() {

    }

    override fun getLayoutResID(): Int = R.layout.activity_common

    override fun initData() {
    }

    override fun initView() {
        val extras = intent.extras
        val type = extras.getString(PreferenceConstant.TYPE_KEY, "")
        toolbar.run {
            title = getString(R.string.app_name)
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        val fragment = when (type) {
            PreferenceConstant.Type.COLLECT_TYPE_KEY -> {
                toolbar.title = getString(R.string.collect)
                CollectFragment.getInstance(extras)
            }
            PreferenceConstant.Type.ABOUT_US_TYPE_KEY -> {
                toolbar.title = getString(R.string.about_us)
                AboutFragment.getInstance(extras)
            }
            PreferenceConstant.Type.SETTING_TYPE_KEY -> {
                toolbar.title = getString(R.string.setting)
                SettingFragment.getInstance(extras)
            }
            PreferenceConstant.Type.SEARCH_TYPE_KEY -> {
                toolbar.title = extras.getString(PreferenceConstant.SEARCH_KEY, "")
                SearchListFragment.getInstance(extras)
            }
            PreferenceConstant.Type.ADD_TODO_TYPE_KEY -> {
                toolbar.title = getString(R.string.add)
                AddTodoFragment.getInstance(extras)
            }
            PreferenceConstant.Type.EDIT_TODO_TYPE_KEY -> {
                toolbar.title = getString(R.string.edit)
                AddTodoFragment.getInstance(extras)
            }
            PreferenceConstant.Type.SEE_TODO_TYPE_KEY -> {
                toolbar.title = getString(R.string.see)
                AddTodoFragment.getInstance(extras)
            }
            else -> {
                null
            }
        }
        fragment ?: return
        supportFragmentManager.beginTransaction()
            .replace(R.id.common_frame_layout, fragment, PreferenceConstant.Type.COLLECT_TYPE_KEY)
            .commit()

    }

    override fun start() {
    }

//    override fun initColor() {
//        super.initColor()
//        EventBus.getDefault().post(ColorEvent(true, mThemeColor))
//    }

}