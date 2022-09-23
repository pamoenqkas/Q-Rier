package com.example.q_rier

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainMenu : AppCompatActivity() {

    private lateinit var binding : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        setCurrentFragment(FragmentHome())
        binding = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        binding.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.home -> setCurrentFragment(FragmentHome())
                R.id.history -> setCurrentFragment(FragmentHistory())
                R.id.account -> setCurrentFragment(FragmentAccount())
            }
            true

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // disini kita menghubungkan menu yang telah kita buat dengan activity ini
        val menuInflater = MenuInflater(this)
        menuInflater.inflate(R.menu.top_right_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) : Boolean{

            val builder: AlertDialog.Builder = AlertDialog.Builder(this@MainMenu)
            builder.setMessage("Are you sure want to exit ?")
                .setPositiveButton("YES", object : DialogInterface.OnClickListener {
                    override fun onClick(dialogInterface: DialogInterface, i: Int){
                        finishAndRemoveTask()
                    }
                })
                .show()

        return super.onOptionsItemSelected(item)
    }

    private fun setCurrentFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.flFragment, fragment)
        fragmentTransaction.commit()
    }

}
