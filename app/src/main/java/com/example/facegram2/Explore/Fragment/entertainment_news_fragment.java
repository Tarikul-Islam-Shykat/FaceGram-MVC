package com.example.facegram2.Explore.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.facegram2.Explore.Adapter.RecyclerViewAdapter;
import com.example.facegram2.Explore.Api.MainNewModel;
import com.example.facegram2.Explore.Api.RequestFromApi;
import com.example.facegram2.Explore.Api.articleArrayModel;
import com.example.facegram2.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class entertainment_news_fragment extends Fragment {
    String api_key = "e95b2045a3b0499fb26a71b16ce59e78";
    ArrayList<articleArrayModel> modelClassArrayList;
    RecyclerViewAdapter adapter;
    String country_code = "in";
    private RecyclerView rec_entertainment;
    private  String Category ="entertainment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Toast.makeText(getContext(), "ent", Toast.LENGTH_SHORT).show();
        View view =  inflater.inflate(R.layout.fragment_entertainment_news_fragment, container, false);
        rec_entertainment = view.findViewById(R.id.recView_entertainment);
        rec_entertainment.setLayoutManager(new LinearLayoutManager(getContext()));
        modelClassArrayList = new ArrayList<>();
        adapter = new RecyclerViewAdapter(getContext(),modelClassArrayList);
        rec_entertainment.setAdapter(adapter);

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