package com.abdullrahman.ecommerce.data.data_source.room.customerData

import androidx.room.TypeConverter
import com.abdullrahman.ecommerce.data.models.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    companion object {
        @TypeConverter
        @JvmStatic
        fun fromAddressItemList(value: MutableList<AddressesItem>): String {
            val gson = Gson()
            val type = object : TypeToken<MutableList<AddressesItem>>() {}.type
            return gson.toJson(value, type)
        }

        @TypeConverter
        @JvmStatic
        fun toAddressItemList(value: String): MutableList<AddressesItem> {
            val gson = Gson()
            val type = object : TypeToken<MutableList<AddressesItem>>() {}.type
            return gson.fromJson(value, type)
        }

        @TypeConverter
        @JvmStatic
        fun fromOptionsItemList(value: List<OptionsItem?>?): String {
            val gson = Gson()
            val type = object : TypeToken<List<OptionsItem?>?>() {}.type
            return gson.toJson(value, type)
        }

        @TypeConverter
        @JvmStatic
        fun toOptionsItemList(value: String): List<OptionsItem?>? {
            val gson = Gson()
            val type = object : TypeToken<List<OptionsItem?>?>() {}.type
            return gson.fromJson(value, type)
        }

        @TypeConverter
        @JvmStatic
        fun fromVariantsItemList(value: List<VariantsItem?>?): String {
            val gson = Gson()
            val type = object : TypeToken<List<VariantsItem?>?>() {}.type
            return gson.toJson(value, type)
        }

        @TypeConverter
        @JvmStatic
        fun toVariantsItemList(value: String): List<VariantsItem?>? {
            val gson = Gson()
            val type = object : TypeToken<List<VariantsItem?>?>() {}.type
            return gson.fromJson(value, type)
        }


        @TypeConverter
        @JvmStatic
        fun fromImagesItemList(value: List<ImagesItem?>?): String {
            val gson = Gson()
            val type = object : TypeToken<List<ImagesItem?>?>() {}.type
            return gson.toJson(value, type)
        }

        @TypeConverter
        @JvmStatic
        fun toImagesItemList(value: String): List<ImagesItem?>? {
            val gson = Gson()
            val type = object : TypeToken<List<ImagesItem?>?>() {}.type
            return gson.fromJson(value, type)
        }

        @TypeConverter
        @JvmStatic
        fun fromImages(value: Image): String {
            val gson = Gson()
            val type = object : TypeToken<Image>() {}.type
            return gson.toJson(value, type)
        }

        @TypeConverter
        @JvmStatic
        fun toImages(value: String):Image{
            val gson = Gson()
            val type = object : TypeToken<Image>() {}.type
            return gson.fromJson(value, type)
        }
    }
}