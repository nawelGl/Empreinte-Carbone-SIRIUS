package edu.ezip.ing1.pds.business.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.Produit;
import edu.ezip.ing1.pds.business.dto.Produits;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.commons.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class XMartCityService {

    private final static String LoggingLabel = "B u s i n e s s - S e r v e r";
    private final Logger logger = LoggerFactory.getLogger(LoggingLabel);

    private enum Queries {
       // SELECT_ALL_STUDENTS("SELECT t.name, t.firstname, t.group FROM \"ezip-ing1\".students t"),
        INSERT_STUDENT("INSERT into \"ezip-ing1\".students (\"name\", \"firstname\", \"group\") values (?, ?, ?)"),

        SELECT_ALL_PRODUCTS("SELECT p.id_produit, p.id_emplacement, p.pays_depart, p.pays_arrive, p.couleur,  p.taille, p.score, p.reference, p.empreinte, p.id_magasin, p.nom_produit   FROM \"ezip-ing1\".produit p");
        private final String query;

        private Queries(final String query) {
            this.query = query;
        }
    }

    public static XMartCityService inst = null;
    public static final XMartCityService getInstance()  {
        if(inst == null) {
            inst = new XMartCityService();
        }
        return inst;
    }

    private XMartCityService() {

    }


    public final Response dispatch(final Request request, final Connection connection)
            throws InvocationTargetException, IllegalAccessException {
        Response response = null;

        if (request != null) {
            String action = request.getRequestOrder();

            switch (action) {
//                case "SELECT_ALL_STUDENTS": // request SELECT
//                    try {
//                        PreparedStatement selectStatement = connection.prepareStatement(Queries.SELECT_ALL_STUDENTS.query);
//                        ResultSet resultSet = selectStatement.executeQuery();
//
//                        Students students = new Students();
//
//                        while (resultSet.next()) {
//                            Student student = new Student();
////                            student.setName(resultSet.getString("name"));
////                            student.setFirstname(resultSet.getString("firstname"));
////                            student.setGroup(resultSet.getString("group"));
//                            student.build(resultSet);
//                            students.add(student);
//                        }
//
//                        // mapper students en Json
//                        ObjectMapper objectMapper = new ObjectMapper();
//                        String responseBody = objectMapper.writeValueAsString(students);
//
//                        response = new Response(request.getRequestId(), responseBody);
//                    } catch (SQLException | JsonProcessingException e) {
//                        response = new Response(request.getRequestId(), "Error executing SELECT_ALL_STUDENTS query");
//                        logger.error("Error executing SELECT_ALL_STUDENTS query: {}", e.getMessage());
//                    } catch (NoSuchFieldException e) {
//                        throw new RuntimeException(e);
//                    }
//                    break;

//                case "INSERT_STUDENT":
//                    try {
//                        String requestBody = request.getRequestBody();
//                        ObjectMapper objectMapper = new ObjectMapper();
//                        Student student = objectMapper.readValue(requestBody, Student.class);
//
//                        PreparedStatement insertStatement = connection.prepareStatement(Queries.INSERT_STUDENT.query);
//                        insertStatement.setString(1, student.getName());
//                        insertStatement.setString(2, student.getFirstname());
//                        insertStatement.setString(3, student.getGroup());
//
//
//
//                        int rowsAffected = insertStatement.executeUpdate();
//
//                        if (rowsAffected > 0) {
//                            response = new Response(request.getRequestId(),String.format("{\"student_id\": %d}", rowsAffected));
//                        } else {
//                            response = new Response(request.getRequestId(), "Failed to insert student");
//                        }
//                    } catch (SQLException | IOException e) {
//                        response = new Response(request.getRequestId(), "Error executing INSERT_STUDENT query");
//                        logger.error("Error executing INSERT_STUDENT query: {}", e.getMessage());
//                    }
//                    break;

                case "SELECT_ALL_PRODUCTS": // request SELECT
                    try {
                        PreparedStatement selectStatement = connection.prepareStatement(Queries.SELECT_ALL_PRODUCTS.query);
                        ResultSet resultSet = selectStatement.executeQuery();

                        Produits produits = new Produits();
                        System.out.println("==================================");
//                        while (resultSet.next()) {
//                            Produit produit = new Produit();
//                            produit.build(resultSet);
//                            produits.add(produit);
//                        }
                        System.out.println(produits);

                        System.out.println("==================================");


                        // mapper students en Json
                        ObjectMapper objectMapper = new ObjectMapper();
                        String responseBody = objectMapper.writeValueAsString(produits);

                        response = new Response(request.getRequestId(), responseBody);
//                    } catch (SQLException | JsonProcessingException e) {
//                        response = new Response(request.getRequestId(), "Error executing SELECT_ALL_PRODUITS query");
//                        logger.error("Error executing SELECT_ALL_PRODUITS query: {}", e.getMessage());
//                    } catch (NoSuchFieldException e) {
//                        throw new RuntimeException(e);
                      }
                        catch(Exception e){
                            System.out.println("==================================");
                            System.out.println(e.getMessage());
                            System.out.println("==================================");
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




//    public final Response dispatch(final Request request, final Connection connection)
//            throws InvocationTargetException, IllegalAccessException {
//        Response response = null;
//
//
//        return response;
//    }


}
