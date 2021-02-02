package com.raontech.displaymanager;

import android.app.Presentation;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SecondaryDisplayYoutube extends Presentation {
    public SecondaryDisplayYoutube(Context outerContext, Display display) {
        super(outerContext, display);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondary_display_youtube);
    }

    private static String getYouTubeId(String youTubeUrl) {
        String pattern = "(?<=youtu.be/|watch\\?v=|/videos/|embed/)[^#&?]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(youTubeUrl);
        if (matcher.find()) {
            return matcher.group();
        } else {
            return "error";
        }
    }

    public void setYoutubeView(String youtubeURL) {
        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_view);
        //noinspection NullableProblems
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(YouTubePlayer youTubePlayer) {
                String videoID = getYouTubeId(youtubeURL);
                youTubePlayer.loadVideo(videoID, 0);
            }
        });
    }
}
