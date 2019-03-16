package com.zhuanghongji.wan.main.knowledge

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.android.material.tabs.TabLayout
import com.zhuanghongji.wan.R
import com.zhuanghongji.wan.base_common.api.datas.Knowledge
import com.zhuanghongji.wan.base_common.api.datas.Knowledges
import com.zhuanghongji.wan.base_common.base.BaseActivity
import com.zhuanghongji.wan.base_common.constants.PreferenceConstant
import com.zhuanghongji.wan.event.ColorEvent
import kotlinx.android.synthetic.main.activity_knowledge.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class KnowledgeActivity : BaseActivity() {

    override fun beforeInit() {

    }

    override fun initEvent() {

    }

    override fun initElse() {

    }

    /**
     * datas
     */
    private var knowledges = mutableListOf<Knowledge>()

    /**
     * toolbar title
     */
    private lateinit var toolbarTitle: String

    /**
     * ViewPagerAdapter
     */
    private val viewPagerAdapter: KnowledgePagerAdapter by lazy {
        KnowledgePagerAdapter(knowledges, supportFragmentManager)
    }

    override fun getLayoutResID(): Int = R.layout.activity_knowledge

    override fun initData() {
        intent.extras?.let {
            toolbarTitle = it.getString(PreferenceConstant.CONTENT_TITLE_KEY) ?: ""
            it.getSerializable(PreferenceConstant.CONTENT_DATA_KEY)?.let {
                val data = it as Knowledges
                data.children.let { children ->
                    knowledges.addAll(children)
                }
            }
        }
    }

    override fun useEventBus(): Boolean = true

    override fun initView() {
        toolbar.run {
            title = toolbarTitle
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            //StatusBarUtil2.setPaddingSmart(this@KnowledgeActivity, toolbar)
        }
        viewPager.run {
            adapter = viewPagerAdapter
            addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
            offscreenPageLimit = knowledges.size
        }
        tabLayout.run {
            setupWithViewPager(viewPager)
            // TabLayoutHelper.setUpIndicatorWidth(tabLayout)
            addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewPager))
            addOnTabSelectedListener(onTabSelectedListener)
        }
        floating_action_btn.run {
            setOnClickListener(onFABClickListener)
        }

    }

    override fun start() {
    }

//    override fun initColor() {
//        super.initColor()
//        refreshColor(ColorEvent(true))
//    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun refreshColor(event: ColorEvent) {
        if (event.isRefresh) {
//            if (!SettingUtil.getIsNightMode()) {
//                tabLayout.setBackgroundColor(SettingUtil.getColor())
//                floating_action_btn.backgroundTintList = ColorStateList.valueOf(mThemeColor)
//            }
        }
    }

    /**
     * onTabSelectedListener
     */
    private val onTabSelectedListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabReselected(tab: TabLayout.Tab?) {
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {
        }

        override fun onTabSelected(tab: TabLayout.Tab?) {
            // 默认切换的时候，会有一个过渡动画，设为false后，取消动画，直接显示
            tab?.let {
                viewPager.setCurrentItem(it.position, false)
            }
        }
    }

    /**
     * FAB 监听
     */
    private val onFABClickListener = View.OnClickListener {
        if (viewPagerAdapter.count == 0) {
            return@OnClickListener
        }
        val fragment: KnowledgeFragment = viewPagerAdapter.getItem(viewPager.currentItem) as KnowledgeFragment
        fragment.scrollToTop()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_type_content, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_share -> {
                Intent().run {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT,
                        getString(
                            R.string.share_article_url,
                            getString(R.string.app_name),
                            knowledges[tabLayout.selectedTabPosition].name,
                            knowledges[tabLayout.selectedTabPosition].id.toString()
                        ))
                    type = PreferenceConstant.CONTENT_SHARE_TYPE
                    startActivity(Intent.createChooser(this, getString(R.string.action_share)))
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}