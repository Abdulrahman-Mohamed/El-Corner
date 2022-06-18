package com.abdullrahman.ecommerce.presentation.homePage

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.abdullrahman.ecommerce.R
import com.abdullrahman.ecommerce.data.data_source.room.customerData.WishList
import com.abdullrahman.ecommerce.data.models.ProductsItem
import com.abdullrahman.ecommerce.databinding.FragmentHomePageBinding
import com.abdullrahman.ecommerce.domain.SliderAdapter
import com.abdullrahman.ecommerce.domain.recyclersAdapters.onClick.LikeOnClick
import com.abdullrahman.ecommerce.domain.recyclersAdapters.onClick.ProductOnClick
import com.abdullrahman.ecommerce.presentation.product.ProductsViewModel
import com.abdullrahman.ecommerce.utils.dialogs.Loading

class HomePageFragment : Fragment(), ProductOnClick ,LikeOnClick{

    lateinit var binding:FragmentHomePageBinding
    lateinit var viewModel: HomePageViewModel
    lateinit var productViewModel:ProductsViewModel
    var isloadingSale = true
    var isloadingHome = true
    lateinit var nav:NavController
    lateinit var mainNavController:NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{

        viewModel = ViewModelProvider(requireActivity()).get(HomePageViewModel::class.java)
        productViewModel = ViewModelProvider(requireActivity()).get(ProductsViewModel::class.java)
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home_page, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.item  = viewModel
        binding.listener = this
        binding.likelistener = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val loading = Loading(requireActivity())
        mainNavController  = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

        nav = findNavController()
        loading.startLoadingdialog()
        binding.root.visibility = View.GONE
        //onBackPressed()
        viewModel.itemhomePageProducts.observe(requireActivity(), Observer {
            if (!it.isEmpty())
            {
               isloadingHome = false
                dismissLoading(loading)
        }})
        viewModel.itemSaleProducts.observe(requireActivity(), Observer {
            if (!it.isEmpty())
            {
               isloadingSale = false
                dismissLoading(loading)
            }})
        viewModel.homePageCollections.observe(requireActivity(), Observer {
            if (it != null)
            {
                val adapter = SliderAdapter(requireContext(),it.smartCollections!!)
                binding.imageSlider.sliderAdapter = adapter

            }
        })

        binding.viewall.setOnClickListener {
            //nav.navigate(R.id.action_homePageFragment_to_categories)
        }
        binding.etSearchBar.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    mainNavController.navigate(R.id.action_buttomNavFragment_to_searchFragment)
                }
            }
        binding.etSearchBar.setOnClickListener {
            mainNavController.navigate(R.id.action_buttomNavFragment_to_searchFragment)

        }
        binding.viewall.setOnClickListener {
            productViewModel.getAllProducts(266723786845)
            mainNavController.navigate(R.id.action_buttomNavFragment_to_productItemsListFragment)
        }
    }

    private fun dismissLoading(loadings: Loading) {
        if (!isloadingHome && !isloadingSale )
        {
            loadings.dismissdialog()
            binding.root.visibility = View.VISIBLE

        }
    }

    private fun onBackPressed() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {

                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), callback)    }

    override fun onClick(Product: ProductsItem) {
        if (isNetworkConnected() && internetIsConnected()) {

            productViewModel.getProduct(Product.id!!)
            mainNavController.navigate(R.id.action_buttomNavFragment_to_productItemFragment)
        }else{
            Toast.makeText(context,"No Internet Connection", Toast.LENGTH_LONG).show()

        }
    }

    override fun OnClick(WishList: WishList, ststue:Boolean) {
        if (!ststue)
        {
            viewModel.deletelike(WishList)
        }else
        {
            viewModel.addlike(WishList)
        }
       // viewModel.getLikes()
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