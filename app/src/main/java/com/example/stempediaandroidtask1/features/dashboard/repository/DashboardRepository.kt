package com.example.stempediaandroidtask1.features.dashboard.repository

import com.example.stempediaandroidtask1.features.dashboard.models.DashboardItem
import com.example.stempediaandroidtask1.utils.NetworkResponse
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class DashboardRepository @Inject constructor() {
    fun getDashboardItems(dataState: MutableStateFlow<NetworkResponse<ArrayList<DashboardItem>>>) {
        var dashItems = ArrayList<DashboardItem>()
        val db = Firebase.firestore
        val items = db.collection("dash_items")
        items.get()
            .addOnSuccessListener {
                it.documents.forEachIndexed { index, documentSnapshot ->
                    val text: String = documentSnapshot.get("text")?.toString()!!
                    val url: String = documentSnapshot.get("image_url")?.toString()!!
                    dashItems.add(DashboardItem(url, text))
                }
                dataState.value = NetworkResponse.Success(dashItems)
            }
            .addOnFailureListener {
                dataState.value = NetworkResponse.Error(it.message.toString())
            }
    }
}