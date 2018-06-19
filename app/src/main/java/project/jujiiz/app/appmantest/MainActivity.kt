package project.jujiiz.app.appmantest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject
import android.widget.SimpleAdapter
import android.widget.TextView

class MainActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    val list = ArrayList<HashMap<String, String>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ////Header////
        val jsonObj = JSONObject(ReadJSON.loadJson(this))
        tvID.text = jsonObj.getString("Id")
        tvFName.text = jsonObj.getString("firstName")
        tvLName.text = jsonObj.getString("lastName")

        ////ListView////
        try {
            val jsonArray = jsonObj.getJSONArray("data")
            for (i in 0 until jsonArray.length()) {
                var temp: HashMap<String, String> = HashMap()
                val insideObj1 = jsonArray.getJSONObject(i)
                temp["docType"] = insideObj1.getString("docType").toString()

                val insideObj2 = insideObj1.getJSONObject("description")
                temp["th"] = insideObj2.getString("th").toString()
                temp["en"] = insideObj2.getString("en").toString()

                list.add(temp)
            }

        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val simpleAdapter = SimpleAdapter(this, list, R.layout.custom_listview_item,
                arrayOf("docType"), intArrayOf(R.id.tvItem)
        )
        lvData.setAdapter(simpleAdapter)

        lvData.onItemClickListener = this
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val Item = lvData.getItemAtPosition(position) as java.util.HashMap<String, String>
        val SelectedItem = Item["docType"].toString()


        val builder = AlertDialog.Builder(this)

        val view = LayoutInflater.from(this).inflate(R.layout.custom_dialog, null)
        val tvTh = view.findViewById<TextView>(R.id.tvTh)
        val tvEn = view.findViewById<TextView>(R.id.tvEn)

        for (i in 0 until list.size) {
            if (list[i]["docType"].equals(SelectedItem)) {
                tvTh.text = list[i]["th"]
                tvEn.text = list[i]["en"]
            }
        }
        builder.setView(view)
        val dialog = builder.create()
        dialog.show()
    }
}
