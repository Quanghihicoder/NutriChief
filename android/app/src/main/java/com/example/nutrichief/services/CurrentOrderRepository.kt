package com.example.nutrichief.services

import android.content.ContentValues
import android.content.Context
import com.example.nutrichief.datamodels.CurrentOrderItem

class CurrentOrderRepository(context: Context) {
    private val dbHelper = FoodDbHelper(context)

    //insert item to the table
    fun insertCurrentOrdersData(item: CurrentOrderItem) {
        val db = dbHelper.writableDatabase
        val cv = ContentValues()

        cv.put(CurrentCartContract.CurrentCartEntry.COL_CURRENT_ORDER_ID, item.orderId)
        cv.put(CurrentCartContract.CurrentCartEntry.COL_CURRENT_ORDER_TAKE_AWAY_TIME, item.takeAwayTime)
        cv.put(CurrentCartContract.CurrentCartEntry.COL_CURRENT_ORDER_PAYMENT_STATUS, item.paymentStatus)
        cv.put(CurrentCartContract.CurrentCartEntry.COL_CURRENT_ORDER_ITEM_NAMES, item.orderItemNames)
        cv.put(CurrentCartContract.CurrentCartEntry.COL_CURRENT_ORDER_ITEM_QUANTITIES, item.orderItemQuantities)
        cv.put(CurrentCartContract.CurrentCartEntry.COL_CURRENT_ORDER_TOTAL_ITEM_PRICE, item.totalItemPrice)
        cv.put(CurrentCartContract.CurrentCartEntry.COL_CURRENT_ORDER_TAX, item.tax)
        cv.put(CurrentCartContract.CurrentCartEntry.COL_CURRENT_ORDER_SUB_TOTAL, item.subTotal)

        db.insert(CurrentCartContract.CurrentCartEntry.CURRENT_ORDER_TABLE_NAME, null, cv)
    }

    //read all items of the table
    fun readCurrentOrdersData(): MutableList<CurrentOrderItem> {

        val db = dbHelper.readableDatabase
        val projection = arrayOf(
            CurrentCartContract.CurrentCartEntry.COL_CURRENT_ORDER_ID,
            CurrentCartContract.CurrentCartEntry.COL_CURRENT_ORDER_TAKE_AWAY_TIME,
            CurrentCartContract.CurrentCartEntry.COL_CURRENT_ORDER_PAYMENT_STATUS,
            CurrentCartContract.CurrentCartEntry.COL_CURRENT_ORDER_ITEM_NAMES,
            CurrentCartContract.CurrentCartEntry.COL_CURRENT_ORDER_ITEM_QUANTITIES,
            CurrentCartContract.CurrentCartEntry.COL_CURRENT_ORDER_TOTAL_ITEM_PRICE,
            CurrentCartContract.CurrentCartEntry.COL_CURRENT_ORDER_TAX,
            CurrentCartContract.CurrentCartEntry.COL_CURRENT_ORDER_SUB_TOTAL
        )

        val cursor = db.query(
            CurrentCartContract.CurrentCartEntry.CURRENT_ORDER_TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            null
        )
        val list: MutableList<CurrentOrderItem> = ArrayList()
        with(cursor) {
            while (moveToNext()) {
                val id = getString(getColumnIndexOrThrow(CurrentCartContract.CurrentCartEntry.COL_CURRENT_ORDER_ID))
                val takeawayTime = getString(getColumnIndexOrThrow(CurrentCartContract.CurrentCartEntry.COL_CURRENT_ORDER_TAKE_AWAY_TIME))
                val paymentStatus = getString(getColumnIndexOrThrow(CurrentCartContract.CurrentCartEntry.COL_CURRENT_ORDER_PAYMENT_STATUS))
                val name = getString(getColumnIndexOrThrow(CurrentCartContract.CurrentCartEntry.COL_CURRENT_ORDER_ITEM_NAMES))
                val quantity = getString(getColumnIndexOrThrow(CurrentCartContract.CurrentCartEntry.COL_CURRENT_ORDER_ITEM_QUANTITIES))
                val totalPrice = getString(getColumnIndexOrThrow(CurrentCartContract.CurrentCartEntry.COL_CURRENT_ORDER_TOTAL_ITEM_PRICE))
                val tax = getString(getColumnIndexOrThrow(CurrentCartContract.CurrentCartEntry.COL_CURRENT_ORDER_TAX))
                val subtotal = getString(getColumnIndexOrThrow(CurrentCartContract.CurrentCartEntry.COL_CURRENT_ORDER_SUB_TOTAL))
                list.add(CurrentOrderItem(id, takeawayTime, paymentStatus, name, quantity, totalPrice, tax, subtotal))
            }
        }
        cursor.close()
        return list
    }

    //update status of history order to cancel and delete item from the current order table
    fun deleteCurrentOrderRecord(orderId: String): String {
        val query = "DELETE FROM ${CurrentCartContract.CurrentCartEntry.CURRENT_ORDER_TABLE_NAME} WHERE ${CurrentCartContract.CurrentCartEntry.COL_CURRENT_ORDER_ID}='${orderId}';"
        val db = dbHelper.writableDatabase
        db.execSQL(query)

        val updateOrderHistoryQuery =
            "UPDATE ${HistoryContract.HistoryEntry.ORDER_HISTORY_TABLE_NAME} SET ${HistoryContract.HistoryEntry.COL_ORDER_STATUS}='Order Cancelled' WHERE ${HistoryContract.HistoryEntry.COL_ORDER_ID}='${orderId}';"
        db.execSQL(updateOrderHistoryQuery)

        db.close()
        return "Order Cancelled"
    }

    //update status of history order to done and delete item from the current order table
    fun confirmOrderDone(orderId: String): String {
        val query = "DELETE FROM ${CurrentCartContract.CurrentCartEntry.CURRENT_ORDER_TABLE_NAME} WHERE ${CurrentCartContract.CurrentCartEntry.COL_CURRENT_ORDER_ID}='${orderId}';"
        val db = dbHelper.writableDatabase
        db.execSQL(query)

        val updateOrderHistoryQuery =
            "UPDATE ${HistoryContract.HistoryEntry.ORDER_HISTORY_TABLE_NAME} SET ${HistoryContract.HistoryEntry.COL_ORDER_STATUS}='Order Done' WHERE ${HistoryContract.HistoryEntry.COL_ORDER_ID}='${orderId}';"
        db.execSQL(updateOrderHistoryQuery)

        db.close()
        return "Order Done"
    }

    // drop the table
    fun dropCurrentOrdersTable() {
        try {
            val db = dbHelper.writableDatabase
            db.delete(CurrentCartContract.CurrentCartEntry.CURRENT_ORDER_TABLE_NAME, null, null)
//            Toast.makeText(context, "All records are deleted", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
//            Toast.makeText(context, "Unable to delete the records", Toast.LENGTH_SHORT).show()
        }
    }
}