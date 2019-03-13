package com.zhuanghongji.wan.base_common.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * 用于 ViewPager 的 Fragment Adapter
 */
class BaseFragmentAdapter: FragmentPagerAdapter {

    private var mFragments: List<Fragment> = ArrayList()

    private var mTitles: List<String> = ArrayList()

    constructor(fm: FragmentManager?, fragments: List<Fragment>) : super(fm) {
        mFragments = fragments
    }

    constructor(fm: FragmentManager?, fragments: List<Fragment>, titles: List<String>) : super(fm) {
        mFragments = fragments
        mTitles = titles
    }

    /**
     * 设置 Fragments
     *
     * Note: the size [fragments] can not equal to the size of [titles].
     */
    fun setFragments(fm: FragmentManager, fragments: List<Fragment>, titles: List<String>) {
        // 设置前先清空事务中的 Fragment
        with(fm.beginTransaction()) {
            for (f in fragments) {
                remove(f)
            }
            commitAllowingStateLoss()
        }
        fm.executePendingTransactions()

        mTitles = titles
        mFragments = fragments
        notifyDataSetChanged()
    }

    /**
     * 根据 [position] 来获取对应的标题
     *
     * 当 Fragment 的数量与 Titles 的数量不相等时，返回 `null`。
     */
    override fun getPageTitle(position: Int): CharSequence? {
        return mTitles[position]
    }

    /**
     * 根据 [position] 来获取对应的 [Fragment]
     */
    override fun getItem(position: Int): Fragment {
        return mFragments[position]
    }

    /**
     * 获取 Fragments 的数量
     */
    override fun getCount(): Int {
        return mFragments.size
    }
}