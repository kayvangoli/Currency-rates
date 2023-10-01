package com.k1apps.currencyrates

import com.k1apps.currencyrates.domain.usecase.CurrencyRateData
import com.k1apps.currencyrates.domain.usecase.CurrencyRatesException
import com.k1apps.currencyrates.domain.usecase.LoadCurrencyRatesUseCase
import com.k1apps.currencyrates.viewmodel.CurrencyRateFlag
import com.k1apps.currencyrates.viewmodel.CurrencyRateViewData
import com.k1apps.currencyrates.viewmodel.CurrencyRatesViewModel
import com.k1apps.currencyrates.viewmodel.formatDateToCustomFormat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockkStatic
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CurrencyRatesViewModelTest {
    @MockK
    lateinit var loadCurrencyRatesUseCase: LoadCurrencyRatesUseCase

    @InjectMockKs
    lateinit var viewModel: CurrencyRatesViewModel

    private lateinit var updateMessage: String

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        mockkStatic("com.k1apps.currencyrates.viewmodel.ExtensionsKt")
        every { formatDateToCustomFormat(any()) } returns "30/03/2023 - 16:00"
        updateMessage = "30/03/2023 - 16:00"
    }

    @Test
    fun `the previous price data is lower than the current data, then the flag should be up`() {
        /*Give*/
        val previousValues = arrayListOf(
            CurrencyRateViewData(
                "currency", 0, 1.0, CurrencyRateFlag.DOWN
            )
        )
        val currentValues = arrayListOf(
            CurrencyRateData("currency", 2.0)
        )
        viewModel.setFakeCurrencyRates(previousValues)


        /*When*/
        coEvery { loadCurrencyRatesUseCase() } returns flowOf(Result.success(currentValues))
        viewModel.loadCurrencyRates()

        /*Then*/
        viewModel.currencyRates.observeForever {
            assert(it[0].currencyRateFlag == CurrencyRateFlag.UP)
        }
        viewModel.updateDate.observeForever {
            assert(it == updateMessage)
        }
    }
    @Test
    fun `the previous price data is greater than the current data, then the flag should be Down`() {
        /*Give*/
        val previousValues = arrayListOf(
            CurrencyRateViewData(
                "currency", 0, 3.0, CurrencyRateFlag.DOWN
            )
        )
        val currentValues = arrayListOf(
            CurrencyRateData("currency", 2.0)
        )
        viewModel.setFakeCurrencyRates(previousValues)


        /*When*/
        coEvery { loadCurrencyRatesUseCase() } returns flowOf(Result.success(currentValues))
        viewModel.loadCurrencyRates()

        /*Then*/
        viewModel.currencyRates.observeForever {
            assert(it[0].currencyRateFlag == CurrencyRateFlag.DOWN)
        }
        viewModel.updateDate.observeForever {
            assert(it == updateMessage)
        }
    }
    @Test
    fun `there is no previous price data then the flag should be UP`() {
        /*Give*/
        val currentValues = arrayListOf(
            CurrencyRateData("currency", 2.0)
        )


        /*When*/
        coEvery { loadCurrencyRatesUseCase() } returns flowOf(Result.success(currentValues))
        viewModel.loadCurrencyRates()

        /*Then*/
        viewModel.currencyRates.observeForever {
            assert(it[0].currencyRateFlag == CurrencyRateFlag.UP)
        }
        viewModel.updateDate.observeForever {
            assert(it == updateMessage)
        }
    }
    @Test
    fun `the useCase returns invalid data error then the currencyRates shouldn't be update and the errorLiveData should post the correct message`() {
        /*Give*/
        val errorMessage = CurrencyRatesException.Error.INVALID_DATA.toString()

        /*When*/
        coEvery { loadCurrencyRatesUseCase() } returns flowOf(
            Result.failure(CurrencyRatesException(CurrencyRatesException.Error.INVALID_DATA))
        )
        viewModel.loadCurrencyRates()

        /*Then*/
        viewModel.currencyRates.observeForever {

        }
        viewModel.errorMessage.observeForever {
            assert(it == errorMessage)
        }
    }
    @Test
    fun `the useCase returns no connectivity error then the currencyRates shouldn't be update and the errorLiveData should post the correct message`() {
        /*Give*/
        val errorMessage = CurrencyRatesException.Error.NO_CONNECTIVITY.toString()

        /*When*/
        coEvery { loadCurrencyRatesUseCase() } returns flowOf(
            Result.failure(CurrencyRatesException(CurrencyRatesException.Error.NO_CONNECTIVITY))
        )
        viewModel.loadCurrencyRates()

        /*Then*/
        viewModel.currencyRates.observeForever {

        }
        viewModel.errorMessage.observeForever {
            assert(it == errorMessage)
        }
    }
}