package trandu.com.rxsample.dto;

import java.util.List;

public class Team {

    public List<Athlete> athletes;
    public String name;

    public Team() {
    }

    public Team(List<Athlete> athletes, String name) {
        this.athletes = athletes;
        this.name = name;
    }
}
