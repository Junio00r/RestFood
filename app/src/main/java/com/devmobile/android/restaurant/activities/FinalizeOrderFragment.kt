package com.devmobile.android.restaurant.activities

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ExpandableListView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.devmobile.android.restaurant.ExpandableListAdapter
import com.devmobile.android.restaurant.databinding.FragmentFinalizeOrderBinding
import com.google.android.material.button.MaterialButton
import java.util.LinkedList

class FinalizeOrderFragment : FragmentActivity(), View.OnClickListener {
    private lateinit var binding: FragmentFinalizeOrderBinding
    private lateinit var buttonDoOrder: MaterialButton
    private lateinit var expandableListView: ExpandableListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentFinalizeOrderBinding.inflate(this.layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {

        expandableListView = binding.expandableListView
        buttonDoOrder = binding.buttonRealizeOrder
        buttonDoOrder.setOnClickListener(this)

        setExpandableList()
    }

    override fun onClick(v: View?) {

        when (v) {

            buttonDoOrder -> {

                val message = "Seu pedido foi enviado para o balcão do restaurante"
                val toast = Toast.makeText(this, message, Toast.LENGTH_LONG)

                toast.setGravity(Gravity.BOTTOM, 0, 440)
                toast.show()
            }
        }
    }

    private fun setExpandableList() {


        val entradaSection = LinkedList<String>()
        entradaSection.addAll(
            listOf(
                "Comida1",
                "Comida2",
                "Comida3",
                "Comida4",
            )
        )

        val principalSection = LinkedList<String>()
        principalSection.addAll(
            listOf(
                "Comida1",
                "Comida2",
                "Comida3",
                "Comida4",
            )
        )

        val hashMap = HashMap<Int, LinkedList<String>>()
        hashMap[0] = entradaSection
        hashMap[1] = principalSection

        val expandableListAdapter = ExpandableListAdapter(this, hashMap)
        expandableListView.setAdapter(expandableListAdapter)
    }
}