package com.example.easymeals.dataprovider;

import com.example.easymeals.entity.Recipe;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class SpoonacularDataProvider {

    public List<Recipe> loadData() {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://api.spoonacular.com/recipes/complexSearch?apiKey=fa62df8d2bd1463d93ce67caff6959c7")).header("content-type", "application/json").method("GET", HttpRequest.BodyPublishers.ofString("")).build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject(response.body());
        JSONArray array = jsonObject.getJSONArray("results");
        return IntStream.range(0, array.length()).mapToObj(i -> {
            try {
                return new ObjectMapper().readValue(array.get(i).toString(), Recipe.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

}
