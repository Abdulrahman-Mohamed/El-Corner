package com.abdullrahman.ecommerce.data.data_source.shopify_api

import com.abdullrahman.ecommerce.domain.Constants.SHOPIFY_USER_NAME
import com.abdullrahman.ecommerce.domain.Constants.SOPIFY_PASSWORD_KEY
import com.abdullrahman.ecommerce.domain.Constants.URL
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object{
    private val retrofit by lazy{
        var gson = GsonBuilder()
            .setLenient()
            .create()
        val client =OkHttpClient.Builder().
                addInterceptor(
                    ShopifyInterceptor(SHOPIFY_USER_NAME, SOPIFY_PASSWORD_KEY)
                ).build()
        Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

    }
        val api by lazy {
            //write class response
            retrofit.create( ShopifyApi::class.java)
        }
    }
}