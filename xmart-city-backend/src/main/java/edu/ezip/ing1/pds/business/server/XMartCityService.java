package edu.ezip.ing1.pds.business.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ezip.ing1.pds.business.dto.*;

import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.commons.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.lang.Integer;
import java.util.ArrayList;

public class XMartCityService {

    private final static String LoggingLabel = "B u s i n e s s - S e r v e r";
    private final Logger logger = LoggerFactory.getLogger(LoggingLabel);

    private enum Queries {
        INSERT_PRODUCT("INSERT into \"ezip-ing1\".produit (\"idEmplacement\", \"idVilleDepart\", \"idVilleArrive\", \"couleur\", \"taille\", \"reference\", \"score\", \"genre\", \"empreinte\", \"idMagasin\", \"idMarque\", \"nomProduit\") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"),
        INSERT_POINT("INSERT into \"ezip-ing1\".point (\"coordX\", \"coordY\", \"idRayon\") values (?, ?, ?)"),
        
        SELECT_ALL_PRODUCTS("SELECT * FROM \"ezip-ing1\".produit"),
        SELECT_PRODUCT_BY_REFERENCE("SELECT * FROM \"ezip-ing1\".produit WHERE reference=?"),
        SELECT_EMPLACEMENT_BY_ID("SELECT * FROM  \"ezip-ing1\".emplacement WHERE \"idEmplacement\" = ?"),
        SELECT_ETAGE_BY_ID("SELECT * FROM  \"ezip-ing1\".etage WHERE \"idEtage\" = ?"),
        SELECT_HIGHEST_FLOOR("SELECT \"numeroEtage\" FROM \"ezip-ing1\".\"etage\" ORDER BY \"numeroEtage\" DESC LIMIT 1"),
        SELECT_POINTS_BY_ID_RAYON("SELECT * FROM  \"ezip-ing1\".point WHERE \"idRayon\" = ?"),
        SELECT_SOUS_CATEGORIE_B_BY_ID("SELECT * FROM \"ezip-ing1\".\"sousCategorieB\" WHERE \"idSousCategorieB\" = ?"),
        SELECT_CATEGORIE_BY_ID("SELECT * FROM \"ezip-ing1\".\"categorie\" WHERE \"idCategorie\" = ?"),
        SELECT_MARQUE_BY_ID("SELECT * FROM \"ezip-ing1\".\"marque\" WHERE \"idMarque\" = ?"),

        SELECT_SOUS_CATEGORIE_A_BY_ID("SELECT * FROM \"ezip-ing1\".\"sousCategorieA\" WHERE \"idSousCategorieA\" = ?"),

        SELECT_BEFORE_VENTE_BY_REFERENCE("SELECT reference, quantite, score, empreinte\n" +
                "FROM \"ezip-ing1\".vend\n" +
                "INNER JOIN \"ezip-ing1\".produit ON vend.\"idProduit\" = produit.\"idProduit\"\n" +
                "INNER JOIN \"ezip-ing1\".magasin ON vend.\"idMagasin\" = magasin.\"idMagasin\"\n" +
                "WHERE \"reference\"=? AND vend.\"date\" < magasin.\"dateInstallation\";"),
        SELECT_AFTER_VENTE_BY_REFERENCE("SELECT reference, quantite, score, empreinte\n" +
                "FROM \"ezip-ing1\".vend\n" +
                "INNER JOIN \"ezip-ing1\".produit ON vend.\"idProduit\" = produit.\"idProduit\"\n" +
                "INNER JOIN \"ezip-ing1\".magasin ON vend.\"idMagasin\" = magasin.\"idMagasin\"\n" +
                "WHERE \"reference\"=? AND vend.\"date\" > magasin.\"dateInstallation\";"),

        SELECT_TRANSPORTMODE_BY_ID("SELECT * FROM  \"ezip-ing1\".transportmode WHERE \"idTransportMode\" = ?"),
        SELECT_VILLE_BY_ID("SELECT * FROM  \"ezip-ing1\".ville WHERE \"idVille\" = ?"),

        SELECT_3_SUGGESTIONS("SELECT * FROM  \"ezip-ing1\".produit WHERE \"idCategorie\" = ? AND \"idSousCatA\" = ? AND \"idSousCatB\" = ? AND \"empreinte\" < ? AND \"couleur\" = ? AND \"reference\" != ? ORDER BY \"empreinte\" ASC LIMIT 3"),

    SELECT_ALL_PRODUCTS_BY_CATEGORIES("SELECT * FROM  \"ezip-ing1\".produit WHERE \"idCategorie\" = ? AND \"idSousCatA\" = ? AND \"idSousCatB\" = ? "),

        SELECT_ALL_SCORE("SELECT * FROM \"ezip-ing1\".score"),

        SELECT_ALL_CATEGORIE("SELECT * FROM \"ezip-ing1\".categorie"),
        SELECT_ALL_SOUS_CAT_A("SELECT * FROM  \"ezip-ing1\".\"sousCategorieA\" WHERE \"codeGenre\" = ? OR \"codeGenre\"= ?"),
        SELECT_ALL_SOUS_CAT_B("SELECT * FROM  \"ezip-ing1\".\"sousCategorieB\" WHERE \"codeGenre\" = ? AND \"idSousCategorieA\"=? OR \"codeGenre\"= ? AND \"idSousCategorieA\"=?"),


        UPDATE_INFO_PRODUCT("UPDATE \"ezip-ing1\".produit  SET \"empreinte\" = ?, \"score\" = ?  WHERE \"reference\" = ?"),
        UPDATE_BORNES_SCORE("UPDATE \"ezip-ing1\".score  SET \"borneInf\" = ?, \"borneSup\" = ?  WHERE \"lettreScore\" = ?"),


        SELECT_BESTSELLER_BEFORE("SELECT reference, score, taille, genre, empreinte, prix ,CAST(SUM(vend.quantite) AS INTEGER) AS sum\n" +
                "FROM \"ezip-ing1\".vend\n" +
                "INNER JOIN \"ezip-ing1\".produit ON vend.\"idProduit\" = produit.\"idProduit\"\n" +
                "INNER JOIN \"ezip-ing1\".magasin ON vend.\"idMagasin\" = magasin.\"idMagasin\"\n" +
                "WHERE vend.\"date\" < magasin.\"dateInstallation\"\n" +
                "GROUP BY reference, score , taille, genre, empreinte, prix\n" +
                "ORDER BY SUM(vend.quantite) DESC\n" +
                "LIMIT 3;"),
        SELECT_BESTSELLER_AFTER("SELECT reference, score, taille, genre, empreinte, prix ,CAST(SUM(vend.quantite) AS INTEGER) AS sum\n" +
                "FROM \"ezip-ing1\".vend\n" +
                "INNER JOIN \"ezip-ing1\".produit ON vend.\"idProduit\" = produit.\"idProduit\"\n" +
                "INNER JOIN \"ezip-ing1\".magasin ON vend.\"idMagasin\" = magasin.\"idMagasin\"\n" +
                "WHERE vend.\"date\" > magasin.\"dateInstallation\"\n" +
                "GROUP BY reference, score , taille, genre, empreinte, prix\n" +
                "ORDER BY SUM(vend.quantite) DESC\n" +
                "LIMIT 3;"),


        SELECT_VENTE_BY_SCORE("SELECT TO_CHAR(DATE_TRUNC('month', vend.date), 'YYYY-MM') AS month, produit.score, CAST(SUM(vend.quantite) AS INTEGER) AS sum\n" +
                "FROM \"ezip-ing1\".vend\n" +
                "INNER JOIN \"ezip-ing1\".produit ON vend.\"idProduit\" = produit.\"idProduit\"\n" +
                "INNER JOIN \"ezip-ing1\".magasin ON vend.\"idMagasin\" = magasin.\"idMagasin\"\n" +
                "WHERE produit.score = ?\n" +
                "GROUP BY TO_CHAR(DATE_TRUNC('month', vend.date), 'YYYY-MM'), produit.score\n" +
                "ORDER BY TO_CHAR(DATE_TRUNC('month', vend.date), 'YYYY-MM');"),
        SELECT_ALL_REFERENCES("SELECT \"reference\" FROM \"ezip-ing1\".produit "),

        SELECT_SOUS_CATEGORIE_B_BY_NAME("SELECT * FROM  \"ezip-ing1\".\"sousCategorieB\" WHERE \"nom\" = ?"),


        DELETE_PATH("DELETE FROM \"ezip-ing1\".point WHERE \"idRayon\" = ?;");




        private final String query;

        private Queries(final String query) {
            this.query = query;
        }
    }

    public static XMartCityService inst = null;

    public static final XMartCityService getInstance() {
        if (inst == null) {
            inst = new XMartCityService();
        }
        return inst;
    }

    private XMartCityService() {

    }


    public final Response dispatch(final Request request, final Connection connection)
            throws InvocationTargetException, IllegalAccessException, SQLException {
        Response response = null;

        if (request != null) {
            String action = request.getRequestOrder();

            switch (action) {

                case "SELECT_ALL_PRODUCTS":
                    try {
                        PreparedStatement selectStatement = connection.prepareStatement(Queries.SELECT_ALL_PRODUCTS.query);
                        ResultSet resultSet = selectStatement.executeQuery();

                        Produits produits = new Produits();

                        while (resultSet.next()) {
                            Produit produit = new Produit();
                            produit.build(resultSet);
                            System.out.println("produit to string :");
                            System.out.println(produit.toString());
                            produits.add(produit);
                        }

                        System.out.println("produits to string :");
                        System.out.println(produits.toString());

                        // mapper produits en Json
                        ObjectMapper objectMapper = new ObjectMapper();
                        String responseBody = objectMapper.writeValueAsString(produits);

                        response = new Response(request.getRequestId(), responseBody);
                    } catch (SQLException | JsonProcessingException e) {
                        response = new Response(request.getRequestId(), "Error executing SELECT_ALL_PRODUITS query");
                        logger.error("Error executing SELECT_ALL_PRODUITS query: {}", e.getMessage());
                    } catch (NoSuchFieldException e) {
                        throw new RuntimeException(e);
                    }
                    break;

                case "INSERT_PRODUCT":
                    try {
                        String requestBody = request.getRequestBody();
                        ObjectMapper objectMapper = new ObjectMapper();
                        Produit produit = objectMapper.readValue(requestBody, Produit.class);

                        PreparedStatement insertStatement = connection.prepareStatement(Queries.INSERT_PRODUCT.query);
                        insertStatement.setInt(1, produit.getIdProduit());
                        insertStatement.setInt(2, produit.getIdEmplacement());
                        insertStatement.setInt(3, produit.getIdVilleDepart());
                        insertStatement.setInt(3, produit.getIdVilleDepart());
                        insertStatement.setString(3, produit.getCouleur());
                        insertStatement.setString(3, produit.getTaille());
                        insertStatement.setInt(3, produit.getReference());
                        insertStatement.setString(3, produit.getScore());
                        insertStatement.setString(3, produit.getGenre());
                        insertStatement.setDouble(3, produit.getEmpreinte());
                        insertStatement.setInt(3, produit.getIdMagasin());
                        insertStatement.setInt(3, produit.getIdMarque());
                        insertStatement.setString(3, produit.getNomProduit());
                        insertStatement.setInt(3, produit.getIdTransportMode());
                        insertStatement.setDouble(3, produit.getPoids());


                        produit.build(insertStatement);

                        int rowsAffected = insertStatement.executeUpdate();

                        if (rowsAffected > 0) {
                            response = new Response(request.getRequestId(), String.format("{\"idProduit\": %d}", rowsAffected));
                        } else {
                            response = new Response(request.getRequestId(), "Failed to insert product");
                        }
                    } catch (SQLException | IOException e) {
                        response = new Response(request.getRequestId(), "Error executing INSERT_PRODUCT query");
                        logger.error("Error executing INSERT_PRODUCT query: {}", e.getMessage());
                    } catch (NoSuchFieldException e) {
                        throw new RuntimeException(e);
                    }
                    break;

                case "INSERT_POINT":
                    try {
                        PreparedStatement selectStatement = connection.prepareStatement(Queries.INSERT_POINT.query);
                        String requestBody = request.getRequestBody();
                        ObjectMapper objectMapper = new ObjectMapper();

                        PointChemin point = objectMapper.readValue(requestBody, PointChemin.class);
                        selectStatement.setInt(1,point.getCoordX());
                        selectStatement.setInt(2,point.getCoordY());
                        selectStatement.setInt(3,point.getIdRayon());
                        selectStatement.executeUpdate();
                        return new Response(request.getRequestId(),point.toString());

                    } catch (/*SQLException | IOException e*/ Exception e) {
                        response = new Response(request.getRequestId(), "Error executing INSERT_POINT query");
                        logger.error("Error executing INSERT_POINT query: {}", e.getMessage());
                    }
                    break;

                case "UPDATE_INFO_PRODUCT":
                    try {
                        PreparedStatement updateStatement = connection.prepareStatement(Queries.UPDATE_INFO_PRODUCT.query);
                        String requestBody = request.getRequestBody();
                        ObjectMapper objectMapper = new ObjectMapper();

                        Produit produit = objectMapper.readValue(requestBody, Produit.class);

                        updateStatement.setDouble(1,produit.getEmpreinte());
                        updateStatement.setString(2,produit.getScore());
                        updateStatement.setInt(3, produit.getReference());

                        updateStatement.executeUpdate();
                        return new Response(request.getRequestId(),produit.toString());

                    } catch (/*SQLException | IOException e*/ Exception e) {


                        response = new Response(request.getRequestId(), "Error executing UPDATE_INFO_PRODUCT query");
                        logger.error("Error executing UPDATE_INFO_PRODUCT query: {}", e.getMessage());
                    }
                    break;

                case "UPDATE_BORNES_SCORE":
                    try {
                        PreparedStatement updateStatement = connection.prepareStatement(Queries.UPDATE_BORNES_SCORE.query);
                        String requestBody = request.getRequestBody();
                        ObjectMapper objectMapper = new ObjectMapper();

                       Score score = objectMapper.readValue(requestBody, Score.class);

                        updateStatement.setDouble(1,score.getborneInf());
                        updateStatement.setDouble(2,score.getborneSup());
                        updateStatement.setString(3, score.getlettreScore());

                        updateStatement.executeUpdate();
                        return new Response(request.getRequestId(),score.toString());

                    } catch (/*SQLException | IOException e*/ Exception e) {


                        response = new Response(request.getRequestId(), "Error executing  UPDATE_BORNES_SCORE query");
                        logger.error("Error executing UPDATE_BORNES_SCORE query: {}", e.getMessage());
                    }
                    break;


                case "SELECT_PRODUCT_BY_REFERENCE":

                    try{
                    PreparedStatement selectStatement = connection.prepareStatement(Queries.SELECT_PRODUCT_BY_REFERENCE.query);
                        String ref = request.getRequestBody().replaceAll("\"", "");

                    selectStatement.setInt(1, Integer.valueOf(ref));

                        // mapper produits en Json
                        ObjectMapper objectMapper = new ObjectMapper();
                        ResultSet resultSet = selectStatement.executeQuery();
                        selectStatement.setInt(1, Integer.valueOf(ref));

                        Produits produits = new Produits();

                        while (resultSet.next()) {
                            Produit produit = new Produit();
                            produit.build(resultSet);
                            produits.add(produit);
                        }

                        String responseBody = objectMapper.writeValueAsString(produits);

                        response = new Response(request.getRequestId(), responseBody);
                    } catch (SQLException | JsonProcessingException e) {
                        response = new Response(request.getRequestId(), "Error executing SELECT_PRODUCT_BY_REFERENCE query");
                        logger.error("Error executing SELECT_PRODUCT_BY_REFERENCE query: {}", e.getMessage());
                    } catch (NoSuchFieldException e) {
                        throw new RuntimeException(e);

                    }
                    break;

                case "SELECT_ALL_REFERENCES":
                    try {
                        PreparedStatement selectStatement = connection.prepareStatement(Queries.SELECT_ALL_REFERENCES.query);
                        ResultSet resultSet = selectStatement.executeQuery();

                        ArrayList<Integer> referencesList = new ArrayList<>();

                        while (resultSet.next()) {
                            Integer reference = resultSet.getInt("reference");
                            referencesList.add(reference);
                        }


                        ObjectMapper objectMapper = new ObjectMapper();
                        String responseBody = objectMapper.writeValueAsString(referencesList);

                        response = new Response(request.getRequestId(), responseBody);
                    } catch (SQLException | JsonProcessingException e) {
                        response = new Response(request.getRequestId(), "Error executing SELECT_ALL_REFERENCE query");
                        logger.error("Error executing SELECT_ALL_REFERENCE query: {}", e.getMessage());
                    }
                    break;


                case "SELECT_BEFORE_VENTE_BY_REFERENCE": // requête SELECT with date
                    try {
                        PreparedStatement selectStatement = connection.prepareStatement(Queries.SELECT_BEFORE_VENTE_BY_REFERENCE.query);
                        String ref = request.getRequestBody().replaceAll("\"", "");

                        selectStatement.setInt(1, Integer.valueOf(ref));
                        ResultSet resultSet = selectStatement.executeQuery();

                        Ventes ventes = new Ventes();

                        while (resultSet.next()) {
                            Vente vente = new Vente();
                            vente.build(resultSet);
                            ventes.add(vente);
                        }
                        System.out.println("Ventes to String:");


                        ObjectMapper objectMapper = new ObjectMapper();
                        String responseBody = objectMapper.writeValueAsString(ventes);

                        response = new Response(request.getRequestId(), responseBody);
                    }catch (SQLException | JsonProcessingException e){
                        response = new Response(request.getRequestId(), "Error executing SELECT_BEFORE_VENTE_BY_REF query");
                        logger.error("Error executing SELECT_BEFORE_VENTE_BY_REF query: {}", e.getMessage());
                    }catch (NoSuchFieldException e){
                        throw  new RuntimeException(e);
                        }
                        break;


                case "SELECT_AFTER_VENTE_BY_REFERENCE": // requête SELECT with date
                    try {
                        PreparedStatement selectStatement = connection.prepareStatement(Queries.SELECT_AFTER_VENTE_BY_REFERENCE.query);
                        String ref = request.getRequestBody().replaceAll("\"", "");

                        selectStatement.setInt(1, Integer.valueOf(ref));
                        ResultSet resultSet = selectStatement.executeQuery();

                        Ventes ventes = new Ventes();

                        while (resultSet.next()) {
                            Vente vente = new Vente();
                            vente.build(resultSet);
                            ventes.add(vente);
                        }
                        System.out.println("Ventes to String:");


                        ObjectMapper objectMapper = new ObjectMapper();
                        String responseBody = objectMapper.writeValueAsString(ventes);

                        response = new Response(request.getRequestId(), responseBody);
                    }catch (SQLException | JsonProcessingException e){
                        response = new Response(request.getRequestId(), "Error executing SELECT_AFTER_VENTE_BY_REF query");
                        logger.error("Error executing SELECT_AFTER_VENTE_BY_REF query: {}", e.getMessage());
                    }catch (NoSuchFieldException e){
                        throw  new RuntimeException(e);
                    }
                    break;


                case "SELECT_EMPLACEMENT_BY_ID":
                    try{
                        PreparedStatement selectStatement = connection.prepareStatement(Queries.SELECT_EMPLACEMENT_BY_ID.query);
                        String id = request.getRequestBody().replaceAll("\"", "");

                        System.out.println("@@@@@@@@@@@@@@@@ ID DU REQUEST BODY : " + id);

                        selectStatement.setInt(1, Integer.valueOf(id));

                        // mapper produits en Json
                        ObjectMapper objectMapper = new ObjectMapper();

                        ResultSet resultSet = selectStatement.executeQuery();

                        Emplacement emplacement = new Emplacement();

                    while (resultSet.next()) {
                        emplacement.setIdEmplacement(resultSet.getInt("idEmplacement"));
                        emplacement.build(resultSet);
                    }

                        String responseBody = objectMapper.writeValueAsString(emplacement);

                        response = new Response(request.getRequestId(), responseBody);
                    } catch (SQLException | JsonProcessingException e) {
                        response = new Response(request.getRequestId(), "Error executing SELECT_EMPLACEMENT_BY_ID query");
                        logger.error("Error executing SELECT_EMPLACEMENT_BY_ID query: {}", e.getMessage());
                    } catch (NoSuchFieldException e) {
                        throw new RuntimeException(e);
                    }
                    break;


                case "SELECT_ETAGE_BY_ID":
                    try{
                        PreparedStatement selectStatement = connection.prepareStatement(Queries.SELECT_ETAGE_BY_ID.query);
                        String id = request.getRequestBody().replaceAll("\"", "");

                        selectStatement.setInt(1, Integer.valueOf(id));

                        // mapper produits en Json
                        ObjectMapper objectMapper = new ObjectMapper();

                        ResultSet resultSet = selectStatement.executeQuery();

                        Etage etage = new Etage();

                        while (resultSet.next()) {
                            etage.setIdEtage(resultSet.getInt("idEtage"));
                            etage.build(resultSet);
                        }

                        String responseBody = objectMapper.writeValueAsString(etage);

                        response = new Response(request.getRequestId(), responseBody);
                    } catch (SQLException | JsonProcessingException e) {
                            response = new Response(request.getRequestId(), "Error executing SELECT_ETAGE_BY_ID query");
                        logger.error("Error executing SELECT_ETAGE_BY_ID query: {}", e.getMessage());
                    } catch (NoSuchFieldException e) {
                        throw new RuntimeException(e);
                    }
                    break;

                case "SELECT_POINTS_BY_ID_RAYON":
                    try{
                        PreparedStatement selectStatement = connection.prepareStatement(Queries.SELECT_POINTS_BY_ID_RAYON.query);
                        String id = request.getRequestBody().replaceAll("\"", "");

                        selectStatement.setInt(1, Integer.valueOf(id));

                        ResultSet resultSet = selectStatement.executeQuery();

                        PathPointChemin path = new PathPointChemin();

                        while (resultSet.next()) {
                            PointChemin point = new PointChemin();
                            point.build(resultSet);
                            System.out.println("Point to string :");
                            System.out.println(point.toString());
                            path.getPath().add(point);
                        }

                        ObjectMapper objectMapper = new ObjectMapper();
                        String responseBody = objectMapper.writeValueAsString(path);
                        response = new Response(request.getRequestId(), responseBody);

                    } catch (SQLException | JsonProcessingException e) {
                        response = new Response(request.getRequestId(), "Error executing SELECT_POINTS_BY_ID_RAYON query");
                        logger.error("Error executing SELECT_POINTS_BY_ID_RAYON query: {}", e.getMessage());
                    } catch (NoSuchFieldException e) {
                        throw new RuntimeException(e);
                    }
                    break;

//                case "SELECT_HIGHEST_FLOOR":
//                    try{
//                        PreparedStatement selectStatement = connection.prepareStatement(Queries.SELECT_HIGHEST_FLOOR.query);
//                        String id = request.getRequestBody().replaceAll("\"", "");
//
//                        // mapper produits en Json
//                        ObjectMapper objectMapper = new ObjectMapper();
//                        ResultSet resultSet = selectStatement.executeQuery();
//                        //Etage etage = new Etage();
//                        int highestFloor;
//
//                        while (resultSet.next()) {
//                            highestFloor = resultSet.getInt("numeroEtage");
//                            //etage.build(resultSet);
//                        }
//
//                        String responseBody = objectMapper.writeValueAsString(etage);
//
//                        response = new Response(request.getRequestId(), responseBody);
//                    } catch (SQLException | JsonProcessingException e) {
//                        response = new Response(request.getRequestId(), "Error executing SELECT_HIGHEST_FLOOR query");
//                        logger.error("Error executing SELECT_HIGHEST_FLOOR query: {}", e.getMessage());
//                    } catch (NoSuchFieldException e) {
//                        throw new RuntimeException(e);
//                    }
//                    break;

                case "SELECT_SOUS_CATEGORIE_B_BY_ID":
                    try{
                        PreparedStatement selectStatement = connection.prepareStatement(Queries.SELECT_SOUS_CATEGORIE_B_BY_ID.query);
                        String id = request.getRequestBody().replaceAll("\"", "");

                        selectStatement.setInt(1, Integer.valueOf(id));

                        // mapper produits en Json
                        ObjectMapper objectMapper = new ObjectMapper();

                        ResultSet resultSet = selectStatement.executeQuery();

                        SousCategorieB sousCategorieB = new SousCategorieB();

                        while (resultSet.next()) {
                            sousCategorieB.build(resultSet);
                        }

                        String responseBody = objectMapper.writeValueAsString(sousCategorieB);

                        response = new Response(request.getRequestId(), responseBody);
                    } catch (SQLException | JsonProcessingException e) {
                        response = new Response(request.getRequestId(), "Error executing SELECT_SOUS_CATEGORIE_B_BY_ID query");
                        logger.error("Error executing SELECT_SOUS_CATEGORIE_B_BY_ID query: {}", e.getMessage());
                    } catch (NoSuchFieldException e) {
                        throw new RuntimeException(e);
                    }
                    break;

                case "SELECT_SOUS_CATEGORIE_A_BY_ID":
                    try{
                        PreparedStatement selectStatement = connection.prepareStatement(Queries.SELECT_SOUS_CATEGORIE_A_BY_ID.query);
                        String id = request.getRequestBody().replaceAll("\"", "");

                        selectStatement.setInt(1, Integer.valueOf(id));

                        // mapper produits en Json
                        ObjectMapper objectMapper = new ObjectMapper();

                        ResultSet resultSet = selectStatement.executeQuery();

                        SousCategorieA sousCategorieA = new SousCategorieA();

                        while (resultSet.next()) {
                            sousCategorieA.build(resultSet);
                        }

                        String responseBody = objectMapper.writeValueAsString(sousCategorieA);

                        response = new Response(request.getRequestId(), responseBody);
                    } catch (SQLException | JsonProcessingException e) {
                        response = new Response(request.getRequestId(), "Error executing SELECT_SOUS_CATEGORIE_A_BY_ID query");
                        logger.error("Error executing SELECT_SOUS_CATEGORIE_A_BY_ID query: {}", e.getMessage());
                    } catch (NoSuchFieldException e) {
                        throw new RuntimeException(e);
                    }
                    break;

                case "SELECT_CATEGORIE_BY_ID":
                    try{
                        PreparedStatement selectStatement = connection.prepareStatement(Queries.SELECT_CATEGORIE_BY_ID.query);
                        String id = request.getRequestBody().replaceAll("\"", "");

                        selectStatement.setInt(1, Integer.valueOf(id));

                        // mapper produits en Json
                        ObjectMapper objectMapper = new ObjectMapper();

                        ResultSet resultSet = selectStatement.executeQuery();

                        Categorie categorie = new Categorie();

                        while (resultSet.next()) {
                            categorie.build(resultSet);
                        }

                        String responseBody = objectMapper.writeValueAsString(categorie);

                        response = new Response(request.getRequestId(), responseBody);
                    } catch (SQLException | JsonProcessingException e) {
                        response = new Response(request.getRequestId(), "Error executing SELECT_CATEGORIE_BY_ID query");
                        logger.error("Error executing SELECT_CATEGORIE_BY_ID query: {}", e.getMessage());
                    } catch (NoSuchFieldException e) {
                        throw new RuntimeException(e);
                    }
                    break;

                case "SELECT_MARQUE_BY_ID":
                    try{
                        PreparedStatement selectStatement = connection.prepareStatement(Queries.SELECT_MARQUE_BY_ID.query);
                        String id = request.getRequestBody().replaceAll("\"", "");

                        selectStatement.setInt(1, Integer.valueOf(id));

                        // mapper produits en Json
                        ObjectMapper objectMapper = new ObjectMapper();

                        ResultSet resultSet = selectStatement.executeQuery();

                        Marque marque= new Marque();

                        while (resultSet.next()) {
                            marque.build(resultSet);
                        }

                        String responseBody = objectMapper.writeValueAsString(marque);

                        response = new Response(request.getRequestId(), responseBody);
                    } catch (SQLException | JsonProcessingException e) {
                        response = new Response(request.getRequestId(), "Error executing SELECT_MARQUE_BY_ID query");
                        logger.error("Error executing SELECT_MARQUE_BY_ID query: {}", e.getMessage());
                    } catch (NoSuchFieldException e) {
                        throw new RuntimeException(e);
                    }
                    break;


                case "SELECT_TRANSPORTMODE_BY_ID":
                    try{
                        PreparedStatement selectStatement = connection.prepareStatement(Queries.SELECT_TRANSPORTMODE_BY_ID.query);
                        String id = request.getRequestBody().replaceAll("\"", "");

                        selectStatement.setInt(1, Integer.valueOf(id));

                        // mapper produits en Json
                        ObjectMapper objectMapper = new ObjectMapper();

                        ResultSet resultSet = selectStatement.executeQuery();

                       TransportMode transportMode = new TransportMode();

                        while (resultSet.next()) {
                           transportMode.setIdTransportMode(resultSet.getInt("idTransportMode"));
                          transportMode.build(resultSet);
                        }

                        String responseBody = objectMapper.writeValueAsString(transportMode);

                        response = new Response(request.getRequestId(), responseBody);
                    } catch (SQLException | JsonProcessingException e) {
                        response = new Response(request.getRequestId(), "Error executing SELECT_TRANSPORTMODE_BY_ID query");
                        logger.error("Error executing SELECT_TRANSPORTMODE_BY_ID query: {}", e.getMessage());
                    } catch (NoSuchFieldException e) {
                        throw new RuntimeException(e);
                    }
                    break;

                case "SELECT_VILLE_BY_ID":
                    try{
                        PreparedStatement selectStatement = connection.prepareStatement(Queries.SELECT_VILLE_BY_ID.query);
                        String id = request.getRequestBody().replaceAll("\"", "");

                        selectStatement.setInt(1, Integer.valueOf(id));

                        // mapper produits en Json
                        ObjectMapper objectMapper = new ObjectMapper();

                        ResultSet resultSet = selectStatement.executeQuery();

                        Ville ville = new Ville();

                        while (resultSet.next()) {
                            ville.setIdVille(resultSet.getInt("idVille"));
                            ville.build(resultSet);
                        }

                        String responseBody = objectMapper.writeValueAsString(ville);

                        response = new Response(request.getRequestId(), responseBody);
                    } catch (SQLException | JsonProcessingException e) {
                        response = new Response(request.getRequestId(), "Error executing SELECT_VILLE_BY_ID query");
                        logger.error("Error executing SELECT_VILLE_BY_ID query: {}", e.getMessage());
                    } catch (NoSuchFieldException e) {
                        throw new RuntimeException(e);
                    }
                    break;

                case "SELECT_ALL_SCORE":
                    try {
                        PreparedStatement selectStatement = connection.prepareStatement(Queries.SELECT_ALL_SCORE.query);
                        ResultSet resultSet = selectStatement.executeQuery();

                        Scores scores = new Scores();

                        while (resultSet.next()) {
                            Score score = new Score();
                            score.build(resultSet);
                            System.out.println("score to string :");
                            System.out.println(score.toString());
                            scores.add(score);
                        }

                        System.out.println("scores to string :");
                        System.out.println(scores.toString());

                        // mapper produits en Json
                        ObjectMapper objectMapper = new ObjectMapper();
                        String responseBody = objectMapper.writeValueAsString(scores);

                        response = new Response(request.getRequestId(), responseBody);
                    } catch (SQLException | JsonProcessingException e) {
                        response = new Response(request.getRequestId(), "Error executing SELECT_ALL_SCORE query");
                        logger.error("Error executing SELECT_ALL_SCORE query: {}", e.getMessage());
                    } catch (NoSuchFieldException e) {
                        throw new RuntimeException(e);
                    }
                    break;

                case "SELECT_ALL_CATEGORIE":
                    try {
                        PreparedStatement selectStatement = connection.prepareStatement(Queries.SELECT_ALL_CATEGORIE.query);
                        ResultSet resultSet = selectStatement.executeQuery();

                        Categories categories= new Categories();

                        while (resultSet.next()) {
                            Categorie categorie= new Categorie();
                            categorie.build(resultSet);
                            System.out.println("categorie to string :");
                            System.out.println(categories.toString());
                            categories.add(categorie);
                        }

                        System.out.println("categories to string :");
                        System.out.println(categories.toString());

                        // mapper produits en Json
                        ObjectMapper objectMapper = new ObjectMapper();
                        String responseBody = objectMapper.writeValueAsString(categories);

                        response = new Response(request.getRequestId(), responseBody);
                    } catch (SQLException | JsonProcessingException e) {
                        response = new Response(request.getRequestId(), "Error executing SELECT_ALL_CATEGORIE query");
                        logger.error("Error executing SELECT_ALL_CATEGORIE query: {}", e.getMessage());
                    } catch (NoSuchFieldException e) {
                        throw new RuntimeException(e);
                    }
                    break;


                case "SELECT_ALL_SOUS_CAT_A":
                    try{
                        PreparedStatement selectStatement = connection.prepareStatement(Queries.SELECT_ALL_SOUS_CAT_A.query);

                        String parametres= request.getRequestBody().replaceAll("\"", "");

                        String[] tabParametres = parametres.split(",");

                        selectStatement.setInt(1, Integer.valueOf(tabParametres[0]));
                        selectStatement.setInt(2, Integer.valueOf(tabParametres[1]));


                        ResultSet resultSet = selectStatement.executeQuery();

                        SousCategoriesA sousCategoriesA=new SousCategoriesA();

                        while (resultSet.next()) {
                            SousCategorieA sousCategorieA=new SousCategorieA();
                            sousCategorieA.build(resultSet);

                            sousCategoriesA.add(sousCategorieA);
                        }

                        System.out.println("sous categorie A to string  :");
                        System.out.println(sousCategoriesA.toString());

                        // mapper produits en Json
                        ObjectMapper objectMapper = new ObjectMapper();
                        String responseBody = objectMapper.writeValueAsString(sousCategoriesA);

                        response = new Response(request.getRequestId(), responseBody);
                    } catch (SQLException | JsonProcessingException e) {
                        response = new Response(request.getRequestId(), "Error executing SELECT_ALL_SOUS_CAT_A query");
                        logger.error("Error executing SELECT_ALL_SOUS_CAT_A query: {}", e.getMessage());
                    } catch (NoSuchFieldException e) {
                        throw new RuntimeException(e);
                    }
                    break;

                case "SELECT_ALL_SOUS_CAT_B":
                    try{
                        PreparedStatement selectStatement = connection.prepareStatement(Queries.SELECT_ALL_SOUS_CAT_B.query);

                        String parametres= request.getRequestBody().replaceAll("\"", "");

                        String[] tabParametres = parametres.split(",");

                        selectStatement.setInt(1, Integer.valueOf(tabParametres[0]));
                        selectStatement.setInt(2, Integer.valueOf(tabParametres[1]));
                        selectStatement.setInt(3, Integer.valueOf(tabParametres[2]));
                        selectStatement.setInt(4, Integer.valueOf(tabParametres[3]));




                        ResultSet resultSet = selectStatement.executeQuery();

                        SousCategoriesB sousCategoriesB=new SousCategoriesB();

                        while (resultSet.next()) {
                            SousCategorieB sousCategorieB=new SousCategorieB();
                            sousCategorieB.build(resultSet);

                            sousCategoriesB.add(sousCategorieB);
                        }

                        System.out.println("sous categorie B to string  :");
                        System.out.println(sousCategoriesB.toString());

                        // mapper produits en Json
                        ObjectMapper objectMapper = new ObjectMapper();
                        String responseBody = objectMapper.writeValueAsString(sousCategoriesB);

                        response = new Response(request.getRequestId(), responseBody);
                    } catch (SQLException | JsonProcessingException e) {
                        response = new Response(request.getRequestId(), "Error executing SELECT_ALL_SOUS_CAT_B query");
                        logger.error("Error executing SELECT_ALL_SOUS_CAT_B query: {}", e.getMessage());
                    } catch (NoSuchFieldException e) {
                        throw new RuntimeException(e);
                    }
                    break;

                case "SELECT_3_SUGGESTIONS":
                    try{
                        PreparedStatement selectStatement = connection.prepareStatement(Queries.SELECT_3_SUGGESTIONS.query);

                        String parametres= request.getRequestBody().replaceAll("\"", "");

                        String[] tabParametres = parametres.split(",");

                        selectStatement.setInt(1, Integer.valueOf(tabParametres[0]));
                        selectStatement.setInt(2, Integer.valueOf(tabParametres[1]));
                        selectStatement.setInt(3, Integer.valueOf(tabParametres[2]));
                        selectStatement.setDouble(4, Double.valueOf(tabParametres[3]));
                        selectStatement.setString(5, tabParametres[4]);
                        selectStatement.setInt(6,Integer.valueOf(tabParametres[5]));



                        ResultSet resultSet = selectStatement.executeQuery();

                        Produits produits= new Produits();

                        while (resultSet.next()) {
                            Produit produit=new Produit();
                            produit.build(resultSet);
                            System.out.println("produit to string :");
                            System.out.println(produit.toString());
                            produits.add(produit);
                        }

                        System.out.println("produits to string :");
                        System.out.println(produits.toString());

                        // mapper produits en Json
                        ObjectMapper objectMapper = new ObjectMapper();
                        String responseBody = objectMapper.writeValueAsString(produits);

                        response = new Response(request.getRequestId(), responseBody);
                    } catch (SQLException | JsonProcessingException e) {
                        response = new Response(request.getRequestId(), "Error executing SELECT_3_SUGGESTIONS query");
                        logger.error("Error executing SELECT_3_SUGGESTIONS query: {}", e.getMessage());
                    } catch (NoSuchFieldException e) {
                        throw new RuntimeException(e);
                    }
                    break;

                case "SELECT_ALL_PRODUCTS_BY_CATEGORIES":
                    try{
                        PreparedStatement selectStatement = connection.prepareStatement(Queries.SELECT_ALL_PRODUCTS_BY_CATEGORIES.query);

                        String parametres= request.getRequestBody().replaceAll("\"", "");

                        String[] tabParametres = parametres.split(",");

                        selectStatement.setInt(1, Integer.valueOf(tabParametres[0]));
                        selectStatement.setInt(2, Integer.valueOf(tabParametres[1]));
                        selectStatement.setInt(3, Integer.valueOf(tabParametres[2]));

                        ResultSet resultSet = selectStatement.executeQuery();

                        Produits produits= new Produits();

                        while (resultSet.next()) {
                            Produit produit=new Produit();
                            produit.build(resultSet);
                            System.out.println("produit to string :");
                            System.out.println(produit.toString());
                            produits.add(produit);
                        }

                        System.out.println("produits to string :");
                        System.out.println(produits.toString());

                        // mapper produits en Json
                        ObjectMapper objectMapper = new ObjectMapper();
                        String responseBody = objectMapper.writeValueAsString(produits);

                        response = new Response(request.getRequestId(), responseBody);
                    } catch (SQLException | JsonProcessingException e) {
                        response = new Response(request.getRequestId(), "Error executing SELECT_ALL_PRODUCTS_BY_CATEGORIES query");
                        logger.error("Error executing SELECT_ALL_PRODUCTS_BY_CATEGORIES query: {}", e.getMessage());
                    } catch (NoSuchFieldException e) {
                        throw new RuntimeException(e);
                    }
                    break;

                case "SELECT_BESTSELLER_BEFORE": // requête SELECT
                    try {
                        PreparedStatement selectStatement = connection.prepareStatement(Queries.SELECT_BESTSELLER_BEFORE.query);
                        ResultSet resultSet = selectStatement.executeQuery();
                        BestSellers bestSellers = new BestSellers();

                        while (resultSet.next()) {
                            BestSeller bestSeller = new BestSeller();
                            bestSeller.build(resultSet);
                            bestSellers.add(bestSeller);
                        }
                        ObjectMapper objectMapper = new ObjectMapper();
                        String responseBody = objectMapper.writeValueAsString(bestSellers);

                        response = new Response(request.getRequestId(), responseBody);

                    }catch (SQLException | JsonProcessingException e){
                        response = new Response(request.getRequestId(), "Error executing SELECT_BESTSELLER_BEFORE query");
                        logger.error("Error executing SELECT_BEFORE_VENTE_BY_REF query: {}", e.getMessage());
                    }catch (NoSuchFieldException e){
                        throw  new RuntimeException(e);
                    }
                    break;

                case "SELECT_BESTSELLER_AFTER": // requête SELECT
                    try {
                        PreparedStatement selectStatement = connection.prepareStatement(Queries.SELECT_BESTSELLER_AFTER.query);
                        ResultSet resultSet = selectStatement.executeQuery();
                        BestSellers bestSellers = new BestSellers();

                        while (resultSet.next()) {
                            BestSeller bestSeller = new BestSeller();
                            bestSeller.build(resultSet);
                            bestSellers.add(bestSeller);
                        }
                        ObjectMapper objectMapper = new ObjectMapper();
                        String responseBody = objectMapper.writeValueAsString(bestSellers);

                        response = new Response(request.getRequestId(), responseBody);
                    }catch (SQLException | JsonProcessingException e){
                        response = new Response(request.getRequestId(), "Error executing SELECT_BESTSELLER_AFTER query");
                        logger.error("Error executing SELECT_BEFORE_VENTE_BY_REF query: {}", e.getMessage());
                    }catch (NoSuchFieldException e){
                        throw  new RuntimeException(e);
                    }
                    break;

                case "SELECT_VENTE_BY_SCORE": // requête SELECT avec score
                    try {
                        PreparedStatement selectStatement = connection.prepareStatement(Queries.SELECT_VENTE_BY_SCORE.query);
                        String score = request.getRequestBody().replaceAll("\"", "");

                        selectStatement.setString(1, score);
                        ResultSet resultSet = selectStatement.executeQuery();

                        VenteScores venteScores = new VenteScores();

                        while (resultSet.next()) {
                            VenteScore venteScore = new VenteScore();
                            venteScore.build(resultSet);
                            venteScores.add(venteScore);
                        }


                        ObjectMapper objectMapper = new ObjectMapper();
                        String responseBody = objectMapper.writeValueAsString(venteScores);

                        response = new Response(request.getRequestId(), responseBody);
                    }catch (SQLException | JsonProcessingException e){
                        response = new Response(request.getRequestId(), "Error executing SELECT_VENTE_PAR_SCORE query");
                        logger.error("Error executing SELECT_VENTE_PAR_SCORE query: {}", e.getMessage());
                    }catch (NoSuchFieldException e) {
                        throw new RuntimeException(e);
                    }
                    break;

                case "DELETE_PATH" :
                    try{
                        PreparedStatement deleteStatement = connection.prepareStatement(Queries.DELETE_PATH.query);
                        String id = request.getRequestBody().replaceAll("\"", "");;
                        //ID d'affiche mais pas ce qu'il y a en dessous
                        deleteStatement.setInt(1, Integer.valueOf(id));
                        int affectedRows = deleteStatement.executeUpdate();
                        if(affectedRows > 0){
                            response = new Response(request.getRequestId(), "Deleted successfully");
                        } else response = new Response(request.getRequestId(), "Nothing to delete !");
                    } catch(Exception exception){
                        response = new Response(request.getRequestId(), "Error executing DELETE_PATH query");
                        logger.error("Error executing DELETE_PATH query: {}", exception.getMessage());
                    }
                    break;

                case "SELECT_SOUS_CATEGORIE_B_BY_NAME" :
                    try{
                        PreparedStatement selectStatement = connection.prepareStatement(Queries.SELECT_SOUS_CATEGORIE_B_BY_NAME.query);
                        String name = request.getRequestBody().replaceAll("\"", "");
                        selectStatement.setString(1, name);

                        ResultSet resultSet = selectStatement.executeQuery();

                        SousCategoriesB categories= new SousCategoriesB();

                        while (resultSet.next()) {
                            SousCategorieB categorie= new SousCategorieB();
                            categorie.build(resultSet);
                            System.out.println("Sous cat B to string :");
                            System.out.println(categories.toString());
                            categories.add(categorie);
                        }

                        System.out.println("Sous cat B to string :");
                        System.out.println(categories.toString());

                        // mapper produits en Json
                        ObjectMapper objectMapper = new ObjectMapper();
                        String responseBody = objectMapper.writeValueAsString(categories);

                        response = new Response(request.getRequestId(), responseBody);
                    } catch (SQLException | JsonProcessingException e) {
                        response = new Response(request.getRequestId(), "Error executing SELECT_SOUS_CATEGORIE_B_BY_NAME query");
                        logger.error("Error executing SELECT_SOUS_CATEGORIE_B_BY_NAME query: {}", e.getMessage());
                    } catch (NoSuchFieldException e) {
                        throw new RuntimeException(e);
                    }
                    break;

                }
            }

            return response;
        }


    }

