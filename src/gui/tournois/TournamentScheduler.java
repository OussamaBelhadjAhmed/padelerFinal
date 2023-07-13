package gui.tournois;

import entite.MatchTournois;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TournamentScheduler {
    private static final int NUM_TEAMS = 8;

    public static List<MatchTournois> generateMatchSchedule() {
        List<MatchTournois> matchSchedule = new ArrayList<>();
        List<String> teams = generateTeams();

        // Round 1 (Quarterfinals)
        Collections.shuffle(teams);
        for (int i = 0; i < NUM_TEAMS / 2; i++) {
            String team1 = teams.get(i);
            String team2 = teams.get(NUM_TEAMS - 1 - i);
            MatchTournois match = new MatchTournois(i + 1, 0, 0, 0, null, 0, 0, "Quarterfinals");
            matchSchedule.add(match);
        }

        // Round 2 (Semifinals)
        List<String> winnersRound2 = new ArrayList<>();
        for (int i = 0; i < NUM_TEAMS / 4; i++) {
            MatchTournois quarterfinalMatch1 = matchSchedule.get(i * 2);
            MatchTournois quarterfinalMatch2 = matchSchedule.get(i * 2 + 1);
            int winner = selectWinner(quarterfinalMatch1, quarterfinalMatch2);
            winnersRound2.add(teams.get(winner));
            MatchTournois match = new MatchTournois(i + 5, 0, 0, 0, null, 0, 0, "Semifinals");
            matchSchedule.add(match);
        }

        // Round 3 (Final)
        int winnerIndexRound3 = selectWinner(matchSchedule.get(4), matchSchedule.get(5));
        String winnerRound3 = teams.get(winnerIndexRound3);
        MatchTournois matchFinal = new MatchTournois(7, 0, 0, 0, null, 0, 0, "Final");
        matchSchedule.add(matchFinal);

        // Assign teams to matches
        assignTeamsToMatches(matchSchedule, teams);

        return matchSchedule;
    }

    // Generate a list of teams participating in the tournament
    private static List<String> generateTeams() {
        List<String> teams = new ArrayList<>();

        for (int i = 1; i <= NUM_TEAMS; i++) {
            teams.add("Team " + i);
        }

        return teams;
    }

    // Assign teams to matches based on the team list
    private static void assignTeamsToMatches(List<MatchTournois> matchSchedule, List<String> teams) {
        int teamIndex = 0;

        for (MatchTournois match : matchSchedule) {
            int team1 = teamIndex + 1;
            int team2 = teamIndex + 2;

            match.setIdTeam1(team1);
            match.setIdTeam2(team2);

            teamIndex += 2;
        }
    }



    // Select the winner between two teams randomly
    private static int selectWinner(MatchTournois match1, MatchTournois match2) {
        int team1Index = Math.random() < 0.5 ? 0 : 1;
        int team2Index = Math.random() < 0.5 ? 0 : 1;
        return Math.random() < 0.5 ? team1Index : team2Index;
    }
}
