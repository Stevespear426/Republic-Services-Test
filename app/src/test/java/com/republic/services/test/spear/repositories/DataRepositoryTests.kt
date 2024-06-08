package com.republic.services.test.spear.repositories

import com.republic.services.test.spear.MOCK_DRIVERS
import com.republic.services.test.spear.MOCK_ROUTES
import com.republic.services.test.spear.MainCoroutineRule
import com.republic.services.test.spear.models.DataResponse
import com.republic.services.test.spear.repositories.local.AppDatabase
import com.republic.services.test.spear.repositories.local.DriverDao
import com.republic.services.test.spear.repositories.local.RouteDao
import com.republic.services.test.spear.repositories.remote.ApiService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DataRepositoryTests {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var instance: DataRepository

    @RelaxedMockK
    lateinit var apiService: ApiService

    @RelaxedMockK
    lateinit var db: AppDatabase

    private val mockResponse = DataResponse(MOCK_DRIVERS, MOCK_ROUTES)

    private val mockDriverEntities = MOCK_DRIVERS.map { it.toEntity() }

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        instance = DataRepository(apiService, db)
        coEvery { apiService.getData() } returns mockResponse
    }

    @Test
    fun `test get drivers ASC without cache`() = runTest {
        instance.getDriversASC()
        verify(exactly = 2) { db.driverDao() }
        coVerify { apiService.getData() }
        verify { db.insertResponse(mockResponse) }
    }

    @Test
    fun `test get drivers ASC with cache`() = runTest {
        val mockDao = mockk<DriverDao>(relaxed = true)
        every { mockDao.getDriversASC() } returns mockDriverEntities
        every { db.driverDao() } returns mockDao
        instance.getDriversASC()
        verify(exactly = 1) { db.driverDao() }
        coVerify(exactly = 0) { apiService.getData() }
        verify(exactly = 0) { db.insertResponse(mockResponse) }
    }

    @Test
    fun `test get drivers DESC without cache`() = runTest {
        instance.getDriversDESC()
        verify(exactly = 2) { db.driverDao() }
        coVerify { apiService.getData() }
        verify { db.insertResponse(mockResponse) }
    }

    @Test
    fun `test get drivers DESC with cache`() = runTest {
        val mockDao = mockk<DriverDao>(relaxed = true)
        every { mockDao.getDriversDESC() } returns mockDriverEntities
        every { db.driverDao() } returns mockDao
        instance.getDriversDESC()
        verify(exactly = 1) { db.driverDao() }
        coVerify(exactly = 0) { apiService.getData() }
        verify(exactly = 0) { db.insertResponse(mockResponse) }
    }

    @Test
    fun `test get routes without cache`() = runTest {
        instance.getRoutes()
        verify(exactly = 2) { db.routeDao() }
        coVerify { apiService.getData() }
        verify { db.insertResponse(mockResponse) }
    }

    @Test
    fun `test get routes with cache`() = runTest {
        val mockDao = mockk<RouteDao>(relaxed = true)
        every { mockDao.getRoutes() } returns MOCK_ROUTES
        every { db.routeDao() } returns mockDao
        instance.getRoutes()
        verify(exactly = 1) { db.routeDao() }
        coVerify(exactly = 0) { apiService.getData() }
        verify(exactly = 0) { db.insertResponse(mockResponse) }
    }

    @Test
    fun `test fetch data`() = runTest {
        instance.fetchData()
        coVerify(exactly = 1) { apiService.getData() }
        verify { db.insertResponse(mockResponse) }
    }
}