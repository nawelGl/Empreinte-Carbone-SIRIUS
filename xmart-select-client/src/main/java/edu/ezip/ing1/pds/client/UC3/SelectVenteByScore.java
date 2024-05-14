package edu.ezip.ing1.pds.client.UC3;

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

public class SelectVenteByScore extends ClientRequest<Object, VenteScores> {

    //Attributs pour lancer la requête.
    private final static String LoggingLabel = "S e l e c t -  V e n t e - B y - S c o r e";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);

    private final static String networkConfigFile = "network.yaml";

    private static final String requestOrder = "SELECT_VENTE_BY_SCORE";
    private static final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();

    protected static int totalQuantite;




    public SelectVenteByScore(
            NetworkConfig networkConfig, int myBirthDate, Request request, Object info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public VenteScores readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final VenteScores venteScores = mapper.readValue(body, VenteScores.class);
        return venteScores;
    }

    public static VenteScores launchSelectVenteByScore(Request request) throws IOException, InterruptedException{
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
        final SelectVenteByScore clientRequest = new SelectVenteByScore(
                networkConfig,
                birthdate++, request, null, requestBytes);
        clientRequests.push(clientRequest);

        totalQuantite=0;
        try {
            while (!clientRequests.isEmpty()) {
                final ClientRequest joinedClientRequest = clientRequests.pop();
                joinedClientRequest.join();
                logger.debug("Thread {} complete.", joinedClientRequest.getThreadName());
                final VenteScores venteScores = (VenteScores) joinedClientRequest.getResult();

                final AsciiTable asciiTable = new AsciiTable();

                for (final VenteScore venteScore : venteScores.getVenteScores()) {
                    asciiTable.addRule();
                    asciiTable.addRow(venteScore.getMonth(), venteScore.getScore(), venteScore.getSum());

                }
                asciiTable.addRule();
                logger.debug("\n{}\n", asciiTable.render());
                return venteScores;
            }
        } catch(Exception e){
            System.out.println("Erreur : référence inexistante");
            return null;
        }

        return null;
    }

}