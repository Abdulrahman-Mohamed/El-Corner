package com.abdullrahman.ecommerce.data.models

import com.google.gson.annotations.SerializedName

data class Customer(
//
//	@field:SerializedName("customer")
//	val customer: Customer? = null,

	@field:SerializedName("note")
	val note: Any? = null,

	@field:SerializedName("addresses")
	val addresses: List<AddressesItem?>? = listOf(),

	@field:SerializedName("last_order_name")
	val lastOrderName: String? = "",

	@field:SerializedName("created_at")
	val createdAt: String? = "",

	@field:SerializedName("multipass_identifier")
	val multipassIdentifier: Any? = "",

	@field:SerializedName("accepts_marketing_updated_at")
	val acceptsMarketingUpdatedAt: String? = "",

	@field:SerializedName("default_address")
	val defaultAddress: DefaultAddress? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = "",

	@field:SerializedName("accepts_marketing")
	val acceptsMarketing: Boolean? = false,

	@field:SerializedName("currency")
	val currency: String? = "EGP",

	@field:SerializedName("id")
	val id: Long? = null,

	@field:SerializedName("state")
	val state: String? = "",

	@field:SerializedName("marketing_opt_in_level")
	val marketingOptInLevel: Any? = null,

	@field:SerializedName("first_name")
	val firstName: String? = "",

	@field:SerializedName("email")
	val email: String? = "",

	@field:SerializedName("total_spent")
	val totalSpent: String? = "",

	@field:SerializedName("last_order_id")
	val lastOrderId: Long? = null,

	@field:SerializedName("tax_exempt")
	val taxExempt: Boolean? = false,

	@field:SerializedName("last_name")
	val lastName: String? = "",

	@field:SerializedName("verified_email")
	val verifiedEmail: Boolean? = true,

	@field:SerializedName("tags")
	val tags: String? = "",

	@field:SerializedName("orders_count")
	val ordersCount: Long? = null,

	@field:SerializedName("phone")
	val phone: Any? = null,

	@field:SerializedName("admin_graphql_api_id")
	val adminGraphqlApiId: String? = "",

	@field:SerializedName("tax_exemptions")
	val taxExemptions: List<Any?>? = null
)
data class AddressContainer(
	@field:SerializedName("address")
	val address:AddressesItem
	)
data class AddressesItem(

	@field:SerializedName("zip")
	val zip: String? = null,

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("address2")
	val address2: String? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("address1")
	val address1: String? = null,

	@field:SerializedName("last_name")
	val lastName: Any? = null,

	@field:SerializedName("province_code")
	val provinceCode: String? = null,

	@field:SerializedName("country_code")
	val countryCode: String? = null,

	@field:SerializedName("default")
	val jsonMemberDefault: Boolean? = null,

	@field:SerializedName("province")
	val province: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("country_name")
	val countryName: String? = null,

	@field:SerializedName("company")
	val company: Any? = null,

	@field:SerializedName("id")
	val id: Long? = null,

	@field:SerializedName("customer_id")
	val customerId: Long? = null,

	@field:SerializedName("first_name")
	val firstName: Any? = null
)

data class DefaultAddress(

	@field:SerializedName("zip")
	val zip: String? = null,

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("address2")
	val address2: String? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("address1")
	val address1: String? = null,

	@field:SerializedName("last_name")
	val lastName: Any? = null,

	@field:SerializedName("province_code")
	val provinceCode: String? = null,

	@field:SerializedName("country_code")
	val countryCode: String? = null,

	@field:SerializedName("default")
	val jsonMemberDefault: Boolean? = null,

	@field:SerializedName("province")
	val province: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("country_name")
	val countryName: String? = null,

	@field:SerializedName("company")
	val company: Any? = null,

	@field:SerializedName("id")
	val id: Long? = null,

	@field:SerializedName("customer_id")
	val customerId: Long? = null,

	@field:SerializedName("first_name")
	val firstName: Any? = null
)
