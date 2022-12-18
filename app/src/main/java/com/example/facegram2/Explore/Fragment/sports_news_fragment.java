package com.example.facegram2.Explore.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.facegram2.Explore.Adapter.RecyclerViewAdapter;
import com.example.facegram2.Explore.Api.MainNewModel;
import com.example.facegram2.Explore.Api.RequestFromApi;
import com.example.facegram2.Explore.Api.articleArrayModel;
import com.example.facegram2.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class sports_news_fragment extends Fragment {

    String api_key = "e95b2045a3b0499fb26a71b16ce59e78";
    ArrayList<articleArrayModel> modelClassArrayList;
    RecyclerViewAdapter adapter;
    String country_code = "in";
    private RecyclerView rec_sport;
    private  String Category ="sports";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_sports_news_fragment, container, false);
        rec_sport = view.findViewById(R.id.recView_sports);
        rec_sport.setLayoutManager(new LinearLayoutManager(getContext()));
        modelClassArrayList = new ArrayList<>();
        adapter = new RecyclerViewAdapter(getContext(),modelClassArrayList);
        rec_sport.setAdapter(adapter);

        findNews();
        return view;
    }

    private void findNews() {
        RequestFromApi.getApiInterface().getCategoryNews(country_code, Category,10,api_key).enqueue(new Callback<MainNewModel>() {
            @Override
            public void onResponse(Call<MainNewModel> call, Response<MainNewModel> response) {
                if(response.isSuccessful()){
                    modelClassArrayList.addAll(response.body().getArticles());
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<MainNewModel> call, Throwable t) {

            }
        });
    }
}