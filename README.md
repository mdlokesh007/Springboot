# Springboot

## Workflow to create keypair , keystore , trust store 

1. Create a KeyStore , key pair .
keytool -genkeypair -alias mykey -keyalg RSA -keysize 2048 -validity 365 -keystore keystore.jks

2. Export the Public Certificate from keystore . 
keytool -export -alias mykey -file mycert.cer -keystore keystore.jks

3. Create a TrustStore and Import the Certificate.
keytool -import -trustcacerts -alias myserver -file mycert.cer -keystore truststore.jks

4.view  
keytool -list -keystore keystore.jks


5. convert to pcks12 (optional)
keytool -importkeystore -srckeystore keystore.jks -destkeystore keystore.p12 -deststoretype PKCS12


## Explanation of repo code .

1. To enable HTTPS when you are using embedded jetty , while using spring boot 3.2.3/3.3.3 . 

2. Spring boot project - add test get api 

3. Spring boot project - add scheduler 
