package com.zhuanghongji.wan.main.knowledge.tree

import android.content.Context
import android.text.Html
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zhuanghongji.wan.R
import com.zhuanghongji.wan.base_common.api.datas.Knowledges

class KnowledgeTreeAdapter(private val context: Context?, datas: MutableList<Knowledges>) : BaseQuickAdapter<Knowledges, BaseViewHolder>(
    R.layout.item_knowledge_tree_list, datas) {

    override fun convert(helper: BaseViewHolder?, item: Knowledges?) {
        helper?.setText(R.id.title_first, item?.name)
        item?.children.let {
            helper?.setText(R.id.title_second,
                it?.joinToString("    ", transform = { child ->
                    Html.fromHtml(child.name)
                })
            )

        }
    }

}