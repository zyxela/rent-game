package com.example.game_rent.data_classes

data class CatalogItem(
    var name: String,
    var price: Double,
    var description: String,
    var image:String = "gta6.jpg"
) {
    var id: String = ""
    constructor(
        id: String,
        name: String,
        price: Double,
        description: String
    ) : this(name, price, description) {
        this.id = id
    }

}
