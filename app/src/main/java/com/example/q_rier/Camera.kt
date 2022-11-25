package com.example.q_rier

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.SearchManager
import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Camera
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.q_rier.databinding.ActivityCameraBinding
import com.google.zxing.integration.android.IntentIntegrator
import com.shashank.sony.fancytoastlib.FancyToast
import java.lang.Exception

class Camera : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityCameraBinding

    companion object{
        private const val  CAMERA_REQUEST_CODE = 100
        private const val  STORAGE_REQUEST_CODE = 101

        private const val TAG = "MAIN_TAG"
    }

    private lateinit var cameraPermissions: Array<String>
    private lateinit var storagePermissions: Array<String>

    private var imageUri: Uri? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cameraBtn.setOnClickListener(this)
        binding.galleryBtn.setOnClickListener(this)
        binding.scanBtn.setOnClickListener(this)

        cameraPermissions = arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        storagePermissions = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

        val intentIntegrator = IntentIntegrator(this)
        intentIntegrator.setDesiredBarcodeFormats(listOf(IntentIntegrator.QR_CODE))
        intentIntegrator.initiateScan()
    }

    override fun onClick(v: View?){
        when(v?.id){
            R.id.cameraBtn -> {
                if (checkCameraPermissions()){
                    pickImageCamera()
                }else{
                    requestCameraPermission()
                }
            }
            R.id.galleryBtn -> {
                if (checkStoragePermissions()){
                    pickImageGallery()
                }else{
                    requestStoragePermission()
                }
            }
            R.id.scanBtn -> {
                if (imageUri == null){
                    showToast("Pick image first")
                }else{
                    detectResultFromImage()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var result = IntentIntegrator.parseActivityResult(resultCode, data)
        if (result != null) {
            AlertDialog.Builder(this)
                .setMessage("Would you like to go to ${result.contents}?")
                .setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->
                    val intent = Intent(Intent.ACTION_WEB_SEARCH)
                    intent.putExtra(SearchManager.QUERY,result.contents)
                    startActivity(intent)
                })
                .setNegativeButton("No",DialogInterface.OnClickListener { dialogInterface, i ->  })
                .create()
                .show()

        }
    }

    private fun checkCameraPermissions(): Boolean{
        val resultCamera = (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED)
        val resultStorage = (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED)

        return resultCamera && resultStorage
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(this, cameraPermissions, CAMERA_REQUEST_CODE)
    }

    private fun checkStoragePermissions(): Boolean{
        val result = (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED)

        return result
    }

    private fun requestStoragePermission() {
        ActivityCompat.requestPermissions(this, storagePermissions, STORAGE_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            CAMERA_REQUEST_CODE -> {
                if (grantResults.isNotEmpty()){
                    val cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                    val storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED

                    if (cameraAccepted && storageAccepted){
                        pickImageCamera()
                    }else{
                        showToast("Camera dan Storage Permissions Are Required")
                    }
                }
            }

            STORAGE_REQUEST_CODE -> {
                if (grantResults.isNotEmpty()){
                    val cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                    val storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED

                    if (cameraAccepted && storageAccepted){
                        pickImageCamera()
                    }else{
                        showToast("Storage Are Required...")
                    }
                }
            }
        }
    }

    private fun showToast(message: String){
        FancyToast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun pickImageCamera(){
        val contentValues = ContentValues()
        contentValues.put(MediaStore.Images.Media.TITLE, "Foto Sample")
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, "Deskripsi Foto Sample")

        imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)

        cameraActivityResultLauncher.launch(intent)
    }

    private val cameraActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){result ->
        if (result.resultCode == Activity.RESULT_OK){
            val data = result.data
            Log.d(TAG, "cameraActivityResultLauncher: imageUri: $imageUri")

            binding.imageTv.setImageURI(imageUri)
        }
    }

    private fun pickImageGallery(){
        val intent = Intent(Intent.ACTION_PICK)

        intent.type = "image/*"
        galleryActivityResultLauncher.launch(intent)
    }

    private val galleryActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){result ->
        if (result.resultCode == Activity.RESULT_OK){
            val data = result.data

            imageUri = data!!.data
            Log.d(TAG, "galleryActivityResultLauncher: imageUri: $imageUri")

            binding.imageTv.setImageURI(imageUri)
        }else{
            showToast("Dibatalkan .................")
        }
    }

    private fun detectResultFromImage(){

    }





}