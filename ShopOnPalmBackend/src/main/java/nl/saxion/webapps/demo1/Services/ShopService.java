package nl.saxion.webapps.demo1.Services;

import nl.saxion.webapps.demo1.Models.Product;
import nl.saxion.webapps.demo1.Models.ProductInShop;
import nl.saxion.webapps.demo1.Models.Shop;
import nl.saxion.webapps.demo1.Repositories.ProductInShopRepository;
import nl.saxion.webapps.demo1.Repositories.ProductRepository;
import nl.saxion.webapps.demo1.Repositories.ShopRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

/**
 * Service class for working with shops
 * @author Permut Alex
 * @version 1.0
 */
@Service
public class ShopService {

    /**
     * Shop repository for access to shops table
     */
    private ShopRepository shopRepository;

    /**
     * Product in shop repository for access to product_in_shop table
     */
    private ProductInShopRepository productInShopRepository;

    /**
     * @param shopRepository - getting reference to shop repository
     * @param productInShopRepository - getting reference to product in shop repository
     * @see ShopService#ShopService(ShopRepository, ProductInShopRepository) ()
     */
    public ShopService(ShopRepository shopRepository, ProductInShopRepository productInShopRepository) {
        this.shopRepository = shopRepository;
        this.productInShopRepository = productInShopRepository;
    }

    /**
     * Method for get all shops from table
     * @return List of shops
     */
    public List<Shop> getAllShops()
    {
        return shopRepository.findAll();
    }

    /**
     * Method for get shop by id
     * @param id - identifier of shop
     * @return Shop instance
     */
    public Optional<Shop> getById(long id)
    {
        return shopRepository.findById(id);
    }



    /**
     * Method for creating new shop and adding it to database table
     * @param shop - instance of adding shop
     * @return instance of saved shop
     */
    public Shop createShop(Shop shop)
    {
        return shopRepository.save(shop);
    }

    /**
     * Method for update existing shop in database table
     * @param shop - instance of updating shop
     * @return instance of updated shop
     */
    public Shop updateShop(Shop shop)
    {
        return  shopRepository.save(shop);
    }


    /**
     * Method for delete existing shop in database table by id
     * @param id - identifier of deleting product
     */
    public void deleteShop(long id)
    {
        Optional<Shop> shop = shopRepository.findById(id);

        var productsInShop = productInShopRepository.getProductInShopByShopId(id);
        for (var productInShop:productsInShop) {
            productInShopRepository.delete(productInShop);
        }

        if (shop.isPresent())
        {
            shopRepository.delete(shop.get());
        }
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Object with this id not exist");
    }

    /**
     * Method for adding product to shop with price and amount
     * @param shop - instance of shop
     * @param product - instance of product
     * @param price - selling price
     * @param amount - available amount of this product in this shop
     * @return instance of product in shop
     */
    public ProductInShop addProductToShop(Shop shop, Product product, float price, int amount)
    {
        ProductInShop productInShop = new ProductInShop(shop, product, price, amount);
        productInShopRepository.save(productInShop);

        return productInShop;
    }

    /**
     * Method for removing product from shop
     * @param shopId - identifier of shop
     * @param productInShopId - identifier product in shop
     * @return instance of product in shop
     */
    public ProductInShop deleteProduct(long shopId, long productInShopId)
    {
        Shop shop = shopRepository.findById(shopId).get();
        if (shop!=null)
        {
            var productInShopForDelete = shop.getProductInShop(productInShopId);
            if (productInShopForDelete!=null)
            {
                productInShopRepository.delete(productInShopForDelete);

            }
            else
                new Exception("Product in shop with this id not exist");
            return  productInShopForDelete;
        }
        else
            new Exception("Shop with this id not exist");
        return null;
    }


}
