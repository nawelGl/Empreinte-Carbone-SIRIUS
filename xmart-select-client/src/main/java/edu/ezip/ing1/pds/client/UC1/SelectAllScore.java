package edu.ezip.ing1.pds.client.UC1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.vandermeer.asciitable.AsciiTable;
import edu.ezip.commons.LoggingUtils;
import edu.ezip.ing1.pds.business.dto.Score;
import edu.ezip.ing1.pds.business.dto.Scores;
import edu.ezip.ing1.pds.client.Categories.SelectSousCategorieAByID;
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


public class SelectAllScore extends ClientRequest<Object, Scores>{

    //Attributs pour lancer la requête.
    private final static String LoggingLabel = "S e l e c t - A L L  - S c o r e";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String networkConfigFile = "network.yaml";
    private static final String threadName = "inserter-client";
    private static final String requestOrder = "SELECT_ALL_SCORE";
    private static final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();


    public SelectAllScore(
            NetworkConfig networkConfig, int myBirthDate, Request request, Object info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public Scores readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final Scores Scores = mapper.readValue(body, Scores.class);
        return Scores;
    }


    public static Scores launchSelectAllScore(Request request) throws IOException, InterruptedException{
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
                final Scores Scores = (Scores) joinedClientRequest.getResult();
                final AsciiTable asciiTable = new AsciiTable();
                for (final Score Score : Scores.getScores()) {
                    asciiTable.addRule();
                    asciiTable.addRow(Score.getlettreScore(),Score.getborneInf(),Score.getborneSup());
                }
                asciiTable.addRule();
                logger.debug("\n{}\n", asciiTable.render());
                return Scores;
            }
        } catch(Exception e){
            System.out.println("Erreur : id inexistant");
            return null;
        }

        return null;
    }


}
