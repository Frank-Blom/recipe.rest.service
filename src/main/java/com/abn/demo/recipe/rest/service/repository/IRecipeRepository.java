package com.abn.demo.recipe.rest.service.repository;

import com.abn.demo.recipe.rest.service.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface IRecipeRepository extends CrudRepository<Recipe, Integer> {

    Optional<List<Recipe>> findByInstructionsContaining(String instructions);
    Optional<List<Recipe>> findByIsVegetarianTrue();
    Optional<List<Recipe>> findByServingsEqualsAndIngredientsIn(int servings, List<String> ingredient);
    Optional<List<Recipe>> findByServingsEquals(int servings);
    Optional<List<Recipe>> findByInstructionsContainingAndIngredientsNotIn(String instructions, List<String> ingredient);
    Optional<List<Recipe>> findByIngredientsIn(List<String> ingredient);
    Optional<List<Recipe>> findByIngredientsNotIn(List<String> ingredient);


}
