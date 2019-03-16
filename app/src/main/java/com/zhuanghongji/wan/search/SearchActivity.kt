package com.zhuanghongji.wan.search

import android.content.Intent
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.zhuanghongji.wan.R
import com.zhuanghongji.wan.base_common.api.datas.HotSearch
import com.zhuanghongji.wan.base_common.api.datas.SearchHistory
import com.zhuanghongji.wan.base_common.base.BaseMvpActivity
import com.zhuanghongji.wan.base_common.constants.PreferenceConstant
import com.zhuanghongji.wan.base_common.manager.DisplayManager
import com.zhuanghongji.wan.common.CommonActivity
import com.zhuanghongji.wan.utils.CommonUtil
import com.zhuanghongji.wan.widget.RecyclerViewItemDecoration
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.toolbar_search.*

class SearchActivity : BaseMvpActivity<SearchContract.View, SearchContract.Presenter>(), SearchContract.View {


    override fun initEvent() {

    }

    override fun initElse() {

    }

    override fun createPresenter(): SearchContract.Presenter = SearchPresenter()

    /**
     * 热搜数据
     */
    private var mHotSearchDatas = mutableListOf<HotSearch>()

    /**
     * datas
     */
    private val datas = mutableListOf<SearchHistory>()

    /**
     * SearchHistoryAdapter
     */
    private val searchHistoryAdapter by lazy {
        SearchHistoryAdapter(this, datas)
    }

    /**
     * LinearLayoutManager
     */
    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(this)
    }

    /**
     * RecyclerView Divider
     */
    private val recyclerViewItemDecoration by lazy {
        RecyclerViewItemDecoration(this)
    }

    override fun getLayoutResID(): Int = R.layout.activity_search

    override fun initData() {
    }

    override fun initView() {
         // super.initView()
        toolbar.run {
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        hot_search_flow_layout.run {
            setOnTagClickListener { view, position, parent ->
                if (mHotSearchDatas.size > 0) {
                    val hotSearchBean = mHotSearchDatas[position]
                    goToSearchList(hotSearchBean.name)
                    true
                }
                false
            }
        }

        rv_history_search.run {
            layoutManager = linearLayoutManager
            adapter = searchHistoryAdapter
            itemAnimator = DefaultItemAnimator()
            // addItemDecoration(recyclerViewItemDecoration)
        }

        searchHistoryAdapter.run {
            bindToRecyclerView(rv_history_search)
            onItemClickListener = this@SearchActivity.onItemClickListener
            onItemChildClickListener = this@SearchActivity.onItemChildClickListener
            setEmptyView(R.layout.search_empty_view)
        }

        search_history_clear_all_tv.setOnClickListener {
            datas.clear()
            searchHistoryAdapter.replaceData(datas)
            mPresenter?.clearAllHistory()
        }

        mPresenter?.getHotSearchData()
    }

    override fun onResume() {
        super.onResume()
        mPresenter?.queryHistory()
    }

    override fun start() {
    }

    private fun goToSearchList(key: String) {
        mPresenter?.saveSearchKey(key)
        Intent(this, CommonActivity::class.java).run {
            putExtra(PreferenceConstant.TYPE_KEY, PreferenceConstant.Type.SEARCH_TYPE_KEY)
            putExtra(PreferenceConstant.SEARCH_KEY, key)
            startActivity(this)
        }
    }

    override fun showHistoryData(historyBeans: MutableList<SearchHistory>) {
        searchHistoryAdapter.replaceData(historyBeans)
    }

    override fun showHotSearchData(hotSearchDatas: MutableList<HotSearch>) {
        this.mHotSearchDatas.addAll(hotSearchDatas)
        hot_search_flow_layout.adapter = object : TagAdapter<HotSearch>(hotSearchDatas) {
            override fun getView(parent: FlowLayout?, position: Int, hotSearchBean: HotSearch?): View {
                val tv: TextView = LayoutInflater.from(parent?.context).inflate(R.layout.flow_layout_tv,
                    hot_search_flow_layout, false) as TextView
                val padding: Int = DisplayManager.dip2px(10F)!!
                tv.setPadding(padding, padding, padding, padding)
                tv.text = hotSearchBean?.name
                tv.setTextColor(CommonUtil.randomColor())
                return tv
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView
        searchView.maxWidth = Integer.MAX_VALUE
        searchView.onActionViewExpanded()
        searchView.queryHint = getString(R.string.search_tint)
        searchView.setOnQueryTextListener(queryTextListener)
        searchView.isSubmitButtonEnabled = true
        try {
            val field = searchView.javaClass.getDeclaredField("mGoButton")
            field.isAccessible = true
            val mGoButton = field.get(searchView) as ImageView
            mGoButton.setImageResource(R.drawable.ic_search_white_24dp)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return super.onCreateOptionsMenu(menu)
    }

    /**
     * OnQueryTextListener
     */
    private val queryTextListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            goToSearchList(query.toString())
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            return false
        }
    }

    /**
     * ItemClickListener
     */
    private val onItemClickListener = BaseQuickAdapter.OnItemClickListener { _, _, position ->
        if (searchHistoryAdapter.data.size != 0) {
            val item = searchHistoryAdapter.data[position]
            goToSearchList(item.key)
        }
    }

    /**
     * ItemChildClickListener
     */
    private val onItemChildClickListener =
        BaseQuickAdapter.OnItemChildClickListener { _, view, position ->
            if (searchHistoryAdapter.data.size != 0) {
                val item = searchHistoryAdapter.data[position]
                when (view.id) {
                    R.id.iv_clear -> {
                        mPresenter?.deleteById(item.id)
                        searchHistoryAdapter.remove(position)
                    }
                }
            }
        }

}