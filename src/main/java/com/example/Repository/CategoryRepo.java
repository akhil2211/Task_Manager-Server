package com.example.Repository;

import com.example.Model.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepo extends CrudRepository<Category,Integer> {
}
