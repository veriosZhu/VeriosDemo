package zhu.verios.veriosdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import zhu.verios.veriosdemo.bigimage.BigImageActivity;
import zhu.verios.veriosdemo.scrollviewex.ScrollViewExActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_big_image:
            startActivity(new Intent(this, BigImageActivity.class));
            break;
            case R.id.btn_custom_viewgroup:
                startActivity(new Intent(this, ScrollViewExActivity.class));
                break;
            default:
        }
    }
}
