package crudtable.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import crudtable.model.Product;

public interface ProductService {

	Page<Product> search(String name, Pageable pageRequest);

	Product update(Product product);

	Product findById(long id);

	Product deleteById(long id);
	

}
