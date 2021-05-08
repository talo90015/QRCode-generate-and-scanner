package com.talo.theqrcode.fragmentbag

import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.talo.theqrcode.R
import kotlinx.android.synthetic.main.color_choose.*
import kotlinx.android.synthetic.main.fragment_q_r_generate.*


/**
 * A simple [Fragment] subclass.
 * Use the [QRGenerateFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class QRGenerateFragment : Fragment(){

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
        //建議不在這邊編輯
        //呼叫onActivityCreate方法再開始編輯
        return inflater.inflate(R.layout.fragment_q_r_generate, null)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btn_output_url.setOnClickListener { btnOutputUrl() }
        btn_choose_color.setOnClickListener { btnChooseColor() }
    }

    private fun btnChooseColor() {
        val dialog = Dialog(activity!!)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.color_choose)

        dialog.btn_close.setOnClickListener {

            dialog.dismiss()
        }
        dialog.show()
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