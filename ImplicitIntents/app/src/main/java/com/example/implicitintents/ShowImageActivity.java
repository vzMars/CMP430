package com.example.implicitintents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

public class ShowImageActivity extends AppCompatActivity {

    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);

        imageView = findViewById(R.id.imageView);

        Intent intent = getIntent();

        Bitmap bitmap = (Bitmap)intent.getExtras().get("data");
        Drawable drawable = new BitmapDrawable(getResources(), bitmap);

        imageView.setImageDrawable(drawable);
    }
}