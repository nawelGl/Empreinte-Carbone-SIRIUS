package edu.ezip.ing1.pds.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import edu.ezip.ing1.pds.business.dto.PointChemin;
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

public class InsertPointsRequest extends ClientRequest<PointChemin, String> {

    //===================== Attributs ============================

    private final static String LoggingLabel = "I n s e r t - P o i n t";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String networkConfigFile = "network.yaml";
    private static final String threadName = "insert-point";
    private static final String requestOrder = "INSERT_POINT";
    private static final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();

    //============================================================

    public InsertPointsRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, PointChemin info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    // Fonction pour faire les inserts :

    public static void insertPoints(String responseBody) throws IOException, InterruptedException{


        final ObjectMapper objectMapper= new ObjectMapper();
       final PointChemin pointCheminToInsert = objectMapper.readValue(responseBody, PointChemin.class);


        //final PointChemin pointCheminToInsert = new PointChemin();
        final NetworkConfig networkConfig =  ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
        logger.trace("Points loaded : {}", pointCheminToInsert.toString());
        logger.debug("Load Network config file : {}", networkConfig.toString());

        int birthdate = 0;
        Request request = new Request();
        request.setRequestContent(responseBody);

//       final ObjectMapper objectMapper = new ObjectMapper();
        final String jsonifiedGuy = request.getRequestBody();

        logger.trace("Point with its JSON face : {}", jsonifiedGuy);
        final String requestId = UUID.randomUUID().toString();
        request.setRequestId(requestId);
        request.setRequestOrder(requestOrder);
        request.setRequestContent(jsonifiedGuy);
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        final byte []  requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);

        final InsertPointsRequest clientRequest = new InsertPointsRequest(
                networkConfig,
                birthdate++, request, pointCheminToInsert, requestBytes);
        clientRequests.push(clientRequest);

        while (!clientRequests.isEmpty()) {
            final ClientRequest clientRequest2 = clientRequests.pop();
            clientRequest2.join();
            final PointChemin pointChemin = (PointChemin)clientRequest2.getInfo();
            logger.debug("Thread {} complete : {} {} {} --> {}",
                    clientRequest2.getThreadName(),
                    pointChemin.getIdPoint(), pointChemin.getCoordX(), pointChemin.getCoordY(), pointChemin.getIdRayon(),
                    clientRequest2.getResult());
        }

    }

    @Override
    public String readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final Map<String, Integer> produitIdMap = mapper.readValue(body, Map.class);
        final String result  = produitIdMap.get("idPoint").toString();
        return result;
    }
}