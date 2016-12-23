package com.example.richo.permissionpractice;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

/**
 * Created by richo on 2016/12/23.
 */

public class PhotoAdapter extends ArrayAdapter<String> {
    public PhotoAdapter(Context context, ArrayList<String> photos) {
        super(context, 0, photos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String url = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }

        Uri uri = Uri.parse(url);
        SimpleDraweeView imageView = (SimpleDraweeView) convertView.findViewById(R.id.thumbnail);
        imageView.setImageURI(uri);

        return convertView;
    }
}
