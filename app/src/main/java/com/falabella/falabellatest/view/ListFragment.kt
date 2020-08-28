package com.falabella.falabellatest.view

import android.app.SearchManager
import android.content.Context.SEARCH_SERVICE
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog.Builder
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.falabella.falabellatest.R
import com.falabella.falabellatest.model.IndicatorDetail
import com.falabella.falabellatest.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.fragment_list.*


class ListFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var viewModel: ListViewModel
    private val indicatorListAdapter = IndicatorsListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        setHasOptionsMenu(true)
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBackButtonPressed()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        viewModel.refresh()

        indicatorList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = indicatorListAdapter
        }

        val searchManager = activity?.getSystemService(SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        searchView.setOnQueryTextListener(this)

        refreshLayot.setOnRefreshListener {
            indicatorList.visibility = View.GONE
            tvError.visibility = View.GONE
            loadingView.visibility = View.VISIBLE
            viewModel.refresh()
            refreshLayot.isRefreshing = false

        }

        observeViewModel()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.logout_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_logout -> {
                showAlert()
            }
        }
        return super.onOptionsItemSelected(item)
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
                loadingView.visibility = View.GONE
                tvError.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {
                loadingView.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    tvError.visibility = View.GONE
                    indicatorList.visibility = View.GONE
                }
            }
        })
    }



    private fun onBackButtonPressed(){
        requireActivity().onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    showAlert()
                }

            })
    }


    fun showAlert() {
        activity?.let {
            val alertDialog = Builder(it).create()
            alertDialog.setTitle(getString(R.string.logout_title))
            alertDialog.setMessage(getString(R.string.logout_confirmation_msg))
            alertDialog.run {
                setCancelable(false)
                setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.accept_button)
                ) { dialog, which ->
                    findNavController().navigate(R.id.action_listFragment_to_loginFragment)
                    dialog.cancel() }
                setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.cancel_button)) {
                        dialog, which -> dialog.cancel() }
                show()
            }
        }

    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        indicatorListAdapter.filter.filter(newText)
        return false
    }
}