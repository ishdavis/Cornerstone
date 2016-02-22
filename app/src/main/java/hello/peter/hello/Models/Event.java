package hello.peter.hello.Models;

import java.util.ArrayList;

/**
 * Created by Ishdavis on 2/20/2016.
 */
public class Event {

    protected String Location, name;
    protected ArrayList<Person> members;
    protected int hour, minute;
    protected Person creator;

    public Event(){}

    public Event(String loc, String name, ArrayList<Person> mem, int h, int m, Person p){
        Location = loc;
        this.name = name;
        members = mem;
        hour = h;
        minute = m;
        creator = p;
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

    public ArrayList<Person> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<Person> members) {
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
