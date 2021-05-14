package com.talo.theqrcode.fragmentbag

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.talo.theqrcode.R
import kotlinx.android.synthetic.main.fragment_q_r_scanner.*


private const val CAMERA_REQUEST_CODE = 100

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [QRScannerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class QRScannerFragment : Fragment() {

    private lateinit var codeScanner: CodeScanner

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

        return inflater.inflate(R.layout.fragment_q_r_scanner, container, false)
    }

    private var savingUrl = 0

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setUpPermissions()
        codeScanner()

    }

    private fun codeScanner(){
        codeScanner = CodeScanner(activity!!, scanner)
        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS

            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.CONTINUOUS
            isAutoFocusEnabled = true
            isFlashEnabled = false

            decodeCallback = DecodeCallback {
                activity?.runOnUiThread {
                    txt_url.text = it.text
                    if (txt_url.text != ""){
                        btn_url.visibility = View.VISIBLE
                        btn_add.visibility = View.VISIBLE
                    }
                    btn_add.setOnClickListener {
                        //儲存網址
                        saveUrl()
                        Toast.makeText(activity, "Saving Url", Toast.LENGTH_SHORT).show()
                    }
                    btn_url.setOnClickListener {
                        val url = Uri.parse(txt_url.text.toString())
                        val intent = Intent(Intent.ACTION_VIEW, url)
                        startActivity(intent)
                    }
                }
            }
            errorCallback = ErrorCallback {
                activity?.runOnUiThread {
                    Toast.makeText(activity, "camera initialization error ${it.message} ",Toast.LENGTH_SHORT).show()
                }
            }
        }
        scanner.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }
    private fun setUpPermissions(){
        val permission = ContextCompat.checkSelfPermission(activity!!, android.Manifest.permission.CAMERA)
        if (permission != PackageManager.PERMISSION_GRANTED){
            makeRequest()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(activity!!,
            arrayOf(android.Manifest.permission.CAMERA),
            CAMERA_REQUEST_CODE)
    }
    private fun saveUrl(){
        savingUrl++
        var num = activity!!.getSharedPreferences("save", Context.MODE_PRIVATE)
            .getInt("url_number", savingUrl)

        when (num) {
            1 -> {
                activity!!.getSharedPreferences("save", Context.MODE_PRIVATE)
                    .edit()
                    .putString("url_1", txt_url.text.toString())
                    .apply()
            }
            2 -> {
                activity!!.getSharedPreferences("save", Context.MODE_PRIVATE)
                    .edit()
                    .putString("url_2", txt_url.text.toString())
                    .apply()
            }
            3 -> {
                activity!!.getSharedPreferences("save", Context.MODE_PRIVATE)
                    .edit()
                    .putString("url_3", txt_url.text.toString())
                    .apply()
            }
            4 -> {
                activity!!.getSharedPreferences("save", Context.MODE_PRIVATE)
                    .edit()
                    .putString("url_4", txt_url.text.toString())
                    .apply()
            }
            else ->{
                Toast.makeText(activity, "最多保存4筆記錄", Toast.LENGTH_SHORT).show()
            }
        }
        activity!!.getSharedPreferences("save", Context.MODE_PRIVATE)
            .edit()
            .putInt("url_number", savingUrl)
            .apply()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            CAMERA_REQUEST_CODE ->{
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(activity, "You need the camera permission",Toast.LENGTH_SHORT).show()
                }
            }
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