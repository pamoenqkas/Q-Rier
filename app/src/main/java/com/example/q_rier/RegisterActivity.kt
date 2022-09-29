package com.example.q_rier


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.example.q_rier.databinding.ActivityRegisterBinding
import com.example.q_rier.room.Pelanggan
import com.example.q_rier.room.PelangganDB
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class RegisterActivity : AppCompatActivity() {
    val db by lazy { PelangganDB(this) }


    var binding: ActivityRegisterBinding? = null
    private val CHANNEL_ID_1 = "channel_notification_01"
    private val CHANNEL_ID_2 = "channel_notification_02"
    private val notificationId1 = 101
    private val notificationId2 = 102

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle("Registration")
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        createNotificationChannel()


        binding?.apply {

            btnRegister.setOnClickListener(View.OnClickListener {
                var checkLogin = false
                val bundle = Bundle()
                val username = inputLayoutUsernameRegister.text.toString()
                val password = inputLayoutPasswordRegister.text.toString()
                val email = inputLayoutEmailRegister.text.toString()
                val tanggalLahir = inputLayoutTanggalLahirRegister.text.toString()
                val phoneNumber = inputLayoutPhoneNumberRegister.text.toString()
                sendNotification1()

                if (username.isEmpty()) {
                    inputLayoutUsername.setError("Username must be filled with text")
                    checkLogin = false
                }


                if (password.isEmpty()) {
                    inputLayoutPassword.setError("Password must be filled with text")
                    checkLogin = false
                }

                if (email.isEmpty()) {
                    inputLayoutEmail.setError("Email must be filled with text")
                    checkLogin = false
                }

                if (tanggalLahir.isEmpty()) {
                    inputLayoutTanggalLahir.setError("Tanggal lahir must be filled with text")
                    checkLogin = false
                }

                if (phoneNumber.isEmpty()) {
                    inputLayoutPhoneNumber.setError("Phone number must be filled with text")
                    checkLogin = false
                }

                if (username != "" && password != "" && email != "" && tanggalLahir != "" && phoneNumber != "") checkLogin =
                    true

                if (!checkLogin) return@OnClickListener
                val moveHome = Intent(this@RegisterActivity, MainActivity::class.java)
                CoroutineScope(Dispatchers.IO).launch {
                    db.PelanganDao().addNote(
                        Pelanggan(
                            0, inputLayoutUsernameRegister.text.toString(),
                            inputLayoutPasswordRegister.text.toString(),inputLayoutEmailRegister.text.toString(),
                            inputLayoutTanggalLahirRegister.text.toString(), inputLayoutPhoneNumberRegister.text.toString()

                        )
                    )
                    finish()
                }
                bundle.putString("username", username)
                bundle.putString("password", password)
                bundle.putString("email", email)
                bundle.putString("tanggalLahir", tanggalLahir)
                bundle.putString("phoneNumber", phoneNumber)
                moveHome.putExtras(bundle)
                startActivity(moveHome)
            })
        }
    }
    //test
    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "Notification Title"
            val descriptionText = "Notification Description"

            val channel1 = NotificationChannel(CHANNEL_ID_1, name, NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = descriptionText
            }
            val channel2 = NotificationChannel(CHANNEL_ID_2, name, NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel1)
            notificationManager.createNotificationChannel(channel2)

        }
    }

    private fun sendNotification1(){

        val intent : Intent = Intent (this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val broadcastIntent : Intent = Intent(this, NotificationReceiver::class.java)
        broadcastIntent.putExtra("toastMessage", binding?.textView?.text.toString())
        val actionIntent = PendingIntent.getBroadcast(this,0,broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT)
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

        with(NotificationManagerCompat.from(this)){
            notify(notificationId1, builder.build())
        }
    }
}
