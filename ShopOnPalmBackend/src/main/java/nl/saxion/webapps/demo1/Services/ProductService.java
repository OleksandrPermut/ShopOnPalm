package nl.saxion.webapps.demo1.Services;

import nl.saxion.webapps.demo1.Models.Product;
import nl.saxion.webapps.demo1.Models.ProductInShop;
import nl.saxion.webapps.demo1.Models.Shop;
import nl.saxion.webapps.demo1.Repositories.ProductInShopRepository;
import nl.saxion.webapps.demo1.Repositories.ProductRepository;
import nl.saxion.webapps.demo1.Repositories.ShopRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service class for working with products
 * @author Permut Alex
 * @version 1.0
 */
@Service
public class ProductService {
    /**
     * Product repository for access to products table
     */
    private ProductRepository productRepository;

    /**
     * Product repository for access to shops table
     */
    private ShopRepository shopRepository;

    /**
     * Product in shop repository for access to product_in_shop table
     */
    private ProductInShopRepository productInShopRepository;

    /**
     * @param productRepository - getting reference to product repository
     * @param shopRepository - getting reference to shop repository
     * @param productInShopRepository - getting reference to product in shop repository
     * @see ProductService#ProductService(ProductRepository, ShopRepository, ProductInShopRepository) ()
     */
    public ProductService(ProductRepository productRepository, ShopRepository shopRepository, ProductInShopRepository productInShopRepository) {
        this.productRepository = productRepository;
        this.shopRepository = shopRepository;
        this.productInShopRepository = productInShopRepository;
    }

    /**
     * Method for get product by id
     * @param id - identifier of product
     * @return Product instance
     */
    public Optional<Product> getById(long id)
    {
        var result = productRepository.findById(id);
        return result;
    }

    /**
     * Method for get product selling in shop by id
     * @param id - identifier of product in shop
     * @return ProductInShop instance
     */
    public Optional<ProductInShop> getProductInShopById(long id)
    {
        return productInShopRepository.findById(id);
    }


    /**
     * Method for get all products from table
     * @return List of products
     */
    public List<Product> getAllProducts()
    {
        return productRepository.findAll();
    }

    /**
     * Method for get list of products witch are not currently selling in shop by id
     * @param shopId - identifier of shop
     * @return List of products
     */
    public List<Product> getAvailableProducts(long shopId)
    {
        var shop = shopRepository.findById(shopId);
        if (shop.isPresent()) {
            List<Product> allProducts = productRepository.findAll();
            List<Product> availableProducts = new ArrayList<>();
            for (var product : allProducts) {
                boolean isFind = false;
                for (var productInShop : shop.get().getProductsInShop())
                {
                    if (product.getId() == productInShop.getProduct().getId()) {
                        isFind = true;
                        break;
                    }
                }
                if (isFind == false)
                    availableProducts.add(product);
            }
            return availableProducts;
        }
        else
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Object with this id not exist");
        }

    }

    /**
     * Method for creating new product and adding it to database table
     * @param product - instance of adding product
     * @return instance of saved product
     */
    public Product createProduct(Product product)
    {
        return productRepository.save(product);
    }

    /**
     * Method for update existing product in database table
     * @param product - instance of updating product
     * @return instance of updated product
     */
    public Product updateProduct(Product product)
    {
            return productRepository.save(product);
    }

    /**
     * Method for delete existing product in database table by id
     * @param id - identifier of deleting product
     */
    public void deleteProduct(long id)
    {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent())
        {
            productRepository.delete(product.get());
        }
        else
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Object with this id not exist");

    }

    /**
     * Method to check - if product with this id selling in any shop
     * @param productId - identifier of checking product
     * @return true - if product selling in any shop, false - if not
     */
    public boolean isProductSelling(long productId)
    {
        return productInShopRepository.getProductInShopByProductId(productId).stream().count() != 0;
    }



}
