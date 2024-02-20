package edu.ezip.ing1.pds.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.Produit;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;

import java.io.IOException;
import java.util.Map;

public class InsertProductsClientRequest extends ClientRequest<Produit, String> {

    public InsertProductsClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, Produit info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public String readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final Map<String, Integer> produitIdMap = mapper.readValue(body, Map.class);
        final String result  = produitIdMap.get("idProduit").toString();
        return result;
    }
}
