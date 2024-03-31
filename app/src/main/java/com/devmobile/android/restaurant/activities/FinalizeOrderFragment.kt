package com.devmobile.android.restaurant.activities

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ExpandableListView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.devmobile.android.restaurant.adapters.ExpandableListAdapter
import com.devmobile.android.restaurant.databinding.FragmentFinalizeOrderBinding
import com.devmobile.android.restaurant.enums.FoodSection
import com.google.android.material.button.MaterialButton
import kotlin.collections.ArrayList

class FinalizeOrderFragment : FragmentActivity(), View.OnClickListener {
    private lateinit var binding: FragmentFinalizeOrderBinding
    private lateinit var buttonDoOrder: MaterialButton
    private lateinit var expandableListView: ExpandableListView
    private val expandableListData = HashMap<FoodSection, ArrayList<Array<*>>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        intent?.let {

            binding = FragmentFinalizeOrderBinding.inflate(this.layoutInflater)
            setContentView(binding.root)

            restoreData()
            init()
        }
    }

    private fun init() {

        expandableListView = binding.expandableListView
        buttonDoOrder = binding.buttonRealizeOrder
        buttonDoOrder.setOnClickListener(this)

        restoreData()
        setExpandableListAdapter()
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

    private fun restoreData() {

        val aux = intent.extras!!.size() / 5
        val groupExpandableListData = ArrayList<Array<*>>()

        for (i in 0 until aux) {

            val foodSectionOrdinal = intent.getIntExtra("foodSectionOrdinal_$i", -1)
            val foodItemArray = arrayOf(
                intent.getLongExtra("foodId_$i", -1),
                intent.getStringExtra("foodName_$i"),
                intent.getFloatExtra("foodPrice_$i", -1F),
                intent.getIntExtra("foodSectionOrdinal_$i", -1),
                intent.getIntExtra("quantityAdded_$i", -1)
            )

            groupExpandableListData.add(foodItemArray)
            expandableListData[FoodSection.entries[foodSectionOrdinal]] = groupExpandableListData
        }
    }

    private fun setExpandableListAdapter() {

        val expandableListAdapter = ExpandableListAdapter(this, expandableListData)
        expandableListView.setAdapter(expandableListAdapter)
    }
}