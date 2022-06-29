package com.example.chattest.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.viewbinding.ViewBinding
import com.example.chattest.utils.extensions.bindDataTo

abstract class BaseFragment<T : ViewBinding> (private val bindingClass: Class<T>) : Fragment() {

    abstract val viewModel: BaseViewModel?
    var navController: NavController? = null

    lateinit var binding: T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        @Suppress("UNCHECKED_CAST")
        binding = bindingClass.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
            .invoke(null, inflater, container, false) as T
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //if ((activity as MainActivity).ws != null){
            //viewModel?.ldWebSocket?.postValue((activity as MainActivity).ws)
        //}

        navController = Navigation.findNavController(view)
        created()
        observe()
        observeError()

        //println("test5: ${viewModel?.test?.value}")

        /*bindDataTo(viewModel?.connect){
            if (it){
                (activity as MainActivity).reconnect()
            }
        }*/

        /*bindDataTo(viewModel?.ldWebSocket){
            (activity as MainActivity).ws = it
        }*/

        bindDataTo(viewModel?.ldReconnect){
            if (it){
                viewModel?.ldReconnect?.postValue(false)
                onStart()
            } else {
                println("false999")
            }
        }
    }

    abstract fun created()

    private fun observeError() {
    }

    protected open fun observe() {}

    /*private fun restartFragment() {
        onStart()
    }*/

    override fun onStart() {
        super.onStart()
        println("start")
    }



    fun toast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}