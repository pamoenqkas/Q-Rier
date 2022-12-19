package com.example.q_rier

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView

class ViewProfile : AppCompatActivity() {
    private lateinit var inputUsername: MaterialTextView
    private lateinit var inputEmail: MaterialTextView
    private lateinit var inputPhone: MaterialTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_profile)

        val btnUpdate : Button = findViewById(R.id.btn_update)
        btnUpdate.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        })
        inputUsername = findViewById(R.id.et_username1)
        inputEmail = findViewById(R.id.et_email1)
        inputPhone = findViewById(R.id.et_phoneNumber1)

        val bundle = intent.extras
        if (bundle != null){
            inputUsername.setText(bundle.getString("username"))
            inputEmail.setText(bundle.getString("email"))
            inputPhone.setText(bundle.getString("phoneNumber"))
        }


    }
}