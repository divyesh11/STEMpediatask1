package com.example.stempediaandroidtask1.features.dashboard.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.stempediaandroidtask1.databinding.FragmentDashboardBinding
import com.example.stempediaandroidtask1.databinding.ItemDashboardBinding
import com.example.stempediaandroidtask1.features.dashboard.models.DashboardItem
import com.example.stempediaandroidtask1.features.dashboard.viewmodel.DashboardViewModel
import com.example.stempediaandroidtask1.utils.NetworkResponse
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DashboardFragment : Fragment() {
    val TAG = "DashboardFragment"
    private var _viewBinding: FragmentDashboardBinding? = null
    private val viewBinding get() = _viewBinding!!
    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dashboardViewModel = ViewModelProvider(requireActivity())[DashboardViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentDashboardBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDataForDashboard()
    }

    private fun getDataForDashboard() {
        dashboardViewModel.getDashboardItems()
        lifecycleScope.launch {
            dashboardViewModel.dashboardItems
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    handleNetworkResponseForDashboardItems(it)
                }
        }
    }

    private fun handleNetworkResponseForDashboardItems(networkResponse: NetworkResponse<ArrayList<DashboardItem>>) {
        var items = ArrayList<ItemDashboardBinding>()
        items.add(viewBinding.dashItem1)
        items.add(viewBinding.dashItem2)
        items.add(viewBinding.dashItem3)
        items.add(viewBinding.dashItem4)
        items.add(viewBinding.dashItem5)

        when (networkResponse) {
            is NetworkResponse.Loading -> {
            }
            is NetworkResponse.Error -> {
                Toast.makeText(this.requireContext(), "Some Error Occurred", Toast.LENGTH_LONG)
                    .show()
            }
            is NetworkResponse.Success -> {
                val dashboardItems = networkResponse.data
                var i = 0
                while (i < 5) {
                    fillItemWithData(items[i], dashboardItems?.get(i))
                    i += 1
                }
            }
        }
    }

    private fun fillItemWithData(item: ItemDashboardBinding, itemData: DashboardItem?) {
        item.imageText.text = itemData?.text
        Picasso.get().load(itemData?.url).into(item.imageView)
    }
}