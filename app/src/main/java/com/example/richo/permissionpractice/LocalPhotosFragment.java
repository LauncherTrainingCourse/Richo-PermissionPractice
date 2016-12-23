package com.example.richo.permissionpractice;


import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Sample query: "https://pixabay.com/api/?key=4091555-aa95abdd3d9b6ff1ce2e35859&q=architecture&image_type=photo&category=buildings"
 */
public class LocalPhotosFragment extends Fragment {
    PhotoAdapter mAdapter;

    public LocalPhotosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_local_photos, container, false);

        ArrayList<String> photos = new ArrayList<>();
        mAdapter = new PhotoAdapter(getContext(), photos);

        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;

        GridView gridView = (GridView) view.findViewById(R.id.photos_view);
        gridView.setColumnWidth(width/3);
        gridView.setAdapter(mAdapter);

        return view;
    }

}
