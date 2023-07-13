package entite;

import java.sql.Date;


public class Reclamation {

    private int id;
    private int idUser;
    private int idClub;
    private String email;
    private Date date;
    private String reponse;
    private String statut;
    private String description;
    private String categorie;
   

    public Reclamation(int id, int idUser, int idClub, String email, Date date, String reponse, String statut, String description, String categorie) {
        this.id = id;
        this.idUser = idUser;
        this.idClub = idClub;
        this.email = email;
        this.date = date;
        this.reponse = reponse;
        this.statut = statut;
        this.description = description;
        this.categorie = categorie;
    }

    public Reclamation(int id, int idUser, String email, Date date, String description, String statut,String categorie, String reponse) {
        this.id = id;
        this.idUser = idUser;
        this.email = email;
        this.date = date;
        this.reponse = reponse;
        this.statut = statut;
        this.description = description;
        this.categorie = categorie;
    }


    public Reclamation(int idUser, int idClub, String reponse, String description) {
        this.idUser = idUser;
        this.idClub = idClub;
        this.reponse = reponse;
        this.description = description;
    }


    public Reclamation(int id, int idUser, int idClub, String email, Date date, String reponse, String statut) {
        this.id = id;
        this.idUser = idUser;
        this.idClub = idClub;
        this.email = email;
        this.date = date;
        this.reponse = reponse;
        this.statut = statut;
    }

    public Reclamation(String description) {
        this.description = description;
    }

    public Reclamation() {
    }

    public Reclamation(int id, int idUser, String email, Date date, String statut, String description) {
        this.id = id;
        this.idUser = idUser;
        this.email = email;
        this.date = date;
        this.statut = statut;
        this.description = description;
    }

    public Reclamation(String categorie, String description) {
        this.categorie = categorie;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdClub() {
        return idClub;
    }

    public void setIdClub(int idClub) {
        this.idClub = idClub;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

 
}