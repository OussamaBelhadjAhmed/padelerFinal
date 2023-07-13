/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entite;

import entite.Commentaire;

import java.sql.Date;
import java.util.ArrayList;

import java.util.List;

/**
 *
 * @author safdh
 */
public class Publication {

    private int idPublication;
    private String Description;
    private String image;
    private Date datePub;
    private int reaction;
    private List<Commentaire> LC;
//    private List<Commentaire> commentaire = new ArrayList<Commentaire>(0);
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Publication(int idPublication, String Description, String image, int reaction, User user) {
        this.idPublication = idPublication;
        this.Description = Description;
        this.image = image;
        this.reaction = reaction;
        this.user = user;
    }

    

    public Publication(String Description, Date datePub) {
        this.Description = Description;
        this.datePub = datePub;
    }

    public Publication(String description, String image) {
        Description = description;
        this.image = image;

    }

    public Publication(int idPublication, String Description, Date datePub, int reaction) {
        this.idPublication = idPublication;
        this.Description = Description;
        this.datePub = datePub;
        this.reaction = reaction;
    }
    public Publication(int idPublication, String Description, Date datePub, int reaction,String image) {
        this.idPublication = idPublication;
        this.Description = Description;
        this.datePub = datePub;
        this.reaction = reaction;
        this.image = image ;
    }


    public Publication() {
    }

    public Publication(String description) {
        this.Description = description;
    }

    public int getIdPublication() {
        return idPublication;
    }

    public void setIdPublication(int idPublication) {
        this.idPublication = idPublication;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public Date getDatePub() {
        return datePub;
    }

    public void setDatePub(Date datePub) {
        this.datePub = datePub;
    }

    public int getReaction() {
        return reaction;
    }

    public void setReaction(int reaction) {
        this.reaction = reaction;
    }

  

    

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Commentaire> getLC() {
        return LC;
    }

    public void setLC(List<Commentaire> LC) {
        this.LC = LC;
    }
    

    @Override
    public String toString() {
        return "Publication{" + "idPublication=" + idPublication + ", Description=" + Description + ", image=" + image + ", datePub=" + datePub + ", reaction=" + reaction + ", user=" + user.getFirstName()+ '}';
    }

  

    
}
