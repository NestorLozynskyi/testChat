package com.example.chattest.ui.screens.registration

import com.example.chattest.base.BaseFragment
import com.example.chattest.databinding.FragmentRegistrationBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegistrationFragment : BaseFragment<FragmentRegistrationBinding>(FragmentRegistrationBinding::class.java) {

    override val viewModel: RegistrationViewModel by viewModel()

    override fun created() {
        with(binding){
            next.setOnClickListener {
                viewModel.registration(
                    editLogin.text.toString(),
                    editEmail.text.toString(),
                    editPass.text.toString(),
                    editRePass.text.toString()
                )
            }
        }
    }
}