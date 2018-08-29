package zhu.verios.veriosdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import zhu.verios.veriosdemo.bigimage.BigImageActivity;

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
            default:
        }
    }
}
