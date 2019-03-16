package com.zhuanghongji.wan.todo

import android.os.Build
import android.view.*
import android.widget.PopupWindow
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.zhuanghongji.wan.R
import com.zhuanghongji.wan.base_common.api.datas.TodoType
import com.zhuanghongji.wan.base_common.base.BaseActivity
import com.zhuanghongji.wan.base_common.constants.PreferenceConstant
import com.zhuanghongji.wan.base_common.manager.DisplayManager
import com.zhuanghongji.wan.event.ColorEvent
import com.zhuanghongji.wan.event.TodoEvent
import com.zhuanghongji.wan.event.TodoTypeEvent
import kotlinx.android.synthetic.main.activity_todo.*
import kotlinx.android.synthetic.main.toolbar.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class TodoActivity : BaseActivity() {
    override fun initEvent() {

    }

    override fun initElse() {

    }

    private var mType = 0

    private var mTodoFragment: TodoFragment? = null

    private lateinit var datas: MutableList<TodoType>
    /**
     * PopupWindow
     */
    private var mSwitchPopupWindow: PopupWindow? = null

    override fun getLayoutResID(): Int = R.layout.activity_todo

    override fun initData() {
        datas = getTypeData()
    }

    override fun initView() {
        toolbar.run {
            title = datas[0].name // getString(R.string.nav_todo)
            setSupportActionBar(this)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        bottom_navigation.run {
            // 以前使用 BottomNavigationViewHelper.disableShiftMode(this) 方法来设置底部图标和字体都显示并去掉点击动画
            // 升级到 28.0.0 之后，官方重构了 BottomNavigationView ，目前可以使用 labelVisibilityMode = 1 来替代
            // BottomNavigationViewHelper.disableShiftMode(this)
            labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED
            setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        }

        floating_action_btn.run {
            setOnClickListener {
                EventBus.getDefault().post(TodoEvent(PreferenceConstant.TODO_ADD, mType))
            }
        }

        val transaction = supportFragmentManager.beginTransaction()
        if (mTodoFragment == null) {
            mTodoFragment = TodoFragment.getInstance(mType)
            transaction.add(R.id.container, mTodoFragment!!, "todo")
        } else {
            transaction.show(mTodoFragment!!)
        }
        transaction.commit()
    }

    override fun start() {
    }

    private fun getTypeData(): MutableList<TodoType> {
        val list = mutableListOf<TodoType>()
        list.add(TodoType(0, "只用这一个", true))
        list.add(TodoType(1, "工作", false))
        list.add(TodoType(2, "学习", false))
        list.add(TodoType(3, "生活", false))
        return list
    }

    /**
     * 初始化 PopupWindow
     */
    private fun initPopupWindow(dataList: List<TodoType>) {
        val recyclerView = layoutInflater.inflate(R.layout.layout_popup_todo, null) as RecyclerView
        val adapter = TodoPopupAdapter()
        adapter.setNewData(dataList)
        adapter.setOnItemClickListener { adapter, view, position ->
            mSwitchPopupWindow?.dismiss()
            val itemData = adapter.data[position] as TodoType
            mType = itemData.type
            toolbar.title = itemData.name
            adapter.data.forEachIndexed { index, any ->
                val item = any as TodoType
                item.isSelected = index == position
            }
            adapter.notifyDataSetChanged()
            bottom_navigation.selectedItemId = R.id.action_notodo
            EventBus.getDefault().post(TodoTypeEvent(mType))
        }
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@TodoActivity)
            this.adapter = adapter
        }
        mSwitchPopupWindow = PopupWindow(recyclerView)
        mSwitchPopupWindow?.apply {
            width = ViewGroup.LayoutParams.WRAP_CONTENT
            height = ViewGroup.LayoutParams.WRAP_CONTENT
            isOutsideTouchable = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                elevation = DisplayManager.dip2px(10F).toFloat()
            }
            // setBackgroundDrawable(ColorDrawable(mThemeColor))
            setOnDismissListener {
                dismiss()
            }
            setTouchInterceptor { v, event ->
                if (event.action == MotionEvent.ACTION_OUTSIDE) {
                    dismiss()
                    true
                }
                false
            }
        }
    }

    /**
     * 展示 PopupWindow
     */
    private fun showPopupWindow(dataList: MutableList<TodoType>) {
        if (mSwitchPopupWindow == null) initPopupWindow(dataList)
        if (mSwitchPopupWindow?.isShowing == true) mSwitchPopupWindow?.dismiss()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mSwitchPopupWindow?.showAsDropDown(toolbar, -DisplayManager.dip2px(5F), 0, Gravity.END)
        } else {
            mSwitchPopupWindow?.showAtLocation(toolbar, Gravity.BOTTOM, -DisplayManager.dip2px(5F), 0)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.clear()
        menuInflater.inflate(R.menu.menu_todo, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_todo_type -> {
                showPopupWindow(datas)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

//    override fun initColor() {
//        super.initColor()
//        refreshColor(ColorEvent(true))
//    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun refreshColor(event: ColorEvent) {
//        if (event.isRefresh) {
//            floating_action_btn.backgroundTintList = ColorStateList.valueOf(mThemeColor)
//        }
    }

    /**
     * NavigationItemSelect监听
     */
    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            return@OnNavigationItemSelectedListener when (item.itemId) {
                R.id.action_notodo -> {
                    EventBus.getDefault().post(TodoEvent(PreferenceConstant.TODO_NO, mType))
                    true
                }
                R.id.action_completed -> {
                    EventBus.getDefault().post(TodoEvent(PreferenceConstant.TODO_DONE, mType))
                    true
                }
                else -> {
                    false
                }
            }
        }

}