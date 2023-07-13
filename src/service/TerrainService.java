/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entite.Club;
import entite.Terrain;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.DataSource;

/**
 *
 * @author oussama.hadjahmed
 */
public class TerrainService implements Iservice<Terrain> {

    private Connection connexion;
    private Statement ste;
    private PreparedStatement pst;
    private ResultSet rs;

    public TerrainService() {
        connexion = DataSource.getInstance().getCnx();
    }

    @Override
    public void insert(Terrain t) {
        //"','" + t.getStatus() + "','" + t.getClub().getIdClub() +
        String requete = "insert into Terrain (name,status,idClub) values('" + t.getName() + "','" + 1 + "','" + t.getClub().getIdClub() + "')";

        try {
            ste = connexion.createStatement();
            ste.executeUpdate(requete);
        } catch (SQLException ex) {
            Logger.getLogger(TerrainService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Terrain> readAll() {
        String requete = "select * from terrain";
        List<Terrain> list = new ArrayList<>();
        try {
            ste = connexion.createStatement();
            rs = ste.executeQuery(requete);
            while (rs.next()) {
                Club c = new Club();
                c.setIdClub(rs.getInt("idClub"));
                Terrain terrain = new Terrain(rs.getInt("idTerrain"), rs.getString("name"), rs.getInt("status"), c);
                list.add(terrain);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TerrainService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public Terrain readById(int id) {
        Terrain t = new Terrain();

        try {

            String requete = "SELECT * FROM terrain WHERE idTerrain=" + Integer.toString(id);
            pst = connexion.prepareStatement(requete);
            rs = pst.executeQuery(requete);

            while (rs.next()) {
                t.setIdTerrain(rs.getInt("idTerrain"));
                t.setName(rs.getString("name"));
                t.setStatus(rs.getInt("status"));
                Club c = new Club();
                c.setIdClub(rs.getInt("idClub"));
                t.setClub(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TerrainService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return t;
    }

    public List<Terrain> readByClubName(String clubName) {
        //Terrain t = new Terrain();
        List<Terrain> listTerrain = new ArrayList<>();

        try {

            String requete = "SELECT * FROM terrain t , club c WHERE t.idClub=c.idClub AND c.clubName= " + "\"" + clubName + "\"";
            pst = connexion.prepareStatement(requete);
            rs = pst.executeQuery(requete);

            while (rs.next()) {
                Terrain t = new Terrain();

                t.setIdTerrain(rs.getInt("idTerrain"));
                t.setName(rs.getString("name"));
                t.setStatus(rs.getInt("status"));
                Club c = new Club();
                c.setIdClub(rs.getInt("idClub"));
                c.setName(rs.getString("clubName"));
                t.setClub(c);
                listTerrain.add(t);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TerrainService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listTerrain;
    }

    @Override
    public void delete(int id) {
        try {
            String requete = "DELETE FROM terrain WHERE idTerrain = ?";
            pst = connexion.prepareStatement(requete);
            pst.setInt(1, id);
            int rowsDeleted = pst.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Le Terrain avec l'ID " + id + " a été supprimé avec succès.");
            } else {
                System.out.println("Aucun Terrain trouvé avec l'ID " + id + ". La suppression a échoué.");
            }

        } catch (SQLException ex) {
            Logger.getLogger(TerrainService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Terrain t) {
        String query = "UPDATE Terrain SET name=?, status=?, idClub=? WHERE idTerrain=?";

        try {
            pst = connexion.prepareStatement(query);
            pst.setString(1, t.getName());
            pst.setInt(2, t.getStatus());
            pst.setInt(3, t.getClub().getIdClub());
            pst.setInt(4, t.getIdTerrain());

            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Terrain updated successfully.");
            } else {
                System.out.println("Failed to update Terrain.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(TerrainService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Terrain> readAllWitClubName() {
        String requete = "select * from terrain t, club c where  t.idClub=c.idClub";
        List<Terrain> list = new ArrayList<>();
        try {
            ste = connexion.createStatement();
            rs = ste.executeQuery(requete);
            while (rs.next()) {
                Club c = new Club();
                c.setName(rs.getString("clubName"));
                Terrain terrain = new Terrain(rs.getInt("idTerrain"), rs.getString("name"), rs.getInt("status"), c);
                list.add(terrain);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TerrainService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public Terrain readByClubNameAndTerrainName(String clubName , String terrainName) {
        //Terrain t = new Terrain();
        Terrain listTerrain = new Terrain() ;

        try {

            String requete = "SELECT * FROM terrain t , club c WHERE t.idClub=c.idClub AND   t.name = "+"\"" + terrainName + "\""+"  AND  c.clubName= " + "\"" + clubName + "\"";
            pst = connexion.prepareStatement(requete);
            rs = pst.executeQuery(requete);

            while (rs.next()) {
                Terrain t = new Terrain();

                t.setIdTerrain(rs.getInt("idTerrain"));
                t.setName(rs.getString("name"));
                t.setStatus(rs.getInt("status"));
                Club c = new Club();
                c.setIdClub(rs.getInt("idClub"));
                c.setName(rs.getString("clubName"));
                t.setClub(c);
                listTerrain=t;
            }
        } catch (SQLException ex) {
            Logger.getLogger(TerrainService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listTerrain;
    }
}
