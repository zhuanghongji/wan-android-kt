package com.zhuanghongji.wan.main.wechat

import android.view.View
import com.google.android.material.tabs.TabLayout
import com.zhuanghongji.wan.R
import com.zhuanghongji.wan.base_common.api.datas.WxChapter
import com.zhuanghongji.wan.base_common.base.BaseMvpFragment
import com.zhuanghongji.wan.event.ColorEvent
import com.zhuanghongji.wan.main.knowledge.KnowledgeFragment
import com.zhuanghongji.wan.main.knowledge.tree.WeChatContract
import com.zhuanghongji.wan.manager.SettingManager
import kotlinx.android.synthetic.main.fragment_wechat.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class WeChatFragment : BaseMvpFragment<WeChatContract.View, WeChatContract.Presenter>(), WeChatContract.View {

    companion object {
        fun getInstance(): WeChatFragment = WeChatFragment()
    }


    override fun createPresenter(): WeChatContract.Presenter = WeChatPresenter()

    /**
     * datas
     */
    private val datas = mutableListOf<WxChapter>()

    /**
     * ViewPagerAdapter
     */
    private val viewPagerAdapter: WeChatPagerAdapter by lazy {
        WeChatPagerAdapter(datas, childFragmentManager)
    }

    override fun getLayoutResID(): Int = R.layout.fragment_wechat

    override fun initView(view: View) {
        mLayoutStatusView = multiple_status_view
        viewPager.run {
            addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        }

        tabLayout.run {
            setupWithViewPager(viewPager)
            // TabLayoutHelper.setUpIndicatorWidth(tabLayout)
            addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewPager))
            addOnTabSelectedListener(onTabSelectedListener)
        }

        refreshColor(ColorEvent(true))

    }

    override fun showLoading() {
        mLayoutStatusView?.showLoading()
    }

    override fun showError(errorMsg: String) {
        super.showError(errorMsg)
        mLayoutStatusView?.showError()
    }

    override fun lazyLoad() {
        mPresenter?.requestWxChapters()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun refreshColor(event: ColorEvent) {
        if (event.isRefresh) {
            if (!SettingManager.getIsNightMode()) {
                tabLayout.setBackgroundColor(SettingManager.getColor())
            }
        }
    }

    override fun doReConnected() {
        if (datas.size == 0) {
            super.doReConnected()
        }
    }

    override fun setWxChapters(chapters: MutableList<WxChapter>) {
        chapters.let {
            datas.addAll(it)
            viewPager.run {
                adapter = viewPagerAdapter
                offscreenPageLimit = datas.size
            }
        }
        if (chapters.isEmpty()) {
            mLayoutStatusView?.showEmpty()
        } else {
            mLayoutStatusView?.showContent()
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

    override fun scrollToTop() {
        if (viewPagerAdapter.count == 0) {
            return
        }
        val fragment: KnowledgeFragment = viewPagerAdapter.getItem(viewPager.currentItem) as KnowledgeFragment
        fragment.scrollToTop()
    }

}