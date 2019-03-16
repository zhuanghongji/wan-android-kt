package com.zhuanghongji.wan.login

import android.content.Intent
import android.view.View
import com.zhuanghongji.wan.R
import com.zhuanghongji.wan.base_common.api.datas.LoginInfo
import com.zhuanghongji.wan.base_common.base.BaseMvpActivity
import com.zhuanghongji.wan.base_common.constants.PreferenceConstant
import com.zhuanghongji.wan.base_common.delegate.Preference
import com.zhuanghongji.wan.base_common.ext.toast
import com.zhuanghongji.wan.event.LoginEvent
import com.zhuanghongji.wan.register.RegisterActivity
import com.zhuanghongji.wan.utils.DialogUtil
import kotlinx.android.synthetic.main.activity_login.*
import org.greenrobot.eventbus.EventBus

class LoginActivity : BaseMvpActivity<LoginContract.View, LoginContract.Presenter>(), LoginContract.View {

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

    /**
     * token
     */
    private var token: String by Preference(PreferenceConstant.TOKEN_KEY, "")


    override fun createPresenter(): LoginContract.Presenter = LoginPresenter()

    private val mDialog by lazy {
        DialogUtil.getWaitDialog(this, getString(R.string.login_ing))
    }

    override fun showLoading() {
        mDialog.show()
    }

    override fun hideLoading() {
        mDialog.dismiss()
    }

    override fun getLayoutResID(): Int = R.layout.activity_login

    override fun useEventBus(): Boolean = false

    override fun initData() {
    }

    override fun initView() {
        et_username.setText(user)
        btn_login.setOnClickListener(onClickListener)
        tv_sign_up.setOnClickListener(onClickListener)
    }

    override fun start() {
    }

    override fun loginSuccess(data: LoginInfo) {
        toast(getString(R.string.login_success))
        isLogin = true
        user = data.username
        pwd = data.password
        token = data.token

        EventBus.getDefault().post(LoginEvent(true))
        finish()
    }

    override fun loginFail() {
    }

    /**
     * OnClickListener
     */
    private val onClickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.btn_login -> {
                login()
            }
            R.id.tv_sign_up -> {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)
                finish()
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
        }
    }

    /**
     * Login
     */
    private fun login() {
        if (validate()) {
            mPresenter?.loginWanAndroid(et_username.text.toString(), et_password.text.toString())
        }
    }

    /**
     * Check UserName and PassWord
     */
    private fun validate(): Boolean {
        var valid = true
        val username: String = et_username.text.toString()
        val password: String = et_password.text.toString()

        if (username.isEmpty()) {
            et_username.error = getString(R.string.username_not_empty)
            valid = false
        }
        if (password.isEmpty()) {
            et_password.error = getString(R.string.password_not_empty)
            valid = false
        }
        return valid

    }

}
