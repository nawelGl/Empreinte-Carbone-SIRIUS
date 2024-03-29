//package edu.ezip.ing1.pds.business.server;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import edu.ezip.ing1.pds.business.dto.Produits;
//import edu.ezip.ing1.pds.business.dto.Vente;
//import edu.ezip.ing1.pds.business.dto.Ventes;
//import edu.ezip.ing1.pds.commons.Request;
//import edu.ezip.ing1.pds.commons.Response;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import  edu.ezip.ing1.pds.business.dto.Produit;
//
//import java.lang.reflect.InvocationTargetException;
//import java.sql.*;
//
//public class XMartCityService {
//
//    private final static String LoggingLabel = "B u s i n e s s - S e r v e r";
//    private final Logger logger = LoggerFactory.getLogger(LoggingLabel);
//
//    private enum Queries {
//        // SELECT_ALL_STUDENTS("SELECT t.name, t.firstname, t.group FROM \"ezip-ing1\".students t"),
//        INSERT_STUDENT("INSERT into \"ezip-ing1\".students (\"name\", \"firstname\", \"group\") values (?, ?, ?)"),
//
//
//        INSERT_PRODUCT("INSERT into \"ezip-ing1\".produit (\"idEmplacement\", \"paysDepart\", \"paysArrivee\", \"couleur\", \"taille\", \"reference\", \"score\", \"genre\", \"empreinte\", \"idMagasin\", \"idMarque\", \"nomProduit\") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"),
//
//
//        //SELECT_ALL_PRODUCTS("SELECT p.idProduit, p.idEmplacement, p.paysDepart, p.paysArrivee, p.couleur,  p.taille, p.score, p.reference, p.empreinte, p.idMagasin, p.nomProduit   FROM \"ezip-ing1\".produit p");
//        // SELECT_ALL_PRODUCTS("SELECT p.idProduit, p.idEmplacement, p.paysDepart, p.paysArrivee, p.couleur,  p.taille, p.score, p.reference, p.empreinte, p.idMagasin, p.nomProduit   FROM \"ezip-ing1\".produit p");
//        SELECT_ALL_PRODUCTS("SELECT * FROM \"ezip-ing1\".produit"),
//        SELECT_ALL_VENTES("SELECT v. FROM \"ezip-ing1\".vend v"),
//
//        SELECT_BEFORE_VENTE_BY_REFERENCE("SELECT \"reference\",\"quantite\", \"score\",\"empreinte\" FROM \"ezip-ing1\".vend\n" +
//                "INNER JOIN \"ezip-ing1\".produit\n" +
//                "ON produit.\"idProduit\" =vend.\"idProduit\"\n" +
//                "WHERE \"date\"<'2024-01-01';"),
//        SELECT_PRODUCT_BY_REFERENCE("SELECT nomProduit FROM \"ezip-ing1\".produit WHERE reference=?");
//        private final String query;
//
//        private Queries(final String query) {
//            this.query = query;
//        }
//    }
//
//    public static XMartCityService inst = null;
//    public static final XMartCityService getInstance()  {
//        if(inst == null) {
//            inst = new XMartCityService();
//        }
//        return inst;
//    }
//
//    private XMartCityService() {
//
//    }
//
//
//    public final Response dispatch(final Request request, final Connection connection)
//            throws InvocationTargetException, IllegalAccessException {
//        Response response = null;
//
//        if (request != null) {
//            String action = request.getRequestOrder();
//
//            switch (action) {
//
//                case "SELECT_ALL_PRODUCTS": // request SELECT
//                    try {
//                        PreparedStatement selectStatement = connection.prepareStatement(Queries.SELECT_ALL_PRODUCTS.query);
//                        ResultSet resultSet = selectStatement.executeQuery();
//
//                        Produits produits = new Produits();
//
//                        while (resultSet.next()) {
//                            Produit produit = new Produit();
//                            produit.build(resultSet);
//                            System.out.println("produit to string :");
//                            System.out.println(produit.toString());
//                            produits.add(produit);
//                        }
//
//                        System.out.println("produits to string :");
//                        System.out.println(produits.toString());
//
//                        // mapper produits en Json
//                        ObjectMapper objectMapper = new ObjectMapper();
//                        String responseBody = objectMapper.writeValueAsString(produits);
//
//                        response = new Response(request.getRequestId(), responseBody);
//                    } catch (SQLException | JsonProcessingException e) {
//                        response = new Response(request.getRequestId(), "Error executing SELECT_ALL_PRODUITS query");
//                        logger.error("Error executing SELECT_ALL_PRODUITS query: {}", e.getMessage());
//                    } catch (NoSuchFieldException e) {
//                        throw new RuntimeException(e);
//                    }
//                    break;
//
////
////                case "SELECT_ALL_VENTES": // requête SELECT
////                    try {
////                        PreparedStatement selectStatement = connection.prepareStatement(Queries.SELECT_ALL_VENTES.query);
////                        ResultSet resultSet = selectStatement.executeQuery();
////
////                        Ventes ventes = new Ventes();
////
////                        while (resultSet.next()) {
////                            Vente vente = new Vente();
////                            vente.setIdMagasin(resultSet.getInt("idMagasin"));
////                            vente.setIdProduit(resultSet.getInt("idProduit"));
////                            java.sql.Date sqlDate = resultSet.getDate("date");
////                            if (sqlDate != null) {
////                                long timeInMillis = sqlDate.getTime();
////                                vente.setDateEnMs(timeInMillis);
////                                vente.setDate(new Date(timeInMillis));
////                            }
////                            vente.setQuantite(resultSet.getInt("quantite"));
////                            System.out.println("vente to string :");
////                            System.out.println(vente.toString());
////                            vente.build(resultSet);
////                            ventes.add(vente);
////                        }
////                        System.out.println("###########################################");
////                        System.out.println("Ventes to String:");
////
////                        try {
////                            ObjectMapper objectMapper = new ObjectMapper();
////                            SimpleModule module = new SimpleModule();
////                            module.addSerializer(Date.class, new SqlDateSerializer());
////                            module.addDeserializer(Date.class, new DateDeserializers.SqlDateDeserializer());
////                            objectMapper.registerModule(module);
////                            String responseBody = objectMapper.writeValueAsString(ventes);
////                            response = new Response(request.getRequestId(), responseBody);
////                        } catch (JsonProcessingException e) {
////                            // Gérer l'erreur de sérialisation
////                            logger.error("Erreur lors de la sérialisation de l'objet Ventes en JSON : {}", e.getMessage());
////                            response = new Response(request.getRequestId(), "Erreur lors de la sérialisation de l'objet Ventes en JSON");
////                        }
////                    } catch (SQLException e) {
////                        response = new Response(request.getRequestId(), "Erreur lors de l'exécution de la requête SELECT_ALL_VENTES");
////                        logger.error("Erreur lors de l'exécution de la requête SELECT_ALL_VENTES: {}", e.getMessage());
////                    } catch (NoSuchFieldException e) {
////                        throw new RuntimeException(e);
////                    }
////                    break;
//
//                case "SELECT_BEFORE_VENTE_BY_REFERENCE": // requête SELECT with date
//                    try {
//                        PreparedStatement selectStatement = connection.prepareStatement(Queries.SELECT_BEFORE_VENTE_BY_REFERENCE.query);
//                        ResultSet resultSet = selectStatement.executeQuery();
//
//                        Ventes ventes = new Ventes();
//
//                        while (resultSet.next()) {
//                            Vente vente = new Vente();
//                            vente.build(resultSet);
//                            ventes.add(vente);
//                        }
//                        System.out.println("Ventes to String:");
//
//                        try {
//                            ObjectMapper objectMapper = new ObjectMapper();
//                            String responseBody = objectMapper.writeValueAsString(ventes);
//
//                            response = new Response(request.getRequestId(), responseBody);
//
//                        } catch (JsonProcessingException e) {
//                            throw new RuntimeException(e);
//                        }
//                        break;
//                    } catch (SQLException e) {
//                        throw new RuntimeException(e);
//                    } catch (NoSuchFieldException e) {
//                        throw new RuntimeException(e);
//                    }
//
//                default:
//                    // Handle unknown action
//                    response = new Response(request.getRequestId(), "Unknown action");
//                    break;
//
//            }
//        }
//
//
//        return response;
//    }
//
//
//}