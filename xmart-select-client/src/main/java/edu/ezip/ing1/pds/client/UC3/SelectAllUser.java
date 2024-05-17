package edu.ezip.ing1.pds.client.UC3;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import edu.ezip.ing1.pds.business.dto.*;

import edu.ezip.commons.LoggingUtils;
import edu.ezip.ing1.pds.client.commons.ConfigLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;
import org.slf4j.event.Level;


import java.util.Deque;
import java.util.ArrayDeque;
import java.io.IOException;
import java.util.UUID;

public class SelectAllUser extends ClientRequest<Object, Users> {
    // Attributs pour les requêtes

    private  final static String LoggingLabel ="S e l e c t - A l l - U  s e r ";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);

    private final static String networkConfigFile = "network.yaml";

    private static final String requestOrder = "SELECT_All_USER";
    private static final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();

    public SelectAllUser(
            NetworkConfig networkConfig, int myBirthDate, Request request, Object info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public Users readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final Users users = mapper.readValue(body, Users.class);
        return users;
    }

// Fonction pour lancer les requêtes (à la place de main)

    public static Users launchSelectAllUsers(Request request) throws  IOException, InterruptedException{
        final NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
        logger.debug("Load Network config file: {}", networkConfig.toString());

        int birthdate=0;
        final ObjectMapper objectMapper = new ObjectMapper();
        final String requestId = UUID.randomUUID().toString();
        request.setRequestId(requestId);
        request.setRequestOrder(requestOrder);
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        final byte [] requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);
        LoggingUtils.logDataMultiLine(logger, Level.TRACE, requestBytes);

        final SelectAllUser clientRequest = new SelectAllUser(
                networkConfig,
                birthdate++,    request, null,requestBytes);
        clientRequests.push(clientRequest);

        while (!clientRequests.isEmpty()) {
            final ClientRequest joinedClientRequest = clientRequests.pop();
            joinedClientRequest.join();
            logger.debug("Thread {} complete.", joinedClientRequest.getThreadName());
            final Users users = (Users) joinedClientRequest.getResult();

            //return asciiTable.render();
            return users;
        }

        return null;






    }
}
