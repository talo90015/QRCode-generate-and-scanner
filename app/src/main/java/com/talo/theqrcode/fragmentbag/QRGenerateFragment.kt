package com.talo.theqrcode.fragmentbag

import android.app.Dialog
import android.content.DialogInterface
import android.content.DialogInterface.OnClickListener
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.talo.theqrcode.R
import kotlinx.android.synthetic.main.color_choose.*
import kotlinx.android.synthetic.main.fragment_q_r_generate.*
import java.util.*


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

    private var imgId = intArrayOf(R.drawable.the_qr_code)

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
        img_QR.setOnLongClickListener { imgQRLongClick() }
        img_QR.setImageResource(imgId[0])
    }

    private fun imgQRLongClick() : Boolean{
        AlertDialog.Builder(activity!!)
            .setTitle(R.string.qrcode)
            .setMessage("保存生成QRCode")
            .setPositiveButton("確定"){_, _ ->
                //TODO 下載功能實作 https://www.youtube.com/watch?v=FcCtT1C7NGI 4:50
            }
            .show()
        return true

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