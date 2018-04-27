package org.koin.sample

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.DefaultHeaders
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.commandLineEnvironment
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.koin.dsl.module.applicationContext
import org.koin.ktor.ext.inject
import org.koin.standalone.StandAloneContext.startKoin

interface HelloRepository {
    fun getHello(): String
}

class HelloRepositoryImpl : HelloRepository {
    override fun getHello(): String = "Ktor & Koin"
}

interface HelloService {
    fun sayHello(): String
}

class HelloServiceImpl(val helloRepository: HelloRepository) : HelloService {
    override fun sayHello() = "Hello ${helloRepository.getHello()} !"
}

fun Application.main() {
    install(DefaultHeaders)
    install(CallLogging)

    val service: HelloService by inject()
    routing {
        get("/hello") {
            call.respondText(service.sayHello())
        }
    }
}

val helloAppModule = applicationContext {
    bean { HelloServiceImpl(get()) as HelloService }
    bean { HelloRepositoryImpl() as HelloRepository }
}

fun main(args: Array<String>) {
    // Start Koin
    startKoin(arrayListOf(helloAppModule))
    // Start Ktor
    embeddedServer(Netty, commandLineEnvironment(args)).start()
}
