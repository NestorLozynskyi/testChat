package com.example.chattest.ui.screens.authorization

import com.example.chattest.R
import com.example.chattest.base.BaseFragment
import com.example.chattest.databinding.FragmentAuthorizationBinding
import com.example.chattest.utils.extensions.bindDataTo
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthorizationFragment : BaseFragment<FragmentAuthorizationBinding>(FragmentAuthorizationBinding::class.java) {

    override val viewModel: AuthorizationViewModel by viewModel()

    override fun created() {
        viewModel.connect()
        with(binding) {
            next.setOnClickListener {
                viewModel.login(editLogin.text.toString(), editPass.text.toString())
            }
            registration.setOnClickListener {
                try {
                    navController?.navigate(R.id.action_authorizationFragment_to_registrationFragment)
                } catch (e: Exception){}
            }
            token.setOnClickListener {
                viewModel.loginViaToken()
            }
        }
    }

    override fun observe() {
        bindDataTo(viewModel.ld){
            if (it) {
                viewModel.ld.postValue(null)
                goToChooseChat()
            }
        }
    }
    private fun goToChooseChat(){
        try {
            navController?.navigate(R.id.action_authorizationFragment_to_chooseChatFragment)
        } catch (e: Exception){}
    }
}