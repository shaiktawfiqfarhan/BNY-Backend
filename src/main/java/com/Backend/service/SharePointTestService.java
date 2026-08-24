package com.Backend.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import org.springframework.stereotype.Service;

@Service
public class SharePointTestService {
	
    public String readPage() {

        try {

            String url =
                    "https://mphasis.sharepoint.com/:f:/s/BNYMOps/IgCb0dPUchNlRoTsk6nQF8b8Adm-Iko-mSn6-G1VrVxXlew?e=mjbteW";

            TrustManager[] trustAllCerts = new TrustManager[] {
            	    new X509TrustManager() {

            	        public X509Certificate[] getAcceptedIssuers() {
            	            return null;
            	        }

            	        public void checkClientTrusted(
            	                X509Certificate[] certs,
            	                String authType) {
            	        }

            	        public void checkServerTrusted(
            	                X509Certificate[] certs,
            	                String authType) {
            	        }
            	    }
            	};

            	SSLContext sslContext =
            	        SSLContext.getInstance("TLS");

            	sslContext.init(
            	        null,
            	        trustAllCerts,
            	        new SecureRandom());

            	HttpClient client =
            	        HttpClient.newBuilder()
            	                .sslContext(sslContext)
            	                .build();

            HttpRequest request =
                    HttpRequest.newBuilder()
                            .uri(URI.create(url))
                            .GET()
                            .build();

            HttpResponse<String> response =
                    client.send(
                            request,
                            HttpResponse.BodyHandlers.ofString());

            return "Status = "
            + response.statusCode()
            + "\n\n"
            + response.body();

        } catch (Exception e) {

            e.printStackTrace();

            return e.toString();
        }
    }
}