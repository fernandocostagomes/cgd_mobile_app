package br.com.fgomes.cgd.system

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Criado por fernando.gomes em 28/11/2023.
 */
class ApiServImplem {

    class ApiServImplem {
        companion object {
            fun create(): InterfaceApiService {
                val retrofit = Retrofit.Builder()
                    .baseUrl("http:127.0.0.1:8081/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                return retrofit.create(InterfaceApiService::class.java)
            }
        }
    }
}