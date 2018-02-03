package org.koin.sampleapp.repository.data

import org.koin.sampleapp.repository.data.geocode.Geocode
import org.koin.sampleapp.repository.data.geocode.Location


fun Geocode.getLocation(): Location? = results.firstOrNull()?.geometry?.location