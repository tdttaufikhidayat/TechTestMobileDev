package com.taufikhidayat.techtestmobiledev

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import com.taufikhidayat.techtestmobiledev.core.navigation.AppNavHost
import com.taufikhidayat.techtestmobiledev.ui.theme.TechTestMobileDevTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TechTestMobileDevTheme {
                AppNavHost()
            }
        }
    }
}