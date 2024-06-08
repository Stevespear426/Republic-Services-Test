package com.republic.services.test.spear.presenters

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.republic.services.test.spear.models.Driver
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertEquals
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@HiltAndroidTest
class DriversScreenTests {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createComposeRule()

    @RelaxedMockK
    lateinit var mockViewModel: DriversScreenViewModel

    private val mockDrivers = listOf(
        Driver( "9", "Bruce Spruce"),
        Driver( "19", "Andy Garcia"),
        Driver( "14", "Jenny Lowe"),
        Driver( "13", "Amber Shoe"),
        Driver( "6", "Adam Stand"),
        Driver( "15", "Ellis Roth"),
        Driver( "2", "Chris Willis"),
        Driver( "16", "Danika Johnson"),
        Driver( "3", "Archie King"),
        Driver( "25", "Monica Brown"),
    ).map { it.toEntity() }


    @Before
    fun setUp() {
        hiltRule.inject()
        MockKAnnotations.init(this, relaxUnitFun = true)
        every { mockViewModel.state } returns MutableStateFlow(DriversPageState(true))
    }


    @Test
    fun testLoading() {
        every { mockViewModel.state } returns MutableStateFlow(DriversPageState(true))
        composeTestRule.setContent {
            DriversScreen(mockViewModel) {

            }
        }
        composeTestRule.onNodeWithTag("Drivers Loading Test").assertIsDisplayed()
    }

    @Test
    fun testError() {
        every { mockViewModel.state } returns MutableStateFlow(DriversPageState(false))
        composeTestRule.setContent {
            DriversScreen(mockViewModel) {

            }
        }
        composeTestRule.onNodeWithTag("Drivers Error Test").assertIsDisplayed()
    }

    @Test
    fun testContent() {
        every { mockViewModel.state } returns MutableStateFlow(DriversPageState(drivers = mockDrivers))
        composeTestRule.setContent {
            DriversScreen(mockViewModel) {

            }
        }
        composeTestRule.onNodeWithTag("Drivers Column Test").assertIsDisplayed()
    }

    @Test
    fun testDriverClick() {
        var driverId: String? = null
        val latch = CountDownLatch(1)
        every { mockViewModel.state } returns MutableStateFlow(DriversPageState(drivers = mockDrivers))
        composeTestRule.setContent {
            DriversScreen(mockViewModel) {
                driverId = it
                latch.countDown()
            }
        }
        composeTestRule.onNodeWithText("Andy Garcia").performClick()
        latch.await(5, TimeUnit.SECONDS)
        assertEquals("19", driverId)
    }
}