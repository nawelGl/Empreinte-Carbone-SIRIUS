package edu.ezip.ing1.pds.client.Categories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.vandermeer.asciitable.AsciiTable;
import edu.ezip.commons.LoggingUtils;
import edu.ezip.ing1.pds.business.dto.SousCategorieA;
import edu.ezip.ing1.pds.business.dto.SousCategorieB;
import edu.ezip.ing1.pds.business.dto.SousCategoriesA;
import edu.ezip.ing1.pds.business.dto.SousCategoriesB;
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

public class SelectSousCategorieAByName extends ClientRequest<Object, SousCategorieA> {

    //Attributs pour lancer la requête.
    private final static String LoggingLabel = "S e l e c t - S o u s - C a t e g o r i e - A - B y - N a m e";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String networkConfigFile = "network.yaml";
    private static final String threadName = "selecter-client";
    private static final String requestOrder = "SELECT_SOUS_CATEGORIE_A_BY_NAME";
    private static final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();


    public SelectSousCategorieAByName(
    NetworkConfig networkConfig, int myBirthDate, Request request, Object info, byte[] bytes)
    throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public SousCategorieA readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final SousCategorieA sousCategorieA = mapper.readValue(body, SousCategorieA.class);
        return sousCategorieA;
    }

    public static SousCategorieA launchSelectSousCatAByName(String name) throws IOException, InterruptedException{
        final NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
        logger.debug("Load Network config file : {}", networkConfig.toString());

        int birthdate = 0;
        final ObjectMapper objectMapper = new ObjectMapper();

        final String requestId = UUID.randomUUID().toString();
        Request request=new Request();
        request.setRequestContent(name);
        request.setRequestId(requestId);
        request.setRequestOrder(requestOrder);
        request.setRequestContent(name);
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
                final SousCategoriesA SousCategoriesA = (SousCategoriesA) joinedClientRequest.getResult();
                final AsciiTable asciiTable = new AsciiTable();
                SousCategorieA derniereCategorie = new SousCategorieA();
                for (final SousCategorieA sousCategorieA: SousCategoriesA.getSousCategoriesA()) {
                    asciiTable.addRule();
                    asciiTable.addRow(sousCategorieA.getIdSouscatA(), sousCategorieA.getNomSouscatA(),sousCategorieA.getcodeGenre(),sousCategorieA.getIdSouscatA());
                    derniereCategorie = sousCategorieA;
                }
                asciiTable.addRule();
                logger.debug("\n{}\n", asciiTable.render());
                return derniereCategorie;
            }
        } catch(Exception e){
            System.out.println("Erreur : id inexistant");
            return null;
        }
        return null;
    }


}
