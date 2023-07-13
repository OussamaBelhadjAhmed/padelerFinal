/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entite;

import java.sql.Date;

/**
 *
 * @author oussama.hadjahmed
 */
public class Reservation {

    private int idReservation;
    private Date date;
    private String timeReservation ;
    private Club club ;
    private Terrain terrain ;
    private User user ;

    public int getIdReservation() {
        return idReservation;
    }

    public Date getDate() {
        return date;
    }

    public String getTimeReservation() {
        return timeReservation;
    }

    public Club getClub() {
        return club;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public User getUser() {
        return user;
    }

    public void setIdReservation(int idReservation) {
        this.idReservation = idReservation;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTimeReservation(String timeReservation) {
        this.timeReservation = timeReservation;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Reservation() {
    }

    public Reservation(int idReservation) {
        this.idReservation = idReservation;
    }

    public Reservation(int idReservation, Date date, String timeReservation, Club club, Terrain terrain, User user) {
        this.idReservation = idReservation;
        this.date = date;
        this.timeReservation = timeReservation;
        this.club = club;
        this.terrain = terrain;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Reservation{" + "idReservation=" + idReservation + ", date=" + date + ", timeReservation=" + timeReservation + ", club=" + club + ", terrain=" + terrain + ", user=" + user + '}';
    }
    
    
    
    
    
    
    
    

}
