package com.incapp.onlinelearning;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoViewActivity extends AppCompatActivity {

    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);

        videoView = findViewById(R.id.videoView);

        getSupportActionBar().hide();

        showVideo();
    }

    private void showVideo() {
        String url = getIntent().getStringExtra("video");

        Uri uri = Uri.parse(url);

        videoView.setVideoURI(uri);

        //Seekbar to control video
        MediaController mediaController = new MediaController(VideoViewActivity.this, true);

        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        //Allow VideoView to prepare before playing video from url.
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                videoView.start();
            }
        });
    }
}
