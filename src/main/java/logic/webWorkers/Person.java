package logic.webWorkers;

import java.util.Date;

/**
 * Created by vlad on 12/16/15.
 */
public class Person {

    private int sex;
    private int ageCategory;
    private String country;

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
    }

    public Person(int sex, int ageCategory, String country) {
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
        return country != null;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getAgeCategory() {
        return ageCategory;
    }

    public void setAge(int age) {

        if(age <= 12)
            this.ageCategory = 0;
        else if( age > 12 && age <= 16)
            this.ageCategory = 1;
        else if( age > 16 && age <= 20)
            this.ageCategory = 2;
        else if( age > 20 && age <= 25)
            this.ageCategory = 3;
        else if( age > 25 && age <= 35)
            this.ageCategory = 4;
        else if( age > 35 && age <= 45)
            this.ageCategory = 5;
        else if( age > 45 && age <= 60)
            this.ageCategory = 6;
        else
            this.ageCategory = 7;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
