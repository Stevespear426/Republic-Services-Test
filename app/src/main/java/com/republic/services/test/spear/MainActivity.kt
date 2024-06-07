package com.republic.services.test.spear

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.republic.services.test.spear.presenters.DriversScreen
import com.republic.services.test.spear.presenters.RoutesScreen
import com.republic.services.test.spear.ui.theme.RepublicServicesTestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RepublicServicesTestTheme {
                val navController = rememberNavController()
                NavHost(navController, "drivers") {
                    composable("drivers") {
                        DriversScreen {
                            navController.navigate("route/$it")
                        }
                    }
                    composable("route/{driverId}") {
                        val driverId = requireNotNull(it.arguments?.getString("driverId"))
                        RoutesScreen(driverId) {
                            navController.popBackStack()
                        }
                    }
                }
            }
        }
    }
}
