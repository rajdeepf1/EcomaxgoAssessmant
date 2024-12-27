package com.example.ecomaxgoassessmant.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.ecomaxgoassessmant.R
import com.example.ecomaxgoassessmant.models.Location

class CustomSpinnerAdapterFrom(context: Context, locations: List<Location>) :
    ArrayAdapter<Location>(context, 0, locations) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.spinner_item_layout_from, parent, false)

        // Get the current Location object
        val location = getItem(position)

        val textView: TextView = view.findViewById(R.id.spinner_text)
        textView.text = location?.titleCode // Set the title for the selected item


        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.spinner_item_layout_from, parent, false)

        // Get the current Location object
        val location = getItem(position)

        val textView: TextView = view.findViewById(R.id.spinner_text)
        textView.text = location?.toString() // Show title and coordinates in the dropdown

        return view
    }
}