package com.raontech.displaymanager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.slider.Slider;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.ArrayList;
import java.util.Objects;

public class DisplayFragment extends Fragment {
    public static final int PERMISSION_READ = 0;
    public DisplayManager displayManager = null;
    public Display[] presentationDisplays = null;
    public static ArrayList<VideoModel> videoArrayList;
    public static ArrayList<ImageModel> imageArrayList;
    private SecondaryDisplayMain secondaryDisplay;
    private SwitchMaterial rgb_white_switch, video_image_switch;
    private Slider sliderRed, sliderGreen, sliderBlue, sliderWhite;
    private TextView textViewTitle1, textViewTitle2, textViewRed, textViewGreen, textViewBlue, textViewWhite;
    private int sliderRedValue, sliderGreenValue, sliderBlueValue, sliderWhiteValue;
    private boolean youtubePlaying = false;
    private boolean videoPlaying = false;
    private boolean imageShowing = false;
    private RecyclerView recyclerViewVideo, recyclerViewImage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_display, container, false);
        rgb_white_switch = view.findViewById(R.id.display_rgb_white_switch);
        video_image_switch = view.findViewById(R.id.display_video_image_switch);
        sliderRed = view.findViewById(R.id.display_slider_red);
        sliderGreen = view.findViewById(R.id.display_slider_green);
        sliderBlue = view.findViewById(R.id.display_slider_blue);
        sliderWhite = view.findViewById(R.id.display_slider_white);
        textViewTitle1 = view.findViewById(R.id.display_textview_title1);
        textViewTitle2 = view.findViewById(R.id.display_textview_title2);
        textViewRed = view.findViewById(R.id.display_textview_red);
        textViewGreen = view.findViewById(R.id.display_textview_green);
        textViewBlue = view.findViewById(R.id.display_textview_blue);
        textViewWhite = view.findViewById(R.id.display_textview_white);
        Button buttonYoutube = view.findViewById(R.id.display_button_youtube);
        EditText editTextYoutube = view.findViewById(R.id.display_edittext_url);
        recyclerViewVideo = view.findViewById(R.id.recyclerview_video);
        recyclerViewImage = view.findViewById(R.id.recyclerview_image);
        displayManager = (DisplayManager) Objects.requireNonNull(getActivity()).getSystemService(Context.DISPLAY_SERVICE);
        if (displayManager != null) {
            presentationDisplays = displayManager.getDisplays(DisplayManager.DISPLAY_CATEGORY_PRESENTATION);
            if (presentationDisplays.length > 0) {
                secondaryDisplay = new SecondaryDisplayMain(getActivity().getApplicationContext(), presentationDisplays[0]);
                secondaryDisplay.show();
                buttonYoutube.setOnClickListener(v -> {
                    String youtubeURL = editTextYoutube.getText().toString();
                    youtubeButton(youtubeURL);
                });
            }
            if (checkPermission()) {
                videoList();
                imageList();
            }
        }

        rgb_white_switch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                WhiteSliderInitialize();
                rgb_white_switch.setText("Lightness");
                textViewTitle1.setText("Display Lightness Control");
                textViewRed.setVisibility(View.GONE);
                sliderRed.setVisibility(View.GONE);
                textViewGreen.setVisibility(View.GONE);
                sliderGreen.setVisibility(View.GONE);
                textViewBlue.setVisibility(View.GONE);
                sliderBlue.setVisibility(View.GONE);
                textViewWhite.setVisibility(View.VISIBLE);
                sliderWhite.setVisibility(View.VISIBLE);
            } else {
                RGBSliderInitialize();
                rgb_white_switch.setText("RGB");
                textViewTitle1.setText("Display RGB Control");
                textViewRed.setVisibility(View.VISIBLE);
                sliderRed.setVisibility(View.VISIBLE);
                textViewGreen.setVisibility(View.VISIBLE);
                sliderGreen.setVisibility(View.VISIBLE);
                textViewBlue.setVisibility(View.VISIBLE);
                sliderBlue.setVisibility(View.VISIBLE);
                textViewWhite.setVisibility(View.GONE);
                sliderWhite.setVisibility(View.GONE);
            }

        });

        sliderRed.addOnChangeListener((slider, value, fromUser) -> {
            if (youtubePlaying || videoPlaying || imageShowing) {
                secondaryDisplay = new SecondaryDisplayMain(getActivity().getApplicationContext(), presentationDisplays[0]);
                secondaryDisplay.show();
                youtubePlaying = false;
                videoPlaying = false;
                imageShowing = false;
            }
            sliderRedValue = (int) sliderRed.getValue();
            secondaryDisplay.setBackGroundRGB(sliderRedValue, sliderGreenValue, sliderBlueValue);
            secondaryDisplay.show();

        });

        sliderGreen.addOnChangeListener((slider, value, fromUser) -> {
            if (youtubePlaying || videoPlaying || imageShowing) {
                secondaryDisplay = new SecondaryDisplayMain(getActivity().getApplicationContext(), presentationDisplays[0]);
                secondaryDisplay.show();
                youtubePlaying = false;
                videoPlaying = false;
                imageShowing = false;
            }
            sliderGreenValue = (int) sliderGreen.getValue();
            secondaryDisplay.setBackGroundRGB(sliderRedValue, sliderGreenValue, sliderBlueValue);
            secondaryDisplay.show();

        });

        sliderBlue.addOnChangeListener((slider, value, fromUser) -> {
            if (youtubePlaying || videoPlaying || imageShowing) {
                secondaryDisplay = new SecondaryDisplayMain(getActivity().getApplicationContext(), presentationDisplays[0]);
                secondaryDisplay.show();
                youtubePlaying = false;
                videoPlaying = false;
                imageShowing = false;
            }
            sliderBlueValue = (int) sliderBlue.getValue();
            secondaryDisplay.setBackGroundRGB(sliderRedValue, sliderGreenValue, sliderBlueValue);
            secondaryDisplay.show();

        });

        sliderWhite.addOnChangeListener((slider, value, fromUser) -> {
            if (youtubePlaying || videoPlaying || imageShowing) {
                secondaryDisplay = new SecondaryDisplayMain(getActivity().getApplicationContext(), presentationDisplays[0]);
                secondaryDisplay.show();
                youtubePlaying = false;
                videoPlaying = false;
                imageShowing = false;
            }
            sliderWhiteValue = (int) sliderWhite.getValue();
            secondaryDisplay.setBackGroundWhite(sliderWhiteValue);
        });

        video_image_switch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                video_image_switch.setText("Images");
                textViewTitle2.setText("Display External Image Control");
                recyclerViewVideo.setVisibility(View.GONE);
                recyclerViewImage.setVisibility(View.VISIBLE);
            } else {
                video_image_switch.setText("Videos");
                textViewTitle2.setText("Display External Video Control");
                recyclerViewVideo.setVisibility(View.VISIBLE);
                recyclerViewImage.setVisibility(View.GONE);
            }

        });

        return view;
    }

    public void youtubeButton(String youtubeURL) {
        SecondaryDisplayYoutube secondaryDisplayYoutube = new SecondaryDisplayYoutube(Objects.requireNonNull(getActivity()).getApplicationContext(), presentationDisplays[0]);
        secondaryDisplayYoutube.show();
        RGBSliderInitialize();
        WhiteSliderInitialize();
        secondaryDisplayYoutube.setYoutubeView(youtubeURL);
        youtubePlaying = true;
    }

    public void RGBSliderInitialize() {
        sliderRedValue = 0;
        sliderGreenValue = 0;
        sliderBlueValue = 0;
        sliderRed.setValue(sliderRedValue);
        sliderGreen.setValue(sliderGreenValue);
        sliderBlue.setValue(sliderBlueValue);
        secondaryDisplay.setBackGroundRGB(sliderRedValue, sliderGreenValue, sliderBlueValue);
    }

    public void WhiteSliderInitialize() {
        sliderWhiteValue = 0;
        sliderWhite.setValue(sliderWhiteValue);
        secondaryDisplay.setBackGroundWhite(sliderWhiteValue);
    }



    public void videoList() {
        recyclerViewVideo.setLayoutManager(new LinearLayoutManager(Objects.requireNonNull(getActivity()).getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerViewVideo.setItemAnimator(new DefaultItemAnimator());
        videoArrayList = new ArrayList<>();
        getVideos();
    }

    public void getVideos() {
        ContentResolver contentResolver = Objects.requireNonNull(getActivity()).getContentResolver();
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

        @SuppressLint("Recycle") Cursor cursor = contentResolver.query(uri, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE));
                String duration = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DURATION));
                @SuppressWarnings("deprecation") String data = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
                VideoModel videoModel = new VideoModel();
                videoModel.setVideoTitle(title);
                videoModel.setVideoDuration(timeConversion(Long.parseLong(duration)));
                videoModel.setVideoUri(Uri.parse(data));
                videoArrayList.add(videoModel);
            } while (cursor.moveToNext());
        }

        VideoAdapter adapter = new VideoAdapter(getActivity().getApplicationContext(), videoArrayList);
        recyclerViewVideo.setAdapter(adapter);

        adapter.setOnItemClickListener((pos, v) -> {
            displayManager = (DisplayManager) Objects.requireNonNull(getActivity()).getSystemService(Context.DISPLAY_SERVICE);
            if (displayManager != null) {
                presentationDisplays = displayManager.getDisplays(DisplayManager.DISPLAY_CATEGORY_PRESENTATION);
                if (presentationDisplays.length > 0) {
                    SecondaryDisplayVideo secondaryDisplayVideo = new SecondaryDisplayVideo(getActivity().getApplicationContext(), presentationDisplays[0]);
                    secondaryDisplayVideo.show();
                    secondaryDisplayVideo.setVideoView(pos);
                    RGBSliderInitialize();
                    WhiteSliderInitialize();
                    videoPlaying = true;
                }
            }
        });
    }

    @SuppressLint("DefaultLocale")
    public String timeConversion(long value) {
        String videoTime;
        int dur = (int) value;
        int hrs = (dur / 3600000);
        int mns = (dur / 60000) % 60000;
        int scs = dur % 60000 / 1000;

        if (hrs > 0) {
            videoTime = String.format("%02d:%02d:%02d", hrs, mns, scs);
        } else {
            videoTime = String.format("%02d:%02d", mns, scs);
        }
        return videoTime;
    }

    public void imageList() {
        recyclerViewImage.setLayoutManager(new LinearLayoutManager(Objects.requireNonNull(getActivity()).getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerViewImage.setItemAnimator(new DefaultItemAnimator());
        imageArrayList = new ArrayList<>();
        getImages();
    }

    public void getImages() {
        ContentResolver contentResolver = Objects.requireNonNull(getActivity()).getContentResolver();
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        @SuppressLint("Recycle") Cursor cursor = contentResolver.query(uri, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.TITLE));
                @SuppressWarnings("deprecation") String data = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                ImageModel imageModel = new ImageModel();
                imageModel.setImageTitle(title);
                imageModel.setImageUri(Uri.parse(data));
                imageArrayList.add(imageModel);
            } while (cursor.moveToNext());
        }

        ImageAdapter adapter = new ImageAdapter(getActivity().getApplicationContext(), imageArrayList);
        recyclerViewImage.setAdapter(adapter);

        adapter.setOnItemClickListener((pos, v) -> {
            displayManager = (DisplayManager) Objects.requireNonNull(getActivity()).getSystemService(Context.DISPLAY_SERVICE);
            if (displayManager != null) {
                presentationDisplays = displayManager.getDisplays(DisplayManager.DISPLAY_CATEGORY_PRESENTATION);
                if (presentationDisplays.length > 0) {
                    SecondaryDisplayImage secondaryDisplayImage = new SecondaryDisplayImage(getActivity().getApplicationContext(), presentationDisplays[0]);
                    secondaryDisplayImage.show();
                    secondaryDisplayImage.setImageView(pos);
                    RGBSliderInitialize();
                    WhiteSliderInitialize();
                    imageShowing = true;
                }
            }
        });
    }

    public boolean checkPermission() {
        int READ_EXTERNAL_PERMISSION = ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()).getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        if ((READ_EXTERNAL_PERMISSION != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_READ);
            return false;
        }
        return true;
    }
}
