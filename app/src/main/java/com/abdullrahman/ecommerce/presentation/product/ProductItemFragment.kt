package com.abdullrahman.ecommerce.presentation.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdullrahman.ecommerce.R
import com.abdullrahman.ecommerce.data.data_source.room.customerData.WishList
import com.abdullrahman.ecommerce.data.models.OptionsItem
import com.abdullrahman.ecommerce.data.models.ProductsItem
import com.abdullrahman.ecommerce.databinding.FragmentProductItemBinding
import com.abdullrahman.ecommerce.domain.ImageSliderAdapter
import com.abdullrahman.ecommerce.domain.recyclersAdapters.ChipsAdapter
import com.abdullrahman.ecommerce.presentation.homePage.HomePageViewModel
import com.abdullrahman.ecommerce.utils.dialogs.Loading

class ProductItemFragment : Fragment() {
    lateinit var nav:NavController
    lateinit var binding: FragmentProductItemBinding
    lateinit var viewModel: ProductsViewModel
    lateinit var homeViewModel: HomePageViewModel
    var statue = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_product_item,
            container,
            false
        )
        viewModel = ViewModelProvider(requireActivity()).get(ProductsViewModel::class.java)
        homeViewModel= ViewModelProvider(requireActivity()).get(HomePageViewModel::class.java)
        return binding.root
    }

    lateinit var list: List<OptionsItem?>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val loading = Loading(requireActivity())

        loading.startLoadingdialog()

        nav = findNavController()

        binding.back.setOnClickListener {
            viewModel.product.value = null
            nav.popBackStack()
        }
        val livedata = viewModel.product

        livedata.observeOnce(requireActivity()) {
            binding.root.visibility = View.GONE
            if (it != null) {
                if (!it.options.isNullOrEmpty()) {
                    list = getOptions(it, "Size")
                    binding.sizesRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false)

                    binding.sizesRv.adapter = ChipsAdapter(list, requireContext(), 0)
                }
                list = getOptions(it, "Color")

                if (!list.isNullOrEmpty()) {
                    binding.colorRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL,false)

                    binding.colorRv.adapter = ChipsAdapter(
                        list,
                        requireContext(),
                        1
                    )
                }

                binding.imageSlider.sliderAdapter = ImageSliderAdapter(it.images!!)
                binding.price.text = it!!.variants?.get(0)!!.price
                binding.name.text = it.title
                binding.tvDescription.text = it.bodyHtml
                homeViewModel.likedList!!.observe(requireActivity()){favList->
                    if (favList!= null ){
                     val isThere = favList!!.filter { item -> item!!.title == it!!.title }

                    if (isThere.isNotEmpty()) {
                   binding.imageView.setImageResource(R.drawable.ic_like_marked)
                        statue = true
                    }

                    }
                    binding.imageView.setOnClickListener {view->
                        val wishlist = WishList(
                            id = it!!.id,
                            image = it!!.image,
                            images = it!!.images,
                            bodyHtml = it!!.bodyHtml,
                            options = it!!.options,
                            title = it!!.title,
                            tags = it!!.tags,
                            variants = it!!.variants,
                            vendor = it!!.vendor,
                            status = it!!.status,
                            productType = it!!.productType,
                        )
                        if (!statue)
                        {
                            binding.imageView.setImageResource(R.drawable.ic_like_marked)

                            homeViewModel.addlike(wishlist)

                        }else
                        {
                            binding.imageView.setImageResource(R.drawable.ic_like_group)

                            homeViewModel.deletelike(wishlist)
                        }
                    }

                }

            }
            loading.dismissdialog()
            binding.root.visibility = View.VISIBLE

        }
    }




private fun getOptions(it: ProductsItem, name: String): List<OptionsItem?> {
    return it!!.options!!.filter { it -> it!!.name == name && it != null }

}
    private fun onBackPressed() {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                 //   nav.navigateUp()
                //    nav.navigate(R.id.action_productItemFragment_to_buttomNavFragment)
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), callback)    }

}
fun <T> LiveData<T>.observeOnce(observer: (T) -> Unit) {
    observeForever(object: Observer<T> {
        override fun onChanged(value: T) {
            removeObserver(this)
            observer(value)
        }
    })
}

fun <T> LiveData<T>.observeOnce(owner: LifecycleOwner, observer: (T) -> Unit) {
    observe(owner, object: Observer<T> {
        override fun onChanged(value: T) {
            removeObserver(this)
            observer(value)
        }
    })
}