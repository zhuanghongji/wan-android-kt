package com.zhuanghongji.wan.common.add.todo

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import com.zhuanghongji.wan.R
import com.zhuanghongji.wan.base_common.api.datas.Todo
import com.zhuanghongji.wan.base_common.base.BaseMvpFragment
import com.zhuanghongji.wan.base_common.constants.PreferenceConstant
import com.zhuanghongji.wan.base_common.ext.formatCurrentDate
import com.zhuanghongji.wan.base_common.ext.stringToCalendar
import com.zhuanghongji.wan.base_common.ext.toast
import com.zhuanghongji.wan.event.RefreshTodoEvent
import com.zhuanghongji.wan.utils.DialogUtil
import kotlinx.android.synthetic.main.fragment_add_todo.*
import org.greenrobot.eventbus.EventBus
import java.util.*

class AddTodoFragment : BaseMvpFragment<AddTodoContract.View, AddTodoContract.Presenter>(), AddTodoContract.View {

    companion object {
        fun getInstance(bundle: Bundle): AddTodoFragment {
            val fragment = AddTodoFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun createPresenter(): AddTodoContract.Presenter = AddTodoPresenter()

    /**
     * Date
     */
    private var mCurrentDate = formatCurrentDate()

    /**
     * 类型
     */
    private var mType: Int = 0
    private var mTodoBean: Todo? = null
    /**
     * 新增，编辑，查看 三种状态
     */
    private var mTypeKey = ""
    /**
     * id
     */
    private var mId: Int? = 0

    /**
     * 优先级  重要（1），一般（0）
     */
    private var mPriority = 0

    private val mDialog by lazy {
        DialogUtil.getWaitDialog(activity!!, getString(R.string.save_ing))
    }

    override fun showLoading() {
        mDialog.show()
    }

    override fun hideLoading() {
        mDialog.dismiss()
    }

    override fun getLayoutResID(): Int = R.layout.fragment_add_todo

    override fun getType(): Int = mType
    override fun getCurrentDate(): String = tv_date.text.toString()
    override fun getTitle(): String = et_title.text.toString()
    override fun getContent(): String = et_content.text.toString()
    override fun getStatus(): Int = mTodoBean?.status ?: 0
    override fun getItemId(): Int = mTodoBean?.id ?: 0
    override fun getPriority(): String = mPriority.toString()

    override fun initView(view: View) {
        // super.initView(view)

        mType = arguments?.getInt(PreferenceConstant.TODO_TYPE) ?: 0
        mTypeKey = arguments?.getString(PreferenceConstant.TYPE_KEY) ?: PreferenceConstant.Type.ADD_TODO_TYPE_KEY

        when (mTypeKey) {
            PreferenceConstant.Type.ADD_TODO_TYPE_KEY -> {
                tv_date.text = formatCurrentDate()
            }
            PreferenceConstant.Type.EDIT_TODO_TYPE_KEY -> {
                mTodoBean = arguments?.getSerializable(PreferenceConstant.TODO_BEAN) as Todo ?: null
                et_title.setText(mTodoBean?.title)
                et_content.setText(mTodoBean?.content)
                tv_date.text = mTodoBean?.dateStr
                mPriority = mTodoBean?.priority ?: 0
                if (mTodoBean?.priority == 0) {
                    rb0.isChecked = true
                    rb1.isChecked = false
                } else if (mTodoBean?.priority == 1) {
                    rb0.isChecked = false
                    rb1.isChecked = true
                }
            }
            PreferenceConstant.Type.SEE_TODO_TYPE_KEY -> {
                mTodoBean = arguments?.getSerializable(PreferenceConstant.TODO_BEAN) as Todo ?: null
                et_title.setText(mTodoBean?.title)
                et_content.setText(mTodoBean?.content)
                tv_date.text = mTodoBean?.dateStr
                et_title.isEnabled = false
                et_content.isEnabled = false
                ll_date.isEnabled = false
                btn_save.visibility = View.GONE
                iv_arrow_right.visibility = View.GONE

                ll_priority.isEnabled = false
                if (mTodoBean?.priority == 0) {
                    rb0.isChecked = true
                    rb1.isChecked = false
                    rb1.visibility = View.GONE
                } else if (mTodoBean?.priority == 1) {
                    rb0.isChecked = false
                    rb1.isChecked = true
                    rb0.visibility = View.GONE
                } else {
                    ll_priority.visibility = View.GONE
                }
            }
        }

        ll_date.setOnClickListener {
            var now = Calendar.getInstance()
            if (mTypeKey == PreferenceConstant.Type.EDIT_TODO_TYPE_KEY) {
                mTodoBean?.dateStr?.let {
                    now = it.stringToCalendar()
                }
            }
            val dpd = android.app.DatePickerDialog(activity,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    mCurrentDate = "$year-${month + 1}-$dayOfMonth"
                    tv_date.text = mCurrentDate
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
            )
            dpd.show()
        }

        btn_save.setOnClickListener {
            when (mTypeKey) {
                PreferenceConstant.Type.ADD_TODO_TYPE_KEY -> {
                    mPresenter?.addTodo()
                }
                PreferenceConstant.Type.EDIT_TODO_TYPE_KEY -> {
                    mPresenter?.updateTodo(getItemId())
                }
            }
        }

        rg_priority.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.rb0) {
                mPriority = 0
                rb0.isChecked = true
                rb1.isChecked = false
            } else if (checkedId == R.id.rb1) {
                mPriority = 1
                rb0.isChecked = false
                rb1.isChecked = true
            }
        }

    }

    override fun lazyLoad() {
    }

    override fun showAddTodo(success: Boolean) {
        if (success) {
            toast(getString(R.string.save_success))
            EventBus.getDefault().post(RefreshTodoEvent(true, mType))
            activity?.finish()
        }
    }

    override fun showUpdateTodo(success: Boolean) {
        if (success) {
            toast(getString(R.string.save_success))
            EventBus.getDefault().post(RefreshTodoEvent(true, mType))
            activity?.finish()
        }
    }

}