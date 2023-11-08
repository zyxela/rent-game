package com.example.game_rent.data_classes

data class Order(
    val address: String,
    val game: String,
    val userName: String,
    val price: Double,
) {
    var id: String = ""

    constructor(
        id: String,
        address: String,
        game: String,
        userName: String,
        price: Double,
    ) : this(address, game, userName, price) {
        this.id = id
    }
}
