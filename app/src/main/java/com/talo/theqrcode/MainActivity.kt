package com.talo.theqrcode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.talo.theqrcode.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.inflateMenu(R.menu.menu_main)
        binding.toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.qr_generate ->{

                }
                R.id.qr_scanner ->{

                }
            }
            false
        }
    }


}