package com.example.facesdkdemo.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Network {
    companion object {

        /** Retorna uma Instância do Client Retrofit para Requisições
         */
        fun getRetrofitInstance() : BioPassIDApi {
            return Retrofit.Builder()
                .baseUrl("https://api.biopassid.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(BioPassIDApi::class.java)
        }
    }
}