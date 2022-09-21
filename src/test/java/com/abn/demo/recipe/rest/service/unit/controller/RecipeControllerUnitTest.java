package com.abn.demo.recipe.rest.service.unit.controller;


import com.abn.demo.recipe.rest.service.controller.RecipeController;
import com.abn.demo.recipe.rest.service.domain.Recipe;
import com.abn.demo.recipe.rest.service.repository.IRecipeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = RecipeController.class)
public class RecipeControllerUnitTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private IRecipeRepository repository;

    private List<Recipe> listOfEmployees = List.of(Recipe.builder().id(0).name("Curry").servings(4).instructions("Doe de kokosmelk in de pan").isVegetarian(true).ingredients(List.of("Rijst","Aardappels")).build(),
            Recipe.builder().id(1).name("Pasta").servings(4).instructions("Doe de Tomaten in de pan").isVegetarian(false).ingredients(List.of("Pasta","Tomaten")).build());

    @Test
    public void testFetchAllRecipes() throws Exception {
        Mockito.when(repository.findAll()).thenReturn(listOfEmployees);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/recipe/all").accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(requestBuilder).andReturn();
        String expected = new ObjectMapper().writeValueAsString(listOfEmployees);

        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }
}
