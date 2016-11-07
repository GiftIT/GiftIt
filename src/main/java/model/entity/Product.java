package model.entity;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedList;
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

//    @LazyCollection(LazyCollectionOption.FALSE)
//    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
//    @OneToMany(mappedBy = "product")
//    private List<Sex> sex;


//    @LazyCollection(LazyCollectionOption.FALSE)
//    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
//    @OneToMany(mappedBy = "product")
//    private List<AgeCategory> ageCategories;

    //    @LazyCollection(LazyCollectionOption.FALSE)
//    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
//    @OneToMany(mappedBy = "product")
//    private List<Country> country;

    @LazyCollection(LazyCollectionOption.FALSE)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @OneToMany(mappedBy = "product")
    private List<User> users;

    public Product() {
        users = new LinkedList<>();
    }

    public Product(String name) {
        this.name = name;
        users = new LinkedList<>();
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

//    public List<Sex> getSex() {
//        return sex;
//    }
//
//    public void setSex(List<Sex> sex) {
//        this.sex = sex;
//    }
//
//    public List<AgeCategory> getAgeCategories() {
//        return ageCategories;
//    }
//
//    public void setAgeCategories(List<AgeCategory> ageCategories) {
//        this.ageCategories = ageCategories;
//    }
//
//    public List<Country> getCountry() {
//        return country;
//    }
//
//    public void setCountry(List<Country> country) {
//        this.country = country;
//    }


    public List<User> getUsers () {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public boolean contains(int sex, int age, int country){
        return get(sex, age, country) != null;
    }

    public User get(int sex, int age, int country){
        for(User u : users){
            if(u.getSex() == sex && u.getAge() == age && u.getCountry() == country)
                return u;
        }

        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (posts != product.posts) return false;
        return name != null ? name.equals(product.name) : product.name == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + posts;
        return result;
    }
}
