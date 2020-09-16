package com.example.lopning.cache

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

class TypeConverter {

    //this class is for the screenshot var in our Dao since it's a bitmap type - AKA NOT primitive type

    @TypeConverter
    fun turnByteToBitmap( bytes: ByteArray): Bitmap{
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }



    @TypeConverter
    fun turnBitmapToByte(bitmap : Bitmap) : ByteArray{
        val result = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, result)
        return result.toByteArray()
    }

}