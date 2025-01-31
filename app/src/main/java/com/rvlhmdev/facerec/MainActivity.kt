package com.rvlhmdev.facerec

import android.Manifest
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.rvlhmdev.facerec.databinding.ActivityMainBinding
import com.rvlhmdev.facerec.facedetection.FaceDetectionActivity

class MainActivity : AppCompatActivity() {

    private val cameraPermission = Manifest.permission.CAMERA
    private lateinit var bidning: ActivityMainBinding

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
        if (it) {
            FaceDetectionActivity.start(this)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        bidning = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bidning.root)

        bidning.faceDetectBtn.setOnClickListener {
            requestCameraAndStartDetection()
        }

    }

    private fun requestCameraAndStartDetection() {
        if (isPermissionGranted(cameraPermission)) {
            FaceDetectionActivity.start(this)
        } else {
            requestCameraPermission()
        }
    }


    private fun requestCameraPermission() {
        when {
            shouldShowRequestPermissionRationale(cameraPermission) -> {
                cameraPermissionRequest {
                    openPermissionSetting()
                }
            }
            else -> {
                requestPermissionLauncher.launch(cameraPermission)
            }
        }
    }
}