/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entite;

import entite.Club;
import entite.Disponibiliteterrain;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author oussama.hadjahmed
 */
public class Terrain {

    private int idTerrain;
    private String name;
    private int status;
    private Club club;
    private List<Disponibiliteterrain> disponibiliteterrain = new ArrayList<Disponibiliteterrain>(0);

    public Terrain() {
    }

    public Terrain(int idTerrain, String name, int status, Club club) {
        this.idTerrain = idTerrain;
        this.name = name;
        this.status = status;
        this.club = club;
    }

    public int getIdTerrain() {
        return idTerrain;
    }

    public void setIdTerrain(int idTerrain) {
        this.idTerrain = idTerrain;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Terrain(int idTerrain, String name, int status) {
        this.idTerrain = idTerrain;
        this.name = name;
        this.status = status;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    @Override
    public String toString() {
        return "Terrain{" + "idTerrain=" + idTerrain + ", name=" + name + ", status=" + status + ", club=" + club.getIdClub() + '}';
    }

}
