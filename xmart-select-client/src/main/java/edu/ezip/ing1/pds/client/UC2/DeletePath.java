package edu.ezip.ing1.pds.client.UC2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import edu.ezip.commons.LoggingUtils;
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

public class DeletePath extends ClientRequest<Object, Object>{

    private final static String LoggingLabel = "D e l e t e - P a t h";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String networkConfigFile = "network.yaml";
    private static final String threadName = "inserter-client";
    private static final String requestOrder = "DELETE_PATH";
    private static final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();

    public DeletePath(
            NetworkConfig networkConfig, int myBirthDate, Request request, Object info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    //Voir si besoin d'extend ClientRequest car pas besoin de récupérer des données donc pas besoin de readResult() mais obligé de redéfinir
    //si on extends ClientRequest
    //Note : on l'a utilissé dans les insert alors qu'on récupère pas de données non plus
    @Override
    public String readResult(String body) throws IOException {
//        final ObjectMapper mapper = new ObjectMapper();
//        final PathPointChemin path = mapper.readValue(body, PathPointChemin.class);
//        return path;
        return null;
    }

    public static String launchDeletePath(int id) throws IOException, InterruptedException{
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
        System.out.println("AAAAAAAAAAAAAAAAAAAAAA");
        final SelectEmplacementById clientRequest = new SelectEmplacementById(
                networkConfig,
                birthdate++, request, null, requestBytes);
        clientRequests.push(clientRequest);
        try {
            System.out.println("BBBBBBBBBBBBBBBBBBBBBBBB");
            while (!clientRequests.isEmpty()) {
                final ClientRequest joinedClientRequest = clientRequests.pop();
                joinedClientRequest.join();
                logger.debug("Thread {} complete.", joinedClientRequest.getThreadName());
            }
            System.out.println("CCCCCCCCCCCCCCCCC");
        }catch(Exception exception){
            logger.error(exception.getMessage());
            return "Erreur lors de l'execution de la requête";
        }
        return "Succès de l'execution de la requête.";
    }
}
