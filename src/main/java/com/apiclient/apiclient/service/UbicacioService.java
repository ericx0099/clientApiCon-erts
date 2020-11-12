package com.apiclient.apiclient.service;

import java.time.LocalDateTime;
import com.apiclient.apiclient.entities.Ubicacio;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UbicacioService {
    final String uri = "http://Backendapi-env.eba-bwxmtzsw.us-east-1.elasticbeanstalk.com/";
    public List<Ubicacio> getUbicacions() {

        String urigetUbi = uri+"getUbicacio";
        RestTemplate rt = new RestTemplate();
        ResponseEntity<List<Ubicacio>> rateResponse =
                rt.exchange(urigetUbi,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Ubicacio>>() {
                        });
        List<Ubicacio> llista = rateResponse.getBody();
        return llista;

    }

    public Ubicacio saveUbicacio(Ubicacio ubi){
        String urlAdd = uri+"addUbicacio";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Ubicacio> httpEntity= new HttpEntity<>(ubi,headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<Ubicacio> responseEntity = rt.postForEntity(urlAdd,httpEntity,Ubicacio.class);
        Ubicacio ubiModified = responseEntity.getBody();
        return ubiModified;
    }
}