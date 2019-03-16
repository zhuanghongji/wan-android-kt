package com.zhuanghongji.wan.register

import android.content.Intent
import android.view.View
import com.zhuanghongji.wan.R
import com.zhuanghongji.wan.base_common.api.datas.LoginInfo
import com.zhuanghongji.wan.base_common.base.BaseMvpActivity
import com.zhuanghongji.wan.base_common.constants.PreferenceConstant
import com.zhuanghongji.wan.base_common.delegate.Preference
import com.zhuanghongji.wan.base_common.ext.toast
import com.zhuanghongji.wan.event.LoginEvent
import com.zhuanghongji.wan.login.LoginActivity
import com.zhuanghongji.wan.utils.DialogUtil
import kotlinx.android.synthetic.main.activity_register.*
import org.greenrobot.eventbus.EventBus

class RegisterActivity : BaseMvpActivity<RegisterContract.View, RegisterContract.Presenter>(), RegisterContract.View {

    override fun initEvent() {

    }

    override fun initElse() {

    }

    /**
     * local username
     */
    private var user: String by Preference(PreferenceConstant.USERNAME, "")

    /**
     * local password
     */
    private var pwd: String by Preference(PreferenceConstant.PASSWORD_KEY, "")

    override fun createPresenter(): RegisterContract.Presenter = RegisterPresenter()

    private val mDialog by lazy {
        DialogUtil.getWaitDialog(this, getString(R.string.register_ing))
    }

    override fun showLoading() {
        mDialog.show()
    }

    override fun hideLoading() {
        mDialog.dismiss()
    }

    override fun registerSuccess(data: LoginInfo) {
        toast(getString(R.string.register_success))
        isLogin = true
        user = data.username
        pwd = data.password

        EventBus.getDefault().post(LoginEvent(true))
        finish()
    }

    override fun registerFail() {
        isLogin = false
    }

    override fun getLayoutResID(): Int = R.layout.activity_register

    override fun useEventBus(): Boolean = false

    override fun initData() {
    }

    override fun initView() {
        // super.initView()
        btn_register.setOnClickListener(onClickListener)
        tv_sign_in.setOnClickListener(onClickListener)
    }

    override fun start() {
    }

    /**
     * OnClickListener
     */
    private val onClickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.btn_register -> {
                register()
            }
            R.id.tv_sign_in -> {
                Intent(this@RegisterActivity, LoginActivity::class.java).apply {
                    startActivity(this)
                }
                finish()
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
        }
    }

    /**
     * Register
     */
    private fun register() {
        if (validate()) {
            mPresenter?.registerWanAndroid(et_username.text.toString(),
                et_password.text.toString(),
                et_password2.text.toString())
        }
    }

    /**
     * check data
     */
    private fun validate(): Boolean {
        var valid = true
        val username: String = et_username.text.toString()
        val password: String = et_password.text.toString()
        val password2: String = et_password2.text.toString()
        if (username.isEmpty()) {
            et_username.error = getString(R.string.username_not_empty)
            valid = false
        }
        if (password.isEmpty()) {
            et_password.error = getString(R.string.password_not_empty)
            valid = false
        }
        if (password2.isEmpty()) {
            et_password2.error = getString(R.string.confirm_password_not_empty)
            valid = false
        }
        if (password != password2) {
            et_password2.error = getString(R.string.password_cannot_match)
            valid = false
        }
        return valid
    }

}