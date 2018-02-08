package org.koin.sampleapp.datasource

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.koin.sampleapp.di.testApp
import org.koin.sampleapp.repository.WeatherRepository
import org.koin.standalone.StandAloneContext.closeKoin
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.inject
import org.koin.test.KoinTest

class RepositoryTest : KoinTest {

    val repository by inject<WeatherRepository>()

    @Before
    fun before() {
        startKoin(testApp)
    }

    @After
    fun after() {
        closeKoin()
    }

    @Test
    fun testCachedSearch() {
        val address = "Paris"
        val weather1 = repository.searchWeather(address).blockingGet()
        val weather2 = repository.searchWeather(address).blockingGet()
        assertEquals(weather1, weather2)
    }

    @Test
    fun testGetWeatherSuccess() {
        val address = "Paris"
        repository.searchWeather(address).blockingGet()
        val test = repository.getWeather().test()
        test.awaitTerminalEvent()
        test.assertComplete()
    }

    @Test
    fun testGetWeatherFailed() {
        val test = repository.getWeather().test()
        test.awaitTerminalEvent()
        test.assertValue { list -> list.isEmpty() }
    }
}