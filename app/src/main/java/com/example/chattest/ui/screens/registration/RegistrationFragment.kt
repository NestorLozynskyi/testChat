package com.example.chattest.ui.screens.registration

import com.example.chattest.base.BaseFragment
import com.example.chattest.data.constants.Constants
import com.example.chattest.data.objects.RegistrationRequestData
import com.example.chattest.data.objects.Request
import com.example.chattest.databinding.FragmentRegistrationBinding
import com.example.chattest.network.Connect
import com.example.chattest.utils.shared.SharedManager
import com.google.gson.Gson
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegistrationFragment : BaseFragment<FragmentRegistrationBinding>(FragmentRegistrationBinding::class.java) {

    private val wsConnect: Connect by inject()
    //private val sharedManager: SharedManager by inject()
    private val gson: Gson by inject()

    override fun created() {
        with(binding){
            next.setOnClickListener {
                registration(
                    editLogin.text.toString(),
                    editEmail.text.toString(),
                    editPass.text.toString(),
                    editRePass.text.toString()
                )
            }
        }
    }
    private fun registration(login: String, email: String, pass: String, repass: String){
        wsConnect.ws?.sendText(
            gson.toJson(
                Request(
                    Constants.Request.RegNewAccount,
                    RegistrationRequestData(
                        login, email, pass, repass
                    )
                )
            )
        )
    }
}