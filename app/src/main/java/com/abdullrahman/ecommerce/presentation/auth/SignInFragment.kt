package com.abdullrahman.ecommerce.presentation.auth

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.abdullrahman.ecommerce.MainActivity
import com.abdullrahman.ecommerce.R
import com.abdullrahman.ecommerce.databinding.FragmentSignInBinding
import com.abdullrahman.ecommerce.utils.dialogs.Loading
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment() {
    var is_Completed = false
    lateinit var binding: FragmentSignInBinding
    lateinit var viewModel: AuthViewModel
    lateinit var email: String
    lateinit var password: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //println("Fragment SignIn ${viewModel}")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(requireActivity()).get(AuthViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false)
        binding.api = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val loading = Loading(requireActivity())
        val nav = findNavController()
        val mainNav = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

        viewModel.isloading.value = true

        viewModel.CustomerCheck().observe(requireActivity(), Observer {

            if (it.isNotEmpty()) {
                viewModel.isloading.value = false
                viewModel.isSignedIn.value = true


            } else {
                viewModel.isloading.value = false

            }
        })
        binding.signUp.setOnClickListener {
//
            nav.navigate(R.id.action_signInFragment_to_signUpFragment)

        }
        viewModel.isloading.observe(requireActivity(), Observer {
            if (it) {
                loading.startLoadingdialog()
            } else {
                loading.dismissdialog()

            }
        })
//        viewModel.isSignedIn.observe(requireActivity(), Observer {
//            if (it)
//                nav.navigate(R.id.action_signInFragment_to_homePageFragment)
//        })
        (requireActivity() as MainActivity).setupActionBar(binding.toolBar)

        binding.etEmail.setOnFocusChangeListener { view, b ->
            validate(b, binding.tiEmail, binding.etEmail)
        }
        binding.etPassword.setOnFocusChangeListener { view, b ->
            validate(b, binding.tiPassword, binding.etPassword)
        }
        binding.backArrow.setOnClickListener {
            nav.popBackStack()
        }
        binding.toolBar.setNavigationIcon(null)

    }

    fun validate(b: Boolean, viewL: TextInputLayout, viewE: TextInputEditText) {
        if (!b && viewE.text.isNullOrEmpty()) {
            viewL.error = "This field is required"
        }
    }

}
