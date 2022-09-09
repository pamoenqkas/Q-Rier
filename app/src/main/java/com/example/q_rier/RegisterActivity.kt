package com.example.q_rier

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.android.material.textfield.TextInputLayout


class RegisterActivity : AppCompatActivity() {

    private lateinit var inputUsername: TextInputLayout
    private lateinit var inputPassword: TextInputLayout
    private lateinit var inputEmail: TextInputLayout
    private lateinit var inputTanggalLahir: TextInputLayout
    private lateinit var inputPhoneNumber: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setTitle("Registration")

        inputUsername = findViewById(R.id.inputLayoutUsername)
        inputPassword = findViewById(R.id.inputLayoutPassword)
        inputEmail = findViewById(R.id.inputLayoutEmail)
        inputTanggalLahir = findViewById(R.id.inputLayoutTanggalLahir)
        inputPhoneNumber = findViewById(R.id.inputLayoutPhoneNumber)
        val btnRegister: Button = findViewById(R.id.btnRegister)

        btnRegister.setOnClickListener(View.OnClickListener {
            var checkLogin = false
            val bundle = Bundle()
            val username: String = inputUsername.getEditText()?.getText().toString()
            val password: String = inputPassword.getEditText()?.getText().toString()
            val email: String = inputEmail.getEditText()?.getText().toString()
            val tanggalLahir: String = inputTanggalLahir.getEditText()?.getText().toString()
            val phoneNumber: String = inputPhoneNumber.getEditText()?.getText().toString()

            if (username.isEmpty()){
                inputUsername.setError("Username must be filled with text")
                checkLogin = false
            }


            if (password.isEmpty()){
                inputPassword.setError("Password must be filled with text")
                checkLogin = false
            }

            if(email.isEmpty()){
                inputEmail.setError("Email must be filled with text")
                checkLogin = false
            }

            if(tanggalLahir.isEmpty()){
                inputTanggalLahir.setError("Tanggal lahir must be filled with text")
                checkLogin = false
            }

            if(phoneNumber.isEmpty()){
                inputPhoneNumber.setError("Phone number must be filled with text")
                checkLogin = false
            }

            if (username != "" && password != "" && email != "" && tanggalLahir != "" && phoneNumber != "") checkLogin = true
            if (!checkLogin) return@OnClickListener
            val moveHome = Intent(this@RegisterActivity, HomeActivity::class.java)
            bundle.putString("username", username)
            bundle.putString("password", password)
            bundle.putString("email", email)
            bundle.putString("tanggalLahir", tanggalLahir)
            bundle.putString("phoneNumber", phoneNumber)
            moveHome.putExtra("register",bundle)
            startActivity(moveHome)


        })
    }
}