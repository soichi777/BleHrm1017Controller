package com.soichi.hrm1017controller.presentation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.soichi.hrm1017controller.R;

/**
 * Created by soichi on 2015/11/17.
 */
public class LogMonitorFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflator, ViewGroup container, Bundle savedInstanceState) {

        View view = inflator.inflate(R.layout.log_monitor, container, false);
        TextView textView = (TextView) view.findViewById(R.id.log);
        textView.setText("log monitor is this");
        return view;
    }
}
