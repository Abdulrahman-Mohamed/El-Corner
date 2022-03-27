package com.abdullrahman.ecommerce.presentation.auth

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.abdullrahman.ecommerce.MainActivity
import com.abdullrahman.ecommerce.R
import com.abdullrahman.ecommerce.databinding.FragmentSignInBinding
import com.abdullrahman.ecommerce.utils.dialogs.Loading
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class SignInFragment : Fragment() {
    var is_Completed = false
    lateinit var binding:FragmentSignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = DataBindingUtil.inflate(inflater,R.layout.fragment_sign_in,container,false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val loading = Loading(requireActivity())
        binding.signUp.setOnClickListener {
            loading.startLoadingdialog()

            val handler = Handler()
            handler.postDelayed(Runnable {
                loading.dismissdialog()
                findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)

            },4000)
        }
        binding.btnSignIn.setOnClickListener {

        }
        (requireActivity() as MainActivity).setupActionBar(binding.toolBar)

        binding.etEmail.setOnFocusChangeListener { view, b ->
            validate(b, binding.tiEmail,binding.etEmail)
        }
        binding.etPassword.setOnFocusChangeListener { view, b ->
            validate(b,binding.tiPassword,binding.etPassword)
        }

    }
    fun validate(b:Boolean,viewL:TextInputLayout,viewE:TextInputEditText){
        if(!b &&viewE.text.isNullOrEmpty())
        {
           viewL.error = "This field is required"
        }else{
            is_Completed = true
        }
    }
}