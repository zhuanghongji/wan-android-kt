package com.zhuanghongji.wan.common.collect

import android.content.Context
import android.text.Html
import android.view.View
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zhuanghongji.wan.R
import com.zhuanghongji.wan.base_common.api.datas.ArticleCollection
import com.zhuanghongji.wan.manager.ImageManager

class CollectAdapter(private val context: Context?, datas: MutableList<ArticleCollection>)
    : BaseQuickAdapter<ArticleCollection, BaseViewHolder>(R.layout.item_collect_list, datas) {
    override fun convert(helper: BaseViewHolder?, item: ArticleCollection?) {

        helper ?: return
        item ?: return
        helper.setText(R.id.tv_article_title, Html.fromHtml(item.title))
            .setText(R.id.tv_article_author, item.author)
            .setText(R.id.tv_article_date, item.niceDate)
            .setImageResource(R.id.iv_like, R.drawable.ic_like)
            .addOnClickListener(R.id.iv_like)

        helper.setText(R.id.tv_article_chapterName, item.chapterName)
        if (item.envelopePic.isNotEmpty()) {
            helper.getView<ImageView>(R.id.iv_article_thumbnail)
                .visibility = View.VISIBLE
            context?.let {
                ImageManager.load(it, item.envelopePic, helper.getView(R.id.iv_article_thumbnail))
            }
        } else {
            helper.getView<ImageView>(R.id.iv_article_thumbnail)
                .visibility = View.GONE
        }
    }
}