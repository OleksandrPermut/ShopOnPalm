package nl.saxion.webapps.demo1.Repositories;

import nl.saxion.webapps.demo1.Models.Product;
import nl.saxion.webapps.demo1.Models.ProductInShop;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductInShopRepository  extends
        CrudRepository<ProductInShop, Long> {


    @Query("select c from ProductInShop c where c.product.id = :productId")
    List<ProductInShop> getProductInShopByProductId(@Param("productId") long productId);

    @Query("select c from ProductInShop c where c.shop.id = :shopId")
    List<ProductInShop> getProductInShopByShopId(@Param("shopId") long shopId);


}
