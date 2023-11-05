package com.example.game_rent.data

import android.util.Log
import com.example.game_rent.data_classes.CatalogItem
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class DatabaseInteraction {
    lateinit var db: FirebaseFirestore
    fun connect() {
        try {
            db = Firebase.firestore
        } catch (e: Exception) {
            Log.e("DATABASE_CONNECTION_ERROR", e.message.toString())
        }


    }

    fun getCatalog(){//:List<CatalogItem> {
        //val list:MutableMap<String, Any>
        db.collection("catalog")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                   // list. (document.data)
                    Log.d("TAG", "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w("ERROr", "Error getting documents.", exception)
            }

    }

    fun addCatalogItem(list: List<CatalogItem>) {

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
}