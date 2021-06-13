package com.iktpreobuka.project.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.project.entities.CategoryEntity;
import com.iktpreobuka.project.repositories.CategoryRepository;
import com.iktpreobuka.project.services.BillService;
import com.iktpreobuka.project.services.OfferService;

@RestController
@RequestMapping(path = "/project/categories")
public class CategoryController {

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	private BillService billService;

	@Autowired
	private OfferService offerService;

	List<CategoryEntity> categories = new ArrayList<CategoryEntity>();

	/*
	 * 2.3 kreirati REST endpoint koji vraća listu svih kategorija • putanja
	 * /project/categories
	 */

	@GetMapping
	public Iterable<CategoryEntity> getAll() {
		return (List<CategoryEntity>) categoryRepository.findAll();
	}

	/*
	 * 2.4 kreirati REST endpoint koji omogućava dodavanje nove kategorije • putanja
	 * /project/categories • metoda treba da vrati dodatu kategoriju
	 */

	@PostMapping
	public CategoryEntity createNewCategory(@RequestParam String categoryName,
			@RequestParam String categoryDescription) {
		CategoryEntity category = new CategoryEntity(categoryName, categoryDescription);
		categoryRepository.save(category);
		return category;
	}

	/*
	 * 2.5 kreirati REST endpoint koji omogućava izmenu postojeće kategorije •
	 * putanja /project/categories/{ • ukoliko je prosleđen ID koji ne pripada
	 * nijednoj kategoriji metoda treba da vrati null , a u suprotnom vraća podatke
	 * kategorije sa izmenjenim vrednostima
	 */

	@RequestMapping(method = RequestMethod.PUT, path = "/{id}")
	public CategoryEntity changeOneCategory(@RequestBody CategoryEntity changeCategory, @PathVariable Integer id) {
		if (categoryRepository.existsById(id)) {
			CategoryEntity category = categoryRepository.findById(id).get();
			if (changeCategory.getCategoryName() != null)
				category.setCategoryName(changeCategory.getCategoryName());
			if (changeCategory.getCategoryDescription() != null)
				category.setCategoryDescription(changeCategory.getCategoryDescription());
			return categoryRepository.save(category);
		}
		return null;
	}

	/*
	 * pozvati prethodno kreirane metode u metodi za brisanje kategorije u okviru
	 * CategoryController a
	 */

	@RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
	public CategoryEntity removeCategory(@PathVariable Integer id) {

		if (categoryRepository.existsById(id)) {
			CategoryEntity category = categoryRepository.findById(id).get();

			Boolean checkOffers = offerService.findCategoryWithNonExpiredOffers(id);
			Boolean checkBills = billService.findActiveBills(id);
			if (checkOffers == false && checkBills == false) {
				categoryRepository.delete(category);
				return category;
			}
		}
		return null;
	}

	/*
	 * 2.7 kreirati REST endpoint koji vraća kategoriju po vrednosti prosleđenog ID
	 * a • putanja /project/categories/{ • u slučaju da ne postoji kategorija sa
	 * traženom vrednošću ID a vratiti null
	 */

	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
	public CategoryEntity findOneCategory(@PathVariable Integer id) {
		if (categoryRepository.existsById(id)) {
			return categoryRepository.findById(id).get();
		}
		return null;
	}
}
