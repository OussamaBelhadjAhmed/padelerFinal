/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.sql.*;
import java.util.ArrayList;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import entite.Team;
import entite.Tournois;
import entite.User;
import utils.DataSource;

/**
 *
 * @author wiemhjiri
 */
public class UserService implements Iservice<User>{

    private Connection connexion;
    private Statement ste;
    private PreparedStatement pst;
    private ResultSet rs;

    public UserService() {
        connexion = DataSource.getInstance().getCnx();

    }

    @Override
    public void insert(User user) {
        String requete = "insert into user (firstName,lastName,email,password,numTel) values('" +
                user.getFirstName() + "','" +
                user.getLastName() + "','" +
                user.getEmail() + "','" +
                user.getPassword() + "','" +
                user.getNumTel() + "')";

        try {
            ste = connexion.createStatement();
            ste.executeUpdate(requete);
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void insertPST(User user) {
        String query = "INSERT INTO user (firstName, lastName, email, password, numTel) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pst = connexion.prepareStatement(query)) {
            pst.setString(1, user.getFirstName());
            pst.setString(2, user.getLastName());
            pst.setString(3, user.getEmail());
            pst.setString(4, user.getPassword());
            pst.setInt(5, user.getNumTel());

            pst.executeUpdate();
            System.out.println("User inserted successfully.");
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @Override
    public List<User> readAll(){
        String requete="select * from user";
        List<User> list=new ArrayList<>();
        try {
            ste=connexion.createStatement();
            rs=ste.executeQuery(requete);
            while(rs.next()){
                User user=new User(
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getInt("numTel")
                );
                list.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public User readById(int id) {
        String query = "SELECT * FROM user WHERE idUser = ?";

        try {
            pst = connexion.prepareStatement(query);
            pst.setInt(1,id);

            rs = pst.executeQuery();

            if (rs.next()) {
                int idUser = rs.getInt("idUser");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String email = rs.getString("email");
                String password = rs.getString("password");
                int numTel = rs.getInt("numTel");
                int idTeam = rs.getInt("idTeam");

                return new User(idUser,firstName,lastName,email,password,numTel,idTeam);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
    public User readByEmail(String x) {
        String query = "SELECT * FROM user WHERE email = ?";

        try {
            pst = connexion.prepareStatement(query);
            pst.setString(1,x);

            rs = pst.executeQuery();

            if (rs.next()) {
                int idUser = rs.getInt("idUser");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String email = rs.getString("email");
                String password = rs.getString("password");
                int numTel = rs.getInt("numTel");
                int idTeam = rs.getInt("idTeam");

                return new User(idUser,firstName,lastName,email,password,numTel,idTeam);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(User user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void ajouteUserToTeam(User user, int newIdTeam) {
        String insertUserTeam = "INSERT INTO userTeams (idUser,idTeam,status,dateCreation) VALUES(?,?,?,?)";

        try {
            pst = connexion.prepareStatement(insertUserTeam);
            pst.setInt(1, newIdTeam);
            pst.setInt(2, user.getIdUser());

            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("User's team ID updated successfully.");
            } else {
                System.out.println("Failed to update user's team ID.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public User login(User user) {
        String query = "SELECT * FROM user WHERE firstName = ? and password = ?";

        try {
            pst = connexion.prepareStatement(query);
            pst.setString(1,user.getFirstName());
            pst.setString(2,user.getPassword());

            rs = pst.executeQuery();

            if (rs.next()) {
                int idUser = rs.getInt("idUser");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String email = rs.getString("email");
                String password = rs.getString("password");
                int numTel = rs.getInt("numTel");
                int idTeam = rs.getInt("idTeam");
                String role = rs.getString("role");

                return new User(idUser,firstName,lastName,email,password,numTel,idTeam,role);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
    public User signup(User user) {
        String query = "INSERT INTO user (firstName, lastName, email, password, numTel) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pst = connexion.prepareStatement(query)) {
            pst.setString(1, user.getFirstName());
            pst.setString(2, user.getLastName());
            pst.setString(3, user.getEmail());
            pst.setString(4, user.getPassword());
            pst.setInt(5, user.getNumTel());

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User inserted successfully.");
                return user;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null; // Return null if the user couldn't be inserted
    }

    public boolean checkEmail(String email){
        String query = "SELECT * FROM user WHERE email = ?";
        try {
            pst = connexion.prepareStatement(query);
            pst.setString(1,email);

            rs = pst.executeQuery();

            return rs.next();

        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }





}
