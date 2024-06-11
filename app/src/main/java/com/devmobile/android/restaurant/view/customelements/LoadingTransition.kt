package com.devmobile.android.restaurant.view.customelements

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class LoadingTransition : Fragment() {

    private var transitionLayoutID: Int? = null

    companion object {
        @Volatile
        private var myInstance: LoadingTransition? = null

        fun getInstance(layoutID: Int?): LoadingTransition {

            return myInstance ?: synchronized(this) {
                myInstance ?: LoadingTransition().also {
                    it.transitionLayoutID = layoutID
                    myInstance = it
                }
            }
        }
    }

    fun start(fragmentManager: FragmentManager, containerId: Int, layoutID: Int?) {

        myInstance?.let {

            fragmentManager.beginTransaction().add(containerId, it).commit()
            Log.i("Test", "Have an instance and starting")
        }
        Log.i("Test", "Iniciando o Loading Transition 22222")
    }

    fun stop() {

        myInstance?.let {
            Log.i("Test", "Have been an instance 1")
            it.activity?.supportFragmentManager?.beginTransaction()?.remove(it)?.commit()
            Log.i("Test", "Have been an instance 2")
        }

        myInstance = null
        Log.i(
            "Test",
            "Finalizando o Loading Transition 22222\n" + "-----------------------------------------------------"
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return transitionLayoutID?.let { inflater.inflate(it, container, false) }
    }
}
