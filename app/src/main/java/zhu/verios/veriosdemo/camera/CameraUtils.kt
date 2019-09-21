package zhu.verios.veriosdemo.camera

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Camera
import android.util.Log

/**
 * @author qinxiangzhu on 2019-09-21.
 */
object CameraUtils {

    /** Check if this device has a camera  */
    fun checkCameraHardware(context: Context): Boolean {
        return context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)
    }

    /** A safe way to get an instance of the Camera object.  */
    fun getCameraInstance(): Camera? {
        var c: Camera? = null
        try {
            c = Camera.open() // attempt to get a Camera instance
        } catch (e: Exception) {
            Log.e("cameraUtils","camera open error", e)
            // Camera is not available (in use or does not exist)
        }
        return c // returns null if camera is unavailable
    }

}