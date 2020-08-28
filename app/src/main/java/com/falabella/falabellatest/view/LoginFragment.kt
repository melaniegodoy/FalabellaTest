package com.falabella.falabellatest.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.falabella.falabellatest.R
import com.falabella.falabellatest.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : Fragment() {

    lateinit var viewModel : LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let {
            viewModel = LoginViewModel(it)
        }

        btnLogin.setOnClickListener {
            viewModel.validateLogin(etUsername.text.toString(), etPassword.text.toString())
        }

        observeViewModel()
    }

    private fun observeViewModel(){
        viewModel.login.observe(viewLifecycleOwner, Observer { correctLogin ->
            correctLogin?.let {
                if (it) {
                    val bundle = bundleOf("usernameUuid" to etUsername.text.toString())
                    findNavController().navigate(R.id.action_loginFragment_to_listFragment, bundle)
                } else {
                    Toast.makeText(
                        context, getString(R.string.login_error_msg),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })

    }

}