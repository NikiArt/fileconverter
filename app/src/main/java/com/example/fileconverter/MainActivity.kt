package com.example.fileconverter

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.fileconverter.conversion.MyConversion
import com.example.fileconverter.converter.MyConverter
import com.example.fileconverter.views.MainView
import io.reactivex.android.schedulers.AndroidSchedulers
import java.io.File

class MainActivity : AppCompatActivity(), MainView {
    private val permissons =
        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    private val PERMISSIONS_REQUEST_ID = 0
    private val PICK_IMAGE_REQUEST_ID = 1
    lateinit var presenter: MyPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = MyPresenter(this, AndroidSchedulers.mainThread(), MyConverter(this))
        val button: Button = findViewById(R.id.button)
        button.setOnClickListener {
            presenter.convertButtonClick()
        }
    }

    override fun pickImage() {
        if (!checkPermissions()) {
            requestPermissions()
            return
        }

        onPermissionsGranted()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICK_IMAGE_REQUEST_ID) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    val src: Uri = data.data
                    val dat: Uri =
                        Uri.fromFile(File("" + getExternalFilesDir(Environment.DIRECTORY_PICTURES) + File.separator + "result.png"))
                    val meta = MyConversion(src, dat)
                    presenter.pathsSelected(meta)
                }
            }

        }
    }

    private fun checkPermissions(): Boolean {
        for (permission in permissons) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    private fun onPermissionsGranted() {
        intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "select picture"), PICK_IMAGE_REQUEST_ID)
    }

    fun requestPermissions() {
        ActivityCompat.requestPermissions(this, permissons, PERMISSIONS_REQUEST_ID)
    }

    override fun showConvertationSuccessMessage() {
        Toast.makeText(this, "Converted success", Toast.LENGTH_SHORT).show()
    }

    override fun showConvertationFailedMessage() {
        Toast.makeText(this, "Conversation failed", Toast.LENGTH_SHORT).show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSIONS_REQUEST_ID -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    onPermissionsGranted()
                } else {
                    AlertDialog.Builder(this)
                        .setTitle("Need permission")
                        .setMessage("Please, add permission to the necessaries functions")
                        .setPositiveButton("OK") { _, _ -> requestPermissions() }
                        .setOnCancelListener { requestPermissions() }
                        .create()
                        .show()
                }
            }
        }
    }
}
