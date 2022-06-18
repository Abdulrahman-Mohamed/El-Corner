package com.abdullrahman.ecommerce.presentation.product

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.abdullrahman.ecommerce.R
import com.abdullrahman.ecommerce.data.data_source.room.customerData.WishList
import com.abdullrahman.ecommerce.data.models.ProductsItem
import com.abdullrahman.ecommerce.databinding.FragmentProductItemsListBinding
import com.abdullrahman.ecommerce.domain.recyclersAdapters.SaleAdapter
import com.abdullrahman.ecommerce.domain.recyclersAdapters.onClick.LikeOnClick
import com.abdullrahman.ecommerce.domain.recyclersAdapters.onClick.ProductOnClick
import com.abdullrahman.ecommerce.presentation.homePage.HomePageViewModel
import com.abdullrahman.ecommerce.utils.dialogs.Loading

class ProductItemsListFragment : Fragment(), ProductOnClick,LikeOnClick {
    lateinit var binding: FragmentProductItemsListBinding
    lateinit var viewModel: ProductsViewModel
    lateinit var homeViewModel: HomePageViewModel
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
        viewModel = ViewModelProvider(requireActivity()).get(ProductsViewModel::class.java)
        homeViewModel= ViewModelProvider(requireActivity()).get(HomePageViewModel::class.java)

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_product_items_list,
            container,
            false
        )
        return binding.root
    }

    lateinit var adapter: SaleAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val loading = Loading(requireActivity())
        binding.root.visibility = View.GONE
        mainNavController  = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

        nav = findNavController()
        loading.startLoadingdialog()
        viewModel.getListLikes().observe(requireActivity()){
                likeList ->


        viewModel.productsList.observe(requireActivity())
        {
            if (!it.isNullOrEmpty() && binding.productsRv.adapter == null) {
                binding.productsRv.layoutManager = GridLayoutManager(requireContext(), 2)
                binding.productsRv.setHasFixedSize(true)

                adapter = SaleAdapter(it,requireContext(), this, this)
                if (likeList != null)
                    adapter.addLike(likeList)
                binding.productsRv.adapter = adapter
                loading.dismissdialog()
            } else if (!it.isNullOrEmpty()) {
                adapter.addList(it)
                loading.dismissdialog()

            }else{
                loading.dismissdialog()
                Toast.makeText(requireContext(),"there is no items",Toast.LENGTH_LONG).show()
            }
            binding.root.visibility = View.VISIBLE


        }
        }
        viewModel.more.observe(requireActivity(), Observer {bool ->
            if (bool) {
                binding.tvLoadMore.visibility = View.VISIBLE
                binding.tvLoadMore.setOnClickListener {
                    if (bool){
                        viewModel.loadProductsPaginating()
                        loading.startLoadingdialog()

                    }
                    else
                        binding.tvLoadMore.visibility = View.GONE

                }
            } else if (!bool) {
                binding.tvLoadMore.visibility = View.GONE

            }
        })


    }

    override fun onClick(Product: ProductsItem) {
        if (isNetworkConnected() && internetIsConnected()) {

            viewModel.getProduct(Product.id!!)
        mainNavController.navigate(R.id.action_productItemsListFragment_to_productItemFragment)
        }else{
            Toast.makeText(context,"No Internet Connection", Toast.LENGTH_LONG).show()

        }
    }

    override fun OnClick(WishList: WishList, statue: Boolean) {
        if (statue)
        {
            homeViewModel.deletelike(WishList)
        }else
        {
            homeViewModel.addlike(WishList)
        }
    }
    private fun isNetworkConnected(): Boolean {
        val cm = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null
    }

    fun internetIsConnected(): Boolean {
        return try {
            val command = "ping -c 1 google.com"
            Runtime.getRuntime().exec(command).waitFor() == 0
        } catch (e: Exception) {
            false
        }
    }
}