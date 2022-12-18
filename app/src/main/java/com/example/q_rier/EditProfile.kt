package com.example.q_rier

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.Toast


class EditProfileActivity : AppCompatActivity() {

    lateinit var etUsername: EditText

    lateinit var etEmail: EditText
    lateinit var etPhone:EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        viewInitializations()
    }


    fun viewInitializations() {

        etUsername = findViewById(R.id.et_username)

        etEmail  = findViewById(R.id.et_email)
        etPhone = findViewById(R.id.et_phoneNumber)



        // To show back button in actionbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    // Checking if the input in form is valid
    fun validateInput(): Boolean {
        if (etUsername.text.toString().equals("")) {
            etUsername.setError("Please Enter Username")
            return false
        }
        if (etEmail.text.toString().equals("")) {
            etEmail.setError("Please Enter Email")
            return false
        }

        if (etPhone.text.toString().equals("")) {
            etPhone.setError("Please Enter Phone Number")
            return false
        }


        return true
    }



    // Hook Click Event

    fun performEditProfile (view: View) {
        if (validateInput()) {

            // Input is valid, here send data to your server

            val username = etUsername.text.toString()

            val email = etEmail.text.toString()
            val phone = etPhone.text.toString()


            Toast.makeText(this,"Profile Update Successfully",Toast.LENGTH_SHORT).show()
            // Here you can call you API

        }
    }

}