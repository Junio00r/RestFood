package com.devmobile.android.restaurant.model.datasource.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.devmobile.android.restaurant.usecase.entities.Restaurant
import com.devmobile.android.restaurant.viewmodel.bottomnavigation.ItemBetweenUiAndVM

// Versão para testes offlines
@Entity(
    tableName = "items",
    foreignKeys = [
        ForeignKey(
            entity = Restaurant::class,
            parentColumns = ["id"],
            childColumns = ["restaurantId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["restaurantId"])
    ]
)
data class Item(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val restaurantId: Long,
    val name: String,
    val price: Float,
    val section: String,
    @ColumnInfo(name = "max_items_available") val maxItemsAvailable: Int,
    @ColumnInfo(name = "max_items_by_selection") val maxItemsBySelection: Int = maxItemsAvailable,
    @ColumnInfo(name = "min_items_by_selection") val minItemsBySelection: Int = 0,
    val isRequiredBySelection: Boolean? = null, // Case this item will be a optional or required in select of another item selected
    @ColumnInfo(name = "complementary_sides") val complementarySides: List<Long>? = null,
    @ColumnInfo(name = "image_id") val imageId: Int,
    @ColumnInfo(name = "time_to_prepare") val timeToPrepareInMin: Int,
    val description: String? = null,
)

data class ItemBetweenUiAndVM(
    val name: String,
    var amountAdded: Int,
    val description: String,
    val image: Int,
    val price: Float,
    val minForSelection: Int,
    val maxForSelection: Int,
    var wasSelectedYet: Boolean = false,
    val isRequiredBySelection: Boolean? = null,
    val timeToPrepareInMin: Int
)

fun Item.getUiLayerItem() : ItemBetweenUiAndVM {
    return ItemBetweenUiAndVM(
        name = this.name,
        amountAdded = 0,
        description = this.description ?: "Non description",
        image = this.imageId,
        price = this.price,
        minForSelection = this.minItemsBySelection,
        maxForSelection = this.maxItemsBySelection,
        wasSelectedYet = false,
        isRequiredBySelection = this.isRequiredBySelection,
        timeToPrepareInMin = this.timeToPrepareInMin
    )
}
