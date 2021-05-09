package com.talo.theqrcode.fragmentbag

import android.R.attr.defaultValue
import android.R.attr.key
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.talo.theqrcode.R
import com.talo.theqrcode.RecycleViewPage.Contact
import kotlinx.android.synthetic.main.fragment_q_r_code_list.*
import kotlinx.android.synthetic.main.url_string_layout.view.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [QRCodeListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class QRCodeListFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private val str = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_q_r_code_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        val contacts = listOf<Contact>(
            Contact("")
        )

        recycler.setHasFixedSize(true)
        recycler.layoutManager = LinearLayoutManager(context)
        val adapter = object : RecyclerView.Adapter<ContactViewHolder>() {

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
                //設定佈局
                val view = layoutInflater.inflate(R.layout.url_string_layout, parent, false)
                return ContactViewHolder(view)
            }

            override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
                holder.url.text = contacts[position].urlStr
                holder.btnBrowser.setOnClickListener {
                    Toast.makeText(activity, "hi", Toast.LENGTH_SHORT).show()
                }
            }

            override fun getItemCount(): Int {
                return contacts.size
            }
        }
        recycler.adapter = adapter
    }

    class ContactViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val url: TextView = view.saving_url
        val btnBrowser: ImageButton = view.browser
    }



    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            QRCodeListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}