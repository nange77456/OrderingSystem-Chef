package com.dss.orderingsystemforchef.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dss.orderingsystemforchef.R;
import com.dss.orderingsystemforchef.activities.entry.EntryActivity;
import com.dss.orderingsystemforchef.util.FileUtil;
import com.kongzue.dialog.v2.SelectDialog;

public class FlowFragment extends Fragment {
    private final static String TAG = "FlowFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_flow, container, false);

        Button button = root.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectDialog.show(getContext(), "退出登录", "确定退出登录吗？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FileUtil.deleteUserID(FileUtil.getUserID());
                        Intent jumpIntent = new Intent(getActivity(), EntryActivity.class);
                        startActivity(jumpIntent);
                        getActivity().finish();
                    }
                });
            }
        });

        return root;
    }
}