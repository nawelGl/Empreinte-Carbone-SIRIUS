package edu.ezip.ing1.pds.client.UC2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.vandermeer.asciitable.AsciiTable;
import edu.ezip.commons.LoggingUtils;
import edu.ezip.ing1.pds.business.dto.Etage;
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


public class SelectEtageById extends ClientRequest<Object, Etage>{

     //Attributs pour lancer la requête.
     private final static String LoggingLabel = "S e l e c t - E t a g e - B y - I D";
     private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
     private final static String networkConfigFile = "network.yaml";
     private static final String threadName = "inserter-client";
     private static final String requestOrder = "SELECT_ETAGE_BY_ID";
     private static final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();


     public SelectEtageById(
             NetworkConfig networkConfig, int myBirthDate, Request request, Object info, byte[] bytes)
             throws IOException {
         super(networkConfig, myBirthDate, request, info, bytes);
     }
 
     @Override
     public Etage readResult(String body) throws IOException {
         final ObjectMapper mapper = new ObjectMapper();
         final Etage etage = mapper.readValue(body, Etage.class);
         return etage;
     }
 
 
     public static Etage lauchSelectEtageById(String id) throws IOException, InterruptedException{
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
         final SelectEtageById clientRequest = new SelectEtageById(
                 networkConfig,
                 birthdate++, request, null, requestBytes);
         clientRequests.push(clientRequest);
 
         try {
             while (!clientRequests.isEmpty()) {
                 final ClientRequest joinedClientRequest = clientRequests.pop();
                 joinedClientRequest.join();
                 logger.debug("Thread {} complete.", joinedClientRequest.getThreadName());
                 final Etage etage = (Etage) joinedClientRequest.getResult();
                 final AsciiTable asciiTable = new AsciiTable();
                 asciiTable.addRule();
                 asciiTable.addRow(etage.getIdEtage(), etage.getDescription(), etage.getNumeroEtage());
                 asciiTable.addRule();
                 logger.debug("\n{}\n", asciiTable.render());
                 return etage;
             }
         } catch(Exception e){
             System.out.println("Erreur : id inexistant");
             return null;
         }
 
         return null;
     }
 
    
}
