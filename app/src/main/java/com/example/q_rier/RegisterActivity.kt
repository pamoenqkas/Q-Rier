package com.example.q_rier


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import api.RegisterApi
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.q_rier.databinding.ActivityRegisterBinding
import com.example.q_rier.room.Pelanggan
import com.example.q_rier.room.PelangganDB
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.q_rier.User.User
import com.example.q_rier.User.UserApi
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets


class RegisterActivity : AppCompatActivity() {
    val db by lazy { PelangganDB(this) }


    var binding: ActivityRegisterBinding? = null
    private val CHANNEL_ID_1 = "channel_notification_01"
    private val CHANNEL_ID_2 = "channel_notification_02"
    private val notificationId1 = 101
    private val notificationId2 = 102

    private var inputLayoutUsernameRegister: EditText? = null
    private var inputLayoutPasswordRegister: EditText? = null
    private var inputLayoutEmailRegister: EditText? = null
    private var inputLayoutTanggalLahirRegister: EditText? = null
    private var inputLayoutPhoneNumberRegister: EditText? = null
    private var layoutLoading: LinearLayout? = null
    private var queue: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle("Registration")
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        createNotificationChannel()

        //Menyembunyikan Action Bar
        getSupportActionBar()?.hide()

        binding?.apply {

            btnRegister.setOnClickListener(View.OnClickListener {
                var checkLogin = false
                val username = inputLayoutUsernameRegister.text.toString()
                val password = inputLayoutPasswordRegister.text.toString()
                val email = inputLayoutEmailRegister.text.toString()
                val tanggalLahir = inputLayoutTanggalLahirRegister.text.toString()
                val phoneNumber = inputLayoutPhoneNumberRegister.text.toString()
                sendNotification1()

                if (username.isEmpty()) {
                    inputLayoutUsername.setError("Username must be filled with text")
                    checkLogin = false
                }else if (password.isEmpty()) {
                    inputLayoutPassword.setError("Password must be filled with text")
                    checkLogin = false
                }else if (email.isEmpty()) {
                    inputLayoutEmail.setError("Email must be filled with text")
                    checkLogin = false
                }else if (tanggalLahir.isEmpty()) {
                    inputLayoutTanggalLahir.setError("Tanggal lahir must be filled with text")
                    checkLogin = false
                }else if (phoneNumber.isEmpty()) {
                    inputLayoutPhoneNumber.setError("Phone number must be filled with text")
                    checkLogin = false
                }else if (username != "" && password != "" && email != "" && tanggalLahir != "" && phoneNumber != "") checkLogin =
                    true
                if (!checkLogin) return@OnClickListener
                val moveHome = Intent(this@RegisterActivity, MainActivity::class.java)

                val user = User(username, password, email, tanggalLahir, phoneNumber)

                val stringRequest: StringRequest = object : StringRequest(Method.POST, UserApi.ADD_URL,
                    Response.Listener { response ->
                        val gson = Gson()
                        val user = gson.fromJson(response, User::class.java)
                    }, Response.ErrorListener { error ->
                        try {
                            val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                            val errors = JSONObject(responseBody)
                            Toast.makeText(
                                this@RegisterActivity,
                                errors.getString("message"),
                                Toast.LENGTH_SHORT
                            ).show()
                        }catch (e: Exception){
                            Toast.makeText(this@RegisterActivity, e.message, Toast.LENGTH_SHORT).show()
                        }
                    }){
                    @Throws(AuthFailureError::class)
                    override fun getHeaders(): Map<String, String>{
                        val headers= HashMap<String, String>()
                        headers["Accept"] = "application/json"
                        return headers
                    }
                    @Throws(AuthFailureError::class)
                    override fun getBody(): ByteArray{
                        val gson = Gson()
                        val requestBody = gson.toJson(user)
                        return requestBody.toByteArray(StandardCharsets.UTF_8)
                    }

                    override fun getBodyContentType(): String {
                        return "application/json"
                    }
                }
                queue!!.add(stringRequest)
//                CoroutineScope(Dispatchers.IO).launch {
//                    db.PelanganDao().addNote(
//                        Pelanggan(
//                            0,
//                            inputLayoutUsernameRegister.text.toString(),
//                            inputLayoutPasswordRegister.text.toString(),
//                            inputLayoutEmailRegister.text.toString(),
//                            inputLayoutTanggalLahirRegister.text.toString(),
//                            inputLayoutPhoneNumberRegister.text.toString()
//
//                        )
//                    )
//                    finish()
//                }

//                bundle.putString("username", username)
//                bundle.putString("password", password)
//                bundle.putString("email", email)
//                bundle.putString("tanggalLahir", tanggalLahir)
//                bundle.putString("phoneNumber", phoneNumber)
//                moveHome.putExtras(bundle)
//                startActivity(moveHome)
            })
        }
    }



    //test
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notification Title"
            val descriptionText = "Notification Description"

            val channel1 = NotificationChannel(
                CHANNEL_ID_1,
                name,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = descriptionText
            }
            val channel2 = NotificationChannel(
                CHANNEL_ID_2,
                name,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel1)
            notificationManager.createNotificationChannel(channel2)

        }
    }

    private fun sendNotification1() {

        val intent: Intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val broadcastIntent: Intent = Intent(this, NotificationReceiver::class.java)
        broadcastIntent.putExtra("toastMessage", binding?.textView?.text.toString())
        val actionIntent =
            PendingIntent.getBroadcast(this, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val bigPictureBitmap = ContextCompat.getDrawable(this, R.drawable.logoapp)?.toBitmap()


        val bigPictureStyle = NotificationCompat.BigPictureStyle()
            .bigPicture(bigPictureBitmap)
        val builder = NotificationCompat.Builder(this, CHANNEL_ID_1)
            .setStyle(bigPictureStyle)
            .setSmallIcon(R.drawable.ic_baseline_looks_one_24)
            .setContentTitle("Q-rier")
            .setContentText("Registrasi Berhasil")
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setColor(Color.BLUE)
            .setAutoCancel(true)
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)
            .addAction(R.mipmap.ic_launcher, "Toast", actionIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            notify(notificationId1, builder.build())
        }
    }

//    private fun createUser() {
//        val user = Users(
//            inputLayoutUsernameRegister!!.text.toString(),
//            inputLayoutPasswordRegister!!.text.toString(),
//            inputLayoutEmailRegister!!.text.toString(),
//            inputLayoutTanggalLahirRegister!!.text.toString(),
//            inputLayoutPhoneNumberRegister!!.text.toString(),
//        )
//        val url = URL("http//192.168.18.14/login/register.php")
//        val stringRequest: StringRequest =
//            object :
//                StringRequest(Method.POST, RegisterApi.ADD_URL, Response.Listener { response ->
//                    val gson = Gson()
//                    var user = gson.fromJson(response, Users::class.java)
//
//                    if (user != null)
//                        Toast.makeText(
//                            this@RegisterActivity,
//                            "Data Berhasil Ditambahkan",
//                            Toast.LENGTH_SHORT
//                        ).show()
//
//                    val returnIntent = Intent()
//                    setResult(RESULT_OK, returnIntent)
//                    finish()
//
//
//                }, Response.ErrorListener { error ->
//
//                    try {
//                        val responseBody =
//                            String(error.networkResponse.data, StandardCharsets.UTF_8)
//                        val errors = JSONObject(responseBody)
//                        Toast.makeText(
//                            this@RegisterActivity,
//                            errors.getString("message"),
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    } catch (e: Exception) {
//                        Toast.makeText(this@RegisterActivity, e.message, Toast.LENGTH_SHORT).show()
//                    }
//                }) {
//                @Throws(AuthFailureError::class)
//                override fun getHeaders(): Map<String, String> {
//                    val headers = HashMap<String, String>()
//                    headers["Accept"] = "application/json"
//                    return headers
//                }
//
//                @Throws(AuthFailureError::class)
//                override fun getBody(): ByteArray {
//                    val gson = Gson()
//                    val requestBody = gson.toJson(user)
//                    return requestBody.toByteArray(StandardCharsets.UTF_8)
//                }
//
//                override fun getBodyContentType(): String {
//                    return "application/json"
//                }
//            }
//
//        queue!!.add(stringRequest)
//    }
    fun sendGet() {
        val url = URL("http//192.168.18.14/login/register.php")

        with(url.openConnection() as HttpURLConnection) {
            requestMethod = "GET"  // optional default is GET

            println("\nSent 'GET' request to URL : $url; Response Code : $responseCode")

            inputStream.bufferedReader().use {
                it.lines().forEach { line ->
                    println(line)
                }
            }
        }
    }


}
