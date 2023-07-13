package entite;

public class Commentaire {

    private int idComment;
    private String description;
    private User idUser;
    private Publication pub;

    public Commentaire() {
    }

    public Commentaire(int idComment, String description, User idUser, Publication pub) {
        this.idComment = idComment;
        this.description = description;
        this.idUser = idUser;
        this.pub = pub;
    }

    public Commentaire(String description, Publication pub) {
        this.description = description;
        this.pub = pub;
    }

    public Commentaire(int idComment, String description) {
        this.idComment = idComment;
        this.description = description;
    }

    public Commentaire(int idComment, String description, Publication pub) {
        this.idComment = idComment;
        this.description = description;
        this.pub = pub;
    }
    
    

    // Getters and setters

    @Override
    public String toString() {
        return "Commentaire{" +
                "idComment=" + idComment +
                ", description='" + description + '\'' +
                ", idUser=" + (idUser != null ? idUser.getIdUser() : null) +
                ", pub=" + (pub != null ? pub.getIdPublication() : null) +
                '}';
    }

    public int getIdComment() {
        return idComment;
    }

    public void setIdComment(int idComment) {
        this.idComment = idComment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getIdUser() {
        return idUser;
    }

    public void setIdUser(User idUser) {
        this.idUser = idUser;
    }

    public Publication getPub() {
        return pub;
    }

    public void setPub(Publication pub) {
        this.pub = pub;
    }
    
}
