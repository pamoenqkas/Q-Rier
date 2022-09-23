package com.example.q_rier


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitle("Registration")
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        binding?.apply {

            btnRegister.setOnClickListener(View.OnClickListener {
                var checkLogin = false
                val bundle = Bundle()
                val username = inputLayoutUsernameRegister.text.toString()
                val password = inputLayoutPasswordRegister.text.toString()
                val email = inputLayoutEmailRegister.text.toString()
                val tanggalLahir = inputLayoutTanggalLahirRegister.text.toString()
                val phoneNumber = inputLayoutPhoneNumberRegister.text.toString()

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
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}

