package model.entity;

import javax.persistence.*;


@Entity
public class User {

    @Id
    @GeneratedValue
    private int id;

    private int sex = -1;

    private int age = -1;

    private int country = -1;

    private int amount = 1;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = true)
    private Product product;

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getCountry() {
        return country;
    }

    public void setCountry(int country) {
        this.country = country;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    public void incAmount(){
        amount++;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;

        User that = (User)obj;

        return (this.sex == that.sex && this.age == that.age && this.country == that.country && this.product == that.product);

    }
}
