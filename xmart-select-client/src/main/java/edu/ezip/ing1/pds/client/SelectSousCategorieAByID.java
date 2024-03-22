package edu.ezip.ing1.pds.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.vandermeer.asciitable.AsciiTable;
import edu.ezip.commons.LoggingUtils;
import edu.ezip.ing1.pds.business.dto.SousCategorieA;
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


public class SelectSousCategorieAByID extends ClientRequest<Object, SousCategorieA>{

     //Attributs pour lancer la requête.
     private final static String LoggingLabel = "S e l e c t - S o u s - C a t e g o r i e - A - B y - I D";
     private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
     private final static String networkConfigFile = "network.yaml";
     private static final String threadName = "inserter-client";
     private static final String requestOrder = "SELECT_SOUS_CATEGORIE_A_BY_ID";
     private static final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();


     public SelectSousCategorieAByID(
             NetworkConfig networkConfig, int myBirthDate, Request request, Object info, byte[] bytes)
             throws IOException {
         super(networkConfig, myBirthDate, request, info, bytes);
     }
 
     @Override
     public SousCategorieA readResult(String body) throws IOException {
         final ObjectMapper mapper = new ObjectMapper();
         final SousCategorieA sousCategorieA = mapper.readValue(body, SousCategorieA.class);
         return sousCategorieA;
     }
 
 
     public static SousCategorieA launchSelectEmplacementById(Request request) throws IOException, InterruptedException{
         final NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
         logger.debug("Load Network config file : {}", networkConfig.toString());
 
         int birthdate = 0;
         final ObjectMapper objectMapper = new ObjectMapper();
         final String requestId = UUID.randomUUID().toString();
         request.setRequestId(requestId);
         request.setRequestOrder(requestOrder);
         objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
         final byte []  requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);
         LoggingUtils.logDataMultiLine(logger, Level.TRACE, requestBytes);
         final SelectSousCategorieAByID clientRequest = new SelectSousCategorieAByID(
                 networkConfig,
                 birthdate++, request, null, requestBytes);
         clientRequests.push(clientRequest);
 
         try {
             while (!clientRequests.isEmpty()) {
                 final ClientRequest joinedClientRequest = clientRequests.pop();
                 joinedClientRequest.join();
                 logger.debug("Thread {} complete.", joinedClientRequest.getThreadName());
                 final SousCategorieA sousCategorieA = (SousCategorieA) joinedClientRequest.getResult();
                 final AsciiTable asciiTable = new AsciiTable();
                 asciiTable.addRule();
                 asciiTable.addRow(sousCategorieA.getIdSouscatA(), sousCategorieA.getNomSouscatA(), sousCategorieA.getIdCategorie());
                 asciiTable.addRule();
                 logger.debug("\n{}\n", asciiTable.render());
                 return sousCategorieA;
             }
         } catch(Exception e){
             System.out.println("Erreur : id inexistant");
             return null;
         }
 
         return null;
     }
 
    
}
