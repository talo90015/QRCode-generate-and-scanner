package com.talo.theqrcode.Activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.talo.theqrcode.R
import com.talo.theqrcode.RecycleViewPage.Contact
import com.talo.theqrcode.databinding.ActivitySavingUrlBinding
import kotlinx.android.synthetic.main.activity_saving_url.*
import kotlinx.android.synthetic.main.url_string_layout.*
import kotlinx.android.synthetic.main.url_string_layout.view.*

class SavingUrlActivity : AppCompatActivity() {

    lateinit var binding: ActivitySavingUrlBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saving_url)

        binding = ActivitySavingUrlBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.savingUrlToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.recycler.setHasFixedSize(true)
        binding.recycler.layoutManager = LinearLayoutManager(this)
        theList()
    }

    private fun theList() {
        val url_1 = getSharedPreferences("save", Context.MODE_PRIVATE)
            .getString("url_1", "")
        val url_2 = getSharedPreferences("save", Context.MODE_PRIVATE)
            .getString("url_2", "")
        val url_3 = getSharedPreferences("save", Context.MODE_PRIVATE)
            .getString("url_3", "")
        val url_4 = getSharedPreferences("save", Context.MODE_PRIVATE)
            .getString("url_4", "")

        val contacts = listOf<Contact>(
            Contact(url_1.toString()),
            Contact(url_2.toString()),
            Contact(url_3.toString()),
            Contact(url_4.toString()),
        )
        val adapter = object : RecyclerView.Adapter<ContactViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
                val view = layoutInflater.inflate(R.layout.url_string_layout, parent, false)
                return ContactViewHolder(view)
            }

            override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
                holder.url.text = contacts[position].urlStr
                holder.browser.setOnClickListener {
                    val uri = Uri.parse(holder.url.text.toString())
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(intent)
                }
            }

            override fun getItemCount(): Int {
                return contacts.size
            }
        }
        binding.recycler.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       val id = item.itemId
        if (id == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
    class ContactViewHolder(view: View): RecyclerView.ViewHolder(view){
        val url: TextView = view.saving_url
        val browser: ImageButton = view.browser
    }
}