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

public class XMartCityService {

    private final static String LoggingLabel = "B u s i n e s s - S e r v e r";
    private final Logger logger = LoggerFactory.getLogger(LoggingLabel);

    private enum Queries {
        INSERT_STUDENT("INSERT into \"ezip-ing1\".students (\"name\", \"firstname\", \"group\") values (?, ?, ?)"),
        INSERT_PRODUCT("INSERT into \"ezip-ing1\".produit (\"idEmplacement\", \"idVilleDepart\", \"idVilleArrive\", \"couleur\", \"taille\", \"reference\", \"score\", \"genre\", \"empreinte\", \"idMagasin\", \"idMarque\", \"nomProduit\") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"),
        INSERT_POINT("INSERT into \"ezip-ing1\".point (\"coordX\", \"coordY\", \"idRayon\") values (?, ?, ?)"),
        
        SELECT_ALL_PRODUCTS("SELECT * FROM \"ezip-ing1\".produit"),
        SELECT_PRODUCT_BY_REFERENCE("SELECT * FROM \"ezip-ing1\".produit WHERE reference=?"),
        SELECT_EMPLACEMENT_BY_ID("SELECT * FROM  \"ezip-ing1\".emplacement WHERE \"idEmplacement\" = ?"),
        SELECT_ETAGE_BY_ID("SELECT * FROM  \"ezip-ing1\".etage WHERE \"idEtage\" = ?"),
        SELECT_HIGHER_FLOOR("SELECT \"numeroEtage\" FROM \"ezip-ing1\".\"etage\" ORDER BY \"numeroEtage\" DESC LIMIT 1"),
        SELECT_POINTS_BY_ID_RAYON("SELECT * FROM  \"ezip-ing1\".point WHERE \"idRayon\" = ?"),
        SELECT_SOUS_CATEGORIE_B_BY_ID("SELECT * FROM \"ezip-ing1\".\"sousCategorieB\" WHERE \"idSousCategorieB\" = ?"),
        SELECT_SOUS_CATEGORIE_A_BY_ID("SELECT * FROM \"ezip-ing1\".\"sousCategorieA\" WHERE \"idSousCategorieA\" = ?"),
        SELECT_SOUS_CATEGORIE("SELECT * FROM \"ezip-ing1\".Categorie;"),
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

        SELECT_3_SUGGESTIONS("SELECT * FROM  \"ezip-ing1\".produit WHERE \"idCategorie\" = ? AND \"idSousCatA\" = ? AND \"idSousCatB\" = ? AND \"empreinte\" < ? AND \"couleur\" = ? ORDER BY \"empreinte\" ASC LIMIT 3"),
        SELECT_ALL_SCORE("SELECT * FROM \"ezip-ing1\".score"),
        UPDATE_INFO_PRODUCT("UPDATE \"ezip-ing1\".produit  SET \"empreinte\" = ?, \"score\" = ?  WHERE \"reference\" = ?"),

        SELECT_BESTSELLER_BEFORE("SELECT reference, score, SUM(vend.quantite)\n" +
                "FROM \"ezip-ing1\".vend\n" +
                "INNER JOIN \"ezip-ing1\".produit ON vend.\"idProduit\" = produit.\"idProduit\"\n" +
                "INNER JOIN \"ezip-ing1\".magasin ON vend.\"idMagasin\" = magasin.\"idMagasin\"\n" +
                "WHERE vend.\"date\" < magasin.\"dateInstallation\"\n" +
                "GROUP BY reference, score\n" +
                "ORDER BY SUM(vend.quantite) ASC\n" +
                "LIMIT 3;"),
        SELECT_BESTSELLER_AFTER("SELECT reference, score, SUM(vend.quantite)\n" +
                "FROM \"ezip-ing1\".vend\n" +
                "INNER JOIN \"ezip-ing1\".produit ON vend.\"idProduit\" = produit.\"idProduit\"\n" +
                "INNER JOIN \"ezip-ing1\".magasin ON vend.\"idMagasin\" = magasin.\"idMagasin\"\n" +
                "WHERE vend.\"date\" > magasin.\"dateInstallation\"\n" +
                "GROUP BY reference, score\n" +
                "ORDER BY SUM(vend.quantite) ASC\n" +
                "LIMIT 3;")

        ;






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

                case "SELECT_HIGHER_FLOOR":
                    try{
                        //TODO : completer la requete pour récupérer l'étage le plus haut.
                    } catch(Exception e){

                    }
                    break;


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

                case "SELECT_ALL_CATEGORIE":
                    try{
                        PreparedStatement selectStatement = connection.prepareStatement(Queries.SELECT_EMPLACEMENT_BY_ID.query);
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
                        response = new Response(request.getRequestId(), "Error executing SELECT_EMPLACEMENT_BY_ID query");
                        logger.error("Error executing SELECT_ALL_CATEGORIE query: {}", e.getMessage());
                    } catch (NoSuchFieldException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
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

                case "SELECT_BESTSELLER_BEFORE": // requête SELECT
                    try {
                        PreparedStatement selectStatement = connection.prepareStatement(Queries.SELECT_BESTSELLER_BEFORE.query);
                        ResultSet resultSet = selectStatement.executeQuery();
                        BestSeller bestSeller = new BestSeller();
                        while (resultSet.next()) {
                            bestSeller.build(resultSet);
                        }
                        ObjectMapper objectMapper = new ObjectMapper();
                        String responseBody = objectMapper.writeValueAsString(bestSeller);

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
                        BestSeller bestSeller = new BestSeller();
                        while (resultSet.next()) {
                            bestSeller.build(resultSet);
                        }
                        ObjectMapper objectMapper = new ObjectMapper();
                        String responseBody = objectMapper.writeValueAsString(bestSeller);

                        response = new Response(request.getRequestId(), responseBody);
                    }catch (SQLException | JsonProcessingException e){
                        response = new Response(request.getRequestId(), "Error executing SELECT_BESTSELLER_AFTER query");
                        logger.error("Error executing SELECT_BEFORE_VENTE_BY_REF query: {}", e.getMessage());
                    }catch (NoSuchFieldException e){
                        throw  new RuntimeException(e);
                    }
                    break;

                default:
                    // Handle unknown action
                    response = new Response(request.getRequestId(), "Unknown action");
                    break;

                }
            }

            return response;
        }


    }

