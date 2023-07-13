package service;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import entite.Team;
import entite.User;
import entite.UserTeams;
import utils.DataSource;

public class TeamService implements Iservice<Team> {
    public TeamService() {
        connexion = DataSource.getInstance().getCnx();

    }
    private UserService userService = new UserService();
    private Connection connexion;
    private PreparedStatement pst;
    private Statement ste;
    private ResultSet rs;
    @Override
    public void insert(Team team) {
        String requete = "INSERT INTO team (name) VALUES (?)";
        String query = "SELECT LAST_INSERT_ID() AS idTeam";

        try {
            PreparedStatement statement = connexion.prepareStatement(requete, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, team.getName());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int idTeam = generatedKeys.getInt(1);
                team.setIdTeam(idTeam);
            } else {
                System.out.println("Failed to retrieve team ID after insertion.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Team> readAll() {
        String query = "SELECT * FROM team";
        List<Team> list = new ArrayList<>();
        try {
            ste = connexion.createStatement();
            rs = ste.executeQuery(query);
            while (rs.next()) {
                int idTeam = rs.getInt("idTeam");
                String name = rs.getString("name");


                Team team = new Team(idTeam, name);
                list.add(team);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }


    @Override
    public Team readById(int id) {
        String query = "SELECT * FROM team WHERE idTeam = ?";

        try {
            pst = connexion.prepareStatement(query);
            pst.setInt(1, id);

            rs = pst.executeQuery();

            if (rs.next()) {
                int teamId = rs.getInt("idTeam");
                String name = rs.getString("name");

                return new Team(teamId, name); // Assuming Team constructor takes both id and name parameters
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }


    public Team readByName(String n) {
        String query = "SELECT * FROM team WHERE name = ?";

        try {
            pst = connexion.prepareStatement(query);
            pst.setString(1, n);

            rs = pst.executeQuery();

            if (rs.next()) {
                int idTeam = rs.getInt("idTeam");
                String name = rs.getString("name");

                return new Team(idTeam,name);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM team WHERE idTeam = ?";

        try {
            pst = connexion.prepareStatement(query);
            pst.setInt(1, id);

            pst.executeUpdate();
            System.out.println("Team deleted successfully.");
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Team team) {
        String query = "UPDATE team SET name = ? WHERE idTeam = ?";

        try {
            pst = connexion.prepareStatement(query);
            pst.setString(1, team.getName());


            pst.executeUpdate();
            System.out.println("Team updated successfully.");
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void createTeam2(User user, Team team) {
        String insertUserTeam = "INSERT INTO userTeams (idUser, idTeam, status, dateCreation) VALUES (?, ?, ?, ?)";

        try {

            PreparedStatement insertStatement = connexion.prepareStatement(insertUserTeam, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setInt(1, user.getIdUser());
            insertStatement.setInt(2, team.getIdTeam());
            insertStatement.setBoolean(3, false);
            insertStatement.setDate(4, new Date(System.currentTimeMillis())); // Use the current date for dateCreation

            int rowsInserted = insertStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("UserTeam created successfully!");
            } else {
                System.out.println("Failed to create UserTeam.");
            }
        } catch (SQLException ex) {
            System.out.println("Error occurred while inserting UserTeam: " + ex.getMessage());
        }
    }


    public void createTeam(Team team, String user1, String user2) {
        String insertTeamQuery = "INSERT INTO team (name) VALUES (?)";
        String insertUserTeamQuery = "INSERT INTO userTeams (idUser, idTeam, dateCreation) VALUES (?, ?, ?)";

        try {
            // Insert team record
            PreparedStatement teamStatement = connexion.prepareStatement(insertTeamQuery, Statement.RETURN_GENERATED_KEYS);
            teamStatement.setString(1, team.getName());
            teamStatement.executeUpdate();

            ResultSet generatedKeys = teamStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int teamId = generatedKeys.getInt(1);
                team.setIdTeam(teamId);

                // Retrieve User objects for user1 and user2
                UserService userService = new UserService();
                User u1 = userService.readByEmail(user1);
                User u2 = userService.readByEmail(user2);

                if (u1 != null && u2 != null) {
                    // Add user1 and user2 to the team
                    List<User> teamList = new ArrayList<>();
                    teamList.add(u1);
                    teamList.add(u2);
                    team.setUsers(teamList);

                    // Insert user1 and user2 into userTeams table
                    PreparedStatement userTeamStatement = connexion.prepareStatement(insertUserTeamQuery);
                    userTeamStatement.setInt(1, u1.getIdUser());
                    userTeamStatement.setInt(2, teamId);

                    userTeamStatement.setDate(3, Date.valueOf(LocalDate.now()));
                    userTeamStatement.executeUpdate();

                    userTeamStatement.setInt(1, u2.getIdUser());
                    userTeamStatement.executeUpdate();

                    System.out.println("Team created successfully with ID: " + teamId);
                } else {
                    System.out.println("One or both users do not exist.");
                }
            } else {
                System.out.println("Failed to retrieve team ID after insertion.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



}
