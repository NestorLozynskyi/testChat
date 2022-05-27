package com.example.chattest.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.viewbinding.ViewBinding

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
        println("$view")
        navController = Navigation.findNavController(view)

        created()
        observe()
        observeError()
    }

    abstract fun created()

    private fun observeError() {
    }

    protected open fun observe() {}

    private fun restartFragment(unit: Unit?) {
        onStart()
    }

    override fun onStart() {
        super.onStart()
    }
}