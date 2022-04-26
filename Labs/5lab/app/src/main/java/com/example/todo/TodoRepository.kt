package com.example.todo
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TodoRepository {

    val todoApi: Api = retrofit()
        .create(Api::class.java)

    private fun retrofit() = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}