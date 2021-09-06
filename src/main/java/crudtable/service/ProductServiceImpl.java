package crudtable.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import crudtable.data.ProductRepository;
import crudtable.model.Product;

@Service
public class ProductServiceImpl implements ProductService {	
	
	@Autowired
	ProductRepository productRepo;

	@Override
	public Page<Product> search(String name, Pageable pageRequest) {
		
		if (name.isBlank()) {
			Page<Product> result = productRepo.findAll(pageRequest);
			return result;
		}
		
		Page<Product> result =  productRepo.searchPageByIdnName(name, pageRequest);
		
		return result;
	}

	@Override
	public Product update(Product product) {
		Optional<Product> result = productRepo.findById(product.getId());
		Product p = result.orElse(null);
		// select the modifiable fields
		p.setName(product.getName());
		
		if (result.isPresent()) {
			productRepo.save(p);
			return p;
		}
		
		return null;
	}

	@Override
	public Product findById(long id) {
		Optional<Product> product = productRepo.findById(Long.valueOf(id));
		if (product.isPresent()) {
			return product.get();
		}
		
		return null;
	}

	@Override
	public Product deleteById(long id) {
		Optional<Product> product = productRepo.findById(Long.valueOf(id));
		Product p = product.orElse(null);
	
		if (product.isPresent()) {
			productRepo.deleteById(Long.valueOf(id));
			return p;
		}
		return null;
	}

}
