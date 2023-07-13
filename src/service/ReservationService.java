/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entite.Reservation;
import entite.Club;
import entite.User;
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
public class ReservationService {

    private Connection connexion;
    private Statement ste;
    private PreparedStatement pst;
    private ResultSet rs;

    public ReservationService() {
        connexion = DataSource.getInstance().getCnx();

    }

    public List<Reservation> readAll() {
        String requete = "select * from reservation_terrain rt, terrain t ,club c where rt.idClub = c.idClub and rt.idTerrain = t.idTerrain and rt.idClub = t.idClub ";
        List<Reservation> list = new ArrayList<>();
        try {
            ste = connexion.createStatement();
            rs = ste.executeQuery(requete);
            while (rs.next()) {
                Reservation r = new Reservation();
                r.setIdReservation(rs.getInt("idReservation"));
                Club c = new Club();
                c.setIdClub(rs.getInt("idClub"));
                c.setClubName(rs.getString("clubName"));
                r.setDate(rs.getDate("date"));
                r.setTimeReservation(rs.getString("timeReservation"));
               /* User u = new User();
                u.setIdUser(rs.getInt("idUser"));
                */
                Terrain t = new Terrain();
                t.setIdTerrain(rs.getInt("idTerrain"));
                t.setName(rs.getString("name"));

                r.setClub(c);
                r.setTerrain(t);
                //r.setUser(u);

                list.add(r);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClubService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public void insert(Reservation r) {
        String requete = "insert into reservation_terrain (timeReservation,date,idClub,idTerrain,idUser) values('" + r.getTimeReservation() + "','" + r.getDate() + "','" + r.getClub().getIdClub() + "','" + r.getTerrain().getIdTerrain() + "','" + r.getUser().getIdUser() + "')";

        try {
            ste = connexion.createStatement();
            ste.executeUpdate(requete);
        } catch (SQLException ex) {
            Logger.getLogger(ReservationService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //  ------------------------------------------------------------------------------------------------------------
    public void inserts(Reservation r) {
        StringBuilder queryBuilder = new StringBuilder("INSERT INTO reservation_terrain (");
        StringBuilder valuesBuilder = new StringBuilder("VALUES (");
        List<Object> parameterValues = new ArrayList<>();

        // Vérifier les attributs et construire la requête dynamiquement
        if (r.getTimeReservation() != null) {
            queryBuilder.append("timeReservation, ");
            valuesBuilder.append("?, ");
            parameterValues.add(r.getTimeReservation());
        }
        if (r.getDate() != null) {
            queryBuilder.append("date, ");
            valuesBuilder.append("?, ");
            parameterValues.add(r.getDate());
        }
        if (r.getClub() != null) {
            queryBuilder.append("idClub, ");
            valuesBuilder.append("?, ");
            parameterValues.add(r.getClub().getIdClub());
        }
        if (r.getTerrain() != null) {
            queryBuilder.append("idTerrain, ");
            valuesBuilder.append("?, ");
            parameterValues.add(r.getTerrain().getIdTerrain());
        }
        if (r.getUser() != null) {
            queryBuilder.append("idUser, ");
            valuesBuilder.append("?, ");
            parameterValues.add(r.getUser().getIdUser());
        }

        // Supprimer la virgule finale des chaînes de construction de requête
        queryBuilder.deleteCharAt(queryBuilder.length() - 2);
        valuesBuilder.deleteCharAt(valuesBuilder.length() - 2);

        // Terminer la construction de la requête
        queryBuilder.append(") ");
        valuesBuilder.append(")");

        String query = queryBuilder.toString() + valuesBuilder.toString();

        try (PreparedStatement statement = connexion.prepareStatement(query)) {
            // Définir les valeurs des paramètres de la requête
            for (int i = 0; i < parameterValues.size(); i++) {
                statement.setObject(i + 1, parameterValues.get(i));
            }

            // Exécuter la requête
            statement.executeUpdate();

            System.out.println("Insertion réussie !");
        } catch (SQLException ex) {
            Logger.getLogger(ReservationService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //-------------------------------------------------------------------------------------------------------------------
    public List<Reservation> readAllJoinTerrainAndClub(int idUser) {
        String requete = "";
        if (idUser != 0 && idUser != 99999) {
            requete += "select * from reservation_terrain rt, terrain t, club c, user u where rt.idClub=c.idClub and rt.idTerrain = t.idTerrain AND rt.idUser = u.idUser AND rt.idUser = " + idUser;
        } else {
            requete += "select * from reservation_terrain rt, terrain t, club c, user u where rt.idClub=c.idClub and rt.idTerrain = t.idTerrain ";

        }

        List<Reservation> list = new ArrayList<>();
        try {
            ste = connexion.createStatement();
            rs = ste.executeQuery(requete);
            while (rs.next()) {
                Reservation r = new Reservation();
                r.setIdReservation(rs.getInt("idReservation"));
                Club c = new Club();
                c.setIdClub(rs.getInt("idClub"));
                c.setClubName(rs.getString("clubName"));
                r.setDate(rs.getDate("date"));
                r.setTimeReservation(rs.getString("timeReservation"));
                User u = new User();
                u.setIdUser(rs.getInt("idUser"));
                Terrain t = new Terrain();
                t.setIdTerrain(rs.getInt("idTerrain"));
                t.setName(rs.getString("name"));
                r.setClub(c);
                r.setTerrain(t);
                r.setUser(u);
                list.add(r);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClubService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    
    
    public List<Reservation> readAllByUser(boolean userExists , int idUser) {
    String requete = "SELECT * FROM reservation_terrain rt, terrain t, club c WHERE rt.idClub = c.idClub AND rt.idTerrain = t.idTerrain AND rt.idClub = t.idClub";

    // Modify the query to filter reservations with a non-null user if userExists is true
    if (userExists) {
        requete += " AND rt.idUser="+idUser;
    }

    List<Reservation> list = new ArrayList<>();
    try {
        ste = connexion.createStatement();
        rs = ste.executeQuery(requete);
        while (rs.next()) {
            Reservation r = new Reservation();
            r.setIdReservation(rs.getInt("idReservation"));
            Club c = new Club();
            c.setIdClub(rs.getInt("idClub"));
            c.setClubName(rs.getString("clubName"));
            r.setDate(rs.getDate("date"));
            r.setTimeReservation(rs.getString("timeReservation"));
            /* User u = new User();
            u.setIdUser(rs.getInt("idUser"));
            */
            Terrain t = new Terrain();
            t.setIdTerrain(rs.getInt("idTerrain"));
            t.setName(rs.getString("name"));

            r.setClub(c);
            r.setTerrain(t);
            //r.setUser(u);

            list.add(r);
        }
    } catch (SQLException ex) {
        Logger.getLogger(ClubService.class.getName()).log(Level.SEVERE, null, ex);
    }
    return list;
}


}
