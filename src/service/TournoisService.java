package service;


import entite.*;
import utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TournoisService implements Iservice<Tournois>{
    private Connection connexion;
    private Statement ste;
    private PreparedStatement pst;
    private ResultSet rs;

    public TournoisService() {
        connexion = DataSource.getInstance().getCnx();

    }

    public List<MatchTournois> getSecondPhaseMatchesForCurrentTournament(int tournamentId) {
        String query = "SELECT * FROM matchTournois WHERE idTournois = ? AND phase = ?";
        List<MatchTournois> matches = new ArrayList<>();

        try {
            pst = connexion.prepareStatement(query);
            pst.setInt(1, tournamentId);
            pst.setString(2, "second_phase");

            rs = pst.executeQuery();

            while (rs.next()) {
                int idMatchTournois = rs.getInt("idMatchTournois");
                int idTeam1 = rs.getInt("idTeam1");
                int idTeam2 = rs.getInt("idTeam2");
                int idTournois = rs.getInt("idTournois");
                Date dateMatch = rs.getDate("dateMatch");
                int idTerrain = rs.getInt("idTerrain");
                int idTeamWinner = rs.getInt("idTeamWinner");
                String phase = rs.getString("phase");
                MatchTournois matchTournois = new MatchTournois(idMatchTournois, idTeam1, idTeam2, idTournois, dateMatch, idTerrain, idTeamWinner, phase);

                matches.add(matchTournois);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return matches;
    }

    @Override
    public void insert(Tournois tournois) {
        String query = "INSERT INTO tournois (type, name, dateDebut , dateFin,idClub) VALUES (?, ?,?,?,?)";

        try (PreparedStatement pst = connexion.prepareStatement(query)) {

            pst.setString(1, tournois.getType());
            pst.setString(2, tournois.getName());
            pst.setDate(3, tournois.getDateDebut());
            pst.setDate(4, tournois.getDateFin());
            pst.setInt(5, tournois.getIdClub());

            pst.executeUpdate();
            System.out.println("tournois inserted successfully.");
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Tournois> readAll() {
        String requete="select * from tournois";
        List<Tournois> list=new ArrayList<>();
        try {
            ste=connexion.createStatement();
            rs=ste.executeQuery(requete);
            while(rs.next()){
                Tournois tournois=new Tournois(
                        rs.getInt("idTournois"),
                        rs.getString("type"),
                        rs.getString("name"),
                        rs.getDate("dateDebut"),
                        rs.getDate("dateFin"),
                        rs.getInt("idClub")
                );
                list.add(tournois);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public Tournois readById(int id) {
        String query = "SELECT * FROM tournois WHERE idTournois = ?";

        try {
            pst = connexion.prepareStatement(query);
            pst.setInt(1, id);

            rs = pst.executeQuery();

            if (rs.next()) {
                int idTournois = rs.getInt("idTournois");
                String type = rs.getString("type");
                String name = rs.getString("name");
                Date dateDebut = rs.getDate("dateDebut");
                Date dateFin = rs.getDate("dateFin");

                return new Tournois(idTournois,type,name,dateDebut,dateFin);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM tournois WHERE idTournois = ?";

        try {
            pst = connexion.prepareStatement(query);
            pst.setInt(1, id);

            pst.executeUpdate();
            System.out.println("Tournois deleted successfully.");
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Tournois tournois) {
        String query = "UPDATE tournois SET name = ?, type = ?, dateDebut = ?, dateFin = ?, idClub = ? WHERE idTournois = ?";

        try {
            pst = connexion.prepareStatement(query);
            pst.setString(1, tournois.getName());
            pst.setString(2, tournois.getType());
            pst.setDate(3, tournois.getDateDebut());
            pst.setDate(4, tournois.getDateFin());
            pst.setInt(5, tournois.getIdClub());
            pst.setInt(6, tournois.getIdTournois());

            pst.executeUpdate();
            System.out.println("Tournament updated successfully.");
        } catch (SQLException ex) {
            Logger.getLogger(TournoisService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public void registreTournois(Team team,int user1,int user2){
        String query = "INSERT INTO Team (named) VALUES (?)";
        UserService userService = new UserService();


        try (PreparedStatement pst = connexion.prepareStatement(query)) {
            /*List<User> teamList = new ArrayList<>();
            User u1 = userService.readById(user1);
            User u2 = userService.readById(user2)
            teamList.add(u1);
            teamList.add(u2);
            userService.ajouteUserToTeam(u1,);
            team.setTeams(teamList);*/
            pst.setString(1, team.getName());


            pst.executeUpdate();
            System.out.println("team inserted successfully.");
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public List<Tournois> getTournamentsByClubAndDateRange(int clubId, Date startDate, Date endDate) {
        String query = "SELECT * FROM Tournois WHERE idClub = ? AND dateDebut <= ? AND dateFin >= ?";
        List<Tournois> tournamentList = new ArrayList<>();

        try (PreparedStatement statement = connexion.prepareStatement(query)) {
            statement.setInt(1, clubId);
            statement.setDate(2, startDate);
            statement.setDate(3, endDate);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Tournois tournament = new Tournois();
                    tournament.setIdTournois(resultSet.getInt("idTournois"));
                    tournament.setName(resultSet.getString("name"));
                    tournament.setType(resultSet.getString("type"));
                    tournament.setDateDebut(resultSet.getDate("dateDebut"));
                    tournament.setDateFin(resultSet.getDate("dateFin"));
                    tournament.setIdClub(resultSet.getInt("idClub"));
                    tournamentList.add(tournament);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return tournamentList;
    }

    public int countParticipant(){
        return 1;
    }
    public List<TeamTournois> getListTeamParticipant(int id) {
        String query = "SELECT * FROM teamTournois WHERE idTournois = ?";
        List<TeamTournois> list = new ArrayList<>();

        try {
            pst = connexion.prepareStatement(query);
            pst.setInt(1, id);

            rs = pst.executeQuery();

            while (rs.next()) {
                int idTeamTournois = rs.getInt("idTeamTournois");
                int idTeam = rs.getInt("idTeam");
                int idTournois = rs.getInt("idTournois");
                Date datePaticipe = rs.getDate("datePaticipe");
                Boolean approve = rs.getBoolean("approve");

                TeamTournois teamTournois = new TeamTournois(idTeamTournois, idTeam, idTournois, datePaticipe, approve);
                list.add(teamTournois);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }
    public int countParticipent(int id) {
        String query = "SELECT * FROM teamTournois WHERE idTournois = ?";
        int i=0;
        try {
            pst = connexion.prepareStatement(query);
            pst.setInt(1, id);

            rs = pst.executeQuery();

            while (rs.next()) {
                i++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return i;
    }

    public List<MatchTournois> readAllMatches() {
        String requete="select * from matchTournois";
        List<MatchTournois> list=new ArrayList<>();
        try {
            ste=connexion.createStatement();
            rs=ste.executeQuery(requete);
            while(rs.next()){
                MatchTournois tournois=new MatchTournois(
                        rs.getInt("idMatchTournois"),
                        rs.getInt("idTeam1"),
                        rs.getInt("idTeam2"),
                        rs.getInt("idTournois"),
                        rs.getDate("dateMatch"),
                        rs.getInt("idTerrain"),
                        rs.getInt("idTeamWinner"),
                        rs.getString("phase")

                );
                list.add(tournois);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<MatchTournois> findTournoisDetailsById(int id) {
        List<MatchTournois> matchTournoisList = new ArrayList<>();
        String query = "SELECT * FROM matchTournois WHERE idTournois = ?";

        try {
            pst = connexion.prepareStatement(query);
            pst.setInt(1, id);

            rs = pst.executeQuery();

            while (rs.next()) {
                int idMatchTournois = rs.getInt("idMatchTournois");
                int idTeam1 = rs.getInt("idTeam1");
                int idTeam2 = rs.getInt("idTeam2");
                int idTournois = rs.getInt("idTournois");
                Date dateMatch = rs.getDate("dateMatch");
                int idTerrain = rs.getInt("idTerrain");
                Integer idTeamWinner = rs.getInt("idTeamWinner"); // Use Integer instead of int
                String phase = rs.getString("phase");

                MatchTournois matchTournois = new MatchTournois(idMatchTournois, idTeam1, idTeam2, idTournois, dateMatch, idTerrain, idTeamWinner, phase);
                matchTournoisList.add(matchTournois);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return matchTournoisList;
    }

    public List<MatchTournois> getFirstPhaseMatchesForCurrentTournament(int tournamentId) {

            String query = "SELECT * FROM matchTournois WHERE idTournois = ? AND phase = ?";
            List<MatchTournois> matches = new ArrayList<>();

            try {
                pst = connexion.prepareStatement(query);
                pst.setInt(1, tournamentId);
                pst.setString(2, "first_phase");

                rs = pst.executeQuery();

                while (rs.next()) {
                    int idMatchTournois = rs.getInt("idMatchTournois");
                    int idTeam1 = rs.getInt("idTeam1");
                    int idTeam2 = rs.getInt("idTeam2");
                    int idTournois = rs.getInt("idTournois");
                    Date dateMatch = rs.getDate("dateMatch");
                    int idTerrain = rs.getInt("idTerrain");
                    int idTeamWinner = rs.getInt("idTeamWinner");
                    String phase = rs.getString("phase");
                    MatchTournois matchTournois = new MatchTournois(idMatchTournois, idTeam1, idTeam2, idTournois, dateMatch, idTerrain, idTeamWinner, phase);

                    matches.add(matchTournois);
                }
            } catch (SQLException ex) {
                Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
            }

            return matches;

    }
    public List<Tournois> searchTournois(String searchText) {
        List<Tournois> searchResults = new ArrayList<>();
        String query = "SELECT * FROM tournois WHERE name LIKE ? OR type LIKE ?";

        try {
            pst = connexion.prepareStatement(query);
            pst.setString(1, "%" + searchText + "%");
            pst.setString(2, "%" + searchText + "%");
            ResultSet resultSet = pst.executeQuery();

            while (resultSet.next()) {
                Tournois tournois = new Tournois();
                tournois.setIdTournois(resultSet.getInt("idTournois"));
                tournois.setName(resultSet.getString("name"));
                tournois.setType(resultSet.getString("type"));
                tournois.setDateDebut(resultSet.getDate("dateDebut"));
                tournois.setDateFin(resultSet.getDate("dateFin"));
                tournois.setIdClub(resultSet.getInt("idClub"));

                searchResults.add(tournois);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TournoisService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return searchResults;
    }
    public void updateMatchTournois(MatchTournois matchTournois) {
        String query = "UPDATE matchTournois SET idTeam1 = ?, idTeam2 = ?, idTerrain = ?, idTeamWinner = ? WHERE idMatchTournois = ?";

        try {
            pst = connexion.prepareStatement(query);
            pst.setInt(1, matchTournois.getIdTeam1());
            pst.setInt(2, matchTournois.getIdTeam2());
            pst.setInt(3, matchTournois.getIdTerrain());
            pst.setInt(4, matchTournois.getIdTeamWinner());
            pst.setInt(5, matchTournois.getIdMatchTournois());

            pst.executeUpdate();
            System.out.println("Match updated successfully.");
        } catch (SQLException ex) {
            Logger.getLogger(TournoisService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addSecondPhaseMatch(int idTournois, int idTeam1) {
        String query = "INSERT INTO matchTournois (idTeam1, idTournois, dateMatch, phase) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pst = connexion.prepareStatement(query)) {
            pst.setInt(1, idTeam1);
            pst.setInt(2, idTournois);
            pst.setDate(3, new Date(System.currentTimeMillis())); // Assuming current date/time
            pst.setString(4, "second_phase");

            pst.executeUpdate();
            System.out.println("Second phase match added successfully.");
        } catch (SQLException ex) {
            Logger.getLogger(TournoisService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public List<MatchTournois> getFinalPhaseMatchesForCurrentTournament(int tournamentId) {
        String query = "SELECT * FROM matchTournois WHERE idTournois = ? AND phase = ?";
        List<MatchTournois> matches = new ArrayList<>();

        try {
            pst = connexion.prepareStatement(query);
            pst.setInt(1, tournamentId);
            pst.setString(2, "final_phase");

            rs = pst.executeQuery();

            while (rs.next()) {
                int idMatchTournois = rs.getInt("idMatchTournois");
                int idTeam1 = rs.getInt("idTeam1");
                int idTeam2 = rs.getInt("idTeam2");
                int idTournois = rs.getInt("idTournois");
                Date dateMatch = rs.getDate("dateMatch");
                int idTerrain = rs.getInt("idTerrain");
                int idTeamWinner = rs.getInt("idTeamWinner");
                String phase = rs.getString("phase");
                MatchTournois matchTournois = new MatchTournois(idMatchTournois, idTeam1, idTeam2, idTournois, dateMatch, idTerrain, idTeamWinner, phase);

                matches.add(matchTournois);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return matches;
    }









}
