package com.devmobile.android.restaurant.view.activities

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ExpandableListView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.devmobile.android.restaurant.usecase.ZoneNumberFormat
import com.devmobile.android.restaurant.viewmodel.adapters.FoodExpandableListAdapter
import com.devmobile.android.restaurant.databinding.FragmentFinalizeOrderBinding
import com.devmobile.android.restaurant.usecase.enums.FoodSection
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import kotlin.collections.ArrayList

class FinalizeOrderFragment : FragmentActivity(), View.OnClickListener {
    private lateinit var binding: FragmentFinalizeOrderBinding
    private lateinit var buttonDoOrder: MaterialButton
    private lateinit var expandableListView: ExpandableListView
    private lateinit var textValorTotalDoPedido: MaterialTextView
    private val expandableListData = HashMap<FoodSection, ArrayList<Array<*>>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {

            intent?.let {

                binding = FragmentFinalizeOrderBinding.inflate(this.layoutInflater)
                setContentView(binding.root)

                restoreDataOfIntent()
                init()
            }
        }
    }

    private fun init() {

        textValorTotalDoPedido = binding.textTotalToPay
        expandableListView = binding.expandableListView
        buttonDoOrder = binding.buttonRealizeOrder
        buttonDoOrder.setOnClickListener(this)

        val dataRestored = restoreDataOfIntent()
        setExpandableData(dataRestored)
        setExpandableListAdapter()
        putValueTotalDoPedido()
    }

    private fun setExpandableListAdapter() {

        val expandableListAdapter = FoodExpandableListAdapter(this, expandableListData)
        expandableListView.setAdapter(expandableListAdapter)
    }

    private fun restoreDataOfIntent(): ArrayList<Array<*>> {

        val quantityOfChildFood = intent.extras!!.size() / 5
        val dataRestored = ArrayList<Array<*>>()

        for (i in 0 until quantityOfChildFood) {

            val foodItemArray = arrayOf(
                intent.getLongExtra("foodId_$i", -1),
                intent.getStringExtra("foodName_$i"),
                intent.getFloatExtra("foodPrice_$i", -1F),
                intent.getIntExtra("foodSectionOrdinal_$i", -1),
                intent.getIntExtra("quantityAdded_$i", -1)
            )
            dataRestored.add(foodItemArray)
        }

        return dataRestored
    }

    private fun setExpandableData(dataRestored: ArrayList<Array<*>>) {

        dataRestored.forEach { item ->

            val sectionOrdinal = item[3] as Int
            val foodSection = FoodSection.entries.getOrNull(sectionOrdinal)

            foodSection?.let {
                expandableListData.computeIfAbsent(foodSection) { ArrayList() }.add(item)
            }
        }
    }

    override fun onClick(v: View?) {

        when (v) {

            buttonDoOrder -> placeAnOrder()
        }
    }

    private fun placeAnOrder() {

        val message = "Seu pedido foi enviado para o balcão do restaurante"
        val toast = Toast.makeText(this, message, Toast.LENGTH_LONG)

        toast.setGravity(Gravity.BOTTOM, 0, 440)
        toast.show()
    }

    private fun putValueTotalDoPedido() {

        var valueTotal = 0F

        expandableListData.forEach { group ->
            group.value.forEach { child ->
                valueTotal += child[2].toString().toFloat() * child[4].toString().toInt()
            }
        }

        val textValueTotal = "R$ ${ZoneNumberFormat.format(valueTotal)}"
        textValorTotalDoPedido.text = textValueTotal
    }
}