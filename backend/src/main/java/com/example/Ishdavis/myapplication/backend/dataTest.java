package com.example.Ishdavis.myapplication.backend;
import javax.persistence.Entity;
import javax.persistence.Id;
/**
 * Created by Ishdavis on 12/26/2015.
 */
@Entity
public class dataTest {
    @Id
    String name;
    long id;
    int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
