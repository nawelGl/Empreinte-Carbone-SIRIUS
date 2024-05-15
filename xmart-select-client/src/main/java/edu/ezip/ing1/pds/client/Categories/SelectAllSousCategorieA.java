package edu.ezip.ing1.pds.client.Categories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.vandermeer.asciitable.AsciiTable;
import edu.ezip.commons.LoggingUtils;
import edu.ezip.ing1.pds.business.dto.Produit;
import edu.ezip.ing1.pds.business.dto.Produits;
import edu.ezip.ing1.pds.business.dto.SousCategorieA;
import edu.ezip.ing1.pds.business.dto.SousCategoriesA;
import edu.ezip.ing1.pds.client.Categories.SelectSousCategorieAByID;
import edu.ezip.ing1.pds.client.SelectProductByReference;
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


public class SelectAllSousCategorieA extends ClientRequest<Object, SousCategoriesA>{

    //Attributs pour lancer la requête.
    private final static String LoggingLabel = "S e l e c t - A L L - S o u s - C a t e g o r i e - A";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String networkConfigFile = "network.yaml";
    private static final String threadName = "selecter-client";
    private static final String requestOrder = "SELECT_ALL_SOUS_CAT_A";
    private static final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();


    public SelectAllSousCategorieA(
            NetworkConfig networkConfig, int myBirthDate, Request request, Object info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public SousCategoriesA readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final SousCategoriesA sousCategoriesA = mapper.readValue(body, SousCategoriesA.class);
        return sousCategoriesA;
    }


    public static SousCategoriesA launchSelectAllSousCatA(String parametres) throws IOException, InterruptedException{
        final NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
        logger.debug("Load Network config file : {}", networkConfig.toString());

        int birthdate = 0;
        final ObjectMapper objectMapper = new ObjectMapper();

        final String requestId = UUID.randomUUID().toString();
        Request request=new Request();
        request.setRequestContent(parametres);
        request.setRequestId(requestId);
        request.setRequestOrder(requestOrder);
        request.setRequestContent(parametres);
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        final byte []  requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);
        LoggingUtils.logDataMultiLine(logger, Level.TRACE, requestBytes);
        final SelectAllSousCategorieA clientRequest = new SelectAllSousCategorieA(
                networkConfig,
                birthdate++, request, null, requestBytes);
        clientRequests.push(clientRequest);


        try {
            while (!clientRequests.isEmpty()) {
                final ClientRequest joinedClientRequest = clientRequests.pop();
                joinedClientRequest.join();
                logger.debug("Thread {} complete.", joinedClientRequest.getThreadName());
                final SousCategoriesA sousCategoriesA = (SousCategoriesA) joinedClientRequest.getResult();
                final AsciiTable asciiTable = new AsciiTable();
                for (final SousCategorieA sousCategorieA: sousCategoriesA.getSousCategoriesA()) {
                    asciiTable.addRule();
                    asciiTable.addRow(sousCategorieA.getIdSouscatA(), sousCategorieA.getNomSouscatA(),sousCategorieA.getcodeGenre());
                }
                asciiTable.addRule();
                logger.debug("\n{}\n", asciiTable.render());
                return sousCategoriesA;
            }
        } catch(Exception e){
            System.out.println("Erreur : id inexistant");
            return null;
        }

        return null;
    }


}
