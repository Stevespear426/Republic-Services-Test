package com.republic.services.test.spear.repositories.use_cases

import com.republic.services.test.spear.MainCoroutineRule
import com.republic.services.test.spear.models.SortOptions
import com.republic.services.test.spear.repositories.DataRepository
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetDriversUseCaseTests {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var instance: GetDriversUseCase

    @RelaxedMockK
    lateinit var dataRepository: DataRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        instance = GetDriversUseCase(dataRepository)
    }

    @Test
    fun `test SortOptions ASC`() = runTest {
        instance(SortOptions.ASC).first()
        coVerify { dataRepository.getDriversASC() }
    }

    @Test
    fun `test SortOptions DESC`() = runTest {
        instance(SortOptions.DESC).first()
        coVerify { dataRepository.getDriversDESC() }
    }
}