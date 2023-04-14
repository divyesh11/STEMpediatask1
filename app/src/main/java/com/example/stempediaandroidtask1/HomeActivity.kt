package com.example.stempediaandroidtask1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.stempediaandroidtask1.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var homeBinding: ActivityHomeBinding
//    private val dashboardViewModel : DashboardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        homeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(homeBinding.root)
    }
}