package com.incapp.onlinelearning;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CourseDetailActivity extends AppCompatActivity {

    ImageView imageView;
    TextView textViewDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageView = (ImageView) findViewById(R.id.imageView);
        textViewDetail = (TextView) findViewById(R.id.textView_detail);

        String title = getIntent().getStringExtra("title");
        String image = getIntent().getStringExtra("image");
        String detail = getIntent().getStringExtra("detail");
        final String video = getIntent().getStringExtra("video");

        getSupportActionBar().setTitle(title);

        Glide.with(CourseDetailActivity.this).load(image).into(imageView);
        textViewDetail.setText(detail);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseDetailActivity.this, VideoViewActivity.class);

                intent.putExtra("video", video);

                startActivity(intent);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
