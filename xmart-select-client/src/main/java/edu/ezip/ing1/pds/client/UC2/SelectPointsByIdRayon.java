package edu.ezip.ing1.pds.client.UC2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.vandermeer.asciitable.AsciiTable;
import edu.ezip.commons.LoggingUtils;
import edu.ezip.ing1.pds.business.dto.*;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.ConfigLoader;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.UUID;


public class SelectPointsByIdRayon extends ClientRequest<Object, PointChemin>{

     //Attributs pour lancer la requête.
     private final static String LoggingLabel = "S e l e c t - P o i n t s - B y - I d - r a y o n";
     private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
     private final static String networkConfigFile = "network.yaml";
     private static final String threadName = "inserter-client";
     private static final String requestOrder = "SELECT_POINTS_BY_ID_RAYON";
     private static final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();


     public SelectPointsByIdRayon(
             NetworkConfig networkConfig, int myBirthDate, Request request, Object info, byte[] bytes)
             throws IOException {
         super(networkConfig, myBirthDate, request, info, bytes);
     }
 
     @Override
     public PointChemin readResult(String body) throws IOException {
         final ObjectMapper mapper = new ObjectMapper();
         final PointChemin pointChemin = mapper.readValue(body, PointChemin.class);
         return pointChemin;
     }
 
 
     public static Etage launchSelectPointsByIdRayon(String id) throws IOException, InterruptedException{
         final NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
         logger.debug("Load Network config file : {}", networkConfig.toString());
 
         int birthdate = 0;
         final ObjectMapper objectMapper = new ObjectMapper();
         final String requestId = UUID.randomUUID().toString();
         Request request = new Request();
         request.setRequestId(requestId);
         request.setRequestContent(id);
         request.setRequestOrder(requestOrder);
         objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
         final byte []  requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);
         LoggingUtils.logDataMultiLine(logger, Level.TRACE, requestBytes);
         final SelectPointsByIdRayon clientRequest = new SelectPointsByIdRayon(
                 networkConfig,
                 birthdate++, request, null, requestBytes);
         clientRequests.push(clientRequest);

         //TODO : Faire une collection de points pour les récupérer ... les mettre dans un objet de type Path
         try {
             while (!clientRequests.isEmpty()) {
                 final ClientRequest joinedClientRequest = clientRequests.pop();
                 joinedClientRequest.join();
                 logger.debug("Thread {} complete.", joinedClientRequest.getThreadName());
                 final PathPointChemin points = (PathPointChemin) joinedClientRequest.getResult();
                 final AsciiTable asciiTable = new AsciiTable();
                 for (final PointChemin point : points.getPoints()) {
                     asciiTable.addRule();
                     asciiTable.addRow(point.getIdPoint(), point.getCoordX(), point.getCoordY(), point.getIdRayon());
                 }
                 asciiTable.addRule();
                 logger.debug("\n{}\n", asciiTable.render());
             }
         } catch(Exception e){
             System.out.println("Erreur : id rayon inexistant");
             return null;
         }
 
         return null;
     }
 
    
}
