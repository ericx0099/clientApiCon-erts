package com.apiclient.apiclient.service;


import com.apiclient.apiclient.entities.Client;
import com.apiclient.apiclient.entities.Ubicacio;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ClientService {
    final String uri = "http://Backendapi-env.eba-bwxmtzsw.us-east-1.elasticbeanstalk.com/";
    public List<Client> getClients(){
        String uriC = uri+"getClients";
        RestTemplate rt = new RestTemplate();
        ResponseEntity<List<Client>> rateResponse =
                rt.exchange(uriC,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Client>>() {
                        });
        List<Client> llista = rateResponse.getBody();
        return llista;
    }

    public Client saveClientByUbi(Client client, int idUbi){
        String uriAddCliById = uri+"addClient/"+idUbi;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Client> httpEntity = new HttpEntity<>(client,headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<Client> responseEntity = rt.postForEntity( uriAddCliById, httpEntity, Client.class);
        Client cliModified = responseEntity.getBody();
        return cliModified;
    }
}