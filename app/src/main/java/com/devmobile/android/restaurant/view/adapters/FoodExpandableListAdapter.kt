package com.devmobile.android.restaurant.viewmodel.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import com.devmobile.android.restaurant.usecase.ZoneNumberFormat
import com.devmobile.android.restaurant.R
import com.devmobile.android.restaurant.usecase.enums.FoodSection
import com.google.android.material.textview.MaterialTextView
import java.util.ArrayList

class FoodExpandableListAdapter(

    private val context: Context,
    private val expandableData: HashMap<FoodSection, ArrayList<Array<*>>>,

    ) : BaseExpandableListAdapter() {

    override fun getGroupCount(): Int {

        return expandableData.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {

        return expandableData[FoodSection.entries[groupPosition]]?.size ?: 0
    }

    override fun getGroup(groupPosition: Int): ArrayList<Array<*>> {

        return expandableData[FoodSection.entries[groupPosition]] ?: ArrayList()
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {

        return  getGroup(groupPosition)[childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {

        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {

        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean {

        return false
    }

    override fun getGroupView(
        groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?
    ): View? {

        var view = convertView

        if (view == null) {

            val layoutInflater = LayoutInflater.from(context)
            view = layoutInflater.inflate(R.layout.layout_group_expandable_list, null)
        }

        view?.let {

            setGroupResources(
                it,
                isExpanded,
                groupPosition,
                FoodSection.entries[groupPosition],
                expandableData[FoodSection.entries[groupPosition]]!!

            )
        }

        return view
    }

    @SuppressLint("SetTextI18n")
    private fun setGroupResources(
        v: View,
        isExpanded: Boolean,
        groupPosition: Int,
        foodSection: FoodSection,
        groupListData: ArrayList<Array<*>>
    ) {

        val textGroupList: MaterialTextView = v.findViewById(R.id.textQuantityAndFood)
        val textValueTotalOfGroup: MaterialTextView = v.findViewById(R.id.textTotalValueForSection)

        var qtdFoodForSection = 0
        var valueTotalForSection = 0F

        groupListData.forEach { qtdFoodForSection += it[4].toString().toInt() }
        groupListData.forEach {
            valueTotalForSection += it[4].toString().toFloat() * it[2].toString().toFloat()
        }

        // Preenche os texts das views
        textGroupList.text = "${qtdFoodForSection}x  ${foodSection.getFoodSectionName()}"
        textValueTotalOfGroup.text = ZoneNumberFormat.format(valueTotalForSection)
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {

        var view = convertView

        if (convertView == null) {

            val layoutInflater = LayoutInflater.from(context)
            view = layoutInflater.inflate(R.layout.layout_child_expandable_list, null)
        }

        view?.let {

            setChildResources(
                it,
                groupPosition,
                childPosition,
                FoodSection.entries[groupPosition],
                expandableData[FoodSection.entries[groupPosition]]!!

            )
        }

        return view!!
    }

    @SuppressLint("SetTextI18n")
    private fun setChildResources(
        v: View,
        groupPosition: Int,
        childPosition: Int,
        foodSection: FoodSection,
        groupListData: ArrayList<Array<*>>
    ) {

        val textChild: MaterialTextView = v.findViewById(R.id.textItemName)
        val textValueTotalOfGroup: MaterialTextView = v.findViewById(R.id.textItemPrice)

        val qtdFoodForSection = groupListData[childPosition][4]
        var valueTotalForQuantityFood = 0F

        // Soma o valor em cada grupo da lista
        valueTotalForQuantityFood = groupListData[childPosition][4].toString()
            .toInt() * groupListData[childPosition][2].toString().toFloat()

        // Preenche os texts das views
        textChild.text =
            "+ ${qtdFoodForSection}x ${groupListData[childPosition][1].toString()}"
        textValueTotalOfGroup.text = ZoneNumberFormat.format(valueTotalForQuantityFood)
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {

        return false
    }
}