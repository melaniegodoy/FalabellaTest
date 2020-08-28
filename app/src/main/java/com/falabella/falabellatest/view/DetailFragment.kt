package com.falabella.falabellatest.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.falabella.falabellatest.BR
import com.falabella.falabellatest.R
import com.falabella.falabellatest.databinding.FragmentDetailBinding
import com.falabella.falabellatest.model.IndicatorDetail
import com.falabella.falabellatest.viewmodel.DetailViewModel


class DetailFragment : Fragment() {

    lateinit var binding: FragmentDetailBinding
    lateinit var viewBinding : View
    private lateinit var indicatorUuid : IndicatorDetail
    private lateinit var viewModel: DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail,
            container, false )
        viewBinding = binding.root

        return viewBinding
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        arguments?.let {
            indicatorUuid = DetailFragmentArgs.fromBundle(it).indicatorUuid!!
            viewModel.showDetail(indicatorUuid)
        }

        observeViewModel()
    }

    private fun observeViewModel(){
        viewModel.indicatorDetail.observe(viewLifecycleOwner, Observer { detail ->
            detail?.let {
                binding.setVariable(BR.indicator, it)
            }
        })
    }

}