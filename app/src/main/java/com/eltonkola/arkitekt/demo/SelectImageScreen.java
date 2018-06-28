package com.eltonkola.arkitekt.demo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eltonkola.arkitekt.AppScreen;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by elton on 9/29/17.
 */

public class SelectImageScreen extends AppScreen<String> {

    private final int PICK_PHOTO_FOR_AVATAR= 1001;


    @Override
    public int getView() {
        return R.layout.select_image_screen;
    }

    private ImageView selected_image;

    @Override
    public void onEntered() {
        super.onEntered();

        selected_image = (ImageView)findViewById(R.id.selected_image);

        findViewById(R.id.butClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                close();
            }
        });

        findViewById(R.id.butKlik).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR);
            }
        });


    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PHOTO_FOR_AVATAR && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }
            try {
                InputStream inputStream = mContext.getContentResolver().openInputStream(data.getData());

                selected_image.setImageBitmap(BitmapFactory.decodeStream(inputStream));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            //Now you can do whatever you want with your inpustream, save it as file, upload to a server, decode a bitmap...
        }
    }
}
