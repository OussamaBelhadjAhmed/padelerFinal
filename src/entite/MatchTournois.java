package entite;

import java.sql.Date;

public class MatchTournois {
    private int idMatchTournois;
    private int idTeam1;
    private int  idTeam2;
    private int idTournois;
    private Date dateMatch;

    private int  idTerrain;
    private int idTeamWinner;

    private String phase;

    public int getIdMatchTournois() {
        return idMatchTournois;
    }

    public void setIdMatchTournois(int idMatchTournois) {
        this.idMatchTournois = idMatchTournois;
    }

    public int getIdTeam1() {
        return idTeam1;
    }

    public void setIdTeam1(int idTeam1) {
        this.idTeam1 = idTeam1;
    }

    public int getIdTeam2() {
        return idTeam2;
    }

    public void setIdTeam2(int idTeam2) {
        this.idTeam2 = idTeam2;
    }

    public int getIdTournois() {
        return idTournois;
    }

    public void setIdTournois(int idTournois) {
        this.idTournois = idTournois;
    }

    public Date getDateMatch() {
        return dateMatch;
    }

    public void setDateMatch(Date dateMatch) {
        this.dateMatch = dateMatch;
    }

    public int getIdTerrain() {
        return idTerrain;
    }

    public void setIdTerrain(int idTerrain) {
        this.idTerrain = idTerrain;
    }

    public int getIdTeamWinner() {
        return idTeamWinner;
    }

    public void setIdTeamWinner(int idTeamWinner) {
        this.idTeamWinner = idTeamWinner;
    }




    public MatchTournois(int idMatchTournois, int idTeam1, int idTeam2, int idTournois, Date dateMatch, int idTerrain, int idTeamWinner, String phase) {
        this.idMatchTournois = idMatchTournois;
        this.idTeam1 = idTeam1;
        this.idTeam2 = idTeam2;
        this.idTournois = idTournois;
        this.dateMatch = dateMatch;
        this.idTerrain = idTerrain;
        this.idTeamWinner = idTeamWinner;
        this.phase = phase;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }
}
