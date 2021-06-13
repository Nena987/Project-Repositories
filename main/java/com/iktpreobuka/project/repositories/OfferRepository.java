package com.iktpreobuka.project.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.project.entities.CategoryEntity;
import com.iktpreobuka.project.entities.OfferEntity;

public interface OfferRepository extends CrudRepository<OfferEntity, Integer> {

	public List<OfferEntity> findByActionPriceBetween(Double lowerPrice, Double upperPrice);

	public List<OfferEntity> findByCategory(CategoryEntity category);

	public List<OfferEntity> findByCategoryId(Integer categoryId);

	public List<OfferEntity> findAllByCategoryId(Integer categoryId);

}
