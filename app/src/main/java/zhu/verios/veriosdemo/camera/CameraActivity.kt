package zhu.verios.veriosdemo.camera

import android.hardware.Camera
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import zhu.verios.veriosdemo.R

/**
 * @author qinxiangzhu on 2019-09-19.
 */
class CameraActivity: AppCompatActivity() {

    private fun safeCameraOpen(id: Int): Boolean {
        return try {
            releaseCameraAndPreview()
            mCamera = Camera.open(id)
            true
        } catch (e: Exception) {
            Log.e(getString(R.string.app_name), "failed to open Camera")
            e.printStackTrace()
            false
        }
    }

    private fun releaseCameraAndPreview() {
        preview?.setCamera(null)
        mCamera?.also { camera ->
            camera.release()
            mCamera = null
        }
    }
}