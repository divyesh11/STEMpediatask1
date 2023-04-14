package com.example.stempediaandroidtask1.features.dashboard.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stempediaandroidtask1.features.dashboard.models.DashboardItem
import com.example.stempediaandroidtask1.features.dashboard.repository.DashboardRepository
import com.example.stempediaandroidtask1.utils.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val dashboardRepository: DashboardRepository
) : ViewModel() {

    private val _dashboardItems =
        MutableStateFlow<NetworkResponse<ArrayList<DashboardItem>>>(NetworkResponse.Loading())
    val dashboardItems: StateFlow<NetworkResponse<ArrayList<DashboardItem>>> = _dashboardItems

    fun getDashboardItems() {
        viewModelScope.launch(Dispatchers.IO) {
            dashboardRepository.getDashboardItems(_dashboardItems)
        }
    }
}