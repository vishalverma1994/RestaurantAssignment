package com.restaurants.extension

import android.content.Context
import java.io.IOException
import java.nio.charset.Charset


fun Context.loadJSONFromAsset(fileName: String): String? {
    //function to load the JSON from the Asset and return the object
    var json: String? = null
    val charset: Charset = Charsets.UTF_8
    try {
        val inputStream = this.assets.open(fileName)
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        json = String(buffer, charset)
    } catch (ex: IOException) {
        ex.printStackTrace()
        return null
    }
    return json
}