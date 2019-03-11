package com.zhuanghongji.wan.base_common.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

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

    fun setFragments(fm: FragmentManager, fragments: List<Fragment>, titles: List<String>) {
        mTitles = titles
        with(fm.beginTransaction()) {
            for (f in fragments) {
                remove(f)
            }
            commitAllowingStateLoss()
        }
        fm.executePendingTransactions()
        mFragments = fragments
        notifyDataSetChanged()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitles[position]
    }

    override fun getItem(position: Int): Fragment {
        return mFragments[position]
    }

    override fun getCount(): Int {
        return mFragments.size
    }
}