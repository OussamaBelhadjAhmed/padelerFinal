/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entite;

/**
 *
 * @author safdh
 */
public class Reaction {
      private int id;
    private int TypeReaction;
    private User u;
    private Publication p;

    public Reaction() {
    }

    public Reaction(int Id_Reaction, int Type_Reaction) {
        this.id = Id_Reaction;
        this.TypeReaction = Type_Reaction;
    }

    public Reaction(int Type_Reaction, User u, Publication p) {
        this.TypeReaction = Type_Reaction;
        this.u = u;
        this.p = p;
    }

    public Reaction(int Id_Reaction, int Type_Reaction, User u, Publication p) {
        this.id = Id_Reaction;
        this.TypeReaction = Type_Reaction;
        this.u = u;
        this.p = p;
    }

    public User getU() {
        return u;
    }

    public void setU(User u) {
        this.u = u;
    }

    public Publication getP() {
        return p;
    }

    public void setP(Publication p) {
        this.p = p;
    }
    
    public int getId_Reaction() {
        return id;
    }

    public void setId_Reaction(int Id_Reaction) {
        this.id = Id_Reaction;
    }

    public int getType_Reaction() {
        return TypeReaction;
    }

    public void setType_Reaction(int Type_Reaction) {
        this.TypeReaction = Type_Reaction;
    }

    @Override
    public String toString() {
        return "Reaction{" + "Id_Reaction=" + id + ", Type_Reaction=" + TypeReaction + ", Id_publication=" + p.getIdPublication() + ", Id_user=" + u.getIdUser() + '}'+"\n";
    }
    }
  
    

