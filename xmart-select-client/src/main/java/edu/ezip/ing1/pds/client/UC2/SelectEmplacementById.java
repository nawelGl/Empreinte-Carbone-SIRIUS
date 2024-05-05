package edu.ezip.ing1.pds.client.UC2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.vandermeer.asciitable.AsciiTable;
import edu.ezip.commons.LoggingUtils;
import edu.ezip.ing1.pds.business.dto.Emplacement;
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


import static java.lang.String.valueOf;


public class SelectEmplacementById extends ClientRequest<Object, Emplacement>{

     //Attributs pour lancer la requête.
     private final static String LoggingLabel = "S e l e c t - E m p l a c e m e n t - B y - I D";
     private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
     private final static String networkConfigFile = "network.yaml";
     private static final String threadName = "inserter-client";
     private static final String requestOrder = "SELECT_EMPLACEMENT_BY_ID";
     private static final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();

 
     public SelectEmplacementById(
             NetworkConfig networkConfig, int myBirthDate, Request request, Object info, byte[] bytes)
             throws IOException {
         super(networkConfig, myBirthDate, request, info, bytes);
     }
 
     @Override
     public Emplacement readResult(String body) throws IOException {
         final ObjectMapper mapper = new ObjectMapper();
         final Emplacement emplacement = mapper.readValue(body, Emplacement.class);
         return emplacement;
     }
 
 
     public static Emplacement launchSelectEmplacementById(int id) throws IOException, InterruptedException{
         final NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
         logger.debug("Load Network config file : {}", networkConfig.toString());
         Request request = new Request();
         int birthdate = 0;
         final ObjectMapper objectMapper = new ObjectMapper();
         final String requestId = UUID.randomUUID().toString();
         request.setRequestId(requestId);
         request.setRequestContent(valueOf(id));
         request.setRequestOrder(requestOrder);
         objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
         final byte []  requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);
         LoggingUtils.logDataMultiLine(logger, Level.TRACE, requestBytes);
         final SelectEmplacementById clientRequest = new SelectEmplacementById(
                 networkConfig,
                 birthdate++, request, null, requestBytes);
         clientRequests.push(clientRequest);
 
         try {
             while (!clientRequests.isEmpty()) {
                 final ClientRequest joinedClientRequest = clientRequests.pop();
                 joinedClientRequest.join();
                 logger.debug("Thread {} complete.", joinedClientRequest.getThreadName());
                 final Emplacement emplacement = (Emplacement) joinedClientRequest.getResult();
                 final AsciiTable asciiTable = new AsciiTable();
                 asciiTable.addRule();
                 asciiTable.addRow(emplacement.getIdEmplacement(), emplacement.getAllee(), emplacement.getIdRayon(), emplacement.getIdEtage());
                 asciiTable.addRule();
                 logger.debug("\n{}\n", asciiTable.render());
                 return emplacement;
             }
         } catch(Exception e){
             System.out.println("Erreur : id inexistant");
             return null;
         }
 
         return null;
     }
 
    
}
