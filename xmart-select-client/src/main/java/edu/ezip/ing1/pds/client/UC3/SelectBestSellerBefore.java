package edu.ezip.ing1.pds.client.UC3;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.vandermeer.asciitable.AsciiTable;
import edu.ezip.commons.LoggingUtils;
import edu.ezip.ing1.pds.business.dto.BestSeller;
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

public class SelectBestSellerBefore extends ClientRequest<Object, BestSeller> {

    //Attributs pour lancer la requête.
    private final static String LoggingLabel = "S e l e c t - B e s t S e l l e r - B e f o r e";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);

    private final static String networkConfigFile = "network.yaml";

    private static final String requestOrder = "SELECT_BESTSELLER_BEFORE";
    private static final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();

    protected static int totalQuantite;




    public SelectBestSellerBefore(
            NetworkConfig networkConfig, int myBirthDate, Request request, Object info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public BestSeller readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final BestSeller bestSeller = mapper.readValue(body, BestSeller.class);
        return bestSeller;
    }


    public static int getTotalQuantite(){
        return totalQuantite;
    }

    public static BestSeller launchSelectBestSellerBefore(Request request) throws IOException, InterruptedException{
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
        final SelectBestSellerBefore clientRequest = new SelectBestSellerBefore(
                networkConfig,
                birthdate++, request, null, requestBytes);
        clientRequests.push(clientRequest);

        try {
            while (!clientRequests.isEmpty()) {
                final ClientRequest joinedClientRequest = clientRequests.pop();
                joinedClientRequest.join();
                logger.debug("Thread {} complete.", joinedClientRequest.getThreadName());
                final BestSeller bestSeller = (BestSeller) joinedClientRequest.getResult();
                final AsciiTable asciiTable = new AsciiTable();
                asciiTable.addRule();
                asciiTable.addRow(bestSeller.getReference1(), bestSeller.getScore1(),bestSeller.getSum1(),
                        bestSeller.getReference2(), bestSeller.getScore2(),bestSeller.getSum2(),
                        bestSeller.getReference3(), bestSeller.getScore3(),bestSeller.getSum3());
                asciiTable.addRule();
                logger.debug("\n{}\n", asciiTable.render());
                return bestSeller;
            }
        } catch(Exception e){
            System.out.println("Erreur : référence inexistante");
            return null;
        }

        return null;
    }

}