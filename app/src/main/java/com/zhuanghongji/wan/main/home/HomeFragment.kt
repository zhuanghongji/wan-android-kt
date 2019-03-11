package com.zhuanghongji.wan.main.home

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import cn.bingoogolapple.bgabanner.BGABanner
import com.zhuanghongji.wan.R
import com.zhuanghongji.wan.base_common.api.datas.Article
import com.zhuanghongji.wan.base_common.api.datas.Articles
import com.zhuanghongji.wan.base_common.api.datas.Banner
import com.zhuanghongji.wan.base_common.base.BaseMvpFragment
import com.zhuanghongji.wan.manager.ImageManager
import com.zhuanghongji.wan.widget.SpaceItemDecoration
import kotlinx.android.synthetic.main.activity_splash.*

class HomeFragment: BaseMvpFragment<HomeContract.View, HomeContract.Presenter>(), HomeContract.View {
    
    companion object {
        fun getInstance(): HomeFragment = HomeFragment()
    }

    private val articles = mutableListOf<Article>()
    private lateinit var banners: List<Banner>

    private lateinit var bannerView: View

    private val recyclerViewItemDecoration by lazy {
        activity?.let {
            SpaceItemDecoration(it)
        }
    }

    private val homeAdapter: HomeAdapter by lazy {
        HomeAdapter(this.activity!!, articles)
    }

    private val bannerAdapter: BGABanner.Adapter<ImageView, String> by lazy {
        BGABanner.Adapter<ImageView, String> {
                banner: BGABanner?, itemView: ImageView?, model: String?, position: Int ->
                        ImageManager.load(activity, model, imageView)
        }
    }

    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(activity)
    }

    private var isRefresh = true
    
    override fun createPresenter(): HomeContract.Presenter {
        return HomePresenter()
    }

    override fun getLayoutResID(): Int {
        return R.layout.fragment_refresh_layout
    }

    override fun initView(view: View) {
        super.initView(view)
        mLayou
    }

    override fun lazyLoad() {
        
    }

    override fun scrollToTop() {
//        recyclerView.run {
//            if (linearLayoutManager.findFirstVisibleItemPosition() > 20) {
//
//            }
//        }
    }

    override fun setBanner(banners: List<Banner>) {
        
    }

    override fun setArticles(articles: Articles) {
        
    }

    override fun showCollectSuccess(success: Boolean) {
        
    }

    override fun showCancelCollectSuccess(success: Boolean) {
        
    }


}