package com.vbeeon.iotdbs.presentation.fragment.account

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.vbeeon.iotdbs.R
import com.vbeeon.iotdbs.presentation.base.BaseFragment
import com.vbeeon.iotdbs.utils.openFragment
import com.vbeeon.iotdbs.utils.setOnSafeClickListener
import com.vbeeon.iotdbs.utils.view.CircularSeekBar
import com.vbeeon.iotdbs.viewmodel.MainViewModel
import com.vbeeon.iotdbs.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_create_account.*
import kotlinx.android.synthetic.main.fragment_create_account.edtPass
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_switch_detail_change_color.*


@Suppress("DEPRECATION")
class CreateAccFragment : BaseFragment() {

    lateinit var mViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_create_account
    }

    override fun initView() {
        btnNext.setOnSafeClickListener {
            if (isValidate()){

            }

        }
    }

    override fun initViewModel() {
        mViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
    }

    override fun observable() {

    }

    private fun isValidate(): Boolean {
        if (edtPhone.text.length == 0) {
            showDialogMessage(context, getString(R.string.validate_Username))
            edtPhone.requestFocus()
            edtPhone.setSelection(0)
            return false
        }
        if (edtPass.text.length == 0) {
            showDialogMessage(context, getString(R.string.validate_Pass))
            edtPass.requestFocus()
            edtPass.setSelection(0)
            return false
        }
        if (edtPassComfirm.text.length == 0) {
            showDialogMessage(context, getString(R.string.validate_Pass))
            edtPassComfirm.requestFocus()
            edtPassComfirm.setSelection(0)
            return false
        }
        if (!edtPassComfirm.text.toString().equals(edtPass.text.toString())) {
            showDialogMessage(context, getString(R.string.validate_Pass))
            edtPassComfirm.requestFocus()
            edtPassComfirm.setSelection(edtPassComfirm.text.length)
            return false
        }
        return true
    }
}