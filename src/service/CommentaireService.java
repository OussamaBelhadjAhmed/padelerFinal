/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entite.Commentaire;
import entite.Publication;
import utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author safdh
 */
public class CommentaireService implements Iservice<Commentaire> {

    private Connection connexion;
    private Statement ste;
    private PreparedStatement pst;
    private ResultSet rs;

    public CommentaireService() {
        connexion = DataSource.getInstance().getCnx();
    }

 
    public List<Commentaire> readAll() {
        String requete = "select * from commentaire";
        List<Commentaire> list = new ArrayList<>();
        try {
            ste = connexion.createStatement();
            rs = ste.executeQuery(requete);
            while (rs.next()) {
                Commentaire c = new Commentaire(rs.getInt("idComment"), rs.getString("description"));
                list.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CommentaireService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public Commentaire readById(int id) {

        Commentaire commentaire = new Commentaire();
        try {
            String query = "SELECT * FROM Commentaire WHERE idComment = ?";
            pst = connexion.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            while (rs.next()) {
                commentaire = new Commentaire();
                commentaire.setIdComment(rs.getInt("idComment"));
                commentaire.setDescription(rs.getString("description"));
                            }
        } catch (SQLException ex) {
            Logger.getLogger(CommentaireService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return commentaire;

    }

    public void delete(int id) {
        String query = "DELETE FROM Commentaire WHERE idComment = ?";

        try {
            pst = connexion.prepareStatement(query);
            pst.setInt(1, id);
            int rowsDeleted = pst.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Comment deleted successfully.");
            } else {
                System.out.println("No comment found");

            }
        } catch (SQLException ex) {
            Logger.getLogger(CommentaireService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insert(Commentaire comment) {
    String query = "INSERT INTO Commentaire (description, idPub) VALUES (?, ?)";
        System.out.println(comment.getPub());
    try {
        pst = connexion.prepareStatement(query);
        pst.setString(1, comment.getDescription());
        pst.setInt(2, comment.getPub().getIdPublication());
        
        pst.executeUpdate();
        System.out.println("Comment inserted successfully.");
    } catch (SQLException ex) {
        Logger.getLogger(CommentaireService.class.getName()).log(Level.SEVERE, null, ex);
    }
}

    @Override
    public void update(Commentaire commentaire) {

        String requete = "UPDATE Commentaire SET `description` = '" + commentaire.getDescription() + "' WHERE idComment = " + commentaire.getIdComment();

        try {
            ste = connexion.createStatement();
            ste.executeUpdate(requete);
        } catch (SQLException ex) {
            Logger.getLogger(CommentaireService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}