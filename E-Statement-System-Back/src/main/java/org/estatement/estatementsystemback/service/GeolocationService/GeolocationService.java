package org.estatement.estatementsystemback.service.GeolocationService;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeolocationService {

    private final String GEOLOCATION_API_URL = "http://ip-api.com/json/";

    // Method to get the public IP address
    public String getPublicIpAddress() {
        RestTemplate restTemplate = new RestTemplate();
        String ip = restTemplate.getForObject("http://checkip.amazonaws.com/", String.class).trim();
        System.out.println("Public IP Address: " + ip);
        return ip;
    }

    // Method to get current location of the server based on public IP
    public String getCurrentLocation() {
        String ip = getPublicIpAddress();
        return getLocationFromIp(ip);
    }

    // Method to call ip-api to get geolocation
    public String getLocationFromIp(String ip) {
        String url = GEOLOCATION_API_URL + ip;
        RestTemplate restTemplate = new RestTemplate();
        GeolocationResponse response = restTemplate.getForObject(url, GeolocationResponse.class);

        System.out.println("Geolocation Response: " + response);

        if (response != null && "success".equals(response.getStatus())) {
            return response.getCity() + ", " + response.getRegion() + ", " + response.getCountry();
        }
        return "Unknown location";
    }
}
