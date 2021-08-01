package crudtable.web;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import crudtable.data.ProductRepository;
import crudtable.dto.BootstrapTable;
import crudtable.model.Product;
import crudtable.service.ProductService;


@Controller
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private ProductService productService;

	@GetMapping("/list")
	public ResponseEntity<BootstrapTable<Product>> listProducts(@RequestParam(value = "page", defaultValue = "0") Integer pageNo,
															  @RequestParam(value = "limit", defaultValue = "10") Integer pageSize,
															  @RequestParam(value = "name", required = false) String name) {			
		Pageable pageRequest = PageRequest.of(pageNo, pageSize);
		
		Page<Product> result = productService.search(name, pageRequest);

		BootstrapTable<Product> bTable = new BootstrapTable<>();
		
		if (result.isEmpty()) {
			String errorMessage = "No products found.";
			if (!name.isBlank()) {
				errorMessage = String.format("No products found for 'name: %s'.", name);
			}			
			
			bTable.setError(errorMessage);
			return new ResponseEntity<>(bTable, HttpStatus.NOT_FOUND);
		}
		
		long total = result.getTotalElements();
		String succesfulFoundMessage = String.format("%d Products where found.", total);
		bTable.setRows(result.getContent());
		bTable.setTotal(total);
		bTable.setMessage(succesfulFoundMessage);
		
		return new ResponseEntity<>(bTable, new HttpHeaders(), HttpStatus.OK);
	}
	
	@PostMapping("/new")
	public ResponseEntity<ActionResult> insert(@RequestBody Product product){		
		product = productRepo.save(product);
		String message = String.format("Product with name:'%s' was saved.", product.getName());
		return new ResponseEntity<>(new ActionResult(true, message, product), new HttpHeaders(), HttpStatus.OK);
	}
	
	@PostMapping("/update")
	public ResponseEntity<ActionResult> update(@RequestBody Product product){		
		product = productService.update(product);
		if (Objects.isNull(product)) {
			return new ResponseEntity<>(new ActionResult(false,
					"No products found.", null), new HttpHeaders(), HttpStatus.NOT_FOUND);
		}		
		return new ResponseEntity<>(new ActionResult(true,
				String.format("The product with id:'%d' was successfully updated.", product.getId()), product), new HttpHeaders(), HttpStatus.OK);		
	}
	
	@GetMapping("/get/{id}")
	public ResponseEntity<ActionResult> get(@PathVariable("id") long id){
		Product product = productService.findById(id);		
		if (Objects.isNull(product)) {
			return new ResponseEntity<>(new ActionResult(true, String.format("No products found for  'id:%d'.", id), null), new HttpHeaders(), HttpStatus.NOT_FOUND);
		}		
		return new ResponseEntity<>(new ActionResult(true, String.format("Loading producto for 'id:%d'.", id), product), new HttpHeaders(), HttpStatus.OK);
	}
	
	@GetMapping("/delete/{id}")
	public ResponseEntity<ActionResult> delete(@PathVariable("id") long id){
		Product product = productService.deleteById(id);
		if (Objects.isNull(product)) {
			return new ResponseEntity<>(new ActionResult(false, String.format("No products found for 'id:%d'.", id), null), new HttpHeaders(), HttpStatus.NOT_FOUND);
		}		
		return new ResponseEntity<>(new ActionResult(true, String.format("Product with 'id:%d' was successfully deleted.", id), product), new HttpHeaders(), HttpStatus.OK);
	}
	
}
