package com.zhuanghongji.wan.main.navigation

import android.content.Context
import androidx.core.content.ContextCompat
import com.zhuanghongji.wan.R
import com.zhuanghongji.wan.base_common.api.datas.Navigation
import com.zhuanghongji.wan.base_common.widget.verticaltablayout.adapter.TabAdapter
import com.zhuanghongji.wan.base_common.widget.verticaltablayout.widget.ITabView

class NavigationTabAdapter(context: Context?, list: List<Navigation>) : TabAdapter {

    private var context: Context = context!!
    private var list = mutableListOf<Navigation>()

    init {
        this.list = list as MutableList<Navigation>
    }

    override fun getIcon(position: Int): ITabView.TabIcon? = null


    override fun getBadge(position: Int): ITabView.TabBadge? = null

    override fun getBackground(position: Int): Int = -1

    override fun getTitle(position: Int): ITabView.TabTitle {
        return ITabView.TabTitle.Builder()
            .setContent(list[position].name)
            .setTextColor(
                ContextCompat.getColor(context, R.color.colorAccent),
                ContextCompat.getColor(context, R.color.Grey500))
            .build()
    }

    override fun getCount(): Int = list.size

}