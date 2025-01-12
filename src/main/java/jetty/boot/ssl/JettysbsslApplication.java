package jetty.boot.ssl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.ssl.SSLContextBuilder;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.jetty.JettyServerCustomizer;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.ResourceUtils;

@SpringBootApplication
@EnableScheduling
public class JettysbsslApplication {

	private static final String CLIENT_CERT = "/Users/lokesh/workspace/sts-local/test_certs/keystore.jks";
	private static final String TRUST_JKS = "/Users/lokesh/workspace/sts-local/test_certs/truststore.jks";

	public static void main(String[] args) {
		SpringApplication.run(JettysbsslApplication.class, args);
	}
	
	 @Bean
	    public SslContextFactory.Server sslContextFactory() throws KeyManagementException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, Exception{
		 System.out.println(new Date() +"sslContextFactory initialising .. ");
	        SslContextFactory.Server sslContextFactory = new SslContextFactory.Server();
	        
	        char[] pd = "changeit".toCharArray();
			SSLContext sslContext = SSLContextBuilder.create()
	                .loadKeyMaterial(getKeyStore(CLIENT_CERT, pd), pd)
	                .loadTrustMaterial(getKeyStore(TRUST_JKS, pd), new TrustSelfSignedStrategy()).build();
	        
	        sslContextFactory.setSslContext(sslContext);
	        sslContextFactory.setIncludeProtocols("TLSv1.2");
	        sslContextFactory.setIncludeCipherSuites("TLS_AES_256_GCM_SHA384","TLS_AES_128_GCM_SHA256","TLS_CHACHA20_POLY1305_SHA256","TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384","TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256","TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256","TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384","TLS_ECDHE_RSA_WITH_CHACHA20_POLY1305_SHA256","TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256","TLS_DHE_RSA_WITH_AES_256_GCM_SHA384","TLS_DHE_RSA_WITH_CHACHA20_POLY1305_SHA256","TLS_DHE_DSS_WITH_AES_256_GCM_SHA384","TLS_DHE_RSA_WITH_AES_128_GCM_SHA256","TLS_DHE_DSS_WITH_AES_128_GCM_SHA256","TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA384","TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384","TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256","TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256","TLS_DHE_RSA_WITH_AES_256_CBC_SHA256","TLS_DHE_DSS_WITH_AES_256_CBC_SHA256","TLS_DHE_RSA_WITH_AES_128_CBC_SHA256","TLS_DHE_DSS_WITH_AES_128_CBC_SHA256","TLS_ECDH_ECDSA_WITH_AES_256_GCM_SHA384","TLS_ECDH_RSA_WITH_AES_256_GCM_SHA384","TLS_ECDH_ECDSA_WITH_AES_128_GCM_SHA256","TLS_ECDH_RSA_WITH_AES_128_GCM_SHA256","TLS_ECDH_ECDSA_WITH_AES_256_CBC_SHA384","TLS_ECDH_RSA_WITH_AES_256_CBC_SHA384","TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA256","TLS_ECDH_RSA_WITH_AES_128_CBC_SHA256","TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA","TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA","TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA","TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA","TLS_DHE_RSA_WITH_AES_256_CBC_SHA","TLS_DHE_DSS_WITH_AES_256_CBC_SHA","TLS_DHE_RSA_WITH_AES_128_CBC_SHA","TLS_DHE_DSS_WITH_AES_128_CBC_SHA","TLS_ECDH_ECDSA_WITH_AES_256_CBC_SHA","TLS_ECDH_RSA_WITH_AES_256_CBC_SHA","TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA","TLS_ECDH_RSA_WITH_AES_128_CBC_SHA","TLS_RSA_WITH_AES_256_GCM_SHA384","TLS_RSA_WITH_AES_128_GCM_SHA256","TLS_RSA_WITH_AES_256_CBC_SHA256","TLS_RSA_WITH_AES_128_CBC_SHA256","TLS_RSA_WITH_AES_256_CBC_SHA"
,"TLS_RSA_WITH_AES_128_CBC_SHA","TLS_EMPTY_RENEGOTIATION_INFO_SCSV");
//	        sslContextFactory.setNeedClientAuth(true);
	        return sslContextFactory;
	    }
	 
	 public static KeyStore getKeyStore(String file, char[] password) throws Exception {
	        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
	        File key = ResourceUtils.getFile(file);
	        try (InputStream in = new FileInputStream(key)) {
	            keyStore.load(in, password);
	        }
	        return keyStore;
	    }

	    @Bean
	    public ConfigurableServletWebServerFactory webServerFactory(SslContextFactory.Server sslContextFactory) {
	    	System.out.println(new Date() +"JettyServletWebServerFactory initialising .. ");
	        JettyServletWebServerFactory factory = new JettyServletWebServerFactory();
	        factory.setPort(8081);
	        
	        
	        HttpConfiguration https_config = new HttpConfiguration();
	        https_config.setSendServerVersion(false);
	        https_config.setRequestHeaderSize(512 * 1024);
	        https_config.setResponseHeaderSize(512 * 1024);

	        SecureRequestCustomizer src = new SecureRequestCustomizer();
	        src.setSniHostCheck(false);
	        https_config.addCustomizer(src);
	        
	        JettyServerCustomizer jettyServerCustomizer = server -> server.setConnectors(new Connector[]{new ServerConnector(server, sslContextFactory, new HttpConnectionFactory(https_config))});
	        
	        factory.setServerCustomizers(Collections.singletonList(jettyServerCustomizer));
	        return factory;
	    }
	    
	    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.MINUTES  , initialDelayString ="0")
	    public void checkCertChanged(){
	    	System.out.println(new Date() + "checking whether cert changed ");
	        
	    }

}
