#!/bin/sh

cd samples

cd android-weatherapp
cd app
gradle clean testRelease
cd ../..

cd android-weatherapp-mvvm
cd app
gradle clean testRelease
cd ../..

cd kotlin-helloapp
gradle clean test
cd ..

cd spark-hellowebapp
gradle clean test
cd ..

cd ktor-hellowebapp
gradle clean test
cd ..