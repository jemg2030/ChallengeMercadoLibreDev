package com.example.challengemercadolibredev.mvvmmecadolibre.utils

import android.content.Context
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset

class LoadHome {
    companion object {
        fun loadJSONFromAsset(context: Context): String? {
            var json: String? = null
            json = try {
                val `is`: InputStream = context.getAssets().open("home.json")
                val size: Int = `is`.available()
                val buffer = ByteArray(size)
                `is`.read(buffer)
                `is`.close()
                String(buffer, Charset.forName("UTF-8"))
            } catch (ex: IOException) {
                ex.printStackTrace()
                return null
            }
            return json
        }
    }
}