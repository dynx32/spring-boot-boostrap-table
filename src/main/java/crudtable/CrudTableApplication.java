package crudtable;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import crudtable.data.ProductRepository;
import crudtable.model.Product;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class CrudTableApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudTableApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner demo(ProductRepository repo) {
		return (args) -> {
			Long i = Long.valueOf(1);
			List<Product> products = new ArrayList<>();
			while (i < 101) {
				Product n = new Product(i, String.format("Product %d", i));				
				products.add(n);
				i++;
				log.info(n.toString());
			}
			repo.saveAll(products);
		};
		
	}

}
