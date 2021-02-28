package com.vbeeon.iotdbs.presentation.base


import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.vbeeon.iotdbs.R
import com.vsm.ambientmode.utils.display.Toaster
import timber.log.Timber

abstract class BaseFragment : Fragment() {

    abstract fun getLayoutRes(): Int
    abstract fun initView()
    abstract fun initViewModel()
    abstract fun observable()
    lateinit var progressDialog: ProgressDialog
    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(getLayoutRes(), container, false)
        progressDialog = ProgressDialog(context)
        progressDialog.setCancelable(false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
        observable()
    }

    override fun onDestroyView() {

        super.onDestroyView()
    }

    fun showMessage(message: String) = context?.let { Toaster.show(it, message) }

    fun showMessage(@StringRes resId: Int) = showMessage(getString(resId))


    protected fun handleError(throwable: Throwable?) {
        Timber.e(throwable)
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

//    open fun showDialogResetToken(b: Boolean) {
//        val alertBuilder = AlertDialog.Builder(context)
//        alertBuilder.setCancelable(false)
//        alertBuilder.setTitle(R.string.title_notify)
//        alertBuilder.setMessage(R.string.message_reset_token)
//        alertBuilder.setPositiveButton(R.string.lbl_btn_ok, DialogInterface.OnClickListener { dialogInterface, i ->
//            val intent = Intent(context, MainActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
//            startActivity(intent)
//        })
//        val alert = alertBuilder.create()
//        alert.show()
//    }
}