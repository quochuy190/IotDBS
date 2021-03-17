package com.vbeeon.iotdbs.presentation.fragment.account

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.vbeeon.iotdbs.R
import com.vbeeon.iotdbs.data.local.entity.UserEntity
import com.vbeeon.iotdbs.presentation.activity.AccountActivity
import com.vbeeon.iotdbs.presentation.adapter.UserAdapter
import com.vbeeon.iotdbs.presentation.base.BaseFragment
import com.vbeeon.iotdbs.utils.*
import com.vbeeon.iotdbs.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_list_script.fbtnAddScript
import kotlinx.android.synthetic.main.fragment_list_user.*
import kotlinx.android.synthetic.main.toolbar_main.*
import timber.log.Timber


@Suppress("DEPRECATION")
class ListUserFragment : BaseFragment() {

    val mListScript: MutableList<UserEntity> = ArrayList()
    lateinit var mainViewModel: UserViewModel
    lateinit var adapterScript: UserAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_list_user
    }

    override fun initView() {
        imgBack.setOnSafeClickListener {
            Timber.e("ib_toolbar_close.setOnSafeClickListener")
            activity?.onBackPressed()
        }
        tv_toolbar_title.text = "Quản lý người dùng"
        adapterScript = context?.let {
            UserAdapter(it, doneClick = {
                (context as AccountActivity).replaceFragment(UserDetailFragment.newInstance(mListScript[it].name, mListScript[it].listRoom), true)
            },
                deleteItem = {
                    val builder: AlertDialog.Builder = AlertDialog.Builder(context)
                    builder.setMessage("Bạn có chắc chắn muốn xóa tài khoản này không?")
                        .setCancelable(false)
                        .setPositiveButton(getString(R.string.yes), DialogInterface.OnClickListener { dialog, id ->
                            mainViewModel.deleteUser(it)
                        })
                        .setNegativeButton(getString(R.string.no), DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })
                    val alert: AlertDialog = builder.create()
                    alert.show()
                },
                editItem = {
                    (context as AccountActivity).replaceFragment(EditUserFragment.newInstance(it.id,it.name,
                        it.listRoom), true)
                }
            )
        }!!
        rcvListUser.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rcvListUser.apply { adapter = adapterScript }
        // recyclerView.layoutManager = LinearLayoutManager(this)

        fbtnAddScript.setOnSafeClickListener {
            (context as AccountActivity).replaceFragment(InitRoomInAccFragment.newInstance(), true)
        }
    }

    override fun initViewModel() {
        mainViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        mainViewModel.loadAllUser(this)
    }

    override fun observable() {
        mainViewModel.loadUserRes.observe(this, Observer {
            if (it.size > 0) {
                mListScript.clear()
                mListScript.addAll(it)
                adapterScript.setDatas(mListScript)
                tvUserNull.gone()
            } else {
                Timber.e("load null")
                tvUserNull.visible()
            }
        })
    }


}