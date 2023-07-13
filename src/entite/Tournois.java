package entite;


import java.sql.Date;

public class Tournois {
    private int idTournois;
    private String type;
    private String name;
    private Date dateDebut;

    private Date dateFin;

    private int idClub;

    private int idUser;



    public Tournois(int idTournois, String type, String name, Date dateDebut, Date dateFin) {
        this.idTournois = idTournois;
        this.type = type;
        this.name = name;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public Tournois(int idTournois, String type, String name, Date dateDebut, Date dateFin, int idClub) {
        this.idTournois = idTournois;
        this.type = type;
        this.name = name;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.idClub = idClub;
    }

    public Tournois() {
    }

    public int getIdTournois() {
        return idTournois;
    }

    public void setIdTournois(int idTournois) {
        this.idTournois = idTournois;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "Tournois{" +
                "idTournois=" + idTournois +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                '}';
    }

    public int getIdClub() {
        return idClub;
    }

    public void setIdClub(int idClub) {
        this.idClub = idClub;
    }


}
