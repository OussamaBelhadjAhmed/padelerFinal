package entite;

import java.util.List;

public class Team {
    private int idTeam;
    private String name;
    private List<User> users;

    public Team(String name) {
        this.name = name;
    }

    public Team() {
    }

    public Team(String name, int idTeam) {
        this.name = name;
        this.idTeam = idTeam;
    }

    public Team(int idTeam, String name, List<User> users) {
        this.idTeam = idTeam;
        this.name = name;
        this.users = users;
    }

    public int getIdTeam() {
        return idTeam;
    }

    public void setIdTeam(int idTeam) {
        this.idTeam = idTeam;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Team(int idTeam, String name) {
        this.idTeam = idTeam;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Team{" +
                "idTeam=" + idTeam +
                ", name='" + name + '\'' +
                ", users=" + users +
                '}';
    }
}
