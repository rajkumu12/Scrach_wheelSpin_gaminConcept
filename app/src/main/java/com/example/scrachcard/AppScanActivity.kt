package com.example.scrachcard

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import com.zynksoftware.documentscanner.ScanActivity
import com.zynksoftware.documentscanner.model.DocumentScannerErrorModel
import com.zynksoftware.documentscanner.model.ScannerResults
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date


class AppScanActivity : ScanActivity(){
    lateinit var  appDirectory:File
    override fun onClose() {
        finish()
        Toast.makeText(this, "closed", Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_scan)
        createAppDirectoryInDownloads(this)
        addFragmentContentLayout()
    }
    fun createAppDirectoryInDownloads(context: Context): File? {
        val downloadsDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        appDirectory = File(downloadsDirectory, "ScanAppCropedImage")

        if (!appDirectory.exists()) {
            val directoryCreated = appDirectory.mkdir()
            if (!directoryCreated) {
                // Failed to create the directory
                return null
            }
        }

        return appDirectory
    }


    override fun onError(error: DocumentScannerErrorModel) {
        finish()
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
    }

    override fun onSuccess(scannerResults: ScannerResults) {

        val fileName: String = SimpleDateFormat("yyyyMMddHHmm'.jpg'").format(Date())
        val file = File(appDirectory, fileName)
        try {
            if (!file.exists()) {
                val fileCreated = file.createNewFile()
                if (fileCreated) {
                    var bitmap = BitmapFactory.decodeFile(scannerResults.croppedImageFile?.absolutePath)
                    val outputStream = FileOutputStream(file)
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)

                    outputStream.flush()
                    outputStream.fd.sync()
                    outputStream.close()
                    val returnIntent = Intent()
                    returnIntent.putExtra("result", file.absolutePath)
                    setResult(RESULT_OK, returnIntent)
                    finish()
                    // Failed to create the file

                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
       // var image = ImageModel(scannerResults.croppedImageFile?.absolutePath)

    }
}