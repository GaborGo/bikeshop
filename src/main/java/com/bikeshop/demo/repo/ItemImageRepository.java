package com.bikeshop.demo.repo;


import com.bikeshop.demo.model.ItemImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemImageRepository extends JpaRepository<ItemImage, Long> {

    void deleteAllById(Long id);

}
