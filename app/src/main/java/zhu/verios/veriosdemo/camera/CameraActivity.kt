package zhu.verios.veriosdemo.camera

import android.hardware.Camera
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
        val cameraNumber = Camera.getNumberOfCameras()
        Log.d(TAG, "camera number: $cameraNumber")
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



}