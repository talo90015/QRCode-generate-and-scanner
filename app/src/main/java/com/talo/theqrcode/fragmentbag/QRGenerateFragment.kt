package com.talo.theqrcode.fragmentbag

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.talo.theqrcode.R
import kotlinx.android.synthetic.main.color_choose.*
import kotlinx.android.synthetic.main.fragment_q_r_generate.*
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
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
    private var count = 0

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
        btn_info.setOnClickListener { btnInfoClick() }
        img_QR.setOnLongClickListener { imgQRLongClick() }
        img_QR.setImageResource(imgId[0])
        activity!!.getSharedPreferences("save_number", Context.MODE_PRIVATE)
            .getInt("image_num", 0)

    }

    private fun btnInfoClick() {
        val inflater = layoutInflater
        val view: View = inflater.inflate(
            R.layout.toast_info,
            view?.findViewById(R.id.toast_info) as ViewGroup?
        )
        val toast = Toast(activity)
        toast.setGravity(Gravity.BOTTOM, 0, 0)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = view
        toast.show()
    }

    private fun imgQRLongClick() : Boolean{
        AlertDialog.Builder(activity!!)
            .setTitle(R.string.qrcode)
            .setMessage("保存生成QRCode")
            .setNegativeButton("取消", null)
            .setPositiveButton("確定"){ _, _ ->
                Toast.makeText(activity, "保存 QRCode 中...", Toast.LENGTH_SHORT).show()
                saveQRCode()
            }
            .show()
        return true

    }

    private fun saveQRCode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (ContextCompat.checkSelfPermission(
                    activity!!,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(
                    activity!!,
                    arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    100
                )
            }else{
                saveQRCodeImage()
            }
        }else{
            saveQRCodeImage()
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 100){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(activity, "You need the File permission", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun saveQRCodeImage(){
        val externalStorageState = Environment.getExternalStorageState()
        if (externalStorageState == Environment.MEDIA_MOUNTED){
            val storageDirectory = Environment.getExternalStorageDirectory().toString()
            count++
            val file = File(storageDirectory, "${count}_QRCode.jpg")
            try {
                val stream: OutputStream = FileOutputStream(file)
                val editStr = input_url.text.toString()
                val writer = MultiFormatWriter()
                val matrix: BitMatrix = writer.encode(editStr, BarcodeFormat.QR_CODE, 500, 500)
                val encode = BarcodeEncoder()
                val bitmap: Bitmap = encode.createBitmap(matrix)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                stream.flush()
                stream.close()
                Toast.makeText(activity, "保存", Toast.LENGTH_SHORT).show()
                activity!!.getSharedPreferences("save_number", Context.MODE_PRIVATE)
                    .edit()
                    .putInt("image_num", count)
                    .apply()
            }catch (e: Exception){

            }
        }else{
            Toast.makeText(activity, "Unable to access the storage", Toast.LENGTH_SHORT).show()
        }
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
            btn_info.visibility = View.VISIBLE
            info_card.visibility = View.VISIBLE
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