package nl.saxion.webapps.demo1.Repositories;

import nl.saxion.webapps.demo1.Models.Shop;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShopRepository extends
        CrudRepository<Shop, Long> {

    @Override
    List<Shop> findAll();
}
