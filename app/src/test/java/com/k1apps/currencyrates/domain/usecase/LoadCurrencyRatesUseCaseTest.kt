package com.k1apps.currencyrates.domain.usecase

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalCoroutinesApi::class)
class LoadCurrencyRatesUseCaseTest {

    @MockK
    lateinit var repository: CurrencyRateRepository

    @InjectMockKs
    private lateinit var loadCurrencyRates: LoadCurrencyRatesUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `every two minutes the getCurrencyRates in repository should be invoked`() = runTest {
        /*Given*/
        val aliveTime = 4.minutes + 1.seconds
        val repeatCount = 3
        every { repository.getCurrencyRates() } returns Result.success(arrayListOf())

        /*When*/
        val currencyRatesFlow = loadCurrencyRates()

        /*Then*/
        val job = launch { currencyRatesFlow.collect()}
        advanceTimeBy(aliveTime)
        verify(exactly = repeatCount) { repository.getCurrencyRates() }
        job.cancel()
    }
}