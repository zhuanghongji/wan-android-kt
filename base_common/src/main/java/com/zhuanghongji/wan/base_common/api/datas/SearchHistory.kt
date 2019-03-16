package com.zhuanghongji.wan.base_common.api.datas

import org.litepal.crud.LitePalSupport

/**
 * 搜索历史
 */
data class SearchHistory(val key: String): LitePalSupport() {

    val id: Long = 0
}