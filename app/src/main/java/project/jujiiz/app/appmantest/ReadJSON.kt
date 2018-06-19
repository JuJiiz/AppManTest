package project.jujiiz.app.appmantest

import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import java.io.IOException

class ReadJSON {
    companion object {
        fun loadJson(context: Context): String? {
            var json: String?
            try {
                val jsonAsset = context.assets.open("intern.json")
                val size = jsonAsset.available()
                val buffer = ByteArray(size)
                jsonAsset.read(buffer)
                jsonAsset.close()
                json = String(buffer)
            } catch (ex: IOException) {
                ex.printStackTrace()
                return null
            }

            return json
        }
    }
}