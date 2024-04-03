package edu.ezip.ing1.pds.business.server;

import com.fasterxml.jackson.core.JsonProcessingException;
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
        SELECT_SOUS_CATEGORIE_B_BY_ID("SELECT * FROM \"ezip-ing1\".\"sousCategorieB\" WHERE \"idSousCategorieB\" = ?"),
        SELECT_SOUS_CATEGORIE_A_BY_ID("SELECT * FROM \"ezip-ing1\".\"sousCategorieA\" WHERE \"idSousCategorieA\" = ?"),
        SELECT_SOUS_CATEGORIE("SELECT * FROM \"ezip-ing1\".Categorie;"),
        SELECT_BEFORE_VENTE_BY_REFERENCE("SELECT \"reference\",\"quantite\", \"score\",\"empreinte\" FROM \"ezip-ing1\".vend\n" +
                "INNER JOIN \"ezip-ing1\".produit\n" +
                "ON produit.\"idProduit\" =vend.\"idProduit\"\n" +
                "WHERE \"reference\"=? AND \"date\"<'2024-01-01';"),
        SELECT_AFTER_VENTE_BY_REFERENCE("SELECT \"reference\",\"quantite\", \"score\",\"empreinte\" FROM \"ezip-ing1\".vend\n" +
                "INNER JOIN \"ezip-ing1\".produit\n" +
                "ON produit.\"idProduit\" =vend.\"idProduit\"\n" +
                "WHERE \"reference\"=? AND \"date\">'2024-01-01';");

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
                        String requestBody = request.getRequestBody().get(0);
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
                        insertStatement.setFloat(3, produit.getEmpreinte());
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
                        String requestBody = request.getRequestBody().get(0);
                        System.out.println("REQUEST BODY : " + requestBody);
                      //  PreparedStatement insertStatement = connection.prepareStatement(Queries.INSERT_POINT.query);
                        //ObjectMapper objectMapper = new ObjectMapper();
//                        Path points = objectMapper.readValue(requestBody, Path.class);

//                        System.out.println("Test sur l'objet points de type Path avec l'object mapper : ");
//                        System.out.println(points.toString());
//                        System.out.println(points.getPoints().get(0).x);
//                        System.out.println(points.getPoints().get(0).y);
//                        String sTest = points.toString();

//                        String[] parts = sTest.split("\"x\":|,\"y\":|\\}");
//                        int[] xCoordinates = new int[parts.length - 1];
//                        int[] yCoordinates = new int[parts.length - 1];
//
//                        for (int i = 1; i < parts.length; i += 2) {
//                            xCoordinates[i - 1] = (int) Double.parseDouble(parts[i]);
//                            yCoordinates[i - 1] = (int) Double.parseDouble(parts[i + 1]);
//                        }
//
//                        System.out.println("X Coordinates: " + Arrays.toString(xCoordinates));
//                        System.out.println("Y Coordinates: " + Arrays.toString(yCoordinates));



//                        for (int i = 0; i < request.getRequestBody().size(); i++) {
//                            requestBody = request.getRequestBody().get(i);
//                            points = objectMapper.readValue(requestBody, Path.class);
//                            if(i == 0){
//                                insertStatement.setInt(i+1, points.getCoordX());
//                            } else if (i == 1) {
//                                insertStatement.setInt(i+1, points.getCoordY());
//                            } else if (i == 2){
//                                insertStatement.setInt(i+1, points.getIdRayon());
//                            }
//                        }
//
//                        points.build(insertStatement);

//                        int rowsAffected = insertStatement.executeUpdate();
//
//                        if (rowsAffected > 0) {
//                            response = new Response(request.getRequestId(), String.format("{\"idPoint\": %d}", rowsAffected));
//                        } else {
//                            response = new Response(request.getRequestId(), "Failed to insert point");
//                        }
                    } catch (/*SQLException | IOException e*/ Exception e) {
                        response = new Response(request.getRequestId(), "Error executing INSERT_POINT query");
                        logger.error("Error executing INSERT_POINT query: {}", e.getMessage());
                    }
                        break;


                case "SELECT_PRODUCT_BY_REFERENCE":

                    try{
                    PreparedStatement selectStatement = connection.prepareStatement(Queries.SELECT_PRODUCT_BY_REFERENCE.query);
                        String ref = request.getRequestBody().get(0).replaceAll("\"", "").replaceAll("]", "").replaceAll("\\[", "");
                        System.out.println("REFERENCE PRODUIT : " + ref);
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
                        String ref = request.getRequestBody().get(0).replaceAll("\"", "").replaceAll("]", "").replaceAll("\\[", "");

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
                        String ref = request.getRequestBody().get(0).replaceAll("\"", "");

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
                        String id = request.getRequestBody().get(0).replaceAll("\"", "").replaceAll("]", "").replaceAll("\\[", "");

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

                case "SELECT_SOUS_CATEGORIE_B_BY_ID":
                    try{
                        PreparedStatement selectStatement = connection.prepareStatement(Queries.SELECT_SOUS_CATEGORIE_B_BY_ID.query);
                        String id = request.getRequestBody().get(0).replaceAll("\"", "");

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
                        String id = request.getRequestBody().get(0).replaceAll("\"", "");

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
                        String id = request.getRequestBody().get(0).replaceAll("\"", "");

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

                default:
                    // Handle unknown action
                    response = new Response(request.getRequestId(), "Unknown action");
                    break;
                }
            }

            return response;
        }


    }

