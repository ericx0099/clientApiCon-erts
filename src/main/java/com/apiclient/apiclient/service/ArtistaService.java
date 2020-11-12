package com.apiclient.apiclient.service;

import com.apiclient.apiclient.entities.Artista;
import com.apiclient.apiclient.entities.Client;
import com.apiclient.apiclient.entities.Event;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
@Service
public class ArtistaService {

    final String uri = "http://Backendapi-env.eba-bwxmtzsw.us-east-1.elasticbeanstalk.com/";
    public List<Artista> getArtistes(){
        String uriC = uri+"getArtistes";
        RestTemplate rt = new RestTemplate();
        ResponseEntity<List<Artista>> rateResponse =
                rt.exchange(uriC,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Artista>>() {
                        });
        List<Artista> llista = rateResponse.getBody();
        return llista;
    }
    public Artista saveArtista(Artista artista){
        String uriAddArt = uri+"addArtista";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Artista> httpEntity = new HttpEntity<>(artista,headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<Artista> responseEntity = rt.postForEntity( uriAddArt, httpEntity, Artista.class);
        Artista artModified = responseEntity.getBody();
        return artModified;
    }
    public List<Event> getEventsByArtista(int idA){
        String url = uri+"getEventsByArtista/"+idA;
        RestTemplate rt = new RestTemplate();
        ResponseEntity<List<Event>> rateResponse =
                rt.exchange(url,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Event>>() {
                        });
        List<Event> llista = rateResponse.getBody();
        return llista;

    }
}
