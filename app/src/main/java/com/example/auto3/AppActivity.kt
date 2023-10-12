package com.example.auto3

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth

class AppActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)

        auth = FirebaseAuth.getInstance()

        val logoutButton = findViewById<Button>(R.id.logout_btn)
        logoutButton.setOnClickListener {
            auth.signOut()

            // Сбросьте состояние "запомнить меня"
            val sharedPreferences = getSharedPreferences("com.example.auto3", Context.MODE_PRIVATE)
            sharedPreferences.edit().putBoolean("isChecked", false).apply()

            // Вернуться к экрану входа после выхода из системы
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}