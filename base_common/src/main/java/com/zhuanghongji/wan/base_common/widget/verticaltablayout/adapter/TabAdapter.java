package com.zhuanghongji.wan.base_common.widget.verticaltablayout.adapter;


import com.zhuanghongji.wan.base_common.widget.verticaltablayout.widget.TabView;

/**
 * @author chqiu
 *         Email:qstumn@163.com
 */
public interface TabAdapter {
    int getCount();

    TabView.TabBadge getBadge(int position);

    TabView.TabIcon getIcon(int position);

    TabView.TabTitle getTitle(int position);

    int getBackground(int position);
}
