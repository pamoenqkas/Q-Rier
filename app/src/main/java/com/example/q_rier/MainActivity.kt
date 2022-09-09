package com.example.q_rier

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

class MainActivity: AppCompatActivity() {

    private lateinit var inputUsername: TextInputLayout
    private lateinit var inputPassword: TextInputLayout
    private lateinit var mainLayout: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Ubah Title pada App Bar Aplikasi
        setTitle("User Login")

        // Menghubungkan variabel dengan view
        inputUsername = findViewById(R.id.inputLayoutUsernameLogin)
        inputPassword = findViewById(R.id.inputLayoutPasswordLogin)
        mainLayout = findViewById(R.id.mainLayout)
        val btnClear: Button = findViewById(R.id.btnClear)
        val btnLogin: Button = findViewById(R.id.btnLogin)

        // Aksi btnClear ketika di klik
        btnClear.setOnClickListener {  // Mengkosongkan Input
            inputUsername.getEditText()?.setText("")
            inputPassword.getEditText()?.setText("")

            // Memunculkan SnackBar
            Snackbar.make(mainLayout, "Text Cleared Succes", Snackbar.LENGTH_LONG).show()
        }

        // Aksi pada btnLogin
        btnLogin.setOnClickListener(View.OnClickListener {
            var checkLogin = false
            val username: String = inputUsername.getEditText()?.getText().toString()
            val password: String = inputPassword.getEditText()?.getText().toString()

            // Pengecekan apakah Inputan Username kosong
            if (username.isEmpty()) {
                inputUsername.setError("Username must be filled !")
                checkLogin = false
            }

            // Pengecekan apakah Inputan Password kosong
            if (password.isEmpty()) {
                inputPassword.setError("Password must be filled !")
                checkLogin = false
            }

            // Ganti Password dengan NPM
            if (username == "user" && password == "1234") checkLogin = true
            if (!checkLogin) return@OnClickListener
            val moveHome = Intent(this@MainActivity, HomeActivity::class.java)
            startActivity(moveHome)
        })
    }
}