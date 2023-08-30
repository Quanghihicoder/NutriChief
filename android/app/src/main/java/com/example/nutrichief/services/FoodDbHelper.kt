package com.example.nutrichief.services

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class FoodDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_NAME = "food.db"
        const val DATABASE_VERSION = 1
    }

    //The database is created once when the SQLiteOpenHelper is instantiated
    //and the getWritableDatabase or getReadableDatabase method is called for the first time.
    override fun onCreate(db: SQLiteDatabase) {
        // Create menu table
        val createMenuTable = "CREATE TABLE ${FoodContract.FoodEntry.TABLE_NAME} (" +
                "${FoodContract.FoodEntry.COL_ITEM_ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "${FoodContract.FoodEntry.COL_ITEM_NAME} VARCHAR(256), " +
                "${FoodContract.FoodEntry.COL_ITEM_PRICE} REAL, " +
                "${FoodContract.FoodEntry.COL_ITEM_DESC} VARCHAR(256)," +
                "${FoodContract.FoodEntry.COL_ITEM_STAR} REAL," +
                "${FoodContract.FoodEntry.COL_ITEM_CATEGORY} VARCHAR(256)," +
                "${FoodContract.FoodEntry.COL_ITEM_IMAGE_URL} VARCHAR(256))"
        db.execSQL(createMenuTable)

        // Create current_cart table
        val createCartTable = "CREATE TABLE ${CartContract.CartEntry.CART_TABLE_NAME} (" +
                "${CartContract.CartEntry.CART_ITEM_ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "${CartContract.CartEntry.CART_ITEM_NAME} VARCHAR(256), " +
                "${CartContract.CartEntry.CART_ITEM_PRICE} REAL," +
                "${CartContract.CartEntry.CART_ITEM_SHORT_DESC} VARCHAR(256)," +
                "${CartContract.CartEntry.CART_ITEM_QTY} INTEGER," +
                "${CartContract.CartEntry.CART_ITEM_STARS} REAL," +
                "${CartContract.CartEntry.CART_IMAGE_URL} VARCHAR(256),"+
                "${CartContract.CartEntry.COL_ITEM_ID} INTEGER,"+
                "FOREIGN KEY (${CartContract.CartEntry.COL_ITEM_ID}) REFERENCES ${FoodContract.FoodEntry.TABLE_NAME}(${FoodContract.FoodEntry.COL_ITEM_ID}))"
        db.execSQL(createCartTable)

        // Create current_orders table
        val createCurrentOrdersTable = "CREATE TABLE ${CurrentCartContract.CurrentCartEntry.CURRENT_ORDER_TABLE_NAME} (" +
                "${CurrentCartContract.CurrentCartEntry.COL_CURRENT_ORDER_ID} VARCHAR(256) PRIMARY KEY, " +
                "${CurrentCartContract.CurrentCartEntry.COL_CURRENT_ORDER_TAKE_AWAY_TIME} VARCHAR(256), " +
                "${CurrentCartContract.CurrentCartEntry.COL_CURRENT_ORDER_PAYMENT_STATUS} VARCHAR(256), " +
                "${CurrentCartContract.CurrentCartEntry.COL_CURRENT_ORDER_ITEM_NAMES} VARCHAR(256), " +
                "${CurrentCartContract.CurrentCartEntry.COL_CURRENT_ORDER_ITEM_QUANTITIES} VARCHAR(256), " +
                "${CurrentCartContract.CurrentCartEntry.COL_CURRENT_ORDER_TOTAL_ITEM_PRICE} VARCHAR(256), " +
                "${CurrentCartContract.CurrentCartEntry.COL_CURRENT_ORDER_TAX} VARCHAR(256), " +
                "${CurrentCartContract.CurrentCartEntry.COL_CURRENT_ORDER_SUB_TOTAL} VARCHAR(256), "+
                "${CurrentCartContract.CurrentCartEntry.COL_CURRENT_ORDER_ITEM_ID} INTEGER, " +
                "FOREIGN KEY (${CurrentCartContract.CurrentCartEntry.COL_CURRENT_ORDER_ITEM_ID}) REFERENCES ${CartContract.CartEntry.CART_TABLE_NAME}(${CartContract.CartEntry.CART_ITEM_ID}))"
        db.execSQL(createCurrentOrdersTable)

        // Create old_orders table
        val createHistoryTable = "CREATE TABLE ${HistoryContract.HistoryEntry.ORDER_HISTORY_TABLE_NAME} (" +
                "${HistoryContract.HistoryEntry.COL_ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "${HistoryContract.HistoryEntry.COL_ORDER_DATE} VARCHAR(256), " +
                "${HistoryContract.HistoryEntry.COL_ORDER_ID} VARCHAR(256)," +
                "${HistoryContract.HistoryEntry.COL_ORDER_STATUS} VARCHAR(256)," +
                "${HistoryContract.HistoryEntry.COL_ORDER_PAYMENT} VARCHAR(256)," +
                "${HistoryContract.HistoryEntry.COL_ORDER_PRICE} VARCHAR(256), " +
                "FOREIGN KEY (${HistoryContract.HistoryEntry.COL_ORDER_ID}) REFERENCES ${CurrentCartContract.CurrentCartEntry.CURRENT_ORDER_TABLE_NAME}(${CurrentCartContract.CurrentCartEntry.COL_CURRENT_ORDER_ID}))"
        db.execSQL(createHistoryTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${FoodContract.FoodEntry.TABLE_NAME}")
        db.execSQL("DROP TABLE IF EXISTS ${CartContract.CartEntry.CART_TABLE_NAME}")
        db.execSQL("DROP TABLE IF EXISTS ${CurrentCartContract.CurrentCartEntry.CURRENT_ORDER_TABLE_NAME}")
        db.execSQL("DROP TABLE IF EXISTS ${HistoryContract.HistoryEntry.ORDER_HISTORY_TABLE_NAME}")
        onCreate(db)
    }
}

object FoodContract {
    object FoodEntry : BaseColumns {
        const val TABLE_NAME = "menu"
        const val COL_ITEM_ID = "item_id"
        const val COL_ITEM_NAME = "item_name"
        const val COL_ITEM_PRICE = "item_price"
        const val COL_ITEM_DESC = "item_desc"
        const val COL_ITEM_STAR = "item_star"
        const val COL_ITEM_CATEGORY = "item_category"
        const val COL_ITEM_IMAGE_URL = "item_image_url"
    }
}

object CartContract {
    object CartEntry : BaseColumns {
        const val CART_TABLE_NAME = "current_cart"
        const val COL_ITEM_ID = "item_id"
        const val CART_ITEM_ID = "cart_id"
        const val CART_IMAGE_URL = "image_url"
        const val CART_ITEM_NAME = "item_name"
        const val CART_ITEM_PRICE = "item_price"
        const val CART_ITEM_SHORT_DESC = "item_short_desc"
        const val CART_ITEM_STARS = "item_stars"
        const val CART_ITEM_QTY = "item_qty"
    }
}

object CurrentCartContract {
    object CurrentCartEntry : BaseColumns {
        const val CURRENT_ORDER_TABLE_NAME = "current_orders"
        const val COL_CURRENT_ORDER_ID = "order_id"
        const val COL_CURRENT_ORDER_TAKE_AWAY_TIME = "take_away_time"
        const val COL_CURRENT_ORDER_PAYMENT_STATUS = "payment_status"
        const val COL_CURRENT_ORDER_ITEM_NAMES = "item_names"
        const val COL_CURRENT_ORDER_ITEM_QUANTITIES = "item_quantities"
        const val COL_CURRENT_ORDER_TOTAL_ITEM_PRICE = "total_item_price"
        const val COL_CURRENT_ORDER_TAX = "tax"
        const val COL_CURRENT_ORDER_SUB_TOTAL = "sub_total"
        const val COL_CURRENT_ORDER_ITEM_ID = "item_id"
    }
}

object HistoryContract {
    object HistoryEntry : BaseColumns {
        const val ORDER_HISTORY_TABLE_NAME = "old_orders"
        const val COL_ID = "id"
        const val COL_ORDER_DATE = "order_date"
        const val COL_ORDER_STATUS = "order_status"
        const val COL_ORDER_PAYMENT = "order_payment"
        const val COL_ORDER_PRICE = "order_price"
        const val COL_ORDER_ID = "order_id"
    }
}