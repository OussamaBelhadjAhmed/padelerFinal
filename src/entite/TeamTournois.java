package entite;

import java.sql.Date;

public class TeamTournois {
    private int idTeamTournois;
    private int idTeam;
    private int idTournois;
    private Date datePaticipe;
    private boolean approve;


    public int getIdTeamTournois() {
        return idTeamTournois;
    }

    public void setIdTeamTournois(int idTeamTournois) {
        this.idTeamTournois = idTeamTournois;
    }

    public int getIdTeam() {
        return idTeam;
    }

    public void setIdTeam(int idTeam) {
        this.idTeam = idTeam;
    }

    public int getIdTournois() {
        return idTournois;
    }

    public void setIdTournois(int idTournois) {
        this.idTournois = idTournois;
    }

    public Date getDatePaticipe() {
        return datePaticipe;
    }

    public void setDatePaticipe(Date datePaticipe) {
        this.datePaticipe = datePaticipe;
    }

    public boolean isApprove() {
        return approve;
    }

    public void setApprove(boolean approve) {
        this.approve = approve;
    }

    public TeamTournois(int idTeamTournois, int idTeam, int idTournois, Date datePaticipe, boolean approve) {
        this.idTeamTournois = idTeamTournois;
        this.idTeam = idTeam;
        this.idTournois = idTournois;
        this.datePaticipe = datePaticipe;
        this.approve = approve;
    }

    @Override
    public String toString() {
        return "TeamTournois{" +
                "idTeamTournois=" + idTeamTournois +
                ", idTeam=" + idTeam +
                ", idTournois=" + idTournois +
                ", datePaticipe=" + datePaticipe +
                ", approve=" + approve +
                '}';
    }
}
