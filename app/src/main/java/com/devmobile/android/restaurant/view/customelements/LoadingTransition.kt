package com.devmobile.android.restaurant.view.customelements

import android.os.Bundle
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

    fun start(fragmentManager: FragmentManager, containerId: Int) {

        myInstance?.let { fragmentManager.beginTransaction().add(containerId, it).commit() }
    }

    fun stop(fragmentManager: FragmentManager) {

        myInstance?.let { fragmentManager.beginTransaction().remove(it).commit() }

        myInstance = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return transitionLayoutID?.let { inflater.inflate(it, container, false) }
    }
}
