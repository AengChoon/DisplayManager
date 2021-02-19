package com.raontech.displaymanager;

import android.app.Presentation;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Display;
import android.widget.LinearLayout;

public class SecondaryDisplayMain extends Presentation {
    public SecondaryDisplayMain(Context outerContext, Display display) {
        super(outerContext, display);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.secondary_display_main);
    }

    public void setBackGroundRGB(int red, int green, int blue) {
        LinearLayout display = findViewById(R.id.secondary_display_main);
        display.setBackgroundColor(Color.rgb(red, green, blue));
    }

    public void setBackGroundWhite(int white) {
        LinearLayout display = findViewById(R.id.secondary_display_main);
        display.setBackgroundColor(Color.rgb(white, white, white));
    }
}
