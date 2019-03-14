package com.zhuanghongji.wan.base_common.api

import com.zhuanghongji.wan.base_common.api.datas.*
import io.reactivex.Observable
import retrofit2.http.*

/**
 * 封装了 WAN ANDROID 所有 OPEN API 请求的服务接口
 */
interface ApiService {

    /**
     * 获取轮播图
     *
     * http://www.wanandroid.com/banner/json
     */
    @GET("/banner/json")
    fun getBanner(): Observable<ApiResult<List<Banner>>>

    /**
     * 获取首页置顶文章列表
     * 
     * http://www.wanandroid.com/article/top/json
     */
    @GET("/article/top/json")
    fun getTopArticles(): Observable<ApiResult<MutableList<Article>>>

    /**
     * 获取文章列表
     *
     * http://www.wanandroid.com/article/list/0/json
     * @param pageNum 页码
     */
    @GET("/article/list/{pageNum}/json")
    fun getArticles(@Path("pageNum") pageNum: Int): Observable<ApiResult<Articles>>

    /**
     * 获取知识体系
     *
     * http://www.wanandroid.com/tree/json
     */
    @GET("/tree/json")
    fun getKnowledgeTree(): Observable<ApiResult<List<Knowledges>>>

    /**
     * 知识体系下的文章
     *
     * http://www.wanandroid.com/article/list/0/json?cid=168
     *
     * @param pageNum 页面
     * @param cid cid
     */
    @GET("/article/list/{pageNum}/json")
    fun getKnowledgeArticles(@Path("pageNum") pageNum: Int, @Query("cid") cid: Int)
            : Observable<ApiResult<Articles>>

    /**
     * 导航数据
     *
     * http://www.wanandroid.com/navi/json
     */
    @GET("/navi/json")
    fun getNavigations(): Observable<ApiResult<List<Navigation>>>

    /**
     * 项目数据
     *
     * http://www.wanandroid.com/project/tree/json
     */
    @GET("/project/tree/json")
    fun getProjects(): Observable<ApiResult<List<Project>>>

    /**
     * 项目列表数据
     *
     * http://www.wanandroid.com/project/list/1/json?cid=294
     *
     * @param pageNum 页码
     * @param cid cid
     */
    @GET("/project/list/{pageNum}/json")
    fun getProjectArticles(@Path("pageNum") pageNum: Int, @Query("cid") cid: Int)
            : Observable<ApiResult<Articles>>

    /**
     * 登录
     *
     * http://www.wanandroid.com/user/login
     *
     * @param username 用户名
     * @param password 密码
     */
    @POST("/user/login")
    @FormUrlEncoded
    fun login(@Field("username") username: String,  @Field("password") password: String)
            : Observable<ApiResult<LoginInfo>>

    /**
     * 注册
     *
     * http://www.wanandroid.com/user/register
     *
     * @param username 用户名
     * @param password 密码
     * @param repassword 确认密码
     */
    @POST("/user/register")
    @FormUrlEncoded
    fun register(@Field("username") username: String,
                           @Field("password") password: String,
                           @Field("repassword") repassword: String): Observable<ApiResult<LoginInfo>>

    /**
     * 退出登录
     *
     * http://www.wanandroid.com/user/logout/json
     */
    @GET("/user/logout/json")
    fun logout(): Observable<ApiResult<Any>>

    /**
     *  获取收藏列表
     *
     *  http://www.wanandroid.com/lg/collect/list/0/json
     *
     *  @param pageNum
     */
    @GET("/lg/collect/list/{page}/json")
    fun getCollects(@Path("pageNum") pageNum: Int):
            Observable<ApiResult<Collections<ArticleCollection>>>

    /**
     * 收藏站内文章
     *
     * http://www.wanandroid.com/lg/collect/1165/json
     *
     * @param articleId 文章 id
     */
    @POST("/lg/collect/{articleId}/json")
    fun addCollectArticle(@Path("articleId") articleId: Int): Observable<ApiResult<Any>>

    /**
     * 收藏站外文章
     *
     * http://www.wanandroid.com/lg/collect/add/json
     *
     * @param title 标题
     * @param author 作者
     * @param link 链接
     */
    @POST("/lg/collect/add/json")
    @FormUrlEncoded
    fun addCoolectOutsideArticle(@Field("title") title: String,
                                 @Field("author") author: String,
                                 @Field("link") link: String)
            : Observable<ApiResult<Any>>

    /**
     * 文章列表中取消收藏文章
     *
     * http://www.wanandroid.com/lg/uncollect_originId/2333/json
     *
     * @param articleId 文章 id
     */
    @POST("/lg/uncollect_originId/{articleId}/json")
    fun cancelCollectArticle(@Path("articleId") id: Int): Observable<ApiResult<Any>>

    /**
     * 收藏列表中取消收藏文章
     *
     * http://www.wanandroid.com/lg/uncollect/2805/json
     *
     * @param id id
     * @param originId originId
     */
    @POST("/lg/uncollect/{id}/json")
    @FormUrlEncoded
    fun removeCollectArticle(@Path("id") id: Int, @Field("originId") originId: Int = -1)
            : Observable<ApiResult<Any>>

    /**
     * 搜索热词
     *
     * http://www.wanandroid.com/hotkey/json
     */
    @GET("/hotkey/json")
    fun getHotSearchs(): Observable<ApiResult<MutableList<HotSearch>>>

    /**
     * 搜索
     *
     * http://www.wanandroid.com/article/query/0/json
     *
     * @param pageNum 页面
     * @param key searchKey
     */
    @POST("/article/query/{pageNum}/json")
    @FormUrlEncoded
    fun queryBySearchKey(@Path("pageNum") pageNum: Int,
                         @Field("k") key: String): Observable<ApiResult<Articles>>

    /**
     * 获取TODO列表数据
     *
     * http://wanandroid.com/lg/todo/list/0/json
     *
     * @param type
     */
    @POST("/lg/todo/list/{type}/json")
    fun getTodoByType(@Path("type") type: Int): Observable<ApiResult<AllTodo>>

    /**
     * 获取未完成Todo列表
     *
     * http://wanandroid.com/lg/todo/listnotdo/0/json/1
     *
     * @param type 类型拼接在链接上，目前支持0,1,2,3
     * @param page 拼接在链接上，从1开始
     */
    @POST("/lg/todo/listnotdo/{type}/json/{page}")
    fun getNoTodoList(@Path("page") page: Int, @Path("type") type: Int)
            : Observable<ApiResult<Todos>>

    /**
     * 获取已完成Todo列表
     * http://www.wanandroid.com/lg/todo/listdone/0/json/1
     * @param type 类型拼接在链接上，目前支持0,1,2,3
     * @param page 拼接在链接上，从1开始
     */
    @POST("/lg/todo/listdone/{type}/json/{page}")
    fun getDoneList(@Path("page") page: Int, @Path("type") type: Int)
            : Observable<ApiResult<Todos>>

    /**
     * V2版本 ： 获取TODO列表数据
     *
     * http://www.wanandroid.com/lg/todo/v2/list/页码/json
     *
     * @param page 页码从1开始，拼接在 url 上
     * @param map
     *          status 状态， 1-完成；0未完成; 默认全部展示；
     *          type 创建时传入的类型, 默认全部展示
     *          priority 创建时传入的优先级；默认全部展示
     *          orderby 1:完成日期顺序；2.完成日期逆序；3.创建日期顺序；4.创建日期逆序(默认)；
     */
    @GET("/lg/todo/v2/list/{page}/json")
    fun getTodoList(@Path("page") page: Int, @QueryMap map: MutableMap<String, Any>)
            : Observable<ApiResult<AllTodo>>

    /**
     * 仅更新完成状态Todo
     *
     * http://www.wanandroid.com/lg/todo/done/80/json
     *
     * @param id 拼接在链接上，为唯一标识
     * @param status 0 或 1，传1代表未完成到已完成，反之则反之
     */
    @POST("/lg/todo/done/{id}/json")
    @FormUrlEncoded
    fun updateTodoById(@Path("id") id: Int, @Field("status") status: Int): Observable<ApiResult<Any>>

    /**
     * 删除一条Todo
     *
     * http://www.wanandroid.com/lg/todo/delete/83/json
     *
     * @param todoId
     */
    @POST("/lg/todo/delete/{todoId}/json")
    fun deleteTodoById(@Path("todoId") todoId: Int): Observable<ApiResult<Any>>

    /**
     * 新增一条Todo
     *
     * http://www.wanandroid.com/lg/todo/add/json
     *
     * @param body
     *          title: 新增标题
     *          content: 新增详情
     *          date: 2018-08-01
     *          type: 0
     */
    @POST("/lg/todo/add/json")
    @FormUrlEncoded
    fun addTodo(@FieldMap map: MutableMap<String, Any>): Observable<ApiResult<Any>>

    /**
     * 更新一条Todo内容
     *
     * http://www.wanandroid.com/lg/todo/update/83/json
     *
     * @param todoId todoId
     * @param map
     *          title: 新增标题
     *          content: 新增详情
     *          date: 2018-08-01
     *          status: 0 // 0为未完成，1为完成
     *          type: 0
     */
    @POST("/lg/todo/update/{todoId}/json")
    @FormUrlEncoded
    fun updateTodo(@Path("todoId") todoId: Int, @FieldMap map: MutableMap<String, Any>)
            : Observable<ApiResult<Any>>

    /**
     * 获取公众号列表
     *
     * http://wanandroid.com/wxarticle/chapters/json
     */
    @GET("/wxarticle/chapters/json")
    fun getWXChapters(): Observable<ApiResult<MutableList<WxChapter>>>

    /**
     * 查看某个公众号历史数据
     *
     * http://wanandroid.com/wxarticle/list/405/1/json
     *
     * @param id 公众号 ID
     * @param page 公众号页码
     */
    @GET("/wxarticle/list/{id}/{page}/json")
    fun getWXArticles(@Path("id") id: Int, @Path("page") page: Int)
            : Observable<ApiResult<Articles>>

    /**
     * 在某个公众号中搜索历史文章
     *
     * http://wanandroid.com/wxarticle/list/405/1/json?k=Java
     *
     * @param id 公众号 ID
     * @param key 搜索关键字
     * @param page 公众号页码
     */
    @GET("/wxarticle/list/{id}/{page}/json")
    fun queryWxArticles(@Path("id") id: Int,
                        @Query("k") key: String,
                        @Path("page") page: Int)
            : Observable<ApiResult<Articles>>
}