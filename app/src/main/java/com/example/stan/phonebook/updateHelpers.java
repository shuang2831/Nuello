package com.example.stan.phonebook;

import android.graphics.Color;
import android.widget.ImageView;

/**
 * Created by Stan on 10/4/2016.
 */
public class updateHelpers {

    public static void imgViewUpdater(String imgCase, ImageView img){

        switch(imgCase){

            case "happy": img.setImageResource(R.drawable.happy);
                break;
            case "sad": img.setImageResource(R.drawable.sad);
                break;
            case "neutral": img.setImageResource(R.drawable.neutral);
                break;
            default: img.setImageResource(R.drawable.neutral);
                break;
        }
    }

    public static int statusColor(String imgCase){

        switch(imgCase){

            case "free": return Color.GREEN;

            case "limited": return Color.YELLOW;

            case "busy": return Color.RED;

            default: return Color.WHITE;

        }
    }
}
