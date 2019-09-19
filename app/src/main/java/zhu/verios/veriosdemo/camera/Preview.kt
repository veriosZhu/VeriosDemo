package zhu.verios.veriosdemo.camera

import android.content.Context
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.FrameLayout

/**
 * @author qinxiangzhu on 2019-09-20.
 */
class Preview(context: Context, private val surfaceView: SurfaceView = SurfaceView(context)): FrameLayout(context), SurfaceHolder.Callback {


    var mHolder: SurfaceHolder = surfaceView.holder.apply {
        addCallback(this@Preview)
        setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS)
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {

    }

    override fun surfaceCreated(holder: SurfaceHolder?) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {

    }
}