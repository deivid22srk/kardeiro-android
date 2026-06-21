package com.kardeiro.demo

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kardeiro.library.views.KardeiroButton
import com.kardeiro.library.views.KardeiroTextField

/**
 * Demo activity that uses the Kardeiro layout library.
 *
 * Showcases the KardeiroHeader, KardeiroCard, KardeiroTextField, KardeiroButton
 * and KardeiroBadge components wired up with simple click handlers.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val emailField = findViewById<KardeiroTextField>(R.id.email_field)
        val passwordField = findViewById<KardeiroTextField>(R.id.password_field)

        findViewById<KardeiroButton>(R.id.btn_login).setOnClickListener {
            val email = emailField.getText()
            if (email.isBlank()) {
                emailField.setError("Informe seu e-mail")
            } else {
                emailField.clearError()
                Toast.makeText(this, "Bem-vindo, $email!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
