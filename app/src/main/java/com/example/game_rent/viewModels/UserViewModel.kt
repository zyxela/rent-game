package com.example.game_rent.viewModels

import androidx.lifecycle.ViewModel
import com.example.game_rent.data_classes.CatalogItem

class UserViewModel: ViewModel() {
    var catalogItems : MutableList<CatalogItem>? = null
}