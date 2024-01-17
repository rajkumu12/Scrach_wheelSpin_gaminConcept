package com.example.scrachcard

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zynksoftware.documentscanner.ScanActivity
import com.zynksoftware.documentscanner.model.DocumentScannerErrorModel
import com.zynksoftware.documentscanner.model.ScannerResults

class AppScanActivity : ScanActivity(){

    lateinit var recyclerView:RecyclerView
    private val imagesList = ArrayList<ImageModel>()
    private lateinit var imageAdapter: ImageListAdapter


    override fun onClose() {
        Toast.makeText(this, "closed", Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_scan)
        recyclerView=findViewById(R.id.recyclerview)
        imageAdapter = ImageListAdapter(imagesList,this@AppScanActivity)
        val layoutManager = GridLayoutManager(applicationContext,3)
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = imageAdapter
        addFragmentContentLayout()
    }

    override fun onError(error: DocumentScannerErrorModel) {
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
    }

    override fun onSuccess(scannerResults: ScannerResults) {
        var image = ImageModel(scannerResults.croppedImageFile?.absolutePath)
        imagesList.add(image)
        imageAdapter.notifyDataSetChanged()
    }
}