package com.soichi.hrm1017controller.controller.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soichi.hrm1017controller.R;

/**
 * Created by soichi on 2015/11/18.
 */
public class MapController extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflator, ViewGroup container, Bundle savedInstanceState) {

        View view = inflator.inflate(R.layout.map_controller, container, false);
        TextView textView = (TextView) view.findViewById(R.id.map_controller);
        textView.setText("map controller monitor is this");
        return view;
    }
}
