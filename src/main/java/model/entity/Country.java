package model.entity;

import javax.persistence.*;

/**
 * Created by Babenko on 13.12.2015.
 */
@Table
@Entity(name = "COUNTRY")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_age")
    private int idAge;
    @Column(name = "country")
    private String country;
    @Column(name = "numger_likes")
    private long numberOfLikes;
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = true)
    private Product product;

    public Country() {
    }

    public Country(String country, long numberOfLikes) {
        this.country = country;
        this.numberOfLikes = numberOfLikes;
    }


    public Country(String country, long numberOfLikes, Product product) {
        this.country = country;
        this.numberOfLikes = numberOfLikes;
        this.product = product;
    }

    public void addLikes() {
        this.numberOfLikes++;
    }

    public int getIdAge() {
        return idAge;
    }

    public void setIdAge(int idAge) {
        this.idAge = idAge;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public long getNumberOfLikes() {
        return numberOfLikes;
    }

    public void setNumberOfLikes(long numberOfLikes) {
        this.numberOfLikes = numberOfLikes;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
