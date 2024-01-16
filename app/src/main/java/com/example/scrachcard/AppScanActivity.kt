package com.example.scrachcard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.zynksoftware.documentscanner.ScanActivity
import com.zynksoftware.documentscanner.model.DocumentScannerErrorModel
import com.zynksoftware.documentscanner.model.ScannerResults

class AppScanActivity : ScanActivity(){
    override fun onClose() {
        Toast.makeText(this, "closed", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_scan)
        addFragmentContentLayout()
    }

    override fun onError(error: DocumentScannerErrorModel) {
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
    }

    override fun onSuccess(scannerResults: ScannerResults) {
        Toast.makeText(this, "Scanned", Toast.LENGTH_SHORT).show()
    }
}