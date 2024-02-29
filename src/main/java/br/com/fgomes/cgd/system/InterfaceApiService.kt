package br.com.fgomes.cgd.system

import retrofit2.http.GET

/**
 * Descrever o objetivo da classe.
 *
 * Criado por fernando.gomes em 28/11/2023.
 * Copyright (c) 2023 -  Autotrac Comércio  e Telecomunicações S/A.
 * Todos os direitos reservados.
 */
interface InterfaceApiService {
    @GET("login")
    suspend fun getLogin(): Login
}