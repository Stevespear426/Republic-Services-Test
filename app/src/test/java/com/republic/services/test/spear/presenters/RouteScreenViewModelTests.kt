package com.republic.services.test.spear.presenters

import com.republic.services.test.spear.MOCK_ROUTES
import com.republic.services.test.spear.MainCoroutineRule
import com.republic.services.test.spear.models.Route
import com.republic.services.test.spear.repositories.DataRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RouteScreenViewModelTests {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var instance: RoutesScreenViewModel

    @RelaxedMockK
    lateinit var dataRepository: DataRepository

    private val firstRRoute = Route(1, "West Side Residential Route", "R")
    private val secondCRoute = Route(5, "East Side Commercial Route", "C")
    private val lastIRoute = Route(12, "South Side Industrial Route", "I")

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        instance = RoutesScreenViewModel(dataRepository)
        coEvery { dataRepository.getRoutes() } returns MOCK_ROUTES
    }

    @Test
    fun `test Bruce Spruce #9`() = runTest {
        instance.getRouteForDriver(9)
        assertEquals(
            Route(9, "North Side Industrial Route", "I"),
            instance.state.value.route
        )
    }

    @Test
    fun `test Andy Garcia #19`() = runTest {
        instance.getRouteForDriver(19)
        assertEquals(
            lastIRoute,
            instance.state.value.route
        )
    }

    @Test
    fun `test Jenny Lowe #14`() = runTest {
        instance.getRouteForDriver(14)
        assertEquals(
            firstRRoute,
            instance.state.value.route
        )
    }

    @Test
    fun `test Amber Shoe #13`() = runTest {
        instance.getRouteForDriver(13)
        assertEquals(
            lastIRoute,
            instance.state.value.route
        )
    }

    @Test
    fun `test Adam Stand #6`() = runTest {
        instance.getRouteForDriver(6)
        assertEquals(
            Route(6, "East Side Industrial Route", "I"),
            instance.state.value.route
        )
    }

    @Test
    fun `test Ellis Roth #15`() = runTest {
        instance.getRouteForDriver(15)
        assertEquals(
            secondCRoute,
            instance.state.value.route
        )
    }

    @Test
    fun `test Chris Willis #2`() = runTest {
        instance.getRouteForDriver(2)
        assertEquals(
            Route(2, "West Side Commercial Route", "C"),
            instance.state.value.route
        )
    }

    @Test
    fun `test Danika Johnson #16`() = runTest {
        instance.getRouteForDriver(16)
        assertEquals(
            firstRRoute,
            instance.state.value.route
        )
    }

    @Test
    fun `test Archie King #3`() = runTest {
        instance.getRouteForDriver(16)
        assertEquals(
            firstRRoute,
            instance.state.value.route
        )
    }

    @Test
    fun `test Monica Brown #25`() = runTest {
        instance.getRouteForDriver(25)
        assertEquals(
            secondCRoute,
            instance.state.value.route
        )
    }
}