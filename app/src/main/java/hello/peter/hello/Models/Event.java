package hello.peter.hello.Models;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * Created by Ishdavis on 2/20/2016.
 */
public class Event {

    protected String Location;
    protected String name;

    public String getSummary() {
        return Summary;
    }

    public void setSummary(String summary) {
        Summary = summary;
    }

    public Person getCreator() {
        return creator;
    }

    public void setCreator(Person creator) {
        this.creator = creator;
    }

    protected String Summary;
    protected HashMap<String,Person> members;
    protected int hour, minute;
    protected Person creator;

    public Event(){}

    public Event(String loc, String name, HashMap<String,Person> mem, int h, int m, Person p, String sum){
        Location = loc;
        this.name = name;
        members = mem;
        hour = h;
        minute = m;
        creator = p;
        Summary = sum;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String,Person> getMembers() {
        return members;
    }

    public void setMembers(HashMap<String,Person> members) {
        this.members = members;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
