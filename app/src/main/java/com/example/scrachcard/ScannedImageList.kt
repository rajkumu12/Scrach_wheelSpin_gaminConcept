package com.example.scrachcard
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfDocument.PageInfo
import android.os.Bundle
import android.os.Environment
import android.util.DisplayMetrics
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class ScannedImageList : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var scanagain: TextView
    val REQUEST_PERMISSIONS = 1
    var bitmap: Bitmap? = null
    var boolean_permission = false
    lateinit var exporttoPdf: TextView
    private val imagesList = ArrayList<ImageModel>()
    private lateinit var imageAdapter: ImageListAdapter
    lateinit var  appDirectory:File
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanned_image_list)
        recyclerView=findViewById(R.id.recyclerview)
        scanagain=findViewById(R.id.scan_again)
        exporttoPdf=findViewById(R.id.exporttopdf)
        imageAdapter = ImageListAdapter(imagesList,this@ScannedImageList)
        val layoutManager = GridLayoutManager(applicationContext,3)
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = imageAdapter
        fn_permission()
        scanagain.setOnClickListener {
            val intent = Intent(this, AppScanActivity::class.java)
            resultLauncher.launch(intent)
        }

        exporttoPdf.setOnClickListener {
            createAppDirectoryInDownloads(this)
            createPdf(imagesList)
        }
    }

    fun createAppDirectoryInDownloads(context: Context): File? {
        val downloadsDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        appDirectory = File(downloadsDirectory, "ScanApp")

        if (!appDirectory.exists()) {
            val directoryCreated = appDirectory.mkdir()
            if (!directoryCreated) {
                // Failed to create the directory
                return null
            }
        }

        return appDirectory
    }

    private fun createPdf(imagesList: ArrayList<ImageModel>) {
        val wm = getSystemService(WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val displaymetrics = DisplayMetrics()
        this.windowManager.defaultDisplay.getMetrics(displaymetrics)
        val hight = displaymetrics.heightPixels.toFloat()
        val width = displaymetrics.widthPixels.toFloat()
        val convertHighet = hight.toInt()
        val convertWidth = width.toInt()

        imagesList.forEach {
            var bitmap = BitmapFactory.decodeFile(it.getImage())
            val document = PdfDocument()
            val pageInfo = PageInfo.Builder(bitmap!!.width, bitmap!!.height, 1).create()
            val page = document.startPage(pageInfo)
            val canvas = page.canvas
            val paint = Paint()
            paint.color = Color.parseColor("#ffffff")
            canvas.drawPaint(paint)
            bitmap = Bitmap.createScaledBitmap(bitmap!!, bitmap!!.width, bitmap!!.height, true)
            paint.color = Color.BLUE
            //canvas.drawBitmap(bitmap, 0, 0, null)
            canvas.drawBitmap(bitmap!!, 0f, 0f, paint)
            document.finishPage(page)

            val path = Environment.getExternalStorageDirectory().absolutePath + "/Dir"

            val dir = File(path)
            if (!dir.exists()) dir.mkdirs()

            val file = File(appDirectory, "newFile.pdf")
            try {
                if (!file.exists()) {
                    val fileCreated = file.createNewFile()
                    if (!fileCreated) {
                        // Failed to create the file

                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            // write the document content
            val targetPdf = file.path
            val filePath = File(targetPdf)
            try {
                document.writeTo(FileOutputStream(filePath))
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(this, "Something wrong: $e", Toast.LENGTH_LONG).show()
            }

            // close the document
            document.close()


        }


//        Resources mResources = getResources();
//        Bitmap bitmap = BitmapFactory.decodeResource(mResources, R.drawable.screenshot);

    }


    @SuppressLint("NotifyDataSetChanged")
    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            var image=data?.getStringExtra("result")
            imagesList.add(ImageModel(image))
            imageAdapter.notifyDataSetChanged()


        }
    }

    private fun fn_permission() {
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                applicationContext,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this@ScannedImageList,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {
            } else {
                ActivityCompat.requestPermissions(
                    this@ScannedImageList,
                    arrayOf<String>(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    1
                )
            }
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this@ScannedImageList,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) {
            } else {
                ActivityCompat.requestPermissions(
                    this@ScannedImageList, arrayOf<String>(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    REQUEST_PERMISSIONS
                )
            }
        } else {
            boolean_permission = true
        }
    }

 /*   private fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>?,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions!!, grantResults)
        if (requestCode == REQUEST_PERMISSIONS) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                boolean_permission = true
            } else {
                Toast.makeText(applicationContext, "Please allow the permission", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }*/
}