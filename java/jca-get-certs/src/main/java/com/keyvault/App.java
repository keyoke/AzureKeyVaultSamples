package com.keyvault;

import java.io.Console;
import java.net.Socket;
import java.io.IOException;
import java.io.OutputStream;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import java.security.KeyStore;
import java.security.Security;
import java.security.cert.Certificate;
import com.azure.security.keyvault.jca.KeyVaultJcaProvider;
import com.azure.security.keyvault.jca.KeyVaultLoadStoreParameter;
import java.util.Base64;
import java.util.Enumeration;
import sun.security.provider.X509Factory;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
        
        KeyVaultJcaProvider provider = new KeyVaultJcaProvider();
        Security.addProvider(provider);

        KeyStore keyStore = KeyStore.getInstance("AzureKeyVault");
        KeyVaultLoadStoreParameter parameter = new KeyVaultLoadStoreParameter(
            System.getProperty("azure.keyvault.uri"),
            System.getProperty("azure.keyvault.tenant-id"),
            System.getProperty("azure.keyvault.client-id"),
            System.getProperty("azure.keyvault.client-secret"));
        keyStore.load(parameter);

        for (Enumeration<String> aliases = keyStore.aliases(); aliases.hasMoreElements(); ) {
            String name = aliases.nextElement();

            System.out.println(name);

            Certificate cert = keyStore.getCertificate(name);

            String certStr = Base64.getEncoder().encodeToString(cert.getEncoded());

            System.out.println(X509Factory.BEGIN_CERT);
            System.out.println(certStr);

        }
    }
}
