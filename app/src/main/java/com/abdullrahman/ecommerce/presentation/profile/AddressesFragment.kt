package com.abdullrahman.ecommerce.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.abdullrahman.ecommerce.R
import com.abdullrahman.ecommerce.data.models.AddressesItem
import com.abdullrahman.ecommerce.databinding.FragmentAddressesBinding
import com.abdullrahman.ecommerce.domain.AddressesAdapter
import com.abdullrahman.ecommerce.domain.Constants
import com.abdullrahman.ecommerce.domain.recyclersAdapters.onClick.AddressOnClick
import com.abdullrahman.ecommerce.presentation.auth.AuthViewModel
import com.abdullrahman.ecommerce.presentation.product.observeOnce
import com.abdullrahman.ecommerce.utils.dialogs.Loading
import com.google.android.material.snackbar.Snackbar


class AddressesFragment : Fragment(),AddressOnClick {
    lateinit var binding:FragmentAddressesBinding
    lateinit var viewModel:AuthViewModel
    lateinit var nav:NavController
    lateinit var loading:Loading
    var lista = mutableListOf<AddressesItem?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(requireActivity()).get(AuthViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_addresses,container,false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loading = Loading(requireActivity())
        binding.root.visibility = View.GONE
        loading.startLoadingdialog()
        nav = findNavController()

                viewModel.getAddresses(Constants.id)

                viewModel.addresses.observe(viewLifecycleOwner)
                {
                    if (!it.isNullOrEmpty()){
                        lista = it.toMutableList()
                        binding.rvAddresses.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                        val adapter = AddressesAdapter(lista, requireContext(), this)
                        binding.rvAddresses.adapter = adapter
                        loading.dismissdialog()
                        binding.root.visibility = View.VISIBLE
                        swipeToDelete(binding.rvAddresses, it, adapter)

                    }

            }




        binding.button.setOnClickListener {
            nav.navigate(R.id.action_addressesFragment_to_addAddressesFragment)

        }
        onBackPressed()
    }

    private fun swipeToDelete(
        rvAddresses: RecyclerView,
        list: List<AddressesItem?>,
        adapter: AddressesAdapter
    ) {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT)
        {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                // this method is called
                // when the item is moved.
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int)
            {
                if (viewHolder.adapterPosition != 0){
                // this method is called when we swipe our item to right direction.
                // on below line we are getting the item at a particular position.
                // below line is to get the position
                // of the item at that position.
                val position = viewHolder.adapterPosition
                val address = lista[position]
                var customerId:Long = 0
                println(position)
                // this method is called when item is swiped.
                // below line is to remove item from our array list.

                        viewModel.delete(lista[position]!!.id!!, Constants.id)
                        customerId = Constants.id
                       // viewModel.getAddresses(customerId)
                        adapter.remove(position)


                // below line is to notify our item is removed from adapter.

                // below line is to display our snackbar with action.
                Snackbar.make(rvAddresses," address deleted", Snackbar.LENGTH_LONG)
                    .setAction("Undo",
                        View.OnClickListener { // adding on click listener to our action of snack bar.
                            // below line is to add our item to array list with a position.
                            viewModel.addressItem.value = address
                            viewModel.addAddress(customerId)
                            adapter.add(address!!,position)
                            viewModel.addressAdded.observe(viewLifecycleOwner){
                                if (it){

                                viewModel.getAddresses(customerId)
                                viewModel.addressAdded.value = false
                                }
                            }

                        }).show()
            }else if (viewHolder.adapterPosition == 0){
                adapter.notifyDataSetChanged()
                Toast.makeText(requireContext(),"Default Address cannot be Deleted",Toast.LENGTH_LONG).show()
            }
            }// at last we are adding this
            // to our recycler view.
        }).attachToRecyclerView(rvAddresses)
    }

    private fun onBackPressed()
    {
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */)
            {
                override fun handleOnBackPressed()
                {
                nav.popBackStack()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), callback)
    }
    override fun onClick(address: AddressesItem)
    {
        viewModel.addressItem.value = address
        nav.navigate(R.id.action_addressesFragment_to_updateAddressFragment)
    }
}