package zhu.verios.veriosdemo.bigimage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

import zhu.verios.veriosdemo.R;

public class BigImageActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mImageView;
    private BigImageView mBigImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_image);
        mImageView = findViewById(R.id.iv_big_image_bitmap);
        mBigImageView = findViewById(R.id.biv_big_image);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_big_image_thumbnail:
                try {
                    loadThumbnail(getAssets().open("big_image.jpg"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_big_image_part:
                try {
                    loadPartImage(getAssets().open("big_image.jpg"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            default:
        }
    }

    private void loadThumbnail(InputStream bitmapInput) {
        mBigImageView.setVisibility(View.GONE);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(bitmapInput, null, options);
        int inSampleSize = Math.min(mImageView.getHeight()/options.outHeight, mImageView.getWidth()/options.outWidth);
        options.inJustDecodeBounds = false;
        options.inSampleSize = inSampleSize;
        Bitmap bitmap = BitmapFactory.decodeStream(bitmapInput, null, options);
        mImageView.setImageBitmap(bitmap);
        mImageView.setVisibility(View.VISIBLE);

    }

    private void loadPartImage(InputStream bitmapStream) {
        mImageView.setVisibility(View.GONE);
        mBigImageView.setBitmapInputStream(bitmapStream);
        mBigImageView.setVisibility(View.VISIBLE);
    }

}
