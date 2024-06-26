package edu.ezip.ing1.pds.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import edu.ezip.ing1.pds.business.dto.Produit;
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

public class UpdateInfoProduct extends ClientRequest<Produit, String> {

    //===================== Attributs ============================

    private final static String LoggingLabel = "U p d a t e - I n f o - P r o d u i t ";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String networkConfigFile = "network.yaml";
    private static final String threadName = "update-produit";
        private static final String requestOrder = "UPDATE_INFO_PRODUCT";
    private static final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();

    //============================================================

    public UpdateInfoProduct(
            NetworkConfig networkConfig, int myBirthDate, Request request, Produit info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    // Fonction pour faire les inserts :

    public static void launchUpdateProduit(String responseBody) throws IOException, InterruptedException{


        final ObjectMapper objectMapper= new ObjectMapper();
        final Produit productToInsert = objectMapper.readValue(responseBody, Produit.class);



        final NetworkConfig networkConfig =  ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
        logger.trace("Points loaded : {}", productToInsert.toString());
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

        final UpdateInfoProduct clientRequest = new UpdateInfoProduct(
                networkConfig,
                birthdate++, request, productToInsert, requestBytes);
        clientRequests.push(clientRequest);

        while (!clientRequests.isEmpty()) {

            final ClientRequest clientRequest2 = clientRequests.pop();
            clientRequest2.join();
            final Produit produit= (Produit) clientRequest2.getInfo();
            logger.debug("Thread {} complete : {} {} {} --> {}",
                    clientRequest2.getThreadName(),
                    produit.getIdEmplacement(), produit.getIdVilleDepart(), produit.getIdVilleArrive(), produit.getCouleur(), produit.getTaille(), produit.getReference(), produit.getScore(), produit.getGenre(), produit.getEmpreinte(), produit.getIdMagasin(), produit.getIdMarque(), produit.getNomProduit(),produit.getIdTransportMode(),produit.getPoids(),produit.getPrix(),
                    clientRequest.getResult());
        }


    }

    @Override
    public String readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final Map<String, Integer> produitIdMap = mapper.readValue(body, Map.class);
        final String result  = produitIdMap.get("idProduit").toString();
        return result;
    }
}