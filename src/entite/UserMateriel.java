package entite;

import java.sql.Date;

public class UserMateriel {
    private int idUserMateriel;
    private int idUser;
    private int idMateriel;
    private Date dateDebut;
    private Date dateFin;
    private int approve;

    public UserMateriel(int idUserMateriel, int idUser, int idMateriel, Date dateDebut, Date dateFin, int approve) {
        this.idUserMateriel = idUserMateriel;
        this.idUser = idUser;
        this.idMateriel = idMateriel;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.approve = approve;
    }

    public UserMateriel(int idUserMateriel, int idMateriel, Date dateDebut, Date dateFin) {
        this.idUserMateriel = idUserMateriel;
        this.idMateriel = idMateriel;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public UserMateriel(int idUser, int idMateriel, Date dateDebut, Date dateFin, int approve) {
        this.idUser = idUser;
        this.idMateriel = idMateriel;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.approve = approve;
    }

    public UserMateriel(Date dateDebut, Date dateFin) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public UserMateriel() {

    }

    public int getIdUserMateriel() {
        return idUserMateriel;
    }

    public void setIdUserMateriel(int idUserMateriel) {
        this.idUserMateriel = idUserMateriel;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdMateriel() {
        return idMateriel;
    }

    public void setIdMateriel(int idMateriel) {
        this.idMateriel = idMateriel;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public int getApprove() {
        return approve;
    }

    public void setApprove(int approve) {
        this.approve = approve;
    }


}
