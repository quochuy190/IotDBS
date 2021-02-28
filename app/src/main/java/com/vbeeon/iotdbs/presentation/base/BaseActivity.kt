package com.vbeeon.iotdbs.presentation.base

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.vbeeon.iotdbs.R
import com.vsm.ambientmode.utils.display.Toaster

abstract class BaseActivity : AppCompatActivity() {
    lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(provideLayoutId())
        progressDialog = ProgressDialog(this)
        setupObservers()
        setupView(savedInstanceState)
    }
    protected open fun setupObservers() {
    }
    @LayoutRes
    protected abstract fun provideLayoutId(): Int

    protected abstract fun setupView(savedInstanceState: Bundle?)

    fun showMessage(message: String) = Toaster.show(applicationContext, message)

    fun showMessage(@StringRes resId: Int) = showMessage(getString(resId))

    open fun goBack() = onBackPressed()

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0)
            supportFragmentManager.popBackStackImmediate()
        else super.onBackPressed()
    }

    open fun showProgressDialog(isShow: Boolean) {
        if (isShow) {
            showProgress()
        } else {
            dismissProgress()
        }
    }

    fun showProgress() {
        if (!progressDialog.isShowing) progressDialog.show()
    }

    fun dismissProgress() {
        if (progressDialog.isShowing) progressDialog.dismiss()
    }
    open fun showDialogMessage(context: Context?, message: String?) {
        val alertBuilder = AlertDialog.Builder(context)
        alertBuilder.setCancelable(true)
        alertBuilder.setTitle(R.string.title_notify)
        alertBuilder.setMessage(message)
        val alert = alertBuilder.create()
        alert.show()
    }

}