package com.example.q_rier

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity: AppCompatActivity() {

    private lateinit var inputUsername: TextInputEditText
    private lateinit var inputPassword: TextInputEditText
    private lateinit var mainLayout: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ubah Title pada App Bar Aplikasi
        setTitle("User Login")

        // Menyembunyikan Action Bar
        getSupportActionBar()?.hide()

        val btnRegister : Button = findViewById(R.id.btnRegister)
        btnRegister.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        })

        // Menghubungkan variabel dengan view
        inputUsername = findViewById(R.id.inputLayoutUsernameLogin)
        inputPassword = findViewById(R.id.inputLayoutPasswordLogin)
        mainLayout = findViewById(R.id.mainLayout)
        val btnClear: Button = findViewById(R.id.btnClear)
        val btnLogin: Button = findViewById(R.id.btnLogin)

        // Aksi btnClear ketika di klik
        btnClear.setOnClickListener {  // Mengkosongkan Input
            inputUsername.setText("")
            inputPassword.setText("")

            // Memunculkan SnackBar
            Snackbar.make(mainLayout, "Text Cleared Succes", Snackbar.LENGTH_LONG).show()
        }


        val bundle = intent.extras
        if (bundle != null){
            inputUsername.setText(bundle.getString("username"))
        }

        // Aksi pada btnLogin
        btnLogin.setOnClickListener(View.OnClickListener {
            var checkLogin = false
            val username: String = inputUsername.text.toString()
            val password: String = inputPassword.text.toString()
            val bundle = intent.extras
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

            if (bundle != null){
                if(
                    username == bundle.getString("username") && password == bundle.getString("password")
                )
                    checkLogin = true
            }

            if (!checkLogin){
                Snackbar.make(mainLayout, "Username atau Password anda tidak sesuai", Snackbar.LENGTH_LONG).show()
                return@OnClickListener
            }else{
                val intent = Intent(this, MainMenu::class.java)
                startActivity(intent)
            }
//            val moveHome = Intent(this@MainActivity, MainActivity::class.java)
//            startActivity(moveHome)
        })
    }
}