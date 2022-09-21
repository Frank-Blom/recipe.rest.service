package com.abn.demo.recipe.rest.service.integration.controller;

import com.abn.demo.recipe.rest.service.domain.Recipe;
import com.abn.demo.recipe.rest.service.repository.IRecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class RecipeControllerIntegrationTest {

    @Autowired
    private MockMvc mcv;

    @Autowired
    private IRecipeRepository repository;

    @BeforeEach
    void setup(){
        repository.deleteAll();
    }

    @Test
    public void testFetchAllRecipesStatus200() throws Exception {
        // Create test data
        List<Recipe> listOfEmployees = new ArrayList<>();
        listOfEmployees.add(Recipe.builder().name("Curry").servings(4).instructions("Doe de kokosmelk in de pan").isVegetarian(true).ingredients(List.of("Rijst","Aardappels")).build());
        listOfEmployees.add(Recipe.builder().name("Pasta").servings(4).instructions("Doe de Tomaten in de pan").isVegetarian(false).ingredients(List.of("Pasta","Tomaten")).build());
        repository.saveAll(listOfEmployees);
        // Perform get request
        ResultActions response = mcv.perform(get("/recipe/all"));

        // Verify result
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(listOfEmployees.size())));

    }

    @Test
    public void testFindRecipeByIdStatusNotFound() throws Exception {
        // Perform get request
        ResultActions response = mcv.perform(get("/recipe/999"));

        // Verify result that the recipe is not found
        response.andExpect(status().isNotFound())
                .andDo(print());

    }


    @Test
    public void testFindRecipeByIdStatus200() throws Exception {
        // Create test data
        Recipe recipe = Recipe.builder().name("Curry").servings(4).instructions("Doe de kokosmelk in de pan").isVegetarian(true).ingredients(List.of("Rijst", "Aardappels")).build();
        repository.save(recipe);
        // Perform get request
        ResultActions response = mcv.perform(get("/recipe/" + recipe.getId()));

        // Verify result
        response.andExpect(status().isOk())
                .andDo(print());

    }
}
