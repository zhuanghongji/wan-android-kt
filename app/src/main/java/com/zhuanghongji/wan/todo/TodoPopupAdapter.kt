package com.zhuanghongji.wan.todo

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zhuanghongji.wan.R
import com.zhuanghongji.wan.base_common.api.datas.TodoType

class TodoPopupAdapter : BaseQuickAdapter<TodoType, BaseViewHolder>(R.layout.item_todo_popup_list) {

    override fun convert(helper: BaseViewHolder?, item: TodoType?) {
        helper ?: return
        item ?: return
        val tv_popup = helper.getView<TextView>(R.id.tv_popup)
        tv_popup.text = item.name
        if (item.isSelected) {
            tv_popup.setTextColor(mContext.resources.getColor(R.color.colorAccent))
        } else {
            tv_popup.setTextColor(mContext.resources.getColor(R.color.common_color))
        }
    }
}