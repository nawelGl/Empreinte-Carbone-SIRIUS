package edu.ezip.ing1.pds.client.UC1;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.vandermeer.asciitable.AsciiTable;
import edu.ezip.commons.LoggingUtils;
import edu.ezip.ing1.pds.business.dto.Score;
import edu.ezip.ing1.pds.business.dto.Scores;

import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.ConfigLoader;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.UUID;


public class SelectAllReferences extends ClientRequest<Object, ArrayList<Integer>>{

    //Attributs pour lancer la requête.
    private final static String LoggingLabel = "S e l e c t - A L L  - R e f e r e n c e";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String networkConfigFile = "network.yaml";
    private static final String threadName = "inserter-client";
    private static final String requestOrder = "SELECT_ALL_REFERENCES";
    private static final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();


    public SelectAllReferences(
            NetworkConfig networkConfig, int myBirthDate, Request request, Object info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public ArrayList<Integer> readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final ArrayList<Integer> referencesList;
        // creer un type reference qu'on va pouvoir utiliser pour deserialiser
        TypeReference<ArrayList<Integer>> typeReference = new TypeReference<ArrayList<Integer>>() {};
        referencesList= mapper.readValue(body, typeReference);
        return referencesList;
    }


    public static ArrayList<Integer> launchSelectAllReferences() throws IOException, InterruptedException{
        final NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
        logger.debug("Load Network config file : {}", networkConfig.toString());

        int birthdate = 0;
        final ObjectMapper objectMapper = new ObjectMapper();
        final String requestId = UUID.randomUUID().toString();
        final Request request = new Request();
        request.setRequestId(requestId);
        request.setRequestOrder(requestOrder);
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        final byte []  requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);
        LoggingUtils.logDataMultiLine(logger, Level.TRACE, requestBytes);
        final SelectAllReferences clientRequest = new SelectAllReferences(
                networkConfig,
                birthdate++, request, null, requestBytes);
        clientRequests.push(clientRequest);


        try {
            while (!clientRequests.isEmpty()) {
                final ClientRequest joinedClientRequest = clientRequests.pop();
                joinedClientRequest.join();
                logger.debug("Thread {} complete.", joinedClientRequest.getThreadName());
                final ArrayList<Integer> referencesList = (ArrayList<Integer>) joinedClientRequest.getResult();
                final AsciiTable asciiTable = new AsciiTable();
                for (final Integer reference : referencesList) {
                    asciiTable.addRule();
                    asciiTable.addRow(reference);
                }
                asciiTable.addRule();
                logger.debug("\n{}\n", asciiTable.render());
                return referencesList; // Retourne la liste de références
            }
        } catch(Exception e){
            System.out.println("Erreur : id inexistant");
            return null;
        }

        return null;
    }


}
