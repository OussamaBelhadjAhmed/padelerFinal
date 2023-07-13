/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entite.Club;
import entite.Disponibiliteterrain;
import entite.Terrain;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author oussama.hadjahmed
 */
public class DiponibiliteTerrainService implements IDiponibiliteTerrainService<Disponibiliteterrain> {

    private Connection connexion;
    private Statement ste;
    private PreparedStatement pst;
    private ResultSet rs;

    public DiponibiliteTerrainService() {
        connexion = DataSource.getInstance().getCnx();

    }

    @Override
    public void insert(Disponibiliteterrain t) {
        String requete = "insert into disponibiliteterrain (Date,temps1,temps2,temps3,temps4,temps5,temps6,temps7,temps8,temps9,temps10,temps11,temps12,temps13,temps14,idTerrain) values('" + t.getDate() + "','" + 0 + "','" + 0 + "','" + 0 + "','" + 0 + "','" + 0 + "','" + 0 + "','" + 0 + "','" + 0 + "','" + 0 + "','" + 0 + "','" + 0 + "','" + 0 + "','" + 0 + "','" + 0 + "','" + t.getTerrain().getIdTerrain() + "')";

        try {
            ste = connexion.createStatement();
            ste.executeUpdate(requete);
        } catch (SQLException ex) {
            Logger.getLogger(DiponibiliteTerrainService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Disponibiliteterrain> readAll() {
        String requete = "select * from disponibiliteterrain d , terrain t where d.idTerrain =t.idTerrain;";
        List<Disponibiliteterrain> list = new ArrayList<>();
        try {
            ste = connexion.createStatement();
            rs = ste.executeQuery(requete);
            while (rs.next()) {
                Terrain t = new Terrain();
                t.setIdTerrain(rs.getInt("idTerrain"));
                t.setName(rs.getString("name"));
                t.setClub(new Club(rs.getInt("idClub"), "", ""));
                t.setStatus(rs.getInt("status"));
                Disponibiliteterrain disponibiliteterrain = new Disponibiliteterrain(
                        rs.getInt("idDisponibiliteTerrain"),
                        rs.getDate("Date"),
                        rs.getInt("temps1"),
                        rs.getInt("temps2"),
                        rs.getInt("temps3"),
                        rs.getInt("temps4"),
                        rs.getInt("temps5"),
                        rs.getInt("temps6"),
                        rs.getInt("temps7"),
                        rs.getInt("temps8"),
                        rs.getInt("temps9"),
                        rs.getInt("temps10"),
                        rs.getInt("temps11"),
                        rs.getInt("temps12"),
                        rs.getInt("temps13"),
                        rs.getInt("temps14"),
                        t
                );
                list.add(disponibiliteterrain);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DiponibiliteTerrainService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    @Override
    public Disponibiliteterrain readById(int id) {
        String requete = "select * from disponibiliteterrain where idDisponibiliteTerrain =" + Integer.toString(id);
        Disponibiliteterrain disponibiliteterrain = null;
        try {
            ste = connexion.createStatement();
            rs = ste.executeQuery(requete);
            while (rs.next()) {
                Terrain t = new Terrain();
                t.setIdTerrain(rs.getInt("idTerrain"));
                disponibiliteterrain = new Disponibiliteterrain(
                        rs.getInt("idDisponibiliteTerrain"),
                        rs.getDate("Date"),
                        rs.getInt("temps1"),
                        rs.getInt("temps2"),
                        rs.getInt("temps3"),
                        rs.getInt("temps4"),
                        rs.getInt("temps5"),
                        rs.getInt("temps6"),
                        rs.getInt("temps7"),
                        rs.getInt("temps8"),
                        rs.getInt("temps9"),
                        rs.getInt("temps10"),
                        rs.getInt("temps11"),
                        rs.getInt("temps12"),
                        rs.getInt("temps13"),
                        rs.getInt("temps14"),
                        t
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(DiponibiliteTerrainService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return disponibiliteterrain;
    }

    @Override
    public void delete(int id) {
        try {
            String requete = "DELETE FROM disponibiliteterrain WHERE idDisponibiliteTerrain = ?";
            pst = connexion.prepareStatement(requete);
            pst.setInt(1, id);
            int rowsDeleted = pst.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("La disponibilite terrain avec l'ID " + id + " a été supprimée avec succès.");
            } else {
                System.out.println("Aucun disponibilite terrain trouvé avec l'ID " + id + ". La suppression a échoué.");
            }

        } catch (SQLException ex) {
            Logger.getLogger(DiponibiliteTerrainService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Disponibiliteterrain t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Disponibiliteterrain> readByClubAndTerrain(String terrainName) {
        String requete = "SELECT * FROM disponibiliteterrain d , terrain t WHERE d.idTerrain=t.idTerrain AND t.name= " + "\"" + terrainName + "\"";
        List<Disponibiliteterrain> list = new ArrayList<>();
        try {
            ste = connexion.createStatement();
            rs = ste.executeQuery(requete);
            while (rs.next()) {
                Terrain t = new Terrain();
                t.setIdTerrain(rs.getInt("idTerrain"));
                t.setName(rs.getString("name"));
                t.setClub(new Club(rs.getInt("idClub"), "", ""));
                t.setStatus(rs.getInt("status"));
                Disponibiliteterrain disponibiliteterrain = new Disponibiliteterrain(
                        rs.getInt("idDisponibiliteTerrain"),
                        rs.getDate("Date"),
                        rs.getInt("temps1"),
                        rs.getInt("temps2"),
                        rs.getInt("temps3"),
                        rs.getInt("temps4"),
                        rs.getInt("temps5"),
                        rs.getInt("temps6"),
                        rs.getInt("temps7"),
                        rs.getInt("temps8"),
                        rs.getInt("temps9"),
                        rs.getInt("temps10"),
                        rs.getInt("temps11"),
                        rs.getInt("temps12"),
                        rs.getInt("temps13"),
                        rs.getInt("temps14"),
                        t
                );
                list.add(disponibiliteterrain);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DiponibiliteTerrainService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<Disponibiliteterrain> readByClub(String clubName) {
        String requete = "    select * from disponibiliteterrain d , terrain t, club c where d.idTerrain =t.idTerrain and t.idTerrain = c.idClub and \n"
                + " c.clubName= " + "\"" + clubName + "\"";
        List<Disponibiliteterrain> list = new ArrayList<>();
        try {
            ste = connexion.createStatement();
            rs = ste.executeQuery(requete);
            while (rs.next()) {
                Terrain t = new Terrain();
                t.setIdTerrain(rs.getInt("idTerrain"));
                t.setName(rs.getString("name"));
                t.setClub(new Club(rs.getInt("idClub"), rs.getString("clubName"), ""));
                t.setStatus(rs.getInt("status"));
                Disponibiliteterrain disponibiliteterrain = new Disponibiliteterrain(
                        rs.getInt("idDisponibiliteTerrain"),
                        rs.getDate("Date"),
                        rs.getInt("temps1"),
                        rs.getInt("temps2"),
                        rs.getInt("temps3"),
                        rs.getInt("temps4"),
                        rs.getInt("temps5"),
                        rs.getInt("temps6"),
                        rs.getInt("temps7"),
                        rs.getInt("temps8"),
                        rs.getInt("temps9"),
                        rs.getInt("temps10"),
                        rs.getInt("temps11"),
                        rs.getInt("temps12"),
                        rs.getInt("temps13"),
                        rs.getInt("temps14"),
                        t
                );
                list.add(disponibiliteterrain);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DiponibiliteTerrainService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<Disponibiliteterrain> readByClubAndTerrainName(String clubName, String terrainName) {
        String requete = "    select * from disponibiliteterrain d , terrain t, club c where d.idTerrain =t.idTerrain and t.idTerrain = c.idClub and t.name =" + "\"" + terrainName + "\"" + "and \n"
                + " c.clubName= " + "\"" + clubName + "\"";
        List<Disponibiliteterrain> list = new ArrayList<>();
        try {
            ste = connexion.createStatement();
            rs = ste.executeQuery(requete);
            while (rs.next()) {
                Terrain t = new Terrain();
                t.setIdTerrain(rs.getInt("idTerrain"));
                t.setName(rs.getString("name"));
                t.setClub(new Club(rs.getInt("idClub"), rs.getString("clubName"), ""));
                t.setStatus(rs.getInt("status"));
                Disponibiliteterrain disponibiliteterrain = new Disponibiliteterrain(
                        rs.getInt("idDisponibiliteTerrain"),
                        rs.getDate("Date"),
                        rs.getInt("temps1"),
                        rs.getInt("temps2"),
                        rs.getInt("temps3"),
                        rs.getInt("temps4"),
                        rs.getInt("temps5"),
                        rs.getInt("temps6"),
                        rs.getInt("temps7"),
                        rs.getInt("temps8"),
                        rs.getInt("temps9"),
                        rs.getInt("temps10"),
                        rs.getInt("temps11"),
                        rs.getInt("temps12"),
                        rs.getInt("temps13"),
                        rs.getInt("temps14"),
                        t
                );
                list.add(disponibiliteterrain);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DiponibiliteTerrainService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<Disponibiliteterrain> readByIdTerrainAndDate(int idTerrain, Date date) {
        String requete = "select * from disponibiliteterrain d where d.idTerrain =" + "\"" + idTerrain + "\" and d.Date >=" + "\"" + date + "\"";
        List<Disponibiliteterrain> list = new ArrayList<>();
        try {
            ste = connexion.createStatement();
            rs = ste.executeQuery(requete);
            while (rs.next()) {
                Terrain t = new Terrain();
                t.setIdTerrain(rs.getInt("idTerrain"));
                /*t.setName(rs.getString("name"));
                t.setClub(new Club(rs.getInt("idClub"), rs.getString("clubName"), ""));
                t.setStatus(rs.getInt("status"));*/
                Disponibiliteterrain disponibiliteterrain = new Disponibiliteterrain(
                        rs.getInt("idDisponibiliteTerrain"),
                        rs.getDate("Date"),
                        rs.getInt("temps1"),
                        rs.getInt("temps2"),
                        rs.getInt("temps3"),
                        rs.getInt("temps4"),
                        rs.getInt("temps5"),
                        rs.getInt("temps6"),
                        rs.getInt("temps7"),
                        rs.getInt("temps8"),
                        rs.getInt("temps9"),
                        rs.getInt("temps10"),
                        rs.getInt("temps11"),
                        rs.getInt("temps12"),
                        rs.getInt("temps13"),
                        rs.getInt("temps14"),
                        t
                );
                list.add(disponibiliteterrain);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DiponibiliteTerrainService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    // ----------------------------------------------------------------------------------------------------------
    public void updateDisponibiliteTerrain(Disponibiliteterrain disponibiliteTerrain) {
        int rowsDeleted = 0;
        try {

            // Établir la connexion à la base de données
            // Préparer la requête SQL UPDATE
            StringBuilder queryBuilder = new StringBuilder("UPDATE disponibiliteterrain SET");
            boolean hasUpdate = false;

            if (disponibiliteTerrain.getTemps1() != 0) {
                if (hasUpdate) {
                    queryBuilder.append(",");
                }
                queryBuilder.append(" temps1 = ?");
                hasUpdate = true;
            }
            if (disponibiliteTerrain.getTemps2() != 0) {
                if (hasUpdate) {
                    queryBuilder.append(",");
                }
                queryBuilder.append(" temps2 = ?");
                hasUpdate = true;
            }
            if (disponibiliteTerrain.getTemps3() != 0) {
                if (hasUpdate) {
                    queryBuilder.append(",");
                }
                queryBuilder.append(" temps3 = ?");
                hasUpdate = true;
            }
            if (disponibiliteTerrain.getTemps4() != 0) {
                if (hasUpdate) {
                    queryBuilder.append(",");
                }
                queryBuilder.append(" temps4 = ?");
                hasUpdate = true;
            }
            if (disponibiliteTerrain.getTemps5() != 0) {
                if (hasUpdate) {
                    queryBuilder.append(",");
                }
                queryBuilder.append(" temps5 = ?");
                hasUpdate = true;
            }
            if (disponibiliteTerrain.getTemps6() != 0) {
                if (hasUpdate) {
                    queryBuilder.append(",");
                }
                queryBuilder.append(" temps6 = ?");
                hasUpdate = true;
            }
            if (disponibiliteTerrain.getTemps7() != 0) {
                if (hasUpdate) {
                    queryBuilder.append(",");
                }
                queryBuilder.append(" temps7 = ?");
                hasUpdate = true;
            }
            if (disponibiliteTerrain.getTemps8() != 0) {
                if (hasUpdate) {
                    queryBuilder.append(",");
                }
                queryBuilder.append(" temps8 = ?");
                hasUpdate = true;
            }
            if (disponibiliteTerrain.getTemps9() != 0) {
                if (hasUpdate) {
                    queryBuilder.append(",");
                }
                queryBuilder.append(" temps9 = ?");
                hasUpdate = true;
            }
            if (disponibiliteTerrain.getTemps10() != 0) {
                if (hasUpdate) {
                    queryBuilder.append(",");
                }
                queryBuilder.append(" temps10 = ?");
                hasUpdate = true;
            }
            if (disponibiliteTerrain.getTemps11() != 0) {
                if (hasUpdate) {
                    queryBuilder.append(",");
                }
                queryBuilder.append(" temps11 = ?");
                hasUpdate = true;
            }
            if (disponibiliteTerrain.getTemps12() != 0) {
                if (hasUpdate) {
                    queryBuilder.append(",");
                }
                queryBuilder.append(" temps12 = ?");
                hasUpdate = true;
            }
            if (disponibiliteTerrain.getTemps13() != 0) {
                if (hasUpdate) {
                    queryBuilder.append(",");
                }
                queryBuilder.append(" temps13 = ?");
                hasUpdate = true;
            }
            if (disponibiliteTerrain.getTemps14() != 0) {
                if (hasUpdate) {
                    queryBuilder.append(",");
                }
                queryBuilder.append(" temps14 = ?");
                hasUpdate = true;
            }
            // Ajoutez les autres conditions pour les autres attributs
            queryBuilder.append(" WHERE idDisponibiliteTerrain = ?");
            String query = queryBuilder.toString();

            try {
                pst = connexion.prepareStatement(query);
            } catch (SQLException ex) {
                Logger.getLogger(DiponibiliteTerrainService.class.getName()).log(Level.SEVERE, null, ex);
            }

            // Définir les valeurs des paramètres de la requête
            int paramIndex = 1;

            if (disponibiliteTerrain.getTemps1() != 0) {
                pst.setInt(paramIndex++, disponibiliteTerrain.getTemps1());

            }if (disponibiliteTerrain.getTemps2() != 0) {
                pst.setInt(paramIndex++, disponibiliteTerrain.getTemps2());

            }if (disponibiliteTerrain.getTemps3() != 0) {
                pst.setInt(paramIndex++, disponibiliteTerrain.getTemps3());

            }if (disponibiliteTerrain.getTemps4() != 0) {
                pst.setInt(paramIndex++, disponibiliteTerrain.getTemps5());

            }if (disponibiliteTerrain.getTemps5() != 0) {
                pst.setInt(paramIndex++, disponibiliteTerrain.getTemps5());

            }if (disponibiliteTerrain.getTemps6() != 0) {
                pst.setInt(paramIndex++, disponibiliteTerrain.getTemps6());

            }if (disponibiliteTerrain.getTemps7() != 0) {
                pst.setInt(paramIndex++, disponibiliteTerrain.getTemps7());

            }if (disponibiliteTerrain.getTemps8() != 0) {
                pst.setInt(paramIndex++, disponibiliteTerrain.getTemps8());

            }if (disponibiliteTerrain.getTemps9() != 0) {
                pst.setInt(paramIndex++, disponibiliteTerrain.getTemps9());

            }if (disponibiliteTerrain.getTemps10() != 0) {
                pst.setInt(paramIndex++, disponibiliteTerrain.getTemps10());

            }if (disponibiliteTerrain.getTemps11() != 0) {
                pst.setInt(paramIndex++, disponibiliteTerrain.getTemps11());

            }if (disponibiliteTerrain.getTemps12() != 0) {
                pst.setInt(paramIndex++, disponibiliteTerrain.getTemps12());

            }if (disponibiliteTerrain.getTemps13() != 0) {
                pst.setInt(paramIndex++, disponibiliteTerrain.getTemps13());

            }if (disponibiliteTerrain.getTemps14() != 0) {
                pst.setInt(paramIndex++, disponibiliteTerrain.getTemps14());

            }
            // Définissez les autres valeurs des paramètres pour les autres attributs
            pst.setInt(paramIndex, disponibiliteTerrain.getIdDisponibiliteTerrain());

            // Exécuter la requête
            rowsDeleted = pst.executeUpdate();

            System.out.println("Update rowsDeleted : " + rowsDeleted);
            System.out.println("Disponibiliteterrain mise à jour avec succès !");
        } catch (SQLException ex) {
            Logger.getLogger(ClubService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

// ----------------------------------------------------------------------------------------------------------
}
