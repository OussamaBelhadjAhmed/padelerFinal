/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entite.Publication;
import entite.Reaction;
import entite.User;
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
 * @author safdh
 */
public class ReactionService implements Iservice<Reaction>{
      Connection cnx = DataSource.getInstance().getCnx();
      private Connection connexion;
    private Statement ste;
    private PreparedStatement pst;
    private ResultSet rs;

    @Override
    public void insert(Reaction r) {
        PreparedStatement pt;
        
          try {
              Statement st = cnx.createStatement();
              st.executeUpdate("INSERT INTO `reaction`(`TypeReaction`, `idPublication`, `idUser`) VALUES ("+r.getType_Reaction()+","+r.getP().getIdPublication()+","+r.getU().getIdUser()+")");              
              System.err.println("Reaction Added Successfully");
    
          } catch (SQLException ex) {
              Logger.getLogger(ReactionService.class.getName()).log(Level.SEVERE, null, ex);
          }
    }

    @Override
    public List<Reaction> readAll() {
          String requete = "select * from reaction";
        List<Reaction> list = new ArrayList<>();
        try {
            ste = connexion.createStatement();
            rs = ste.executeQuery(requete);
            while (rs.next()) {
                Reaction r = new Reaction(rs.getInt("id"), rs.getInt("TypeReaction"));
                list.add(r);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReactionService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public Reaction readById(int id) {
           Reaction react = new Reaction();
        String query = "SELECT * FROM Reaction WHERE id = ?";
        try {

            pst = connexion.prepareStatement(query);
            pst.setInt(1, id);
            rs = pst.executeQuery();
            while (rs.next()) {
                react.setType_Reaction(rs.getInt("TypeReaction"));
                            }
        } catch (SQLException ex) {
            Logger.getLogger(ReactionService.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Id: " + react.getId_Reaction());
        return react;
        
    }

    @Override
    public void delete(int id) {
        PreparedStatement pt;
        try {
            pt = cnx.prepareStatement("delete from reaction where Id="+id);
            pt.executeUpdate();
            System.out.println("reaction Deleted Successfully");

        } catch (SQLException ex) {
            Logger.getLogger(ReactionService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    @Override
    public void update(Reaction r) {
        PreparedStatement pt;
        try {
            pt = cnx.prepareStatement("UPDATE reaction SET TypeReaction=? where Id = ?");
            pt.setInt(1, r.getType_Reaction());          
            pt.setInt(2, r.getId_Reaction());
            pt.executeUpdate();
            System.err.println("reaction Updated Successfully");
            
        } catch (SQLException ex) {
            Logger.getLogger(PublicationService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
