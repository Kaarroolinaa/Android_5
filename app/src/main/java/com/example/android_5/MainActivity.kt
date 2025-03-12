package com.example.android_5

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import com.example.android_5.Compass.CompassActivity
import com.example.android_5.GforceMeter.GForceMeterActivity
import com.example.android_5.Pedometer.PedometerActivity
import com.example.android_5.levelView.LevelActivity

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawer_layout)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navigationView: NavigationView = findViewById(R.id.nav_view)

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeAsUpIndicator(android.R.drawable.ic_menu_sort_by_size)

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_level -> {
                    startActivity(Intent(this, LevelActivity::class.java))
                }
                R.id.nav_compass -> {
                    startActivity(Intent(this, CompassActivity::class.java))
                }
                R.id.nav_pedometer -> {
                    startActivity(Intent(this, PedometerActivity::class.java))
                }
                R.id.nav_gforce_meter -> {
                    startActivity(Intent(this, GForceMeterActivity::class.java))
                }
            }
            drawerLayout.closeDrawers()
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
