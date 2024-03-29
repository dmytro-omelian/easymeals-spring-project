package com.example.easymeals.dataprovider;

import com.example.easymeals.clients.SpoonacularClient;
import com.example.easymeals.dataprovider.dto.RecipeDto;
import com.example.easymeals.entity.Recipe;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class SpoonacularDataProvider implements DataProvider {

//    TODO add WebClient to get all that things

    private final WebClient webClient;
    private final SpoonacularClient client;

    @Override
    public Stream<RecipeDto> loadData() {

        webClient.get();
        client.loadData();

        // TODO get these fields from applications.properties
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.spoonacular.com/recipes/complexSearch?apiKey=fa62df8d2bd1463d93ce67caff6959c7"))
                .header("content-type", "application/json")
                .method("GET", HttpRequest.BodyPublishers.ofString(""))
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject(Objects.requireNonNull(response).body());
        // TODO move to final variable
        JSONArray array = jsonObject.getJSONArray("results");
        return IntStream.range(0, array.length()).mapToObj(i -> {
            try {
                return new ObjectMapper().readValue(array.get(i).toString(), RecipeDto.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return null;
        }).filter(Objects::nonNull);
    }

}
