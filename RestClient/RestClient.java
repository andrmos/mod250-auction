package rest;

import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.io.*;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by andre on 21.10.16.
 */
public class RestClient {

    public static void main(String[] args) {
        String httpsUrl = "https://localhost:8181/mod250_auction/webresources/auctions/active";
        try {

            // Create a context that doesn't check certificates.
            SSLContext ssl_ctx = SSLContext.getInstance("TLS");
            TrustManager[] trust_mgr = get_trust_mgr();
            ssl_ctx.init(null,                // key manager
                    trust_mgr,           // trust manager
                    new SecureRandom()); // random number generator
            HttpsURLConnection.setDefaultSSLSocketFactory(ssl_ctx.getSocketFactory());

            URL url = new URL(httpsUrl);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

            // Guard against "bad hostname" errors during handshake.
            con.setHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String host, SSLSession sess) {
                    if (host.equals("localhost")) return true;
                    else return false;
                }
            });

            printContent(con);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

    }

    private static TrustManager[] get_trust_mgr() {
        TrustManager[] certs = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String t) {
                    }

                    public void checkServerTrusted(X509Certificate[] certs, String t) {
                    }
                }
        };
        return certs;
    }

    private static void printContent(HttpsURLConnection con) {
        if (con != null) {

            try {

                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));


                String input;
                while ((input = br.readLine()) != null) {
                    System.out.println(input);
                }
                br.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
