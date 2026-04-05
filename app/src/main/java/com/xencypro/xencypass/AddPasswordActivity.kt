package com.xencypro.xencypass

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.xencypro.xencypass.databinding.ActivityAddPasswordBinding

class AddPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.typeToggle.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (!isChecked) return@addOnButtonCheckedListener
            when (checkedId) {
                R.id.buttonApp -> showAppFields()
                R.id.buttonWebsite -> showWebsiteFields()
            }
        }

        binding.buttonApp.isChecked = true
        binding.backButton.setOnClickListener { finish() }

        binding.savePasswordButton.setOnClickListener {
            Toast.makeText(this, "Password entry saved placeholder", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun showAppFields() {
        binding.appFieldsGroup.visibility = View.VISIBLE
        binding.websiteFieldsGroup.visibility = View.GONE
    }

    private fun showWebsiteFields() {
        binding.appFieldsGroup.visibility = View.GONE
        binding.websiteFieldsGroup.visibility = View.VISIBLE
    }
}
