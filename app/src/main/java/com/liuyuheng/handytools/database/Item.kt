package com.liuyuheng.handytools.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items_table")
class Item(var name: String, val cost: Double, val numberOfItems: Int) {
    @PrimaryKey(autoGenerate = true)
    var id = 0

}