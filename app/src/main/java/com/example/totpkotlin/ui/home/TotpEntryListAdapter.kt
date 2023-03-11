package com.example.totpkotlin.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.totpkotlin.R
import com.example.totpkotlin.databinding.TotpEntryBinding

class TotpEntryListAdapter : ArrayAdapter<TotpEntry> {

    constructor(context: Context, resource: Int) : super(context, resource)

    constructor(context: Context, resource: Int, objects: MutableList<TotpEntry>) : super(
        context,
        resource,
        objects
    )

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        if (convertView == null) {
            val binding: TotpEntryBinding =
                TotpEntryBinding.inflate(LayoutInflater.from(context), parent, false);

            binding.entry = getItem(position);
            return binding.root;
        }

        TotpEntryBinding.bind(convertView);
        return convertView;
    }
}