package com.example.q_rier

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.q_rier.PemesananAdapter
import com.example.q_rier.PemesananApi
import com.example.q_rier.Pemesanan
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.shashank.sony.fancytoastlib.FancyToast
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class ShowPemesanan : AppCompatActivity() {
    private var srPemesanan: SwipeRefreshLayout? = null
    private var adapter: PemesananAdapter? = null
    private var svPemesanan: SearchView? = null
    private var queue: RequestQueue? = null

    companion object{
        const val LAUNCH_ADD_ACTIVITY = 123
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_pemesanan)

        queue = Volley.newRequestQueue(this)
        srPemesanan = findViewById(R.id.sr_pemesanan)
        svPemesanan = findViewById(R.id.sv_pemesanan)

        srPemesanan?.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener { allPemesanan() })
        svPemesanan?.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(s: String): Boolean{
                return false
            }

            override fun onQueryTextChange(s: String?): Boolean {
                adapter!!.filter.filter(s)
                return false
            }
        })

        val fabAdd = findViewById<FloatingActionButton>(R.id.fab_add)
        fabAdd.setOnClickListener{
            val i = Intent(this@ShowPemesanan, AddEditPemesanan::class.java)
            startActivityForResult(i, LAUNCH_ADD_ACTIVITY)
        }
        val rvProduk = findViewById<RecyclerView>(R.id.rv_pemesanan)
        adapter = PemesananAdapter(ArrayList(), this)
        rvProduk.layoutManager = LinearLayoutManager(this)
        rvProduk.adapter = adapter
        allPemesanan()
    }

    private fun allPemesanan(){
        srPemesanan!!.isRefreshing = true
        val stringRequest: StringRequest = object :
            StringRequest(Method.GET, PemesananApi.GET_ALL_URL, Response.Listener { response ->
                val gson = Gson()
                var pemesanan : Array<Pemesanan> = gson.fromJson(response, Array<Pemesanan>::class.java)

                adapter!!.setPemesananList(pemesanan)
                adapter!!.filter.filter(svPemesanan!!.query)
                srPemesanan!!.isRefreshing = false

                if(!pemesanan.isEmpty())
//                    Toast.makeText(this@ShowPemesanan, "Data Berhasil Diambil", Toast.LENGTH_SHORT).show()
                    FancyToast.makeText(this@ShowPemesanan,"Data Berhasil Diambil",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show()

            }, Response.ErrorListener { error ->
                srPemesanan!!.isRefreshing = false
                try {
                    val responseBody =
                        String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
//                    Toast.makeText(this@ShowPemesanan, errors.getString("message"), Toast.LENGTH_SHORT).show()
                    FancyToast.makeText(this@ShowPemesanan,"message",FancyToast.LENGTH_LONG,FancyToast.DEFAULT,true).show()

                }catch (e: Exception){
//                    Toast.makeText(this@ShowPemesanan, e.message, Toast.LENGTH_SHORT).show()
                    FancyToast.makeText(this@ShowPemesanan,e.message,FancyToast.LENGTH_LONG,FancyToast.DEFAULT,true).show()

                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String>{
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                return headers
            }
        }
        queue!!.add(stringRequest)
    }

    fun deletePemesanan(id: Long){
        setLoading(true)
        val stringRequest: StringRequest = object :
            StringRequest(Method.DELETE, PemesananApi.DELETE_URL + id, Response.Listener { response ->
                setLoading(false)

                val gson = Gson()
                var pemesanan = gson.fromJson(response, Pemesanan::class.java)
                if(pemesanan != null)
//                    Toast.makeText(this@ShowPemesanan, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show()
                FancyToast.makeText(this@ShowPemesanan,"Data Berhasil Dihapus",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show()

                allPemesanan()
            }, Response.ErrorListener { error ->
                setLoading(false)
                try{
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
//                    Toast.makeText(this@ShowPemesanan, errors.getString("messsage"), Toast.LENGTH_SHORT).show()
                    FancyToast.makeText(this@ShowPemesanan,"message",FancyToast.LENGTH_LONG,FancyToast.DEFAULT,true).show()

                }catch (e: java.lang.Exception){
//                    Toast.makeText(this@ShowPemesanan, e.message, Toast.LENGTH_SHORT).show()
                    FancyToast.makeText(this@ShowPemesanan,e.message,FancyToast.LENGTH_LONG,FancyToast.DEFAULT,true).show()

                }
            }){
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String>{
                val headers = java.util.HashMap<String, String>()
                headers["Accept"] = "application/json"
                return headers
            }
        }
        queue!!.add(stringRequest)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LAUNCH_ADD_ACTIVITY && resultCode == RESULT_OK) allPemesanan()
    }

    private fun setLoading(isLoading: Boolean){
        if (isLoading){
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
        }else{
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }
}