package com.k1apps.currencyrates.domain.usecase

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
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

    @MockK
    lateinit var connectionManager: ConnectionManager

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
        every { connectionManager.isOnline() } returns true

        /*When*/
        val currencyRatesFlow = loadCurrencyRates()

        /*Then*/
        val job = launch { currencyRatesFlow.collect() }
        advanceTimeBy(aliveTime)
        verify(exactly = repeatCount) { repository.getCurrencyRates() }
        job.cancel()
    }

    @Test
    fun `there is no internet when try to getting currency rates then the result should be failure`() =
        runTest {
            /*Given*/
            every { connectionManager.isOnline() } returns false

            /*When*/
            val currencyRatesFlow = loadCurrencyRates()

            /*Then*/
            val job = launch {
                currencyRatesFlow.collect {
                    assert(it.isFailure)
                    val exception = it.exceptionOrNull() as CurrencyRatesException
                    assert(exception.error == CurrencyRatesException.Error.NO_CONNECTIVITY)
                }
            }
            delay(1000)
            job.cancel()
        }

    @Test
    fun `there is no connection at beginning and it connects again after four minutes then the getCurrencyRate must be called`() =
        runTest {
            /*Given*/
            every { repository.getCurrencyRates() } returns Result.success(arrayListOf())
            every { connectionManager.isOnline() } returns false andThen false andThen true
            /*When*/
            val currencyRatesFlow = loadCurrencyRates()

            /*Then*/
            val job = launch { currencyRatesFlow.collect() }
            verify(exactly = 0) { repository.getCurrencyRates() }
            advanceTimeBy(2.minutes + 1.seconds)
            verify(exactly = 0) { repository.getCurrencyRates() }
            advanceTimeBy(2.minutes + 1.seconds)
            verify(exactly = 1) { repository.getCurrencyRates() }
            job.cancel()
        }

    @Test
    fun `getCurrencyRate result is failure then the use case result should be failure with invalid data `() =
        runTest {
            /*Given*/
            every { connectionManager.isOnline() } returns true
            every { repository.getCurrencyRates() } returns Result.failure(Exception())

            /*When*/
            val currencyRatesFlow = loadCurrencyRates()

            /*Then*/
            val job = launch {
                currencyRatesFlow.collect {
                    assert(it.isFailure)
                    val exception = it.exceptionOrNull() as CurrencyRatesException
                    assert(exception.error == CurrencyRatesException.Error.INVALID_DATA)
                }
            }
            delay(1000)
            job.cancel()
        }
    @Test
    fun `getCurrencyRate result is empty then the use case result should be failure with invalid data `() =
        runTest {
            /*Given*/
            every { connectionManager.isOnline() } returns true
            every { repository.getCurrencyRates() } returns Result.success(arrayListOf())

            /*When*/
            val currencyRatesFlow = loadCurrencyRates()

            /*Then*/
            val job = launch {
                currencyRatesFlow.collect {
                    assert(it.isFailure)
                    val exception = it.exceptionOrNull() as CurrencyRatesException
                    assert(exception.error == CurrencyRatesException.Error.INVALID_DATA)
                }
            }
            delay(1000)
            job.cancel()
        }

}