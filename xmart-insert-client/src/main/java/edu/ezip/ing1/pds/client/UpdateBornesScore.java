package edu.ezip.ing1.pds.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import edu.ezip.ing1.pds.business.dto.Produit;
import edu.ezip.ing1.pds.business.dto.Score;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.ConfigLoader;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateBornesScore extends ClientRequest<Score, String> {

    //===================== Attributs ============================

    private final static String LoggingLabel = "U p d a t e - B o r n e s - S c o r e ";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String networkConfigFile = "network.yaml";
    private static final String threadName = "update-score";
    private static final String requestOrder = "UPDATE_BORNES_SCORE";
    private static final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();

    //============================================================

    public UpdateBornesScore(
            NetworkConfig networkConfig, int myBirthDate, Request request, Score info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    // Fonction pour faire les inserts :

    public static void launchUpdateBornesScore(String responseBody) throws IOException, InterruptedException{


        final ObjectMapper objectMapper= new ObjectMapper();
        final Score scoreToUpdate = objectMapper.readValue(responseBody, Score.class);



        final NetworkConfig networkConfig =  ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
        logger.trace("Points loaded : {}", scoreToUpdate.toString());
        logger.debug("Load Network config file : {}", networkConfig.toString());

        int birthdate = 0;
        Request request = new Request();
        request.setRequestContent(responseBody);
        System.out.println("responseBody   "+responseBody);
        System.out.println("request.getRequestBody() " +request.getRequestBody());


        final String jsonifiedGuy = request.getRequestBody();
        System.out.println("jsonifiedGuy  "  +jsonifiedGuy);

        logger.trace("Point with its JSON face : {}", jsonifiedGuy);
        final String requestId = UUID.randomUUID().toString();
        request.setRequestId(requestId);
        request.setRequestOrder(requestOrder);
        request.setRequestContent(jsonifiedGuy);
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        final byte []  requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);

        final UpdateBornesScore clientRequest = new UpdateBornesScore(
                networkConfig,
                birthdate++, request, scoreToUpdate, requestBytes);
        clientRequests.push(clientRequest);

        while (!clientRequests.isEmpty()) {

            final ClientRequest clientRequest2 = clientRequests.pop();
            clientRequest2.join();
            final Score score= (Score) clientRequest2.getInfo();
            logger.debug("Thread {} complete : {} {} {} --> {}",
                    clientRequest2.getThreadName(),
                    score.getlettreScore(),score.getborneInf(),score.getborneSup(),
                    clientRequest.getResult());
        }


    }

    @Override
    public String readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final Map<String, Integer> scoreIdMap = mapper.readValue(body, Map.class);
        final String result  = scoreIdMap.get("idScore").toString();
        return result;
    }
}