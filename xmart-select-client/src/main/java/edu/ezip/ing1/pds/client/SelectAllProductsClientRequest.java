package edu.ezip.ing1.pds.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.vandermeer.asciitable.AsciiTable;
import edu.ezip.commons.LoggingUtils;
import edu.ezip.ing1.pds.business.dto.Produit;
import edu.ezip.ing1.pds.business.dto.Produits;
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

public class SelectAllProductsClientRequest extends ClientRequest<Object, Produits> {

    //Attributs pour lancer la requête.
    private final static String LoggingLabel = "S e l e c t - P r o d u c t";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String studentsToBeInserted = "students-to-be-inserted.yaml";
    private final static String networkConfigFile = "network.yaml";
    private static final String threadName = "inserter-client";
    private static final String requestOrder = "SELECT_ALL_PRODUCTS";
    private static final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();


    public SelectAllProductsClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, Object info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public Produits readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final Produits produits = mapper.readValue(body, Produits.class);
        return produits;
    }


    //==================================
    //Fonction pour lancer la requête (à la place du main) :
    public static String launchSelectAllProducts() throws IOException, InterruptedException{
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
        final SelectAllProductsClientRequest clientRequest = new SelectAllProductsClientRequest(
                networkConfig,
                birthdate++, request, null, requestBytes);
        clientRequests.push(clientRequest);


        while (!clientRequests.isEmpty()) {
            final ClientRequest joinedClientRequest = clientRequests.pop();
            joinedClientRequest.join();
            logger.debug("Thread {} complete.", joinedClientRequest.getThreadName());
            final Produits produits = (Produits) joinedClientRequest.getResult();
            final AsciiTable asciiTable = new AsciiTable();
            for (final Produit produit : produits.getProduits()) {
                asciiTable.addRule();
                asciiTable.addRow(produit.getIdProduit(), produit.getIdEmplacement(), produit.getIdVilleDepart(), produit.getIdVilleDepart(), produit.getCouleur(), produit.getTaille(), produit.getReference(), produit.getScore(), produit.getGenre(), produit.getEmpreinte(), produit.getIdMagasin(), produit.getIdMarque(), produit.getNomProduit(), produit.getIdsouscatB());
            }
            asciiTable.addRule();
            logger.debug("\n{}\n", asciiTable.render());
            return  asciiTable.render();
        }
        return null;
    }

}