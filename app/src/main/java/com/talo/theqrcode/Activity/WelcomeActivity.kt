package com.talo.theqrcode.Activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.talo.theqrcode.R
import com.talo.theqrcode.databinding.ActivityWelcomeBinding
import pl.droidsonroids.gif.GifDrawable
import pl.droidsonroids.gif.GifImageView
import java.io.IOException

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        Thread {
            try {
                Thread.sleep(1500)
                startActivity(Intent(this@WelcomeActivity, MainActivity::class.java))
                finish()
            } catch (e: Exception) {

            }
        }.start()

        val gifImageView = findViewById<GifImageView>(R.id.gif_image)
        try {
            val gifDrawable = GifDrawable(resources, R.drawable.load_image)
            gifImageView.setImageDrawable(gifDrawable)
        }catch (e: IOException){
            e.printStackTrace()
        }
    }
}