package com.example.nutrichief.services

import android.content.ContentValues
import android.content.Context
import android.widget.Toast
import com.example.nutrichief.datamodels.OrderHistoryItem

class HistoryRepository(val context: Context) {
    private val dbHelper = FoodDbHelper(context)

    // create an item in old_orders table
    fun insertOrderData(item: OrderHistoryItem) {
        val db = dbHelper.writableDatabase
        val cv = ContentValues()

        cv.put(HistoryContract.HistoryEntry.COL_ORDER_DATE, item.date)
        cv.put(HistoryContract.HistoryEntry.COL_ORDER_ID, item.orderId)
        cv.put(HistoryContract.HistoryEntry.COL_ORDER_STATUS, item.orderStatus)
        cv.put(HistoryContract.HistoryEntry.COL_ORDER_PAYMENT, item.orderPayment)
        cv.put(HistoryContract.HistoryEntry.COL_ORDER_PRICE, item.price)
//        cv.put(HistoryContract.HistoryEntry.COL_ID, item.id)

        val result = db.insert(HistoryContract.HistoryEntry.ORDER_HISTORY_TABLE_NAME, null, cv)
        if (result == (-1).toLong()) {
//            Toast.makeText(context, "Failed to Insert Data", Toast.LENGTH_SHORT).show()
        }
    }

    //read all items in old_orders table
    fun readOrderData(): MutableList<OrderHistoryItem> {
        val db = dbHelper.readableDatabase
        val projection = arrayOf(
            HistoryContract.HistoryEntry.COL_ORDER_DATE,
            HistoryContract.HistoryEntry.COL_ORDER_ID,
            HistoryContract.HistoryEntry.COL_ORDER_STATUS,
            HistoryContract.HistoryEntry.COL_ORDER_PAYMENT,
            HistoryContract.HistoryEntry.COL_ORDER_PRICE,
        )

        val cursor = db.query(
            HistoryContract.HistoryEntry.ORDER_HISTORY_TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            null
        )
        val list: MutableList<OrderHistoryItem> = ArrayList()
        with(cursor) {
            while (moveToNext()) {
                val date = getString(getColumnIndexOrThrow(HistoryContract.HistoryEntry.COL_ORDER_DATE))
                val orderId = getString(getColumnIndexOrThrow(HistoryContract.HistoryEntry.COL_ORDER_ID))
                val orderStatus = getString(getColumnIndexOrThrow(HistoryContract.HistoryEntry.COL_ORDER_STATUS))
                val orderPayment = getString(getColumnIndexOrThrow(HistoryContract.HistoryEntry.COL_ORDER_PAYMENT))
                val price = getString(getColumnIndexOrThrow(HistoryContract.HistoryEntry.COL_ORDER_PRICE))
                list.add(OrderHistoryItem(date, orderId, orderStatus, orderPayment, price))
            }
        }
        cursor.close()
        return list
    }

    //delete the old_orders table
    fun dropOrderHistoryTable() {
        try {
            val db = dbHelper.writableDatabase
            db.delete(HistoryContract.HistoryEntry.ORDER_HISTORY_TABLE_NAME, null, null)
            Toast.makeText(context, "All records are deleted", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, "Unable to delete the records", Toast.LENGTH_SHORT).show()
        }
    }
}