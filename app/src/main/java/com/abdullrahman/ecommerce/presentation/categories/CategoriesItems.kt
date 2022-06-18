package com.abdullrahman.ecommerce.presentation.categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.abdullrahman.ecommerce.R
import com.abdullrahman.ecommerce.data.models.SmartCollectionsItem
import com.abdullrahman.ecommerce.databinding.FragmentCategoriesBinding
import com.abdullrahman.ecommerce.databinding.FragmentCategoriesItemsBinding
import com.abdullrahman.ecommerce.presentation.product.ProductsViewModel
import com.bumptech.glide.Glide

class CategoriesItems : Fragment() {
    lateinit var viewModel: CategoriesViewModel
    lateinit var  productVIewModel:ProductsViewModel
    lateinit var binding: FragmentCategoriesItemsBinding
    lateinit var nav:NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        productVIewModel = ViewModelProvider(requireActivity()).get(ProductsViewModel::class.java)
        viewModel = ViewModelProvider(requireActivity()).get(CategoriesViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_categories_items, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nav = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

        viewModel.sellectedCollection.observe(requireActivity(), Observer {
            if (!it.isNullOrEmpty() &&it.size == 3)
            {
                Glide.with( binding.tshirts.context)
                    .load(it[2]!!.image!!.src)
                    .fitCenter()
                    .into( binding.tshirts)
                Glide.with( binding.shoes.context)
                    .load(it[1]!!.image!!.src)
                    .fitCenter()
                    .into( binding.shoes)
                Glide.with( binding.accessories.context)
                    .load(it[0]!!.image!!.src)
                    .fitCenter()
                    .into( binding.accessories)
                onButtonClicked(it)
            }
        })
    }

    private fun onButtonClicked(list: List<SmartCollectionsItem?>) {
        binding.layoutShirts.setOnClickListener {
            productVIewModel.getAllProducts(list[2]!!.id!!)
            nav.navigate(R.id.action_buttomNavFragment_to_productItemsListFragment)

        }
        binding.layoutShoes.setOnClickListener {
            productVIewModel.getAllProducts(list[1]!!.id!!)
            nav.navigate(R.id.action_buttomNavFragment_to_productItemsListFragment)

        }
        binding.layoutAccessories.setOnClickListener {
            productVIewModel.getAllProducts(list[0]!!.id!!)
            nav.navigate(R.id.action_buttomNavFragment_to_productItemsListFragment)
        }

    }
}