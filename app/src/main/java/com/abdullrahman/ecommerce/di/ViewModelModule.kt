package com.abdullrahman.ecommerce.di

import android.content.Context
import androidx.room.Room
import com.abdullrahman.ecommerce.data.data_source.room.customerData.AppDataBase
import com.abdullrahman.ecommerce.data.data_source.room.customerData.CustomerDao
import com.abdullrahman.ecommerce.data.data_source.room.customerData.WishListDao
import com.abdullrahman.ecommerce.data.data_source.shopify_api.ShopifyApi
import com.abdullrahman.ecommerce.data.data_source.shopify_api.ShopifyInterceptor
import com.abdullrahman.ecommerce.domain.Constants
import com.abdullrahman.ecommerce.repository.authRepo.AuthRepositoryImp
import com.abdullrahman.ecommerce.repository.mainRepo.MainRepoImp
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ViewModelComponent::class) // this is new
object ViewModelModule {
    var gson = GsonBuilder()
        .setLenient()
        .create()

    @Provides
    fun providesOkHttpClient(): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(
                ShopifyInterceptor(
                    Constants.SHOPIFY_USER_NAME,
                    Constants.SOPIFY_PASSWORD_KEY
                )
            )
            .build()

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Provides
    fun provideApiService(retrofit: Retrofit): ShopifyApi = retrofit.create(ShopifyApi::class.java)

    @Provides
    @ViewModelScoped
    fun provideAuthRepo(
        @ApplicationContext appContext: Context,
        shopifyApi: ShopifyApi,
        customerDao: CustomerDao
    ): AuthRepositoryImp = AuthRepositoryImp(appContext, shopifyApi, customerDao)

    @Provides
    @ViewModelScoped
    fun provideMainRepo(
        @ApplicationContext appContext: Context,
        shopifyApi: ShopifyApi,
        customerDao: CustomerDao,
        wishListDao: WishListDao
    ): MainRepoImp = MainRepoImp(appContext, shopifyApi, customerDao, wishListDao)

    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDataBase {
        return Room.databaseBuilder(
            appContext,
            AppDataBase::class.java,
            AppDataBase.DatabaseName
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun ProvideCustomerDao(appDataBase: AppDataBase) = appDataBase.CustomerDao()
    @Provides
    fun ProvideWishListDao(appDataBase: AppDataBase) = appDataBase.WishListDao()
}