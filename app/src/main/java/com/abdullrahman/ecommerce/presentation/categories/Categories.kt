package com.abdullrahman.ecommerce.presentation.categories

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.abdullrahman.ecommerce.R
import com.abdullrahman.ecommerce.databinding.FragmentCategoriesBinding
import com.abdullrahman.ecommerce.utils.dialogs.Loading

class Categories : Fragment() {
    lateinit var viewModel: CategoriesViewModel
    lateinit var binding:FragmentCategoriesBinding
    lateinit var nav: NavController
    lateinit var mainNavController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_categories, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(CategoriesViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val loading = Loading(requireActivity())
        mainNavController  = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        nav = findNavController()
        loading.startLoadingdialog()
        viewModel.collections.observe(requireActivity(), Observer {
            if (it.isNotEmpty()){
                loading.dismissdialog()
                viewModel.setSellectedCollection(binding.tvMen.text.toString().lowercase())

                binding.tvMen.setOnClickListener {
                    viewModel.setSellectedCollection(binding.tvMen.text.toString().lowercase())
                    sellected(binding.tvMen)
                    unsellected( binding.tvKids)
                    unsellected(  binding.tvWomen)

                }
                binding.tvKids.setOnClickListener {
                    viewModel.setSellectedCollection("kid")
                    sellected( binding.tvKids)
                    unsellected( binding.tvMen)
                    unsellected(  binding.tvWomen)

                }
                binding.tvWomen.setOnClickListener {
                    viewModel.setSellectedCollection(binding.tvWomen.text.toString().lowercase())
                    sellected( binding.tvWomen)
                    unsellected( binding.tvMen)
                    unsellected(  binding.tvKids)

                }
                binding.etSearchBar.setOnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        mainNavController.navigate(R.id.action_buttomNavFragment_to_searchFragment)
                    }
                }
                binding.etSearchBar.setOnClickListener {
                    mainNavController.navigate(R.id.action_buttomNavFragment_to_searchFragment)

                }

    }
        })

    }
    fun sellected(tv:TextView)
    {
        tv.setBackgroundColor( Color.BLACK)
        tv.setTextColor(Color.WHITE)
        tv.setTypeface(null, Typeface.BOLD)
    }
    fun unsellected(tv:TextView)
    {
        tv.setBackgroundColor( Color.TRANSPARENT)
        tv.setTextColor(Color.BLACK)
        tv.setTypeface(null, Typeface.NORMAL)
    }
}