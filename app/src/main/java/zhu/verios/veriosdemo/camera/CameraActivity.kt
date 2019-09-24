package zhu.verios.veriosdemo.camera

import android.Manifest
import android.content.pm.PackageManager
import android.hardware.Camera
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_camera.*
import zhu.verios.veriosdemo.R


/**
 * @author qinxiangzhu on 2019-09-19.
 */
class CameraActivity: AppCompatActivity(), View.OnClickListener {

    companion object {
        const val TAG = "CameraActivity"
    }

    private lateinit var camera: Camera
    private lateinit var preview: CameraPreview
    private val pictureCallback = object :Camera.PictureCallback {
        override fun onPictureTaken(data: ByteArray, camera: Camera) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        val cameraNumber = Camera.getNumberOfCameras()
        Log.d(TAG, "camera number: $cameraNumber")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), 1)
            } else {
                openCamera()
            }
        }
    }

    fun openCamera(){
        if (CameraUtils.checkCameraHardware(this)) {
            camera = CameraUtils.getCameraInstance()!!
            preview = CameraPreview(this, camera)
            camera_preview.addView(preview)
        }
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.btn_capture -> camera.takePicture(null, null, pictureCallback)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            1 ->
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Start your camera handling here
                    openCamera()
                }
        }
    }



}