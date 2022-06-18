package com.abdullrahman.ecommerce.presentation.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.abdullrahman.ecommerce.R
import com.abdullrahman.ecommerce.data.data_source.room.customerData.WishList
import com.abdullrahman.ecommerce.data.models.ProductsItem
import com.abdullrahman.ecommerce.databinding.FragmentSearchBinding
import com.abdullrahman.ecommerce.domain.recyclersAdapters.SearchRecyclerAdapter
import com.abdullrahman.ecommerce.domain.recyclersAdapters.onClick.LikeOnClick
import com.abdullrahman.ecommerce.domain.recyclersAdapters.onClick.ProductOnClick
import com.abdullrahman.ecommerce.presentation.product.ProductsViewModel

class SearchFragment : Fragment(),ProductOnClick,LikeOnClick {
    lateinit var binding:FragmentSearchBinding
    lateinit var searchViewModel: SearchViewModel
    lateinit var adapter:SearchRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchViewModel = ViewModelProvider(requireActivity()).get(SearchViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_search, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = mutableListOf<ProductsItem?>()
        adapter = SearchRecyclerAdapter(list.toList(),requireContext(),this,this)

        binding.etSearchBar.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString().length != 0)
                searchViewModel.getProductsSearch(p0.toString())
                else{
                    val list = mutableListOf<ProductsItem?>()
                    searchViewModel.searchProducts.value = list

            }}

            override fun afterTextChanged(p0: Editable?) {

            }

        })
        searchViewModel.searchProducts.observe(viewLifecycleOwner){list->
            Log.e("Search", list.size.toString())
            adapter.addList(list)

        }
        searchViewModel.likedList!!.observe(viewLifecycleOwner){likeList->
            if (!likeList.isNullOrEmpty())
            {
                adapter.addLike(likeList)
            }
        }
        binding.recyclerSearch.layoutManager = GridLayoutManager(requireContext(),2)
        binding.recyclerSearch.setHasFixedSize(true)
        binding.recyclerSearch.adapter = adapter

    }

    override fun onClick(Product: ProductsItem) {
        TODO("Not yet implemented")
    }

    override fun OnClick(WishList: WishList, statue: Boolean) {
        TODO("Not yet implemented")
    }
}