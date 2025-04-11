package com.example.marque_paf.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class ConfigRest {
    
    @Value("${service.auth.url:http://servicerest2:8080}")
    private String authServiceUrl;
    
    @Value("${service.library.url:http://servicerest1:8080}")
    private String libraryServiceUrl;
    
    public String getAuthServiceUrl() {
        return authServiceUrl;
    }
    
    public String getLibraryServiceUrl() {
        return libraryServiceUrl;
    }
    
    // Chemins spécifiques
    public String getLoginPath() {
        return authServiceUrl + "/session/login";
    }
    
    public String getRegisterPath() {
        return authServiceUrl + "/session/register";
    }
    
    public String getLibraryBooksPath() {
        return libraryServiceUrl + "/books";
    }
    
    public String getAdminBooksPath() {
        return libraryServiceUrl + "/admin/books";
    }
}
