package com.example.todo
import retrofit2.Response
import retrofit2.http.GET

interface Api {
    @GET("todos")
    suspend fun getTodos(): Response<List<Todo>>
}