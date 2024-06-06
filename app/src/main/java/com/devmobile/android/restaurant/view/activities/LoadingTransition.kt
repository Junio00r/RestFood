package com.devmobile.android.restaurant.view.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

class LoadingTransition : Fragment() {

    private var transitionLayoutID: Int? = null

    companion object {
        @Volatile
        private var myInstance: LoadingTransition? = null

        @OptIn(InternalCoroutinesApi::class)
        fun getInstance(layoutID: Int?): LoadingTransition {

            return myInstance ?: synchronized(this) {
                myInstance ?: LoadingTransition().also {
                    it.transitionLayoutID = layoutID
                    myInstance = it
                }
            }
        }
    }

    fun startTransition(fragmentManager: FragmentManager, containerId: Int, layoutID: Int?) {

        myInstance?.let {

            fragmentManager.beginTransaction().add(containerId, it).commit()
        }
    }

    fun stopTransition() {

        myInstance?.let {

            it.activity?.supportFragmentManager?.beginTransaction()?.remove(it)?.commit()
        }
        myInstance = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return transitionLayoutID?.let { inflater.inflate(it, container, false) }
    }
}
