package model.entity;

import javax.persistence.*;

/**
 * Created by Babenko on 12.12.2015.
 */
@Entity
@Table(name = "SEX")
public class Sex {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sex")
    private int idSex;
    @Column(name = "sex")
    private String sex;
    @Column(name = "likes")
    private long numberOfLikes;
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = true)
    private Product product;


    public Sex() {
    }

    public Sex(String sex, long numberOfLikes) {
        this.sex = sex;
        this.numberOfLikes = numberOfLikes;
    }

    public Sex(String sex, long likes, Product product) {
        this.sex = sex;
        this.numberOfLikes = numberOfLikes;
        this.product = product;
    }


    public void addLikes(int numberOfLikes) {
        this.numberOfLikes += numberOfLikes;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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

    public int getIdSex() {
        return idSex;
    }

    public void setIdSex(int idSex) {
        this.idSex = idSex;
    }
}
