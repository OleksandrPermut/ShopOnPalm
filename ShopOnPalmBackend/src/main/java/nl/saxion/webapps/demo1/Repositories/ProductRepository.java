package nl.saxion.webapps.demo1.Repositories;

import nl.saxion.webapps.demo1.Models.Product;
import nl.saxion.webapps.demo1.Models.Shop;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends
        CrudRepository<Product, Long> {

    @Override
    List<Product> findAll();
}
