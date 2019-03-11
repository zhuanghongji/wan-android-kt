package com.zhuanghongji.wan.event

import com.zhuanghongji.wan.manager.SettingManager

class ColorEvent(var isRefresh: Boolean, var color: Int = SettingManager.getColor())