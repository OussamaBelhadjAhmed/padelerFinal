/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wiemhjiri
 */
public class DataSource {
    //port : 3306
    private String url="jdbc:mysql://localhost/padeler3";
    private String username="root";
    private String password="";
    private String baseUrl ="http://localhost/" ;


    private Connection cnx;
    
    private static DataSource instance;

    private DataSource() {
        try {
            cnx=DriverManager.getConnection(url, username, password);
            System.out.println("Connexion etablie");
        } catch (SQLException ex) {
            Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static DataSource getInstance(){
        if(instance==null)
            instance=new DataSource();
        return instance;
    }

    public Connection getCnx() {
        return cnx;
    }


    public String getBaseUrl() {
        return baseUrl;
    }





}
