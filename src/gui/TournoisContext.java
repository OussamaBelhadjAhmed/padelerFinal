package gui;

import entite.Tournois;
import entite.User;

public class TournoisContext {
    private Tournois cuurentTournois;


    public Tournois getCuurentTournois() {
        return cuurentTournois;
    }


    public void setCuurentTournois(Tournois cuurentTournois) {
        this.cuurentTournois = cuurentTournois;
    }
}
