package com.k1apps.currencyrates.infrustructure.remote

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test

class DefaultCurrencyRateRepositoryTest {
    @MockK
    lateinit var api: CurrencyRateApi

    @InjectMockKs
    private lateinit var repository: DefaultCurrencyRateRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `throwing exception during api call the result should be failure with repository exception`(){
        /*Given*/
        every { api.getRates() } answers  {throw Exception()}

        /*When*/
        val result = repository.getCurrencyRates()

        /*Then*/
        assert(result.isFailure)
        assert(result.exceptionOrNull()!! is RepositoryException)
    }
}