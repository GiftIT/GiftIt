package logic.webWorkers;

import java.util.Date;

/**
 * Created by vlad on 12/16/15.
 */
public class Person {

    private int sex;
    private int ageCategory;
    private int country;

    private static int year;
    public static int getYear(){
        return year;
    }

    public static void updateYear(){
        Person.year = (new Date()).getYear() + 1900;
    }

    public Person() {
        sex = -1;
        ageCategory = -1;
        country = -1;
    }

    public Person(int sex, int ageCategory, int country) {
        this.sex = sex;
        this.ageCategory = ageCategory;
        this.country = country;
    }

    public boolean hasAge(){
        return ageCategory >= 0;
    }

    public boolean hasSex(){
        return sex >= 0;
    }

    public boolean hasCountry(){
        return country != -1;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex*100;
    }

    public int getAgeCategory() {
        return ageCategory;
    }

    public void setAge(int age) {

//        this.ageCategory = age;
        if(age <= 6)
            this.ageCategory = 0;
        else if( age > 6 && age <= 10)
            this.ageCategory = 1;
        else if( age > 10 && age <= 15)
            this.ageCategory = 2;
        else if( age > 15 && age <= 18)
            this.ageCategory = 3;
        else if( age > 18 && age <= 30)
            this.ageCategory = 4;
        else if( age > 30 && age <= 40)
            this.ageCategory = 5;
        else if( age > 40 && age <= 50)
            this.ageCategory = 6;
        else
            this.ageCategory = 7;
        this.ageCategory *= 100;
    }

    public int getCountry() {
        return country;
    }

    public void setCountry(int country) {
        this.country = country*100;
    }
}
