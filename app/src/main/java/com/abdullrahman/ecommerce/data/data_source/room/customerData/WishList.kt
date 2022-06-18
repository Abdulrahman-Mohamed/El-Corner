package com.abdullrahman.ecommerce.data.data_source.room.customerData

import androidx.room.*
import com.abdullrahman.ecommerce.data.models.Image
import com.abdullrahman.ecommerce.data.models.ImagesItem
import com.abdullrahman.ecommerce.data.models.OptionsItem
import com.abdullrahman.ecommerce.data.models.VariantsItem
import com.google.gson.annotations.SerializedName

@Entity
@JvmSuppressWildcards
@TypeConverters(
    Converters::class
)

data class WishList(
    @ColumnInfo(name = "image")
    var image: Image? = null,

    @ColumnInfo(name = "body_html")
    var bodyHtml: String? = null,

    @ColumnInfo(name = "images")
    var images: List<ImagesItem?>? = null,

    @ColumnInfo(name = "created_at")
    var createdAt: String? = null,

    @ColumnInfo(name = "handle")
    var handle: String? = null,

    @ColumnInfo(name = "variants")
    var variants: List<VariantsItem?>? = null,

    @ColumnInfo(name = "title")
    var title: String? = null,

    @ColumnInfo(name = "tags")
    var tags: String? = null,

    @ColumnInfo(name = "published_scope")
    var publishedScope: String? = null,

    @ColumnInfo(name = "product_type")
    var productType: String? = null,

    @ColumnInfo(name = "template_suffix")
    @Ignore
    var templateSuffix: Any? = null,

    @ColumnInfo(name = "updated_at")
    var updatedAt: String? = null,

    @ColumnInfo(name = "vendor")
    var vendor: String? = null,

    @ColumnInfo(name = "admin_graphql_api_id")
    var adminGraphqlApiId: String? = null,

    @ColumnInfo(name = "options")
    var options: List<OptionsItem?>? = null,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null,

    @ColumnInfo(name = "published_at")
    var publishedAt: String? = null,

    @ColumnInfo(name = "status")
    var status: String? = null

)
