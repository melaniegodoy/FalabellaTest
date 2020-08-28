package com.falabella.falabellatest.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.falabella.falabellatest.R
import com.falabella.falabellatest.model.IndicatorDetail
import kotlinx.android.synthetic.main.item_indicator.view.*
import java.util.*
import kotlin.collections.ArrayList

class IndicatorsListAdapter(val indicatorsList : ArrayList<IndicatorDetail>) :
    RecyclerView.Adapter<IndicatorsListAdapter.IndicatorViewHolder>(), Filterable {

    var filterListResult = ArrayList<IndicatorDetail>()

    init {
        filterListResult = indicatorsList
    }

    fun updateIndicatorList(newIndicatorList : List<IndicatorDetail>){
        indicatorsList.clear()
        indicatorsList.addAll(newIndicatorList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndicatorViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate( R.layout.item_indicator, parent, false)
        return IndicatorViewHolder(view)
    }

    override fun onBindViewHolder(holder: IndicatorViewHolder, position: Int) {
        val view = holder.view.tvName
        holder.view.tvName.text = filterListResult[position].nombre
        holder.view.tvValue.text = String.format(view.resources.getString(R.string.indicator_value),
            filterListResult[position].valor.toString())
        holder.view.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToDetailFragment()
            action.indicatorUuid = filterListResult[position]
            Navigation.findNavController(it).navigate(action)
        }
    }


    override fun getItemCount() = filterListResult.size

    class IndicatorViewHolder(var view : View) : RecyclerView.ViewHolder(view)

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charString: CharSequence?): FilterResults {
                val charSearch: String = charString.toString()
                filterListResult = if (charSearch.isEmpty()) {
                    indicatorsList
                } else {
                    val resultList = ArrayList<IndicatorDetail>()
                    for (row in indicatorsList) {
                        if (row.codigo.toLowerCase(Locale.US).contains(charSearch.toLowerCase(Locale.US))) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filterListResult
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults?) {
                filterListResult = filterResults?.values as ArrayList<IndicatorDetail>
                notifyDataSetChanged()
            }
        }
    }


}