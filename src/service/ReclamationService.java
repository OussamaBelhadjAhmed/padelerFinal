/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entite.Reclamation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.DataSource;

/**
 *
 * @author Nouha
 */
public class ReclamationService implements Iservice<Reclamation> {

    private Connection connexion;
    private Statement ste;
    private PreparedStatement pst;
    private ResultSet rs;
    private List<Reclamation> ReclamationList;

    public ReclamationService() {
        connexion = DataSource.getInstance().getCnx();
        ReclamationList = new ArrayList<>();
    }

    public void refreshData() {
        String requete = "SELECT * FROM reclamation";
        List<Reclamation> list = new ArrayList<>();

        try {
            ste = connexion.createStatement();
            rs = ste.executeQuery(requete);

            while (rs.next()) {
                Reclamation reclamation = new Reclamation();
                reclamation.setId(rs.getInt(1));
                reclamation.setIdUser(rs.getInt(2));
                reclamation.setEmail(rs.getString(4));
                reclamation.setDate(rs.getDate("date"));
                reclamation.setDescription(rs.getString(7));
                reclamation.setStatut(rs.getString(6));
                reclamation.setReponse(rs.getString(8));
                reclamation.setCategorie(rs.getString(9));

                list.add(reclamation);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
        }

        ReclamationList = list;
    }

    @Override
    public void insert(Reclamation reclamation) {
        String query = "INSERT INTO reclamation (description,Categorie,email) VALUES (?,?,?)";

        try (PreparedStatement pst = connexion.prepareStatement(query)) {
            pst.setString(1, reclamation.getDescription());
            pst.setString(2, reclamation.getCategorie());
            pst.setString(3, reclamation.getEmail());

            pst.executeUpdate();
            System.out.println("Reclamation inserted successfully.");
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Reclamation> readAll() {
        String requete = "SELECT * FROM reclamation";
        List<Reclamation> list = new ArrayList<>();

        try {
            ste = connexion.createStatement();
            rs = ste.executeQuery(requete);

            while (rs.next()) {
                Reclamation reclamation = new Reclamation(
                        rs.getInt("id"),
                        rs.getInt("idUser"),
                        rs.getString("email"),
                        rs.getDate("date"),
                        rs.getString("description"),
                        rs.getString("statut"),
                        rs.getString("categorie"),
                        rs.getString("reponse")
                );

                list.add(reclamation);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
        }

        ReclamationList = list;
        return list;
    }

    @Override
    public Reclamation readById(int id) {
        String query = "SELECT * FROM reclamation WHERE idReclamation = ?";
        Reclamation reclamation = null;

        try (PreparedStatement pst = connexion.prepareStatement(query)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                reclamation = new Reclamation(
                        rs.getInt("id"),
                        rs.getInt("idUser"),
                        rs.getString("Email"),
                        rs.getDate("date"),
                        rs.getString("description"),
                        rs.getString("statut")
                );
                System.out.println("Reclamation found: " + reclamation);
            } else {
                System.out.println("Reclamation not found for id: " + id);
            }
        } catch (SQLException ex) {
            System.err.println("Error while getting reclamation by id: " + ex.getMessage());
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return reclamation;
    }

    public void delete(int id) {
        String query = "DELETE FROM reclamation WHERE id = ?";

        try (PreparedStatement pst = connexion.prepareStatement(query)) {
            pst.setInt(1, id);
            int affectedRows = pst.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Réclamation supprimée avec succès !");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Reclamation reclamation) {
        String query = "UPDATE reclamation SET Statut = ?, Reponse = ? WHERE id = ?";

        try (PreparedStatement pst = connexion.prepareStatement(query)) {
            pst.setString(1, reclamation.getStatut());
            pst.setString(2, reclamation.getReponse());
            pst.setInt(3, reclamation.getId());

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Reclamation updated successfully. Rows affected: " + rowsAffected);
            } else {
                System.out.println("No reclamation found for id: " + reclamation.getId());
            }
        } catch (SQLException ex) {
            System.err.println("Error while updating reclamation: " + ex.getMessage());
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    @Override
//    public void update(Reclamation reclamation) {
//        String query = " UPDATE reclamation SET Statut = ? WHERE id = ?";
//
//        try (PreparedStatement pst = connexion.prepareStatement(query)) {
//            pst.setString(1, reclamation.getStatut());
//            pst.setInt(2, reclamation.getId());
//
//            int rowsAffected = pst.executeUpdate();
//            if (rowsAffected > 0) {
//                System.out.println("Reclamation updated successfully. Rows affected: " + rowsAffected);
//            } else {
//                System.out.println("No reclamation found for id: " + reclamation.getId());
//            }
//        } catch (SQLException ex) {
//            System.err.println("Error while updating reclamation: " + ex.getMessage());
//            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    public void updateE(Reclamation r) {
//        String requete = "update Reclamation set etat = ? where id = ?";
//
//        try {
//            pst = connexion.prepareStatement(requete);
//            pst.setString(1, "répondu");
//            pst.setInt(2, r.getId());
//            pst.executeUpdate();
//        } catch (SQLException ex) {
//            Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    public void updateR(Reclamation r) {
//        String requete = "update Reclamation set reponse = ? where id = ?";
//
//        try {
//            pst = connexion.prepareStatement(requete);
//            pst.setString(1, r.getReponse());
//            pst.setInt(2, r.getId());
//            pst.executeUpdate();
//        } catch (SQLException ex) {
//            Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    public List<Reclamation> filterByCategory(String category) {
        List<Reclamation> filteredList = new ArrayList<>();
        String query = "SELECT * FROM reclamation WHERE Categorie = ?";

        try (PreparedStatement pst = connexion.prepareStatement(query)) {
            pst.setString(1, category);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Reclamation reclamation = new Reclamation();
                reclamation.setId(rs.getInt(1));
                reclamation.setIdUser(rs.getInt(2));
                reclamation.setEmail(rs.getString(4));
                reclamation.setDate(rs.getDate("date"));
                reclamation.setDescription(rs.getString(7));
                reclamation.setStatut(rs.getString(6));
                reclamation.setReponse(rs.getString(8));
                reclamation.setCategorie(rs.getString(9));

                filteredList.add(reclamation);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return filteredList;
    }
}
