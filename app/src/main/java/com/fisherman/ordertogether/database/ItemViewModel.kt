package com.fisherman.ordertogether.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class ItemViewModel(application: Application) : AndroidViewModel(application) {

    private val itemRepository: ItemRepository

    val allItems: LiveData<List<Item?>?>?

    fun insert(item: Item?) {
        itemRepository.insert(item)
    }

    fun update(item: Item?) {
        itemRepository.update(item)
    }

    fun delete(item: Item?) {
        itemRepository.delete(item)
    }

    fun deleteAllItems() {
        itemRepository.deleteAllItems()
    }

    init {
        itemRepository = ItemRepository(application)
        allItems = itemRepository.allItems
    }
}