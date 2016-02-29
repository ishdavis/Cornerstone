package hello.peter.hello.Models;

import android.webkit.JavascriptInterface;

import com.firebase.client.Firebase;
import com.shaded.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

/**
 * Created by Ishdavis on 2/8/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Person {

    protected String userName,password, phoneNumber, picURL;

    /*public Map<String, Object> getInterests() {
        return interests;
    }

    public void setInterests(Map<String, Object> interests) {
        this.interests = interests;
    }*/

    //protected Map<String,Object> interests;

    public Person(){}

    public Person(String name, String pass, String phone, String pic){
        userName = name;
        password = pass;
        phoneNumber = phone;
        picURL = pic;
    }

    public Person(String name, String pass, String phone, String pic, Map<String,Object> interests){
        userName = name;
        password = pass;
        phoneNumber = phone;
        picURL = pic;
        //this.interests = interests;
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
