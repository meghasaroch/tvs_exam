package com.example.tvs_task;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;

public class DetailActivity extends AppCompatActivity {


    TextView tv_username, tv_designation, tv_location, tv_id, tv_date, tv_sal,tv_timestamp;
    ImageView imv_preview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tv_username = findViewById(R.id.tv_username);
        tv_designation = findViewById(R.id.tv_designation);
        tv_location = findViewById(R.id.tv_location);
        tv_id = findViewById(R.id.tv_id);
        tv_date = findViewById(R.id.tv_date);
        tv_sal = findViewById(R.id.tv_salary);
        tv_timestamp = findViewById(R.id.tv_timestamp);

        imv_preview = findViewById(R.id.imv_preview);
        Intent in = getIntent();
        Bundle bundle = in.getExtras();
        User u = (User)bundle.getSerializable("user_info");

        tv_username.setText(u.username);
        tv_designation.setText(u.designation);
        tv_location.setText(u.location);
        tv_id.setText(u.id+"");
        tv_sal.setText(u.salary);
        tv_date.setText(u.date);
    }

    public void choose_photo(View view)
    {
        Intent in  = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(in,90);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==90 && resultCode==RESULT_OK)
        {
            Bitmap bmp  = (Bitmap) data.getExtras().get("data");
            imv_preview.setImageBitmap(bmp);

            tv_timestamp.setText(new Date()+"");
        }

    }
}
