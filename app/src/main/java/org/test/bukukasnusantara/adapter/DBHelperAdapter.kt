package org.test.bukukasnusantara.adapter

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import org.test.bukukasnusantara.model.DetailCash
import org.test.bukukasnusantara.model.User
import java.lang.Exception


class DBHelperAdapter(var context: Context) : SQLiteOpenHelper(context, DATABASENAME, null,
    1) {

    companion object {
        private const val DATABASENAME = "MY DATABASE"
        private const val TABLEUSERS = "Users"
        private const val COL_USERNAME = "username"
        private const val COL_PASSWORD = "password"
        private const val TABLEDATA = "DetailCashFlow"
        private const val COL_NOMINAL = "nominal"
        private const val COL_KETERANGAN = "keterangan"
        private const val COL_TANGGAL = "tanggal"
        private const val COL_TYPE = "status"
        private const val COL_ID = "id"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE " + TABLEUSERS + " (" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COL_USERNAME + " VARCHAR(256)," +
                    COL_PASSWORD + " VARCHAR(256))"
        )

        db?.execSQL(
            "CREATE TABLE " + TABLEDATA + " (" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COL_NOMINAL + " INTEGER(50)," +
                    COL_KETERANGAN + " VARCHAR(256)," +
                    COL_TANGGAL + " DATE," +
                    COL_TYPE + " VARCHAR(256))"
        )
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLEUSERS)
        onCreate(db)
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLEDATA)
        onCreate(db)
    }

    fun insertUser(user: User) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_USERNAME, user.username)
        contentValues.put(COL_PASSWORD, user.password)
        val result = database.insert(TABLEUSERS, null, contentValues)
        if (result == (0).toLong()) {
            Log.d("DB", "Create data Failed")
        } else {
            Log.d("DB", "Create data Success")
        }
    }

    fun updateUser(user: User){
        val db = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_USERNAME, user.username)
        contentValues.put(COL_PASSWORD, user.password)
        val res = db.update(TABLEUSERS, contentValues, "id = ?", arrayOf("1"))
    }

    fun readUser(): ArrayList<User> {
        val list: ArrayList<User> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from $TABLEUSERS"

        val cursor: Cursor?
        try {
            cursor = db.rawQuery(query, null)
        } catch (e: Exception){
            e.printStackTrace()
            db.execSQL(query)
            return ArrayList()
        }

        if (cursor.moveToFirst()) {
            do {
                val user = User()
                user.username = cursor.getString(cursor.getColumnIndex(COL_USERNAME))
                user.password = cursor.getString(cursor.getColumnIndex(COL_PASSWORD))
                list.add(user)
            }
            while (cursor.moveToNext())
        }
        return list
    }

    fun insertDataCashFlow(data: DetailCash) {
        val database = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_NOMINAL, data.nominal.toInt())
        contentValues.put(COL_KETERANGAN, data.keterangan)
        contentValues.put(COL_TANGGAL, data.tanggal)
        contentValues.put(COL_TYPE, data.type)
        val result = database.insert(TABLEDATA, null, contentValues)
        if (result == (0).toLong()) {
            Log.d("DBHelper", "Failed")
        }
        else {
            Log.d("DBHelper", "Success")
        }
    }
    fun readDataCashFlow(): MutableList<DetailCash> {
        val list: MutableList<DetailCash> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from $TABLEDATA"
        val cursor: Cursor?
        try {
            cursor = db.rawQuery(query, null)
        } catch (e: Exception){
            e.printStackTrace()
            db.execSQL(query)
            return ArrayList()
        }

        if (cursor.moveToFirst()) {
            do {
                val dc = DetailCash()
                dc.nominal = cursor.getInt(cursor.getColumnIndex(COL_NOMINAL))
                dc.keterangan = cursor.getString(cursor.getColumnIndex(COL_KETERANGAN))
                dc.tanggal = cursor.getString(cursor.getColumnIndex(COL_TANGGAL))
                dc.type = cursor.getString(cursor.getColumnIndex(COL_TYPE))
                list.add(dc)
            }
            while (cursor.moveToNext())
        }
        return list
    }

    fun readSortbyDate(): MutableList<DetailCash>{
        val list: MutableList<DetailCash> = ArrayList()
        val db = this.readableDatabase
        val query = "Select * from $TABLEDATA order by $COL_TANGGAL desc limit 1"
        val cursor: Cursor?
        try {
            cursor = db.rawQuery(query, null)
        } catch (e: Exception){
            e.printStackTrace()
            db.execSQL(query)
            return ArrayList()
        }

        if (cursor.moveToFirst()) {
            do {
                val dc = DetailCash()
                dc.nominal = cursor.getInt(cursor.getColumnIndex(COL_NOMINAL))
                dc.keterangan = cursor.getString(cursor.getColumnIndex(COL_KETERANGAN))
                dc.tanggal = cursor.getString(cursor.getColumnIndex(COL_TANGGAL))
                dc.type = cursor.getString(cursor.getColumnIndex(COL_TYPE))
                list.add(dc)
            }
            while (cursor.moveToNext())
        }
        return list
    }
}

