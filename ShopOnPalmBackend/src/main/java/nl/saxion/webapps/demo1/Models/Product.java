package nl.saxion.webapps.demo1.Models;




import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.List;

/**
 * Product class with properties <b>id</b>, <b>name</b> and list of shop where it selling
 * @author Permut Alex
 * @version 1.0
 */
@Entity
public class Product {
    /**
     * Identifier of product
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    /**
     * Name of product
     */
    @Column
    private String name;


    /**
     * list of shop where it selling with price and amount
     */
    @OneToMany(
            mappedBy="product"
    )
    @JsonBackReference
    private List<ProductInShop> productsInShop;


    /**
     * @see Product#Product()
     */
    public Product() {
    }

    /**
     * Method for get id of product {@link Product#id}
     * @return id of product
     */
    public long getId() {
        return id;
    }

    /**
     * Method for set name of product {@link Product#name}
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method for get name of product {@link Product#name}
     * @return name of product
     */
    public String getName() {
        return name;
    }
}
