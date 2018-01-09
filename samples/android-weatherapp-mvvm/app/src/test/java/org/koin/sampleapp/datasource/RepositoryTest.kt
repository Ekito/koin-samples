package org.koin.sampleapp.datasource

import kotlinx.coroutines.experimental.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.koin.sampleapp.di.testLocalDatasource
import org.koin.sampleapp.repository.WeatherRepository
import org.koin.standalone.StandAloneContext.closeKoin
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.inject
import org.koin.test.KoinTest

class RepositoryTest : KoinTest {

    val repository by inject<WeatherRepository>()

    @Before
    fun before() {
        startKoin(testLocalDatasource)
    }

    @After
    fun after() {
        closeKoin()
    }

    @Test
    fun testCachedSearch() = runBlocking {
        val address = "Paris"
        val weather1 = repository.searchWeather(address)
        val weather2 = repository.searchWeather(address)
        assertEquals(weather1.await(), weather2.await())
    }

    @Test
    fun testGetWeatherSuccess() = runBlocking {
        val address = "Paris"
        repository.searchWeather(address).await()
        val list = repository.getWeather().await()
        Assert.assertTrue(list.isNotEmpty())
    }

    @Test
    fun testGetWeatherFailed() = runBlocking {
        try {
            repository.getWeather().await()
            fail()
        } catch (e: Exception) {
            assertTrue(e is IllegalStateException)
        }
    }
}