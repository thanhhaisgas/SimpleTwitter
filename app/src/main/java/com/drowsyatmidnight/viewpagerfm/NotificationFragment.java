package com.drowsyatmidnight.viewpagerfm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.drowsyatmidnight.simpletwitter.R;

/**
 * Created by haint on 01/07/2017.
 */

public class NotificationFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.notificationfm, container, false);
        return rootView;
    }
}
