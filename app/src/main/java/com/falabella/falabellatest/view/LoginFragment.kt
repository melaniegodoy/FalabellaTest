package com.falabella.falabellatest.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
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

        // This callback will only be called when MyFragment is at least Started.

//        // This callback will only be called when MyFragment is at least Started.
//        val callback: OnBackPressedCallback =
//            object : OnBackPressedCallback(true /* enabled by default */) {
//                override fun handleOnBackPressed() {
//                    // Handle the back button event
//                    Toast.makeText(context, "CERRAR", Toast.LENGTH_LONG).show()
//                }
//            }
        val callback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    Log.e("ALOOOO", "AAAAAAAASDEEFDFERFr")
                }

            })

        observeViewModel()
    }

    private fun observeViewModel(){
        viewModel.login.observe(viewLifecycleOwner, Observer { correctLogin ->
            correctLogin?.let {
                if (it) {
                    val action = LoginFragmentDirections
                        .actionLoginFragmentToListFragment(etUsername.text.toString())
                    view?.let { view -> Navigation.findNavController(view).navigate(action) }
                } else {
                    Toast.makeText(
                        context, "Los datos de usuario son incorrectos",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })

    }

}