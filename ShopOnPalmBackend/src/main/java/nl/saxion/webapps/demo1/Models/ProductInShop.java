package nl.saxion.webapps.demo1.Models;

import javax.persistence.*;

/**
 * Product in shop class describe where this product selling, it price and amount with properties <b>Shop</b>, <b>Product</b>,
 * <b>price</b> and <b>amount</b>
 * @author Permut Alex
 * @version 1.0
 */
@Entity
public class ProductInShop {

    /**
     * Identifier of Product In Shop
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Shop where product selling
     */
    @ManyToOne
    @JoinColumn(name="shop_id", nullable=false)
    private Shop shop;

    /**
     * Selling product
     */
    @ManyToOne
    @JoinColumn(name="product_id", nullable=false)
    private Product product;

    /**
     * Selling price
     */
    @Column
    private float Price;

    /**
     * Available amount in shop
     */
    @Column
    private int Amount;

    /**
     * @see ProductInShop#ProductInShop()
     */
    public ProductInShop(){}

    /**
     * @param shop - Shop where product selling
     * @param product - Selling product
     * @param price - Selling price
     * @param amount - Available amount in shop
     * @see ProductInShop#ProductInShop()
     */
    public ProductInShop(Shop shop, Product product, float price, int amount) {
        this.shop = shop;
        this.product = product;
        Price = price;
        Amount = amount;
    }

    /**
     * Method for get id of product {@link ProductInShop#id}
     * @return id of product
     */
    public long getId() {
        return id;
    }

    /**
     * Method for get Shop where product selling {@link ProductInShop#shop}
     * @return shop
     */
    public Shop getShop() {
        return shop;
    }

    /**
     * Method for get Product selling in shop {@link ProductInShop#product}
     * @return product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Method for get price of selling Product for this shop {@link ProductInShop#Price}
     * @return price
     */
    public float getPrice() {
        return Price;
    }

    /**
     * Method for get available amount of Product in this shop {@link ProductInShop#Amount}
     * @return price
     */
    public int getAmount() {
        return Amount;
    }
}
