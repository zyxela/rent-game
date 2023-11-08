package com.example.game_rent.data

import android.util.Log
import com.example.game_rent.data_classes.CatalogItem
import com.example.game_rent.data_classes.Order
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class DatabaseInteraction {
    lateinit var db: FirebaseFirestore
    private fun connect() {
        try {
            db = Firebase.firestore
        } catch (e: Exception) {
            Log.e("DATABASE_CONNECTION_ERROR", e.message.toString())
        }


    }

    suspend fun getCatalog(): MutableList<CatalogItem> = suspendCoroutine { continuation ->
        val list: MutableList<CatalogItem> = mutableListOf()
        connect()

        db.collection("catalog")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val name = document.data["name"].toString()
                    if (name != "") {
                        val price = document.data["price"].toString().toDouble()
                        val item = CatalogItem(name, price)
                        list.add(item)
                    }

                }
                continuation.resume(list)
            }
            .addOnFailureListener { exception ->
                Log.e("ERROr", "Error getting documents.", exception)
                continuation.resume(list)
            }
    }

    fun addCatalogItem(list: List<CatalogItem>) {
        connect()
        for (item in list)
            db.collection("catalog")
                .add(item)
                .addOnSuccessListener {
                    Log.i("COMPLETE_ADDED", "Item successfully added")
                }
                .addOnFailureListener { e ->
                    Log.e("ADDED_ERROR", "${e.message}")
                }

    }

    fun addOrder(item: Order) {
        connect()

        db.collection("orders")
            .add(item)
            .addOnSuccessListener {
                Log.i("COMPLETE_ADDED", "Item successfully added")
            }
            .addOnFailureListener { e ->
                Log.e("ADDED_ERROR", "${e.message}")
            }
    }

    suspend fun getOrders(): MutableList<CatalogItem> = suspendCoroutine { continuation ->
        val list: MutableList<CatalogItem> = mutableListOf()
        connect()

        db.collection("orders")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val name = document.data["game"].toString()
                    if (name != "") {
                        val price = document.data["price"].toString().toDouble()
                        val item = CatalogItem(name, price)
                        list.add(item)
                    }

                }
                continuation.resume(list)
            }
            .addOnFailureListener { exception ->
                Log.e("ERROr", "Error getting documents.", exception)
                continuation.resume(list)
            }
    }

    fun addToListOrder(item: CatalogItem) {
        connect()
        db.collection("orderList")
            .add(item)
            .addOnSuccessListener {
                Log.i("COMPLETE_ADDED", "Item successfully added")
            }
            .addOnFailureListener { e ->
                Log.e("ADDED_ERROR", "${e.message}")
            }


    }
    fun removeOrder(){
        connect()
        db.collection("orders")

    }
}