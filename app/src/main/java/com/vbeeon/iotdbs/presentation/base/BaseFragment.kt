package com.vbeeon.iotdbs.presentation.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.vsm.ambientmode.utils.display.Toaster


import timber.log.Timber

abstract class BaseFragment : Fragment() {

    abstract fun getLayoutRes(): Int
    abstract fun initView()
    abstract fun initViewModel()
    abstract fun observable()


    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(getLayoutRes(), container, false)

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

    private fun showProgressDialog(isShow: Boolean) {
        //TODO
    }

    fun showMessage(message: String) = context?.let { Toaster.show(it, message) }

    fun showMessage(@StringRes resId: Int) = showMessage(getString(resId))


    protected fun handleError(throwable: Throwable?) {
        Timber.e(throwable)
    }

}