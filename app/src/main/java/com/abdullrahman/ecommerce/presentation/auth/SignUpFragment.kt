package com.abdullrahman.ecommerce.presentation.auth

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.abdullrahman.ecommerce.R
import com.abdullrahman.ecommerce.databinding.FragmentSignUpBinding
import com.abdullrahman.ecommerce.data.models.CustomerMokup
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class SignUpFragment : Fragment() {
   lateinit var binding:FragmentSignUpBinding
   lateinit var password:String
   lateinit var firstName:String
   lateinit var lastName:String
   lateinit var phone:String
   lateinit var email:String
   lateinit var customer: CustomerMokup
   lateinit var viewModel: AuthViewModel
   lateinit var nav:NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_sign_up,container,false)
        viewModel = ViewModelProvider(requireActivity()).get(AuthViewModel::class.java)
        return binding.root
                }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nav = findNavController()
        binding.btnSignUp.setOnClickListener {
            onSignUpButtonClicked()
        }
        binding.backArrow.setOnClickListener {
            nav.popBackStack()
        }
        binding.signIn.setOnClickListener {
            nav.navigate(R.id.action_signUpFragment_to_signInFragment)
        }
        focusChange()
        setEditText()
        binding.toolBar.setNavigationIcon(null)

    }
    private fun onSignUpButtonClicked(){
        if (this::password.isInitialized &&this::firstName.isInitialized &&this::lastName.isInitialized &&
            this::email.isInitialized &&this::phone.isInitialized)
        {
            customer = CustomerMokup(firstName = firstName+" "+lastName, lastName = password, email = email, password = password, passwordConfirmation = password, phone = phone)
            viewModel.customer.value = customer
            nav.navigate(R.id.action_signUpFragment_to_addAddressFragment)

        }
    }
    private fun focusChange(){
        binding.etFirstName.setOnFocusChangeListener { view, b ->
            validate(b, binding.tiFirstName,binding.etFirstName)
        }
        binding.etLastName.setOnFocusChangeListener { view, b ->
            validate(b, binding.tiLastName,binding.etLastName)
        }
        binding.etPhone.setOnFocusChangeListener { view, b ->
            validate(b, binding.tiPhone,binding.etPhone)
        }
        binding.etPassword.setOnFocusChangeListener { view, b ->
            validate(b, binding.tiPassword,binding.etPassword)
        }
        binding.etEmail.setOnFocusChangeListener { view, b ->
            validate(b, binding.tiEmail,binding.etEmail)
        }
    }
    fun validate(b:Boolean, viewL: TextInputLayout, viewE: TextInputEditText){
        if(!b &&viewE.text.isNullOrEmpty())
        {
            viewL.error = "This field is required"
        }
    }

    private fun setEditText() {

        binding.etPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                password = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        binding.etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                email = p0.toString()

            }

        })
        binding.etFirstName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                firstName = p0.toString()

            }

        })
        binding.etLastName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                lastName = p0.toString()

            }

        })
        binding.etPhone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                phone = p0.toString()

            }

        })
    }
}