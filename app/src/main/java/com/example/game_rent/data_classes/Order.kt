package com.example.game_rent.data_classes

data class Order(
    var address: String,
    var game: String,
    var userName: String,
    var price: Double,
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
