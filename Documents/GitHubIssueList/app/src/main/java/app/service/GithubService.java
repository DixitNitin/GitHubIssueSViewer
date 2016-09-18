package app.service;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import app.model.Github;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

public interface GithubService {
    String SERVICE_ENDPOINT = "https://api.github.com";


    @GET("/repos/{user}/{repo}/issues")
    Observable<ArrayList<Github>> getUser(@Path("user") String user,@Path("repo") String repo);
}
