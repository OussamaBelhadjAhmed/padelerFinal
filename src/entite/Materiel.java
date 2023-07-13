package entite;

public class Materiel {
    private int idMateriel;
    private int idClub;
    private int idUser;
    private boolean statut;

    private String image;
    private String name;

    public Materiel(int idMateriel, int idClub, int idUser, boolean statut, String name) {
        this.idMateriel = idMateriel;
        this.idClub = idClub;
        this.idUser = idUser;
        this.statut = statut;
        this.name = name;
    }

    public Materiel(String name, String image) {
        this.image = image;
        this.name = name;
    }

    public Materiel() {

    }

    public Materiel(int idMateriel, String image, String name, int idClub) {
        this.idMateriel = idMateriel;
        this.image = image;
        this.name = name;
        this.idClub = idClub;
    }



    public int getIdMateriel() {
        return idMateriel;
    }

    public void setIdMateriel(int idMateriel) {
        this.idMateriel = idMateriel;
    }

    public int getIdClub() {
        return idClub;
    }

    public void setIdClub(int idClub) {
        this.idClub = idClub;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public boolean isStatut() {
        return statut;
    }

    public void setStatut(boolean statut) {
        this.statut = statut;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Materiel{" +
                "idMateriel=" + idMateriel +
                ", idClub=" + idClub +
                ", idUser=" + idUser +
                ", statut=" + statut +
                ", image='" + image + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
