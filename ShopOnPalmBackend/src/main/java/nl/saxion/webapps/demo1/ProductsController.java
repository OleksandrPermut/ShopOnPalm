package nl.saxion.webapps.demo1;

import nl.saxion.webapps.demo1.Models.Product;
import nl.saxion.webapps.demo1.Models.Shop;
import nl.saxion.webapps.demo1.Services.ProductService;
import nl.saxion.webapps.demo1.Services.ShopService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController
@RequestMapping("/products")
public class ProductsController {

    private ProductService productService;

    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public Product findById(@PathVariable long id) {
        var product = productService.getById(id);
        if (product.isPresent())
            return product.get();
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "object with this id not exist");
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public Product create(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @PutMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public Product update(@PathVariable int productId, @RequestBody Product product) {
        if (productId == product.getId()) {
            Product updateProduct = productService.updateProduct(product);
            if (updateProduct != null)
                return updateProduct;
            else
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product with this id not exist");
        }
        else
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id of product differents");
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public void delete(@PathVariable int productId) {
        productService.deleteProduct(productId);
    }

    @GetMapping("/getAvailableProducts/{shopId}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public List<Product> getAvailableProducts(@PathVariable long shopId) {
        return productService.getAvailableProducts(shopId);
    }

    @GetMapping("/isProductSelling/{productId}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public boolean isProductSelling(@PathVariable long productId) {
        return productService.isProductSelling(productId);
    }
}
