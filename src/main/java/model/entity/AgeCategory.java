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
    @Column(name = "left_age_limit")
    private int leftAgeLimit;
    @Column(name = "right_age_limit")
    private int rightAgeLimit;
    @Column(name = "number_likes")
    private long numberOfLikes;
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = true)
    private Product product;
    public AgeCategory() {
    }

    public AgeCategory(long numberOfLikes, Product product, int leftAgeLimit, int rightAgeLimit) {
        this.numberOfLikes = numberOfLikes;
        this.product = product;
        this.leftAgeLimit = leftAgeLimit;
        this.rightAgeLimit = rightAgeLimit;
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

    public int getLeftAgeLimit() {
        return leftAgeLimit;
    }

    public void setLeftAgeLimit(int leftAgeLimit) {
        this.leftAgeLimit = leftAgeLimit;
    }

    public int getRightAgeLimit() {
        return rightAgeLimit;
    }

    public void setRightAgeLimit(int rightAgeLimit) {
        this.rightAgeLimit = rightAgeLimit;
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
