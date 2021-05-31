package com.iktpreobuka.project.repositories;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.project.entities.BillEntity;
import com.iktpreobuka.project.entities.CategoryEntity;
import com.iktpreobuka.project.entities.OfferEntity;

public interface BillRepository extends CrudRepository<BillEntity, Integer> {

	public List<BillEntity> findByUserId(Integer buyerId);

	public List<BillEntity> findByBillCreatedBetween(LocalDate startDate, LocalDate endDate);

	public List<BillEntity> findByOfferCategory(CategoryEntity category);
}
