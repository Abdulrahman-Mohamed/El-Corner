package com.abdullrahman.ecommerce.data.data_source.shopify_api

import com.abdullrahman.ecommerce.data.models.*
import retrofit2.Response
import retrofit2.http.*

interface ShopifyApi {
    @POST("customers/{customer_id}.json")
    suspend fun getCustomer(@Path("customer_id")customer_id:Long):Response<Customer>

    @GET("customers/search.json")
    suspend fun searchCustomer(@Query("query") email_name:String):Response<Search>

    @POST("customers.json")
    suspend fun register(@Body customer: CustomerModelMokup): Response<CustomerModelMokup>

    @PUT("customers/{customer_id}.json")
    suspend fun update(@Path("customer_id") customer_id: Long, @Body customer: Customer):
            Response<Customer>

    @POST("customers/{customer_id}/addresses.json")
    suspend fun addAddress(@Path("customer_id") id: Long, @Body address: AddressContainer):
            Response<AddressContainer>

    @PUT("customers/{customer_id}/addresses/{address_id}.json")
    suspend fun updateAddress(
        @Path("customer_id") customerId: Long,
        @Path("address_id") addressId: Long,
        @Body address: AddressContainer
    ):
            Response<AddressContainer>

    @DELETE("customers/{customer_id}/addresses/{address_id}.json")
    suspend fun deleteAddress(
        @Path("customer_id") customerId: Long,
        @Path("address_id") addressId: Long
    ): Response<AddressContainer>

    @GET("customers/{customer_id}/addresses.json")
    suspend fun getAddresses(@Path("customer_id") customerId: Long):
            Response<Addresses>
    @GET("collections/266723786845/products.json")
    suspend fun getHomePageCollection(): Response<Products>
    @GET("collections/266901749853/products.json")
    suspend fun getSaleCollection(): Response<Products>
    @GET("collections/{collection_id}/products.json")
    suspend fun getSelectedCollection(@Path("collection_id") collectionId:Long): Response<Products>
    @GET("products/{productId}.json")
    suspend fun getProduct(@Path("productId") customerId: Long): Response<Product>
    @GET("products.json")
    suspend fun getAllProductList(): Response<Products>
    @GET("products/{product_id}/variants.json")
    suspend fun getProductVariants(@Path("product_id") customerId: Long): Response<Variants>
    @GET("smart_collections.json")
    suspend fun getSmartCollections(): Response<SmartCollections>
}