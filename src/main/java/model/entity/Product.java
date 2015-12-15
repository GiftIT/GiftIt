package model.entity;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Babenko on 12.12.2015.
 */
@Entity
@Table(name = "PRODUCT")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product")
    private int idProduct;
    @Column(name = "name")
    private String name;
    @Column(name = "posts")
    private int posts;

    @LazyCollection(LazyCollectionOption.FALSE)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @OneToMany(mappedBy = "product")
    private List<Sex> sex;


    @LazyCollection(LazyCollectionOption.FALSE)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @OneToMany(mappedBy = "product")
    private List<AgeCategory> ageCategories;

    @LazyCollection(LazyCollectionOption.FALSE)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @OneToMany(mappedBy = "product")
    private List<Country> country;

    public Product() {
    }

    public Product(String name) {
        this.name = name;
    }

    public Product(String name, int posts, List<Sex> sex, List<AgeCategory> ageCategories) {
        this.name = name;
        this.posts = posts;
        this.sex = sex;
        this.ageCategories = ageCategories;
    }

    public void addPosts() {
        this.posts++;
    }


    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosts() {
        return posts;
    }

    public void setPosts(int posts) {
        this.posts = posts;
    }

    public List<Sex> getSex() {
        return sex;
    }

    public void setSex(List<Sex> sex) {
        this.sex = sex;
    }

    public List<AgeCategory> getAgeCategories() {
        return ageCategories;
    }

    public void setAgeCategories(List<AgeCategory> ageCategories) {
        this.ageCategories = ageCategories;
    }

    public List<Country> getCountry() {
        return country;
    }

    public void setCountry(List<Country> country) {
        this.country = country;
    }

}
