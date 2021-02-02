package com.raontech.displaymanager;

import android.app.Presentation;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.widget.VideoView;

import static com.raontech.displaymanager.DisplayFragment.videoArrayList;

public class SecondaryDisplayVideo extends Presentation {
    public SecondaryDisplayVideo(Context outerContext, Display display) {
        super(outerContext, display);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondary_display_video);
    }
    public void setVideoView(int pos) {
        VideoView secondVideo = findViewById(R.id.video_view);
        secondVideo.setVideoURI(videoArrayList.get(pos).getVideoUri());
        secondVideo.start();
    }
}
