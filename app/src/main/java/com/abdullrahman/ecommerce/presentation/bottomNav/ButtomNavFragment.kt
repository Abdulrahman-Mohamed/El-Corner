package com.abdullrahman.ecommerce.presentation.bottomNav

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.abdullrahman.ecommerce.R
import com.abdullrahman.ecommerce.data.models.ProductsItem
import com.abdullrahman.ecommerce.domain.recyclersAdapters.onClick.ProductOnClick
import com.abdullrahman.ecommerce.presentation.product.ProductsViewModel
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView

class ButtomNavFragment : Fragment() {
    lateinit var bottomNavigationView:BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buttom_nav, container, false)
    }
    lateinit var mainNavController:NavController
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nestedNavHostFragment = childFragmentManager.findFragmentById(R.id.bottom_nav_host) as? NavHostFragment
        val navController = nestedNavHostFragment?.navController

        //main nav controller
         mainNavController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

        bottomNavigationView= view.findViewById<BottomNavigationView>(R.id.bottomNavView)

        bottomNavigationView.itemIconTintList = null;
        if (navController != null) {
            bottomNavigationView.setupWithNavController(navController)
        } else {
            throw RuntimeException("Controller not found")
        }
        handleBottomNavBar(navController,bottomNavigationView )
       // addBadge("2")
    }
    var notificationsBadge : View?  = null
    var notificationsBadge2 : View?  = null

    lateinit var notificationItem: BottomNavigationItemView
    lateinit var cartItem: BottomNavigationItemView

    private fun getBadge() : View {
        if (notificationsBadge != null){
            return notificationsBadge!!
        }
        Log.e("tag",bottomNavigationView.childCount.toString())
        val mbottomNavigationMenuView = bottomNavigationView!!.getChildAt(0) as BottomNavigationMenuView
        notificationItem = mbottomNavigationMenuView.getChildAt(3) as BottomNavigationItemView
        cartItem = mbottomNavigationMenuView.getChildAt(2) as BottomNavigationItemView
        notificationsBadge = LayoutInflater.from(requireContext()).inflate(R.layout.custom_badge_layout,
            mbottomNavigationMenuView,false)
        notificationsBadge2  = LayoutInflater.from(requireContext()).inflate(R.layout.custom_badge_layout,
            mbottomNavigationMenuView,false)
        return notificationsBadge!!
    }
    private fun addBadge(count : String) {
        getBadge()
        notificationsBadge?.findViewById<TextView>(R.id.notifications_badge)!!.text = count
        notificationsBadge2?.findViewById<TextView>(R.id.notifications_badge)!!.text = count
        notificationItem.addView(notificationsBadge)
        cartItem.addView(notificationsBadge2)
        //  bottomNavigationView.addView(notificationsBadge)

    }

    private fun handleBottomNavBar(
        navController: NavController,
        bottomNavigationView: BottomNavigationView
    ) {

        navController.addOnDestinationChangedListener{_,destination,_ ->
            if (destination.id == R.id.productItemFragment2)
            {
                bottomNavigationView.visibility = View.GONE
            }
            else{
                bottomNavigationView.visibility = View.VISIBLE

            }

        }
    }

}