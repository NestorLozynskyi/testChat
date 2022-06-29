package com.example.chattest.ui.screens.authorization

import com.example.chattest.R
import com.example.chattest.base.BaseFragment
import com.example.chattest.databinding.FragmentAuthorizationBinding
import com.example.chattest.ui.main.MainActivity
import com.example.chattest.utils.extensions.bindDataTo
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthorizationFragment : BaseFragment<FragmentAuthorizationBinding>(FragmentAuthorizationBinding::class.java) {

    override val viewModel: AuthorizationViewModel by viewModel()

    override fun onStart() {
        super.onStart()
        viewModel.listenerWS()
    }

    override fun created() {

        //viewModel.wsConnect = (activity as MainActivity).ws

        //viewModel.init()
        viewModel.loginViaToken()
        with(binding) {
            login.setOnClickListener {
                viewModel.login(editLogin.text.toString(), editPass.text.toString())
            }
            registration.setOnClickListener {
                try {
                    navController?.navigate(R.id.action_authorizationFragment_to_registrationFragment)
                } catch (e: Exception){}
            }
        }
    }

    override fun observe() {
        bindDataTo(viewModel.ld){
            try {
                navController?.navigate(R.id.action_authorizationFragment_to_chooseChatFragment)
            } catch (e: Exception) {
            }
        }
        /*bindDataTo(viewModel.ldWebSocket){
            viewModel.listenerWS()
            viewModel.loginViaToken()
        }*/
    }



}