package com.example.game_rent.data_classes

data class CatalogItem(
    var name: String,
    var price: Double,
) {
    var id: String = ""

    constructor(
        id: String,
        name: String,
        price: Double,
    ) : this(name, price) {
        this.id = id
    }
}
