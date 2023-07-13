/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entite;

import entite.Terrain;

import java.sql.Date;

/**
 *
 * @author oussama.hadjahmed
 */
public class Disponibiliteterrain {

    private int idDisponibiliteTerrain;
    private Date date;
    private int temps1;
    private int temps2;
    private int temps3;
    private int temps4;
    private int temps5;
    private int temps6;
    private int temps7;
    private int temps8;
    private int temps9;
    private int temps10;
    private int temps11;
    private int temps12;
    private int temps13;
    private int temps14;
    private Terrain terrain;
    private User user ;

    public Disponibiliteterrain() {
    }

    public Disponibiliteterrain(int idDisponibiliteTerrain, Date date, int temps1, int temps2, int temps3, int temps4, int temps5, int temps6, int temps7, int temps8, int temps9, int temps10, int temps11, int temps12, int temps13, int temps14, Terrain terrain) {
        this.idDisponibiliteTerrain = idDisponibiliteTerrain;
        this.date = date;
        this.temps1 = temps1;
        this.temps2 = temps2;
        this.temps3 = temps3;
        this.temps4 = temps4;
        this.temps5 = temps5;
        this.temps6 = temps6;
        this.temps7 = temps7;
        this.temps8 = temps8;
        this.temps9 = temps9;
        this.temps10 = temps10;
        this.temps11 = temps11;
        this.temps12 = temps12;
        this.temps13 = temps13;
        this.temps14 = temps14;
        this.terrain = terrain;
    }

    public Disponibiliteterrain(Date date, int temps1, int temps2, int temps3, int temps4, int temps5, int temps6, int temps7, int temps8, int temps9, int temps10, int temps11, int temps12, int temps13, int temps14, Terrain terrain) {
        this.date = date;
        this.temps1 = temps1;
        this.temps2 = temps2;
        this.temps3 = temps3;
        this.temps4 = temps4;
        this.temps5 = temps5;
        this.temps6 = temps6;
        this.temps7 = temps7;
        this.temps8 = temps8;
        this.temps9 = temps9;
        this.temps10 = temps10;
        this.temps11 = temps11;
        this.temps12 = temps12;
        this.temps13 = temps13;
        this.temps14 = temps14;
        this.terrain = terrain;
    }

    public int getIdDisponibiliteTerrain() {
        return idDisponibiliteTerrain;
    }

    public void setIdDisponibiliteTerrain(int idDisponibiliteTerrain) {
        this.idDisponibiliteTerrain = idDisponibiliteTerrain;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTemps1() {
        return temps1;
    }

    public void setTemps1(int temps1) {
        this.temps1 = temps1;
    }

    public int getTemps2() {
        return temps2;
    }

    public void setTemps2(int temps2) {
        this.temps2 = temps2;
    }

    public int getTemps3() {
        return temps3;
    }

    public void setTemps3(int temps3) {
        this.temps3 = temps3;
    }

    public int getTemps4() {
        return temps4;
    }

    public void setTemps4(int temps4) {
        this.temps4 = temps4;
    }

    public int getTemps5() {
        return temps5;
    }

    public void setTemps5(int temps5) {
        this.temps5 = temps5;
    }

    public int getTemps6() {
        return temps6;
    }

    public void setTemps6(int temps6) {
        this.temps6 = temps6;
    }

    public int getTemps7() {
        return temps7;
    }

    public void setTemps7(int temps7) {
        this.temps7 = temps7;
    }

    public int getTemps8() {
        return temps8;
    }

    public void setTemps8(int temps8) {
        this.temps8 = temps8;
    }

    public int getTemps9() {
        return temps9;
    }

    public void setTemps9(int temps9) {
        this.temps9 = temps9;
    }

    public int getTemps10() {
        return temps10;
    }

    public void setTemps10(int temps10) {
        this.temps10 = temps10;
    }

    public int getTemps11() {
        return temps11;
    }

    public void setTemps11(int temps11) {
        this.temps11 = temps11;
    }

    public int getTemps12() {
        return temps12;
    }

    public void setTemps12(int temps12) {
        this.temps12 = temps12;
    }

    public int getTemps13() {
        return temps13;
    }

    public void setTemps13(int temps13) {
        this.temps13 = temps13;
    }

    public int getTemps14() {
        return temps14;
    }

    public void setTemps14(int temps14) {
        this.temps14 = temps14;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }

}
