package com.dgtt.business.services;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.security.KeyPair;

@Service
public class ClientService {

    @Transactional
    public void insertClient(String type) throws FileNotFoundException, IOException {
        KeyPair keypair = Keys.keyPairFor(SignatureAlgorithm.RS256);
        byte[] privateKey = keypair.getPrivate().getEncoded();
        byte[] publicKey = keypair.getPublic().getEncoded();
//        String privateKeyStr = Base64.encodeBase64String(privateKey);
//        String publicKeyStr = Base64.encodeBase64String(publicKey);
        File pathPublic;
        File pathPrivate;
        if (type.equalsIgnoreCase("dev")) {
            pathPublic = new File("src/main/resources/dev_public.key");
            pathPrivate = new File("src/main/resources/dev_private.key");
            if (!pathPublic.exists()) {
                pathPublic.mkdirs();
            }
            if (!pathPrivate.exists()) {
                pathPrivate.mkdirs();
            }

            OutputStream outStream = new FileOutputStream(pathPublic);
            outStream.write(publicKey);
            IOUtils.closeQuietly(outStream);

            OutputStream outStream01 = new FileOutputStream(pathPrivate);
            outStream01.write(privateKey);
            IOUtils.closeQuietly(outStream01);
        } else {
            pathPrivate = new File("src/main/resources/prod_private.key");
            pathPublic = new File("src/main/resources/prod_public.key");
            if (!pathPublic.exists()) {
                pathPublic.mkdirs();
            }
            if (!pathPrivate.exists()) {
                pathPrivate.mkdirs();
            }

            OutputStream outStream = new FileOutputStream(pathPublic);
            outStream.write(publicKey);
            IOUtils.closeQuietly(outStream);

            OutputStream outStream01 = new FileOutputStream(pathPrivate);
            outStream01.write(privateKey);
            IOUtils.closeQuietly(outStream01);
        }
    }
}
