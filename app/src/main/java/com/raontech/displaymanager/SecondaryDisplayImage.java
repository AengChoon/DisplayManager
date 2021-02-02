package com.raontech.displaymanager;

import android.app.Presentation;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.widget.ImageView;
import android.widget.VideoView;

import static com.raontech.displaymanager.DisplayFragment.imageArrayList;

public class SecondaryDisplayImage extends Presentation {
    public SecondaryDisplayImage(Context outerContext, Display display) {
        super(outerContext, display);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondary_display_image);
    }
    public void setImageView(int pos) {
        ImageView imageView = findViewById(R.id.image_view);
        imageView.setImageURI(imageArrayList.get(pos).getImageUri());
    }
}
