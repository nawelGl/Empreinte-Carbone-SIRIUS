package edu.ezip.ing1.pds.client.UC3;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.vandermeer.asciitable.AsciiTable;
import edu.ezip.commons.LoggingUtils;
import edu.ezip.ing1.pds.business.dto.Vente;
import edu.ezip.ing1.pds.business.dto.Ventes;
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

public class SelectAfterVenteByReference extends ClientRequest<Object, Ventes> {

    //Attributs pour lancer la requête.
    private final static String LoggingLabel = "S e l e c t - A f t e r -  V e n t e - B y - R e f e r e n c e";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);

    private final static String networkConfigFile = "network.yaml";

    private static final String requestOrder = "SELECT_AFTER_VENTE_BY_REFERENCE";
    private static final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();

    protected static int totalQuantite;




    public SelectAfterVenteByReference(
            NetworkConfig networkConfig, int myBirthDate, Request request, Object info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public Ventes readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final Ventes produits = mapper.readValue(body, Ventes.class);
        return produits;
    }

    // la methode pour recuperer le qtt total de requete
    public static int getTotalQuantiteVentes(Ventes ventes) {
        for (Vente vente : ventes.getVentes()) {
            totalQuantite += vente.getQuantite();
        }
        return totalQuantite;
    }
    public static int getTotalQuantite(){
        return totalQuantite;
    }


    public static Vente launchSelectVenteByReference(Request request) throws IOException, InterruptedException{
        final NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
        logger.debug("Load Network config file : {}", networkConfig.toString());

        int birthdate = 0;
        final ObjectMapper objectMapper = new ObjectMapper();
        final String requestId = UUID.randomUUID().toString();
        request.setRequestId(requestId);
        request.setRequestOrder(requestOrder);
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        final byte []  requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);
        LoggingUtils.logDataMultiLine(logger, Level.TRACE, requestBytes);
        final SelectAfterVenteByReference clientRequest = new SelectAfterVenteByReference(
                networkConfig,
                birthdate++, request, null, requestBytes);
        clientRequests.push(clientRequest);

        totalQuantite=0;
        try {
            while (!clientRequests.isEmpty()) {
                final ClientRequest joinedClientRequest = clientRequests.pop();
                joinedClientRequest.join();
                logger.debug("Thread {} complete.", joinedClientRequest.getThreadName());
                final Ventes ventes = (Ventes) joinedClientRequest.getResult();
                totalQuantite += getTotalQuantiteVentes(ventes);
                final AsciiTable asciiTable = new AsciiTable();
                Vente dernierVente = null;
                for (final Vente vente : ventes.getVentes()) {
                    asciiTable.addRule();
                    asciiTable.addRow(vente.getReference(), vente.getQuantite(), vente.getScore(), vente.getEmpreinte());
                    dernierVente =vente;
                }
                asciiTable.addRule();
                logger.debug("\n{}\n", asciiTable.render());
                return dernierVente;
            }
        } catch(Exception e){
            System.out.println("Erreur : référence inexistante");
            return null;
        }

        return null;
    }

}