package com.jc.bot

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
import android.widget.Toast
import com.jc.bot.data.SharedPreferenceStore
import com.jc.bot.data.SharedPreferenceStore.USERNAME
import com.jc.bot.data.SharedPreferenceStore.loadData
import com.jc.bot.data.SharedPreferenceStore.saveData
import com.jc.bot.databinding.ActivityMainBinding
import com.jc.bot.exceptions.UserInputException

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.myTextField.setText(loadData(applicationContext ,USERNAME,""))
        if (supportActionBar != null) {
            supportActionBar!!.hide()
            // Set the system UI visibility and status bar color
            window?.decorView?.systemUiVisibility = (SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
            window.statusBarColor = Color.TRANSPARENT
        }
    }


    private fun isInputValid() : Boolean {
        var input = binding.myTextField.text.trim()
        if(input.isEmpty()) {
            throw UserInputException("Enter a name")
        }
        return true
    }

    fun startChat(view: View) {
        try {
            if(isInputValid()){
                saveData(
                    applicationContext,
                    USERNAME,
                    binding.myTextField.text.toString()
                )
                startActivity(Intent(applicationContext,ChatActivity::class.java))
            }
        }
        catch (e: UserInputException){
            Toast.makeText(applicationContext,e.message,Toast.LENGTH_LONG).show()
        }
    }
}

