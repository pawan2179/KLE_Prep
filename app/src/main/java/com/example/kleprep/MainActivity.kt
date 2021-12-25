package com.example.kleprep

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.kleprep.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        drawerLayout = binding.drawerLayout
        val navView = binding.navView
        val headerView = navView.getHeaderView(0)

        replaceFragment(PracticeFragment(), "Practice")
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val userNameNav: TextView = headerView.findViewById(R.id.userNameNav)
        val userEmailNav: TextView = headerView.findViewById(R.id.userEmailNav)

        userNameNav.text = intent.getStringExtra("name")
        userEmailNav.text = intent.getStringExtra("email")

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {
            it.isChecked = true
            when(it.itemId) {
                R.id.nav_practice-> replaceFragment(PracticeFragment(), it.title.toString())
                R.id.nav_alumni-> replaceFragment(AlumniFragment(), it.title.toString())
                R.id.nav_contact-> replaceFragment(ContactFragment(), it.title.toString())
                R.id.nav_admin-> replaceFragment(AdminFragment(), it.title.toString())
            }
            true
        }
    }

    fun replaceFragment(fragment: Fragment, title: String) {
        val fragmentManager= supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
        drawerLayout.closeDrawers()
        setTitle(title)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}