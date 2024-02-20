package edu.ezip.ing1.pds.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import edu.ezip.ing1.pds.business.dto.Produit;
import edu.ezip.ing1.pds.business.dto.Produits;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.ConfigLoader;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class MainInsertClient {

    private final static String LoggingLabel = "I n s e r t - P r o d u c t";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String productsToBeInserted = "products-to-be-inserted.yaml";
    private final static String networkConfigFile = "network.yaml";
    private static final String threadName = "insert-product";
    private static final String requestOrder = "INSERT_PRODUCT";
    private static final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();

    public static void main(String[] args) throws IOException, InterruptedException, SQLException {

        final Produits produits = ConfigLoader.loadConfig(Produits.class, productsToBeInserted);
        final NetworkConfig networkConfig =  ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
        logger.trace("Products loaded : {}", produits.toString());
        logger.debug("Load Network config file : {}", networkConfig.toString());

        int birthdate = 0;
        for(final Produit produit : produits.getProduits()) {
            final ObjectMapper objectMapper = new ObjectMapper();
            final String jsonifiedGuy = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(produit);
            logger.trace("Product with its JSON face : {}", jsonifiedGuy);
            final String requestId = UUID.randomUUID().toString();
            final Request request = new Request();
            request.setRequestId(requestId);
            request.setRequestOrder(requestOrder);
            request.setRequestContent(jsonifiedGuy);
            objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
            final byte []  requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);

            final InsertProductsClientRequest clientRequest = new InsertProductsClientRequest(
                                                                        networkConfig,
                                                                        birthdate++, request, produit, requestBytes);
            clientRequests.push(clientRequest);
        }

        while (!clientRequests.isEmpty()) {
            final ClientRequest clientRequest = clientRequests.pop();
            clientRequest.join();
            final Produit produit = (Produit)clientRequest.getInfo();
            logger.debug("Thread {} complete : {} {} {} --> {}",
                                    clientRequest.getThreadName(),
                                    produit.getIdEmplacement(), produit.getPaysDepart(), produit.getPaysArrivee(), produit.getCouleur(), produit.getTaille(), produit.getReference(), produit.getScore(), produit.getGenre(), produit.getEmpreinte(), produit.getIdMagasin(), produit.getIdMarque(), produit.getNomProduit(),
                                    clientRequest.getResult());
        }
    }
}
