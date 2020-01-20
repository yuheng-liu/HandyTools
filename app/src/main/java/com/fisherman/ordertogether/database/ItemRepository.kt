package com.fisherman.ordertogether.database

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class ItemRepository internal constructor(application: Application) {
    private val itemDAO: ItemDAO
    val allItems: LiveData<List<Item?>?>?
    fun insert(item: Item?) {
        InsertItemAsyncTask(itemDAO).execute(item)
    }

    fun update(item: Item?) {
        UpdateItemAsyncTask(itemDAO).execute(item)
    }

    fun delete(item: Item?) {
        DeleteItemAsyncTask(itemDAO).execute(item)
    }

    fun deleteAllItems() {
        DeleteAllItemsAsyncTask(itemDAO).execute()
    }

    private class InsertItemAsyncTask(private val itemDAO: ItemDAO) :
        AsyncTask<Item?, Void?, Void?>() {
        protected override fun doInBackground(vararg items: Item): Void? {
            itemDAO.insert(items[0])
            return null
        }

    }

    private class UpdateItemAsyncTask(private val itemDAO: ItemDAO) :
        AsyncTask<Item?, Void?, Void?>() {
        protected override fun doInBackground(vararg items: Item): Void? {
            itemDAO.update(items[0])
            return null
        }

    }

    private class DeleteItemAsyncTask(private val itemDAO: ItemDAO) :
        AsyncTask<Item?, Void?, Void?>() {
        protected override fun doInBackground(vararg items: Item): Void? {
            itemDAO.delete(items[0])
            return null
        }

    }

    private class DeleteAllItemsAsyncTask(private val itemDAO: ItemDAO) :
        AsyncTask<Void?, Void?, Void?>() {
        protected override fun doInBackground(vararg voids: Void): Void? {
            itemDAO.deleteAllItems()
            return null
        }

    }

    init {
        itemDAO = ItemDatabase.Companion.getInstance(application)!!.itemDAO()
        allItems = itemDAO.allItems
    }
}