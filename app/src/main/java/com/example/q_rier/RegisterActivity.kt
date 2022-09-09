package com.example.q_rier

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class RegisterActivity : AppCompatActivity() {

    private lateinit var inputUsername: TextInputEditText
    private lateinit var inputPassword: TextInputEditText
    private lateinit var inputEmail: TextInputEditText
    private lateinit var inputTanggalLahir: TextInputEditText
    private lateinit var inputPhoneNumber: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setTitle("Registration")

        inputUsername = findViewById(R.id.inputLayoutUsernameRegister)
        inputPassword = findViewById(R.id.inputLayoutPasswordRegister)
        inputEmail = findViewById(R.id.inputLayoutEmailRegister)
        inputTanggalLahir = findViewById(R.id.inputLayoutTanggalLahirRegister)
        inputPhoneNumber = findViewById(R.id.inputLayoutPhoneNumberRegister)
        val btnRegister: Button = findViewById(R.id.btnRegister)

        btnRegister.setOnClickListener(View.OnClickListener {
            var checkLogin = false
            val bundle = Bundle()
            val username: String = inputUsername.text.toString()
            val password: String = inputPassword.text.toString()
            val email: String = inputEmail.text.toString()
            val tanggalLahir: String = inputTanggalLahir.text.toString()
            val phoneNumber: String = inputPhoneNumber.text.toString()

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
            moveHome.putExtras(bundle)
            startActivity(moveHome)
        })
    }
}