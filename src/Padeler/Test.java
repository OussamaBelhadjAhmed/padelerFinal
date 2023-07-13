/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Padeler;

import entite.Disponibiliteterrain;
import entite.Team;


import service.*;
import utils.DataSource;

/**
 *
 * @author wiemhjiri
 */
public class Test {
    
    public static void main(String[] args) {
        DataSource ds1 = DataSource.getInstance();

//        List<Club> l = null ;
//        ClubService cs = new ClubService(); 
//        
//        Club club = new Club() ;
//        club.setIdClub(14);
//        club.setName("testUpdate");
//        club.setAdresse("testUpdate");
//        cs.update(club);
        /*List<Disponibiliteterrain> l = null;
        DiponibiliteTerrainService d = new DiponibiliteTerrainService();
        System.out.println(d.readAll().size());
        Disponibiliteterrain t = new Disponibiliteterrain();

        t.setDate(Date.valueOf(LocalDate.now()));
        d.insert(t);*/
        TeamService teamService = new TeamService();
        UserService userService = new UserService();
        teamService.insert(new Team("safa"));
        Team team = teamService.readByName("safa");
        teamService.createTeam(team,"oussama@gmail.com","mohamed1@gmail.com");

    }
    }