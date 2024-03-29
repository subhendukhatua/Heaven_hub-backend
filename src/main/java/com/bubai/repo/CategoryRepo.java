package com.bubai.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bubai.model.Category;

public interface CategoryRepo extends JpaRepository<Category, Long> {
	
	public Category findByName(String name);
	
	@Query("select c from Category c where c.name = :name and c.parentCategory.name =:parentCategoryName")
	public Category findByNameAndParentCategory(@Param("name")String name, @Param("parentCategoryName")String parentCategoryName);

}
