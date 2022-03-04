package com.example.easymeals.controller;

import com.example.easymeals.dataprovider.dto.RecipeDto;
import com.example.easymeals.exception.InvalidIdentifierException;
import com.example.easymeals.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeService recipeService;
    private final ModelMapper modelMapper;

    @GetMapping
    public List<RecipeDto> getAll() {
        return recipeService.getAll().stream()
                .map(recipe -> modelMapper.map(recipe, RecipeDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id:[\\d]+}")
    public ResponseEntity<RecipeDto> getRecipeById(@PathVariable Long id) {
        return recipeService.findById(id)
                .map(recipe -> ResponseEntity.ok(modelMapper.map(recipe, RecipeDto.class)))
                .orElseThrow(() -> new InvalidIdentifierException(id));
    }

    @PostMapping
    public RecipeDto create(RecipeDto recipeDto) {
        return modelMapper.map(recipeService.save(recipeDto), RecipeDto.class);
    }

    @PutMapping("/{id:[\\d]+}")
    public ResponseEntity<RecipeDto> update(
            @PathVariable Long id,
            @RequestBody RecipeDto recipeDto) {
        return recipeService.update(id, recipeDto)
                .map(source -> ResponseEntity.ok(modelMapper.map(source, RecipeDto.class)))
                .orElseThrow(() -> new InvalidIdentifierException(id));
    }

    @DeleteMapping("/{id:[\\d]+}")
    public ResponseEntity<RecipeDto> delete(@PathVariable Long id) {
        recipeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}