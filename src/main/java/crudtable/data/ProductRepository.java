package crudtable.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import crudtable.model.Product;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
	
	
	Page<Product> findAll(Pageable pageable);

	@Query("SELECT p FROM Product p WHERE UPPER(p.name) LIKE CONCAT('%',UPPER(:searchName),'%')")
	Page<Product> searchPageByIdnName(@Param("searchName") String searchName, Pageable pageRequest);
}
