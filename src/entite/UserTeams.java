package entite;

import java.sql.Date;

public class UserTeams {
    private int idUserTeams;
    private int idUser;
    private int idTeam;
    private boolean status;
    private Date dateCreation;

    public UserTeams(int idUser, int idTeam, boolean status, Date dateCreation) {
        this.idUser = idUser;
        this.idTeam = idTeam;
        this.status = status;
        this.dateCreation = dateCreation;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdTeam() {
        return idTeam;
    }

    public void setIdTeam(int idTeam) {
        this.idTeam = idTeam;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    @Override
    public String toString() {
        return "UserTeams{" +
                "idUserTeams=" + idUserTeams +
                ", idUser=" + idUser +
                ", idTeam=" + idTeam +
                ", status=" + status +
                ", dateCreation=" + dateCreation +
                '}';
    }
}
