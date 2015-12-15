package model.entity;

import javax.persistence.*;

/**
 * Created by Babenko on 12.12.2015.
 */
@Entity
@Table(name = "AGE_CATEGORY")
public class AgeCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_age")
    private int idAge;
    @Column(name = "ageRange")
    private String ageRange;
    @Column(name = "numger_likes")
    private long numberOfLikes;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = true)
    private Product product;

    public AgeCategory() {
    }

    public AgeCategory(String ageRange, long numberOfLikes, Product product) {
        this.ageRange = ageRange;
        this.numberOfLikes = numberOfLikes;
        this.product = product;
    }

    public void addLikes(int likes) {
        this.numberOfLikes += likes;
    }

    public int getIdAge() {
        return idAge;
    }

    public void setIdAge(int idAge) {
        this.idAge = idAge;
    }

    public String getAgeRange() {
        return ageRange;
    }

    public void setAgeRange(String ageRange) {
        this.ageRange = ageRange;
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
