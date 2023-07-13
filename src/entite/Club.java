/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entite;

import entite.Terrain;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author oussama.hadjahmed
 */
public class Club {

    private int idClub;
    private String clubName;

    private String adresse;
    private List<Terrain> terrains = new ArrayList<Terrain>(0);
    private String image;

    public Club() {
    }

    public String getImage() {
        return image;
    }

    public Club(int idClub, String name, String adresse) {
        this.idClub = idClub;
        this.clubName = name;
        this.adresse = adresse;
    }

    public String getClubName() {
        return clubName;
    }

    public void setName(String name) {
        this.clubName = name;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getIdClub() {
        return idClub;
    }

    public void setIdClub(int idClub) {
        this.idClub = idClub;
    }

    public List<Terrain> getTerrains() {
        return terrains;
    }

    public void setTerrains(List<Terrain> terrains) {
        this.terrains = terrains;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public void setImage(String image) {
        this.image = image;
    }
//    @Override
//    public String toString() {
//        return "Club{" + "idClub=" + idClub + ", clubName=" + clubName + ", adresse=" + adresse + '}';
//    }

    @Override
    public String toString() {
        return clubName;
    }

}
