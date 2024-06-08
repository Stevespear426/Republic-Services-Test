package com.republic.services.test.spear.presenters

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.republic.services.test.spear.models.Route
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class RouteScreenTests {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createComposeRule()

    @RelaxedMockK
    lateinit var mockViewModel: RoutesScreenViewModel


    @Before
    fun setUp() {
        hiltRule.inject()
        MockKAnnotations.init(this, relaxUnitFun = true)
        every { mockViewModel.state } returns MutableStateFlow(RoutesPageState(true))
    }


    @Test
    fun testLoading() {
        every { mockViewModel.state } returns MutableStateFlow(RoutesPageState(true))
        composeTestRule.setContent {
            RoutesScreen("1", mockViewModel) {

            }
        }
        composeTestRule.onNodeWithTag("Route Loading Test").assertIsDisplayed()
    }

    @Test
    fun testError() {
        every { mockViewModel.state } returns MutableStateFlow(RoutesPageState(false))
        composeTestRule.setContent {
            RoutesScreen("1", mockViewModel) {

            }
        }
        composeTestRule.onNodeWithTag("Route Error Test").assertIsDisplayed()
    }

    @Test
    fun testContent() {
        every { mockViewModel.state } returns MutableStateFlow(
            RoutesPageState(
                route = Route(
                    1,
                    "West Side Residential Route",
                    "R"
                )
            )
        )
        composeTestRule.setContent {
            RoutesScreen("1", mockViewModel) {

            }
        }
        composeTestRule.onNodeWithTag("Route Column Test").assertIsDisplayed()
    }
}