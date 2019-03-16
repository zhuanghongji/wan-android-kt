package com.zhuanghongji.wan.search

import android.content.Context
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zhuanghongji.wan.R
import com.zhuanghongji.wan.base_common.api.datas.SearchHistory

class SearchHistoryAdapter(private val context: Context?, datas: MutableList<SearchHistory>)
    : BaseQuickAdapter<SearchHistory, BaseViewHolder>(R.layout.item_search_history, datas) {

    override fun convert(helper: BaseViewHolder?, item: SearchHistory?) {
        helper ?: return
        item ?: return

        helper.setText(R.id.tv_search_key, item.key)
            .addOnClickListener(R.id.iv_clear)

    }
}