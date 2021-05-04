package com.talo.theqrcode.fragmentbag

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.talo.theqrcode.R
import kotlinx.android.synthetic.main.fragment_q_r_generate.*


/**
 * A simple [Fragment] subclass.
 * Use the [QRGenerateFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class QRGenerateFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

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

        return inflater.inflate(R.layout.fragment_q_r_generate, null)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btn_output_url.setOnClickListener { btnOutputUrl() }
    }

    private fun btnOutputUrl() {
        val editStr = input_url.text.toString()
        if (editStr.isEmpty()){
            Toast.makeText(activity, R.string.input_text, Toast.LENGTH_SHORT).show()
        }else{
            val writer = MultiFormatWriter()
            val matrix: BitMatrix = writer.encode(editStr, BarcodeFormat.QR_CODE, 500, 500)
            val encode = BarcodeEncoder()
            val bitmap: Bitmap = encode.createBitmap(matrix)
            img_QR.setImageBitmap(bitmap)


        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            QRScannerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}