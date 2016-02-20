package hello.peter.hello.Models;

/**
 * Created by Ishdavis on 2/8/2016.
 */
public class Person {

    protected String userName,password, phoneNumber, picURL;

    public Person(){}

    public Person(String name, String pass, String phone, String pic){
        userName = name;
        password = pass;
        phoneNumber = phone;
        picURL = pic;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPicURL() {
        return picURL;
    }

    /*public Person(String name, String pass, String phone, String bio, String pic){
        userName = name;
        password = pass;
        phoneNumber = phone;
        biography = bio;
        picURL = pic;
    }*/
}
