package com.example.indentintern.Client

import com.example.indentintern.response.JokesResponse
import retrofit2.Response
import retrofit2.http.GET

interface getJokes {

    @GET("jokes/random/5")
    suspend fun jokes() : Response<JokesResponse>
}