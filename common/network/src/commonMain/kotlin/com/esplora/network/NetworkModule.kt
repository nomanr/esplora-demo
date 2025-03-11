package com.esplora.network

import com.esplora.network.api.DefaultEsploraApiService
import com.esplora.network.api.EsploraApiService
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.cache.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.core.qualifier.named
import org.koin.dsl.module

val networkModule = module {
    single {
        Json {
            ignoreUnknownKeys = true
            isLenient = true
            explicitNulls = false
            prettyPrint = true
        }
    }

    single(named("EsploraClient")) {
        HttpClient {
            install(ContentNegotiation) {
                json(get())
            }
            install(Logging) {
                level = LogLevel.ALL
            }
            install(DefaultRequest) {
                url {
                    protocol = URLProtocol.HTTPS
                    host = ApiConfig.TESTNET_API_HOST
                }
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }
    }

    single {
        HttpClient()
    }

    single<EsploraApiService> {
        DefaultEsploraApiService(get(named("EsploraClient")))
    }
}