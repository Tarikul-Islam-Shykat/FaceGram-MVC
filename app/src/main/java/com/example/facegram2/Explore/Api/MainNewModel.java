package com.example.facegram2.Explore.Api;

import java.util.ArrayList;

public class MainNewModel {

    private String status,totalResults;

    private ArrayList<articleArrayModel> articles;

    public MainNewModel(String status, String totalResults, ArrayList<articleArrayModel> articles) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public ArrayList<articleArrayModel> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<articleArrayModel> articles) {
        this.articles = articles;
    }
}
