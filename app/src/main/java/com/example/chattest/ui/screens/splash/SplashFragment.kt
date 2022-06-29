package com.example.chattest.ui.screens.splash

import com.example.chattest.R
import com.example.chattest.base.BaseFragment
import com.example.chattest.databinding.FragmentSplashBinding
import com.example.chattest.ui.main.MainActivity
import com.example.chattest.utils.extensions.bindDataTo
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::class.java) {

    override val viewModel: SplashViewModel by viewModel()

    private val listener = false

    override fun created() {
        //viewModel.wsConnect = (activity as MainActivity).ws

        viewModel.init()

        //viewModel.test.postValue("123321")
    }

    override fun observe() {
        bindDataTo(viewModel.ld){
            if (it){
                activity?.runOnUiThread {
                    navController?.navigate(R.id.action_splashFragment_to_authorizationFragment)
                }
            } else {
                activity?.runOnUiThread {
                    if (!listener){
                        binding.tv.text = "Try again"
                        tryAgain()
                    } else{
                        binding.tv.text = "Try again"
                    }
                }
            }
        }
    }

    private fun tryAgain(){
        binding.tv.setOnClickListener {
            viewModel.init()
        }
    }

}