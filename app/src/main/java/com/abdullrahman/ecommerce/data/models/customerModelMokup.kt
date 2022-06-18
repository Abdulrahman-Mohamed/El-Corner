package com.abdullrahman.ecommerce.data.models

import com.google.gson.annotations.SerializedName

data class SmsMarketingConsent(

	@field:SerializedName("consent_updated_at")
	val consentUpdatedAt: Any? = null,

	@field:SerializedName("consent_collected_from")
	val consentCollectedFrom: String? = null,

	@field:SerializedName("state")
	val state: String? = null,

	@field:SerializedName("opt_in_level")
	val optInLevel: String? = null
)


data class CustomerMokup(

    @SerializedName( "id")
	val customerId: Long? = null,

    @SerializedName( "email")
	val email: String?,

    @SerializedName( "phone")
	val phone: String? = "",

    @SerializedName( "first_name")
	val firstName: String? = "",

    @SerializedName( "last_name")
	val lastName: String? = "",

    @SerializedName( "orders_count")
	val ordersCount: Int = 0,

    @SerializedName( "state")
	val state: String? = "",

    @SerializedName( "currency")
	val currency: String? = "EGP",

    @SerializedName( "note")
	val note: String? = "",

    @SerializedName( "total_spent")
	val totalSpent: String? = "",

    @SerializedName( "addresses")
	var addresses: List<AddressesItem>? = listOf(),

    @SerializedName( "password")
	val password: String? = "",

    @SerializedName( "password_confirmation")
	val passwordConfirmation: String? = "",
)

data class CustomerModelMokup(

	@field:SerializedName("customer")
	val customer: CustomerMokup? = null
)
