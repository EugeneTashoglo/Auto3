package com.example.auto3

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar

import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.auto3.databinding.ActivityNavigateBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth


class Navigate : AppCompatActivity() {
    private lateinit var binding: ActivityNavigateBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNavigateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_navigate)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )

        drawerLayout = findViewById(R.id.drawer_layout)


        auth = FirebaseAuth.getInstance()


        setupActionBarWithNavController(navController, appBarConfiguration)

        navView.setupWithNavController(navController)


        val navigationView = findViewById<NavigationView>(R.id.nav_view_drawer)

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_profile -> {

                    true
                }
                R.id.logout_btn -> {


                    auth.signOut()

                    val sharedPreferences = getSharedPreferences("com.example.auto3", Context.MODE_PRIVATE)
                    sharedPreferences.edit().putBoolean("isChecked", false).apply()

                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()

                    true
                }
                else -> {
                    false
                }
            }
        }

        val burgerIcon = findViewById<ImageView>(R.id.burger_icon)

        burgerIcon.setOnClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }
    }
}