package com.zhuanghongji.wan.todo

import com.chad.library.adapter.base.entity.SectionEntity
import com.zhuanghongji.wan.base_common.api.datas.Todo

class TodoDataBean : SectionEntity<Todo> {

    constructor(isHeader: Boolean, headerName: String) : super(isHeader, headerName)

    constructor(todoBean: Todo) : super(todoBean)

}