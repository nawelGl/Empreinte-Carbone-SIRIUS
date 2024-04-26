package edu.ezip.ing1.pds.client.Categories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.vandermeer.asciitable.AsciiTable;
import edu.ezip.commons.LoggingUtils;
import edu.ezip.ing1.pds.business.dto.Categorie;
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


public class SelectAllCategorie extends ClientRequest<Object, Categorie>{

    //Attributs pour lancer la requête.
    private final static String LoggingLabel = "S e l e c t - A l l - C a t e g o r i e";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String networkConfigFile = "network.yaml";
    private static final String threadName = "inserter-client";
    private static final String requestOrder = "SELECT_All_CATEGORIE";
    private static final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();


    public SelectAllCategorie(
            NetworkConfig networkConfig, int myBirthDate, Request request, Object info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public Categorie readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final Categorie categorie = mapper.readValue(body, Categorie.class);
        return categorie;
    }


    public static Categorie launchSelectEmplacementById(String ref) throws IOException, InterruptedException{
        final NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
        logger.debug("Load Network config file : {}", networkConfig.toString());

        Request request=new Request();
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
                final Categorie categorie = (Categorie) joinedClientRequest.getResult();
                final AsciiTable asciiTable = new AsciiTable();
                asciiTable.addRule();
                asciiTable.addRow(categorie.getIdCategorie(), categorie.getNomCategorie());
                asciiTable.addRule();
                logger.debug("\n{}\n", asciiTable.render());
                return categorie;
            }
        } catch(Exception e){
            System.out.println("Erreur : id inexistant");
            return null;
        }

        return null;
    }


}
