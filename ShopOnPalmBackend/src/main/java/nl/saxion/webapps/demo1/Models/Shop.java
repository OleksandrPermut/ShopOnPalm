package nl.saxion.webapps.demo1.Models;




import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Shop class describe with properties <b>id</b>, <b>name</b> and <b>city</b>
 * @author Permut Alex
 * @version 1.0
 */
@Entity
public class Shop {
    /**
     * Identifier of Shop
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Name of shop
     */
    @Column
    private String name;

    /**
     * Shop location city
     */
    @Column
    private String city;

    @OneToMany(
            mappedBy="shop"
    )

    /**
     * list of prdocuts with price and amount selling in this shop
     */
    @JsonBackReference
    private List<ProductInShop> productsInShop;

    /**
     * @see Shop#Shop() ()
     */
    public Shop(){}

    /**
     * Method for get city of shop {@link Shop#city}
     * @return id of product
     */
    public String getCity() {
        return city;
    }

    /**
     * Method for get name of shop {@link Shop#name}
     * @return id of product
     */
    public String getName() {
        return name;
    }

    /**
     * Method for get id of shop {@link Shop#id}
     * @return id of product
     */
    public long getId() {
        return id;
    }

    /**
     * Method for set name of shop {@link Shop#name}
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method for set city location of shop {@link Shop#city}
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Method for add new product in shop
     * @param product - Product with price and amount for adding to shop
     */
    public void addProduct(ProductInShop product)
    {
        if (productsInShop == null)
            productsInShop = new ArrayList<>();
        productsInShop.add(product);
    }

    /**
     * Method for get product in shop by id
     * @param productInShopId - Id of product in shop instance for getting
     * @return if product with this id exist, return this product in shop
     */
    public ProductInShop getProductInShop(long productInShopId) {
        for (var p: productsInShop) {
            if (p.getId() == productInShopId)
                return p;
        }
        return  null;
    }

    /**
     * Method for delete product from shop by instance
     * @param productInShop - product in shop instance for deleting
     */
    public void deleteProductInShop(ProductInShop productInShop)
    {
        productsInShop.remove(productInShop);
    }

    /**
     * Method for get all products witch selling in shop {@link Shop#productsInShop}
     */
    public List<ProductInShop> getProductsInShop() {
        return productsInShop;
    }
}
