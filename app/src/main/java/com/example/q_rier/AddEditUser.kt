//package com.example.q_rier
//
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.widget.Button
//import android.widget.EditText
//import android.widget.TextView
//import android.widget.Toast
//import com.android.volley.AuthFailureError
//import com.android.volley.RequestQueue
//import com.android.volley.Response
//import com.android.volley.toolbox.StringRequest
//import com.android.volley.toolbox.Volley
//import com.example.q_rier.User.User
//import com.example.q_rier.User.UserApi
//import com.google.gson.Gson
//import org.json.JSONObject
//import java.net.Authenticator.RequestorType
//import java.nio.charset.StandardCharsets
//
//class AddEditUser : AppCompatActivity() {
//    private var etUsername: EditText? = null
//    private var etPassword: EditText? = null
//    private var etEmail: EditText? = null
//    private var etTanggalLahir: EditText? = null
//    private var etPhoneNumber: EditText? = null
//    private var queue: RequestQueue? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_add_edit_user)
//
//        queue = Volley.newRequestQueue(this)
//        etUsername = findViewById(R.id.e_username)
//        etPassword = findViewById(R.id.et_password)
//        etEmail = findViewById(R.id.et_email)
//        etTanggalLahir = findViewById(R.id.et_tanggal_lahir)
//        etPhoneNumber = findViewById(R.id.et_phone_number)
//
//
//
//        val btnCancel = findViewById<Button>(R.id.btn_cancel)
//        btnCancel.setOnClickListener{ finish() }
//        val btnSave = findViewById<Button>(R.id.btn_save)
//        val tvTitle = findViewById<TextView>(R.id.tv_title)
//        val id = intent.getLongExtra("id", -1)
//        if (id == -1L){
//            tvTitle.setText("Edit User")
//            getUserById(id)
//        }
//        btnSave.setOnClickListener { updateUser(id) }
//    }
//
//    private fun getUserById(id: Long){
//        val stringRequest: StringRequest = object :
//            StringRequest(Method.GET, UserApi.GET_BY_ID_URL + id, Response.Listener { response ->
//                val gson = Gson()
//                val user = gson.fromJson(response, User::class.java)
//
//                etUsername!!.setText(user.username)
//                etPassword!!.setText(user.password)
//                etEmail!!.setText(user.email)
//                etTanggalLahir!!.setText(user.tanggalLahir)
//                etPhoneNumber!!.setText(user.phoneNumber)
//
//                Toast.makeText(this@AddEditUser, "Data Berhasil diambil", Toast.LENGTH_SHORT).show()
//            }, Response.ErrorListener { error ->
//                try {
//                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
//                    val errors = JSONObject(responseBody)
//                    Toast.makeText(
//                        this@AddEditUser,
//                        errors.getString("message"),
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }catch (e: Exception){
//                    Toast.makeText(this@AddEditUser, e.message, Toast.LENGTH_SHORT).show()
//                }
//            }){
//                @Throws(AuthFailureError::class)
//                override fun getHeaders(): Map<String, String>{
//                    val headers= HashMap<String, String>()
//                    headers["Accept"] = "application/json"
//                    return headers
//                }
//                @Throws(AuthFailureError::class)
//                override fun getBody(): ByteArray{
//                    val gson = Gson()
//                    val requestBody = gson.toJson(User)
//                    return requestBody.toByteArray(StandardCharsets.UTF_8)
//                }
//
//            override fun getBodyContentType(): String {
//                return "application/json"
//            }
//            }
//        queue!!.add(stringRequest)
//    }
//
//    private fun addUser(id: Long){
//        val user : User(username, password, email, tanggalLahir, phoneNumber)
//        val stringRequest: StringRequest = object :
//            StringRequest(Method.GET, UserApi.GET_BY_ID_URL + id, Response.Listener { response ->
//                val gson = Gson()
//                val user = gson.fromJson(response, User::class.java)
//
//                etUsername!!.setText(user.username)
//                etPassword!!.setText(user.password)
//                etEmail!!.setText(user.email)
//                etTanggalLahir!!.setText(user.tanggalLahir)
//                etPhoneNumber!!.setText(user.phoneNumber)
//
//                Toast.makeText(this@AddEditUser, "Data Berhasil diambil", Toast.LENGTH_SHORT).show()
//            }, Response.ErrorListener { error ->
//                try {
//                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
//                    val errors = JSONObject(responseBody)
//                    Toast.makeText(
//                        this@AddEditUser,
//                        errors.getString("message"),
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }catch (e: Exception){
//                    Toast.makeText(this@AddEditUser, e.message, Toast.LENGTH_SHORT).show()
//                }
//            }){
//            @Throws(AuthFailureError::class)
//            override fun getHeaders(): Map<String, String>{
//                val headers= HashMap<String, String>()
//                headers["Accept"] = "application/json"
//                return headers
//            }
//            @Throws(AuthFailureError::class)
//            override fun getBody(): ByteArray{
//                val gson = Gson()
//                val requestBody = gson.toJson(User)
//                return requestBody.toByteArray(StandardCharsets.UTF_8)
//            }
//
//            override fun getBodyContentType(): String {
//                return "application/json"
//            }
//        }
//        queue!!.add(stringRequest)
//    }
//
//}