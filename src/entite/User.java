package entite;

import static java.sql.Types.NULL;

public class User {
    private int idUser;
    private String firstName;
    private String lastName;

    private String email;

    private String password;

    private int numTel;

    private String role;

    private int idTeam;



    public User() {
    }

    public User(String firstName, String password) {
        this.firstName = firstName;
        this.password = password;
    }

    public User(String firstName, String lastName, String email, String password, int numTel) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.numTel = numTel;
        this.idTeam=NULL;
    }
    public User(int idUser,String firstName, String lastName, String email, String password, int numTel,int idTeam) {
        this.idUser=idUser;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.numTel = numTel;
        this.idTeam=idTeam;
    }

    public User(int idUser, String firstName, String lastName, String email, String password, int numTel, int idTeam, String role) {
        this.idUser=idUser;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.numTel = numTel;
        this.idTeam=idTeam;
        this.role=role;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getNumTel() {
        return numTel;
    }

    public void setNumTel(int numTel) {
        this.numTel = numTel;
    }



    public int getIdTeam() {
        return idTeam;
    }

    public void setIdTeam(int idTeam) {
        this.idTeam = idTeam;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
