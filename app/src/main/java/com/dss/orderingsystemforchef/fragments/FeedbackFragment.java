package com.dss.orderingsystemforchef.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dss.orderingsystemforchef.R;
import com.dss.orderingsystemforchef.adapter.SuggestionAdapter;
import com.dss.orderingsystemforchef.customview.ProgressView;
import com.dss.orderingsystemforchef.network.MCallback;
import com.dss.orderingsystemforchef.network.ServiceCreator;
import com.dss.orderingsystemforchef.network.SuggestionService;
import com.dss.orderingsystemforchef.network.results.SuggestionResult;

public class FeedbackFragment extends Fragment {

    private RecyclerView recyclerView;
    private SuggestionAdapter adapter = new SuggestionAdapter();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feedback, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recyclerView);
        // 设置RecyclerView

        adapter.setEmptyView(new ProgressView(getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        requestAllSuggestions();

    }

    private void requestAllSuggestions(){
        ServiceCreator.createService(SuggestionService.class).getSuggestions().enqueue(new MCallback<SuggestionResult>() {
            @Override
            public void onSuccess(SuggestionResult result) {
                adapter.setNewInstance(result.getData());
            }
        });
    }

}
