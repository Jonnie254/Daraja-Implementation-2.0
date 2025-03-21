package com.jonnie.darajatrialtwo.mpesa.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.CharacterPredicate;
import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Base64;



@Slf4j
public class HelperUtility {

    public static String toBase64String(String value) {
        byte[] data = value.getBytes(StandardCharsets.UTF_8);
        return Base64.getEncoder().encodeToString(data);
    }

    public static String toJson(Object object)  {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    @SneakyThrows
    public static String getSecurityCredential(String b2cSecurityCredential) {
        try {
            byte[] passwordBytes = b2cSecurityCredential.getBytes(StandardCharsets.UTF_8);
            Resource resource = new ClassPathResource("SandboxCertificate.cer");
            InputStream inputStream = resource.getInputStream();
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            X509Certificate certificate = (X509Certificate) cf.generateCertificate(inputStream);
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, certificate.getPublicKey());
            byte[] encryptedBytes = cipher.doFinal(passwordBytes);
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            log.error("Error generating security credentials -> {}", e.getLocalizedMessage(), e);
            throw e;
        }
    }

    public static String getTransactionUniqueId() {
        RandomStringGenerator stringGenerator = new RandomStringGenerator.Builder()
                .withinRange('0', 'z')
                .filteredBy(CharacterPredicates.LETTERS, CharacterPredicates.DIGITS)
                .build();
        return stringGenerator.generate(12).toUpperCase();
    }

    public static String getStkPushPassword(String businessShortCode, String passKey, String timeStamp) {
        String concatenatedString = String.format("%s%s%s", businessShortCode, passKey, timeStamp);
        return HelperUtility.toBase64String(concatenatedString);
    }

    public static String getTransactionTimeStamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.format(System.currentTimeMillis());
    }

}
