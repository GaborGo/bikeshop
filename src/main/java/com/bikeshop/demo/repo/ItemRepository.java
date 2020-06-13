package com.bikeshop.demo.repo;

import com.bikeshop.demo.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository <Item, Long> {

    Item findItemById(Long id);

    List<Item> findAll();

    List<Item> findAllByActiveTrue();

    List<Item> findAllByNameContainingAndActiveTrue(String name);

    List<Item> findAllByCategoryAndActiveTrue(String category);

    List<Item> findAllByActiveTrueOrderById();

    List<Item> findAllByActiveTrueOrderByName();

    List<Item> findAllByActiveTrueOrderByCategory();

    List<Item> findAllByActiveTrueOrderByBrand();

    List<Item> findAllByActiveTrueOrderByIdDesc();

    List<Item> findAllByActiveTrueOrderByPriceAsc();

    List<Item> findAllByActiveTrueOrderByPriceDesc();

    List<Item> findAllByActiveTrueAndCategory(String category);

    List<Item> findAllByActiveTrueAndNameContainingOrActiveTrueAndCategoryContainingOrActiveTrueAndBrandContainingOrActiveTrueAndDescriptionContaining
            (String searchName, String searchCategory, String searchDescription, String brand);

    List<Item> findAllByActiveTrueAndNameContainingOrActiveTrueAndCategoryContainingOrActiveTrueAndBrandContainingOrActiveTrueAndDescriptionContainingOrderByName
            (String searchName, String searchCategory, String searchDescription, String brand);

    List<Item> findAllByActiveTrueAndNameContainingOrActiveTrueAndCategoryContainingOrActiveTrueAndBrandContainingOrActiveTrueAndDescriptionContainingOrderByCategory
            (String searchName, String searchCategory, String searchDescription, String brand);

    List<Item> findAllByActiveTrueAndNameContainingOrActiveTrueAndCategoryContainingOrActiveTrueAndBrandContainingOrActiveTrueAndDescriptionContainingOrderByBrand
            (String searchName, String searchCategory, String searchDescription, String brand);

    List<Item> findAllByActiveTrueAndNameContainingOrActiveTrueAndCategoryContainingOrActiveTrueAndBrandContainingOrActiveTrueAndDescriptionContainingOrderByPriceAsc
            (String searchName, String searchCategory, String searchDescription, String brand);

    List<Item> findAllByActiveTrueAndNameContainingOrActiveTrueAndCategoryContainingOrActiveTrueAndBrandContainingOrActiveTrueAndDescriptionContainingOrderByPriceDesc
            (String searchName, String searchCategory, String searchDescription, String brand);

    List<Item> findAllByActiveTrueAndNameContainingOrActiveTrueAndCategoryContainingOrActiveTrueAndBrandContainingOrActiveTrueAndDescriptionContainingOrderByIdDesc
            (String searchName, String searchCategory, String searchDescription, String brand);
}
