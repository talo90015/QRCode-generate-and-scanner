package com.talo.theqrcode

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuBuilder
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.talo.theqrcode.databinding.ActivityMainBinding
import com.talo.theqrcode.fragmentbag.QRGenerateFragment
import com.talo.theqrcode.fragmentbag.QRScannerFragment
import com.talo.theqrcode.pageAdapter.Page
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.reflect.Method


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayShowTitleEnabled(false)
        setSupportActionBar(toolbar)


        //viewPage元件設定
        //目前兩頁Fragment片段
        //需先設定adapter頁面適配器
        val page = Page(supportFragmentManager)
        page.addFragment(QRGenerateFragment(), "QR_generate")
        page.addFragment(QRScannerFragment(), "QR_scanner")

        val viewPager = binding.viewPage
        viewPager.adapter = page
        viewPager.offscreenPageLimit = 2
        viewPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

        binding.toolbar.inflateMenu(R.menu.menu_main)
        binding.toolbar.setOnMenuItemClickListener {

            when(it.itemId){
                R.id.qr_generate -> {
                    viewPager.currentItem = 0
                }
                R.id.qr_scanner -> {
                    viewPager.currentItem = 1
                }
            }
            false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    //重新複寫Menu方法
    //鏡像映射Icon使其能顯示
    override fun onMenuOpened(featureId: Int, menu: Menu): Boolean {
        if (menu != null) {
            if (menu.javaClass == MenuBuilder::class.java) {
                try {
                    val method: Method = menu.javaClass.getDeclaredMethod(
                        "setOptionalIconsVisible",
                        java.lang.Boolean.TYPE
                    )
                    method.isAccessible = true
                    method.invoke(menu, true)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        return super.onMenuOpened(featureId, menu)
    }
}