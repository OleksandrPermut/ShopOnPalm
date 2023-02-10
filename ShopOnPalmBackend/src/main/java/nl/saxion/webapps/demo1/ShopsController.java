package nl.saxion.webapps.demo1;

import nl.saxion.webapps.demo1.Models.ProductInShop;
import nl.saxion.webapps.demo1.Models.Shop;
import nl.saxion.webapps.demo1.Repositories.ProductRepository;
import nl.saxion.webapps.demo1.Services.ProductService;
import nl.saxion.webapps.demo1.Services.ShopService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.xml.crypto.Data;
import java.util.List;

@RestController
@RequestMapping("/shops")
public class ShopsController {

    private ShopService shopService;
    private ProductService productService;
    public ShopsController(ShopService shopService, ProductService productService) {
        this.shopService = shopService;
        this.productService = productService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public List<Shop> getAllShops() {
            return shopService.getAllShops();
    }


    @GetMapping("/getProductsInShop/{shopId}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public List<ProductInShop> getProductsInShop(@PathVariable long shopId) {
        var shop = shopService.getById(shopId);
        if (shop.isPresent())
            return shop.get().getProductsInShop();
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "object with this id not exist");
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public Shop findById(@PathVariable long id) {
        var shop = shopService.getById(id);
        if (shop.isPresent())
            return shop.get();
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "object with this id not exist");
    }



    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public Shop create(@RequestBody Shop shop) {
        return shopService.createShop(shop);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public Shop update(@PathVariable long id, @RequestBody Shop shop) {
        if (shop.getId() == id)
            return shopService.updateShop(shop);
        else
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Object with this id not exist");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public void delete(@PathVariable long id)
    {
        shopService.deleteShop(id);
    }

    @PutMapping("/addProductToShop")
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ProductInShop addProductToShop(@RequestParam long shopId, @RequestParam long productId, @RequestParam float price, @RequestParam int amount)
    {
        var shop = shopService.getById(shopId);
        if (!shop.isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id incorrect");

        var product = productService.getById(productId);
        if (!product.isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id incorrect");

        var productInShop = shopService.addProductToShop(shop.get(), product.get(), price, amount);
        return productInShop;
    }

    @DeleteMapping("/removeProductFromShop")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ProductInShop removeProductFromShop(@RequestParam long shopId, @RequestParam long productInShopId)
    {
        return shopService.deleteProduct(shopId, productInShopId);
    }
}
