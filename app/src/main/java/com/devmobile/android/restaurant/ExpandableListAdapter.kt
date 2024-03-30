package com.devmobile.android.restaurant

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView
import java.util.LinkedList

class ExpandableListAdapter(

    private val context: Context,
    private val foodsSection: HashMap<Int, LinkedList<String>>,

    ) : BaseExpandableListAdapter() {

    override fun getGroupCount(): Int {

        return foodsSection.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {

        return foodsSection[groupPosition]!!.size
    }

    override fun getGroup(groupPosition: Int): LinkedList<String>? {

        return foodsSection[groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): String {

        return foodsSection[groupPosition]!![childPosition]
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
            view = layoutInflater.inflate(R.layout.expandable_group_list_layout, null)
        }

        view?.let {

            setExpandableGroupResources(it, isExpanded, foodsSection[groupPosition]!!)
        }

        return view
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {

        TODO("Not yet implemented")
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {

        return false
    }

    private fun setExpandableGroupResources(
        v: View, isExpanded: Boolean, hashMapGroupListView: LinkedList<String>
    ) {

        val imageFoodSection: ShapeableImageView = v.findViewById(R.id.imageFoodSection)
        val groupListText: MaterialTextView = v.findViewById(R.id.textQuantityAndFood)
        val valueTotalOfGroup: MaterialTextView = v.findViewById(R.id.textTotalValueForSection)

        imageFoodSection.setImageResource(R.drawable.ic_drinks)
        groupListText.text = hashMapGroupListView[0]
        valueTotalOfGroup.text = DecimalNumberFormatted.format(100)
    }
}