package com.zhuanghongji.wan.main.wechat

import com.zhuanghongji.wan.base_common.api.ApiResult
import com.zhuanghongji.wan.base_common.api.datas.WxChapter
import com.zhuanghongji.wan.base_common.http.HttpManager
import com.zhuanghongji.wan.base_common.mvp.BaseModel
import com.zhuanghongji.wan.main.knowledge.tree.WeChatContract
import io.reactivex.Observable

class WeChatModel : BaseModel(), WeChatContract.Model {

    override fun requestWxChapters(): Observable<ApiResult<MutableList<WxChapter>>> {
        return HttpManager.apiService.getWXChapters()
    }

}