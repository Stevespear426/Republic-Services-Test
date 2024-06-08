package com.republic.services.test.spear.presenters

import com.republic.services.test.spear.MainCoroutineRule
import com.republic.services.test.spear.models.SortOptions
import com.republic.services.test.spear.repositories.DataRepository
import com.republic.services.test.spear.repositories.use_cases.GetDriversUseCase
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class DriversScreenViewModelTests {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var instance: DriversScreenViewModel

    @RelaxedMockK
    lateinit var dataRepository: DataRepository

    @RelaxedMockK
    lateinit var getDriversUseCase: GetDriversUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        instance = DriversScreenViewModel(dataRepository, getDriversUseCase)
        every { getDriversUseCase(any()) } returns flow { emit(listOf(mockk(relaxed = true))) }
    }

    @Test
    fun `test set sort`() = runTest {
        verify(exactly = 1) { getDriversUseCase(SortOptions.ASC) }
        assertEquals(SortOptions.ASC, instance.sort.value)

        instance.setSortOptions(SortOptions.DESC)

        assertEquals(SortOptions.DESC, instance.sort.value)
        verify(exactly = 1) { getDriversUseCase(SortOptions.DESC) }
    }

    @Test
    fun `test refresh data`() = runTest  {
        instance.refreshData()
        coVerify(exactly = 1) { dataRepository.fetchData() }
    }
}