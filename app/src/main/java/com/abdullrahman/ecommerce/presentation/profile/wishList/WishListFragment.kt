package com.abdullrahman.ecommerce.presentation.profile.wishList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.abdullrahman.ecommerce.R
import com.abdullrahman.ecommerce.data.data_source.room.customerData.WishList
import com.abdullrahman.ecommerce.data.models.ProductsItem
import com.abdullrahman.ecommerce.domain.recyclersAdapters.onClick.LikeOnClick
import com.abdullrahman.ecommerce.domain.recyclersAdapters.onClick.ProductOnClick
import com.abdullrahman.ecommerce.presentation.homePage.HomePageViewModel
import com.abdullrahman.ecommerce.presentation.product.ProductsViewModel

class WishListFragment : Fragment(),ProductOnClick, LikeOnClick {

    lateinit var binding:com.abdullrahman.ecommerce.databinding.FragmentWishListBinding
    lateinit var viewModel: HomePageViewModel
    lateinit var productViewModel: ProductsViewModel
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
        viewModel = ViewModelProvider(requireActivity()).get(HomePageViewModel::class.java)
        productViewModel = ViewModelProvider(requireActivity()).get(ProductsViewModel::class.java)
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_wish_list, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.item  = viewModel
        binding.listener = this
        binding.likelistener = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainNavController  = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

        nav = findNavController()
        binding.back.setOnClickListener {
            nav.popBackStack()
        }
    }

    override fun onClick(Product: ProductsItem) {
        productViewModel.getProduct(Product.id!!)
        mainNavController.navigate(R.id.action_buttomNavFragment_to_productItemFragment)

    }

    override fun OnClick(WishList: WishList, statue: Boolean) {
        if (statue)
        {
            viewModel.deletelike(WishList)
        }else
        {
            viewModel.addlike(WishList)
        }
    }


}