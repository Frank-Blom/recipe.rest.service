package com.abn.demo.recipe.rest.service.controller;

import com.abn.demo.recipe.rest.service.exception.RecipeNotFoundException;
import com.abn.demo.recipe.rest.service.repository.IRecipeRepository;
import com.abn.demo.recipe.rest.service.domain.Recipe;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping(path = "/recipe")
@Tag(name = "Recipe", description = "The recipe APIII")
public class RecipeController {
    @Autowired
    private IRecipeRepository recipeRepository;

    @PostMapping(path = "/createData")
    public @ResponseBody String createData ()
    {
        Recipe recipe = new Recipe();
        recipe.setName("Curry");
        recipe.setServings(4);
        recipe.setInstructions("Doe de rijst in de pan");
        recipe.setVegetarian(true);
        recipe.setIngredients(Arrays.asList("rijst", "wortel"));
        recipeRepository.save(recipe);
        return "Recipe saved!";
    }

    @Operation(summary = "Find recipe", description = "Find one recipe by id", tags = { "Recipe" })
    @GetMapping(path = "/{id}")
    public @ResponseBody Recipe findById(@PathVariable int id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException());
    }


    @Operation(summary = "Delete recipe", description = "Delete one recipe by id", tags = { "Recipe" })
    @DeleteMapping(path = "/{id}")
    public @ResponseBody void deleteById(@PathVariable int id) {
        recipeRepository.deleteById(id);
    }

    @Operation(summary = "Add recipe", description = "Add one recipe", tags = { "Recipe" })
    @PostMapping(path ="/add")
    public @ResponseBody String addNewRecipe (@RequestBody Recipe newRecipe) {
        recipeRepository.save(newRecipe);
        return "Recipe saved!";
    }

    @Operation(summary = "Update recipe", description = "Update one recipe", tags = { "Recipe" })
    @PutMapping(path = "/{id}")
    public @ResponseBody Recipe updateRecipe(@RequestBody Recipe newRecipe, @PathVariable int id) {
        return recipeRepository.findById(id)
                .map(recipe -> {
                    recipe.setName(newRecipe.getName());
                    recipe.setVegetarian(newRecipe.isVegetarian());
                    recipe.setInstructions(newRecipe.getInstructions());
                    recipe.setServings(newRecipe.getServings());
                    recipe.setIngredients(newRecipe.getIngredients());
                    return recipeRepository.save(newRecipe);
                })
                .orElseGet(() -> {
                    newRecipe.setId(id);
                    return recipeRepository.save(newRecipe);
                });
    }

    @Operation(summary = "Fetch all recipes", description = "Fetches all recipes currently available in the database", tags = { "Recipe" })
    @GetMapping(path="/all")
    public @ResponseBody Iterable<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    @Operation(summary = "Find recipe by instructions", description = "Find all recipes that have certain terms in their instructions", tags = { "Recipe" })
    @GetMapping(path ="/instructions")
    public @ResponseBody Iterable<Recipe> findRecipesByInstructions(@RequestParam String instructions) {
        return recipeRepository.findByInstructionsContaining(instructions)
                .orElseThrow(() -> new RecipeNotFoundException());
    }

    @Operation(summary = "Find vegetarian recipes", tags = { "Recipe" })
    @GetMapping(path ="/vegetarian")
    public @ResponseBody Iterable<Recipe> findVegetarianRecipes() {
        return recipeRepository.findByIsVegetarianTrue()
                .orElseThrow(() -> new RecipeNotFoundException());
    }


    @Operation(summary = "Find recipes by number of servings", tags = { "Recipe" })
    @GetMapping(path ="/servings")
    public @ResponseBody Iterable<Recipe> findRecipesByNumberOfServings(@RequestParam int servings) {
        return recipeRepository.findByServingsEquals(servings)
                .orElseThrow(() -> new RecipeNotFoundException());
    }


    @Operation(summary = "Find recipes that include ingredients", tags = { "Recipe" })
    @GetMapping(path ="/hasIngredient")
    public @ResponseBody Iterable<Recipe> findRecipesContainingIngredients(@RequestParam List<String> ingredients) {
        return recipeRepository.findByIngredientsIn(ingredients)
                .orElseThrow(() -> new RecipeNotFoundException());
    }


    @Operation(summary = "Find recipes that exclude ingredients", tags = { "Recipe" })
    @GetMapping(path ="/lackIngredient")
    public @ResponseBody Iterable<Recipe> findRecipesNotContainingIngredients(@RequestParam List<String> ingredients) {
        return recipeRepository.findByIngredientsNotIn(ingredients)
                .orElseThrow(() -> new RecipeNotFoundException());
    }


    @Operation(summary = "Find recipes by number of servings and including ingredients", tags = { "Recipe" })
    @GetMapping(path ="/searchServingIngredient")
    public @ResponseBody Iterable<Recipe> findRecipesEqualsServingAndContainingIngredients(@RequestParam int servings,
                                                        @RequestParam List<String> ingredients) {
        return recipeRepository.findByServingsEqualsAndIngredientsIn(servings, ingredients)
                .orElseThrow(() -> new RecipeNotFoundException());
    }


    @Operation(summary = "Find recipes by instructions and excluding ingredients", tags = { "Recipe" })
    @GetMapping(path ="/searchInstructionsIngredient")
    public @ResponseBody Iterable<Recipe> findRecipesContainingInstructionsAndNotContainingIngredients(@RequestParam String instructions,
                                                                                           @RequestParam List<String> ingredients) {
        return recipeRepository.findByInstructionsContainingAndIngredientsNotIn(instructions, ingredients)
                .orElseThrow(() -> new RecipeNotFoundException());
    }
}
