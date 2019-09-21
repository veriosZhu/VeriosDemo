package zhu.verios.veriosdemo.camera

import android.content.Context
import android.hardware.Camera
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.io.IOException

/**
 * @author qinxiangzhu on 2019-09-20.
 */
class CameraPreview(context: Context, val camera: Camera): SurfaceView(context), SurfaceHolder.Callback {


    companion object {
        const val TAG = "CameraPreview"
    }

    init {
        holder.addCallback(this)
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS)
    }


    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        if (holder.surface == null) {
            return
        }
        try {
            camera.stopPreview()
        } catch (e: IOException) {

        }
        try {
            camera.setPreviewDisplay(holder)
            camera.startPreview()
        } catch (e: IOException) {
            Log.e(TAG, "Error setting camera preview: ${e.message}")
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        try {
            camera.setPreviewDisplay(holder)
            camera.startPreview()
        } catch (e: IOException) {
            Log.e(TAG, "Error setting camera preview: ${e.message}")
        }
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {

    }
}