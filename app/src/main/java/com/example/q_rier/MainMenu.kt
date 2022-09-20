package com.example.q_rier

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
// mamamsamsm
        }
    }

    private fun setCurrentFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.flFragment, fragment)
        fragmentTransaction.commit()
    }
}
