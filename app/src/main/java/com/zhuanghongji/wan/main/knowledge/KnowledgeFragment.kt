package com.zhuanghongji.wan.main.knowledge

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.zhuanghongji.wan.R
import com.zhuanghongji.wan.base_common.api.datas.Article
import com.zhuanghongji.wan.base_common.api.datas.Articles
import com.zhuanghongji.wan.base_common.base.BaseMvpFragment
import com.zhuanghongji.wan.base_common.constants.PreferenceConstant
import com.zhuanghongji.wan.base_common.ext.showSnackbar
import com.zhuanghongji.wan.base_common.ext.toast
import com.zhuanghongji.wan.base_common.impl.App
import com.zhuanghongji.wan.base_common.utils.NetworkUtil
import com.zhuanghongji.wan.content.ContentActivity
import com.zhuanghongji.wan.login.LoginActivity
import com.zhuanghongji.wan.widget.SpaceItemDecoration
import kotlinx.android.synthetic.main.fragment_refresh_layout.*

class KnowledgeFragment : BaseMvpFragment<KnowledgeContract.View, KnowledgeContract.Presenter>(), KnowledgeContract.View {


    companion object {
        fun getInstance(cid: Int): KnowledgeFragment {
            val fragment = KnowledgeFragment()
            val args = Bundle()
            args.putInt(PreferenceConstant.CONTENT_CID_KEY, cid)
            fragment.arguments = args
            return fragment
        }
    }

    override fun createPresenter(): KnowledgeContract.Presenter = KnowledgePresenter()

    /**
     * cid
     */
    private var cid: Int = 0

    /**
     * datas
     */
    private val datas = mutableListOf<Article>()

    /**
     * RecyclerView Divider
     */
    private val recyclerViewItemDecoration by lazy {
        activity?.let {
            SpaceItemDecoration(it)
        }
    }

    /**
     * Knowledge Adapter
     */
    private val knowledgeAdapter: KnowledgeAdapter by lazy {
        KnowledgeAdapter(activity, datas)
    }

    /**
     * LinearLayoutManager
     */
    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(activity)
    }

    /**
     * is Refresh
     */
    private var isRefresh = true

    override fun showLoading() {
         swipeRefreshLayout.isRefreshing = isRefresh
    }

    override fun hideLoading() {
        swipeRefreshLayout?.isRefreshing = false
        if (isRefresh) {
            knowledgeAdapter.run {
                setEnableLoadMore(true)
            }
        }
    }

    override fun showError(errorMsg: String) {
        super.showError(errorMsg)
        mLayoutStatusView?.showError()
        knowledgeAdapter.run {
            if (isRefresh)
                setEnableLoadMore(true)
            else
                loadMoreFail()
        }
    }

    override fun scrollToTop() {
        recyclerView.run {
            if (linearLayoutManager.findFirstVisibleItemPosition() > 20) {
                scrollToPosition(0)
            } else {
                smoothScrollToPosition(0)
            }
        }
    }

    override fun getLayoutResID(): Int = R.layout.fragment_refresh_layout

    override fun initView(view: View) {
        mLayoutStatusView = multiple_status_view
        cid = arguments?.getInt(PreferenceConstant.CONTENT_CID_KEY) ?: 0
        swipeRefreshLayout.run {
            setOnRefreshListener(onRefreshListener)
        }
        recyclerView.run {
            layoutManager = linearLayoutManager
            adapter = knowledgeAdapter
            itemAnimator = DefaultItemAnimator()
            recyclerViewItemDecoration?.let { addItemDecoration(it) }
        }

        knowledgeAdapter.run {
            setOnLoadMoreListener(onRequestLoadMoreListener, recyclerView)
            onItemClickListener = this@KnowledgeFragment.onItemClickListener
            onItemChildClickListener = this@KnowledgeFragment.onItemChildClickListener
            // setEmptyView(R.layout.fragment_empty_layout)
        }
    }

    override fun lazyLoad() {
        mLayoutStatusView?.showLoading()
        mPresenter?.requestKnowledgeList(0, cid)
    }

    override fun showCancelCollectSuccess(success: Boolean) {
        if (success) {
            toast(getString(R.string.cancel_collect_success))
        }
    }

    override fun showCollectSuccess(success: Boolean) {
        if (success) {
            toast(getString(R.string.collect_success))
        }
    }

    override fun setKnowledgeList(articles: Articles) {
        articles.datas.let {
            knowledgeAdapter.run {
                if (isRefresh) {
                    replaceData(it)
                } else {
                    addData(it)
                }
                val size = it.size
                if (size < articles.size) {
                    loadMoreEnd(isRefresh)
                } else {
                    loadMoreComplete()
                }
            }
        }
        if (knowledgeAdapter.data.isEmpty()) {
            mLayoutStatusView?.showEmpty()
        } else {
            mLayoutStatusView?.showContent()
        }
    }

    /**
     * RefreshListener
     */
    private val onRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        isRefresh = true
        knowledgeAdapter.setEnableLoadMore(false)
        mPresenter?.requestKnowledgeList(0, cid)
    }

    /**
     * LoadMoreListener
     */
    private val onRequestLoadMoreListener = BaseQuickAdapter.RequestLoadMoreListener {
        isRefresh = false
        swipeRefreshLayout.isRefreshing = false
        val page = knowledgeAdapter.data.size / 20
        mPresenter?.requestKnowledgeList(page, cid)
    }

    /**
     * ItemClickListener
     */
    private val onItemClickListener = BaseQuickAdapter.OnItemClickListener { _, _, position ->
        if (datas.size != 0) {
            val data = datas[position]
            Intent(activity, ContentActivity::class.java).run {
                putExtra(PreferenceConstant.CONTENT_URL_KEY, data.link)
                putExtra(PreferenceConstant.CONTENT_TITLE_KEY, data.title)
                putExtra(PreferenceConstant.CONTENT_ID_KEY, data.id)
                startActivity(this)
            }
        }
    }

    /**
     * ItemChildClickListener
     */
    private val onItemChildClickListener =
        BaseQuickAdapter.OnItemChildClickListener { _, view, position ->
            if (datas.size != 0) {
                val data = datas[position]
                when (view.id) {
                    R.id.iv_like -> {
                        if (isLogin) {
                            if (!NetworkUtil.isNetworkAvailable(App.context)) {
                                showSnackbar(resources.getString(R.string.no_network))
                                return@OnItemChildClickListener
                            }
                            val collect = data.collect
                            data.collect = !collect
                            knowledgeAdapter.setData(position, data)
                            if (collect) {
                                mPresenter?.cancelCollectArticle(data.id)
                            } else {
                                mPresenter?.addCollectArticle(data.id)
                            }
                        } else {
                            Intent(activity, LoginActivity::class.java).run {
                                startActivity(this)
                            }
                            toast(resources.getString(R.string.login_tint))
                        }
                    }
                }
            }
        }

}