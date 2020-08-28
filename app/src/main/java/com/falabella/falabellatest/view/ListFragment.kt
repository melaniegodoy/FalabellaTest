package com.falabella.falabellatest.view

import android.app.SearchManager
import android.content.Context.SEARCH_SERVICE
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.falabella.falabellatest.R
import com.falabella.falabellatest.model.IndicatorDetail
import com.falabella.falabellatest.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.fragment_list.*


class ListFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var viewModel: ListViewModel
    private val indicatorListAdapter = IndicatorsListAdapter(arrayListOf())
    private lateinit var usernameUuid: String
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_list, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModel.fetchFromRemote()


        indicatorList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = indicatorListAdapter
        }

        val searchManager = activity?.getSystemService(SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        searchView.setOnQueryTextListener(this)

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.indicator.observe(viewLifecycleOwner, Observer { indic ->
            indic?.let {
                indicatorList.visibility = View.VISIBLE
                val indicatorsList = ArrayList<IndicatorDetail>()
                indicatorsList.add(it.bitcoin)
                indicatorsList.add(it.dolar)
                indicatorsList.add(it.dolar_intercambio)
                indicatorsList.add(it.euro)
                indicatorsList.add(it.imacec)
                indicatorsList.add(it.ipc)
                indicatorsList.add(it.ivp)
                indicatorsList.add(it.libra_cobre)
                indicatorsList.add(it.tasa_desempleo)
                indicatorsList.add(it.tpm)
                indicatorsList.add(it.uf)
                indicatorsList.add(it.utm)
                indicatorListAdapter.updateIndicatorList(indicatorsList)
            }
        })

        viewModel.listLoadError.observe(viewLifecycleOwner, Observer { isError ->
            isError?.let {
                tvError.visibility = if(it) View.VISIBLE else View.GONE
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {
                loadingView.visibility = if(it) View.VISIBLE else View.GONE
                if(it){
                    tvError.visibility = View.GONE
                    indicatorList.visibility = View.GONE
                }
            }
        })
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        indicatorListAdapter.filter.filter(newText)
        return false
    }
}