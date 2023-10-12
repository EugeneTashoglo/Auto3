package com.example.auto3



import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.content.Intent


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val buttonLogin = findViewById<Button>(R.id.buttonLogin)
        buttonLogin.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        })


        val regButton = findViewById<Button>(R.id.regButton)
        regButton.setOnClickListener(View.OnClickListener {

            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        })
    }
}