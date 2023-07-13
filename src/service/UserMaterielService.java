package service;

import entite.Materiel;
import entite.UserMateriel;
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

public class UserMaterielService implements Iservice<UserMateriel> {
    private Connection connexion;
    private Statement ste;
    private PreparedStatement pst;
    private ResultSet rs;

    public UserMaterielService() {
        connexion = DataSource.getInstance().getCnx();
    }

    @Override
    public void insert(UserMateriel userMateriel) {
        String query = "INSERT INTO userMateriel (idUser, idMateriel, dateDebut, dateFin) VALUES (?, ?, ?, ?)";
        try {
            pst = connexion.prepareStatement(query);
            pst.setInt(1, userMateriel.getIdUser());
            pst.setInt(2, userMateriel.getIdMateriel());
            pst.setDate(3, userMateriel.getDateDebut());
            pst.setDate(4, userMateriel.getDateFin());
            pst.executeUpdate();
            System.out.println("UserMateriel inserted successfully");
        } catch (SQLException ex) {
            Logger.getLogger(UserMaterielService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<UserMateriel> readAll() {
        String query = "SELECT * FROM userMateriel";
        List<UserMateriel> list = new ArrayList<>();
        try {
            ste = connexion.createStatement();
            rs = ste.executeQuery(query);
            while (rs.next()) {
                UserMateriel userMateriel = new UserMateriel(
                        rs.getInt("idUserMateriel"),
                        rs.getInt("idUser"),
                        rs.getInt("idMateriel"),
                        rs.getDate("dateDebut"),
                        rs.getDate("dateFin"),
                        rs.getInt("approve"));
                list.add(userMateriel);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserMaterielService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public UserMateriel readById(int id) {
        String query = "SELECT * FROM userMateriel WHERE idUserMateriel = ?";
        UserMateriel userMateriel = null;
        try {
            pst = connexion.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            if (rs.next()) {
                userMateriel = new UserMateriel(
                        rs.getInt("idUser"),
                        rs.getInt("idMateriel"),
                        rs.getDate("dateDebut"),
                        rs.getDate("dateFin"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserMaterielService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return userMateriel;
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM userMateriel WHERE idUserMateriel = ?";
        try {
            pst = connexion.prepareStatement(query);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("UserMateriel deleted successfully");
        } catch (SQLException ex) {
            Logger.getLogger(UserMaterielService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(UserMateriel userMateriel) {
        String query = "UPDATE userMateriel SET idUser = ?, idMateriel = ?, dateDebut = ?, dateFin = ? WHERE idUserMateriel = ?";
        try {
            pst = connexion.prepareStatement(query);
            pst.setInt(1, userMateriel.getIdUser());
            pst.setInt(2, userMateriel.getIdMateriel());
            pst.setDate(3, userMateriel.getDateDebut());
            pst.setDate(4, userMateriel.getDateFin());
            pst.setInt(5, userMateriel.getIdUserMateriel());
            pst.executeUpdate();
            System.out.println("UserMateriel updated successfully");
        } catch (SQLException ex) {
            Logger.getLogger(UserMaterielService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void approve(int id) {
        String query = "UPDATE userMateriel SET approve = 1 WHERE idUserMateriel = ?";
        try {
            pst = connexion.prepareStatement(query);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("UserMateriel approved successfully");
        } catch (SQLException ex) {
            Logger.getLogger(UserMaterielService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void disapprove(int id) {
        String query = "UPDATE userMateriel SET approve = 2 WHERE idUserMateriel = ?";
        try {
            pst = connexion.prepareStatement(query);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("UserMateriel disapproved successfully");
        } catch (SQLException ex) {
            Logger.getLogger(UserMaterielService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}