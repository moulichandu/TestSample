package com.example.user.testsample.impls

import android.graphics.ColorSpace
import com.example.user.testsample.modle.Employee
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {
    @GET("employees")
    fun fetChEmploye():
            Call<List<Employee>>
    companion object {
        fun create(): ApiService {

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(
                            RxJava2CallAdapterFactory.create())
                    .addConverterFactory(
                            GsonConverterFactory.create())
                    .baseUrl("http://dummy.restapiexample.com/api/v1/")
                    .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}