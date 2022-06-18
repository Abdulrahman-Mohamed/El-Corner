package com.abdullrahman.ecommerce.data.models

import com.google.gson.annotations.SerializedName

data class Variants(

	@field:SerializedName("variants")
	val variants: List<VariantsItem?>? = null
)


