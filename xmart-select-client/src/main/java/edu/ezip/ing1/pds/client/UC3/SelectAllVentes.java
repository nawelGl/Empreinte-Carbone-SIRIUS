package edu.ezip.ing1.pds.client.UC3;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.vandermeer.asciitable.AsciiTable;
import edu.ezip.ing1.pds.business.dto.Ventes;
import edu.ezip.ing1.pds.business.dto.Vente;
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

public class SelectAllVentes extends ClientRequest<Object, Ventes> {
    // Attributs pour les requêtes

    private  final static String LoggingLabel ="S e l e c t - A l l - V e n t e s";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String studentsToBeInserted = "students-to-be-inserted.yaml";
    private final static String networkConfigFile = "network.yaml";
    private static final String threadName = "inserter-client";
    private static final String requestOrder = "SELECT_All_VENTES";
    private static final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();

    public SelectAllVentes(
            NetworkConfig networkConfig, int myBirthDate, Request request, Object info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public Ventes readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final Ventes ventes = mapper.readValue(body, Ventes.class);
        return ventes;
    }

// Fonction pour lancer les requêtes (à la place de main)

    public static Vente launchSelectAllVentes(Request request) throws  IOException, InterruptedException{
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

        final SelectAllVentes clientRequest = new SelectAllVentes(
                networkConfig,
                birthdate++,    request, null,requestBytes);
        clientRequests.push(clientRequest);

        while (!clientRequests.isEmpty()) {
            final ClientRequest joinedClientRequest = clientRequests.pop();
            joinedClientRequest.join();
            logger.debug("Thread {} complete.", joinedClientRequest.getThreadName());
            final Ventes ventes = (Ventes) joinedClientRequest.getResult();
            final AsciiTable asciiTable = new AsciiTable();
            Vente dernierVente = null;
            for (final Vente vente : ventes.getVentes()) {
                asciiTable.addRule();
                asciiTable.addRow(vente.getQuantite());
                dernierVente = vente;
                System.out.println("===============================================");
                System.out.println("vente dans selectAllVentes : " + vente.toString());
                System.out.println("dernier vente dans selectAllVentes : " + dernierVente.toString());
                System.out.println("===============================================");
            }
            asciiTable.addRule();
            logger.debug("\n{}\n", asciiTable.render());
            //return asciiTable.render();
            return dernierVente;
        }

        return null;






    }
}
