package com.mycompany.encryption;

import com.sun.org.apache.xml.internal.security.encryption.XMLCipher;
import javax.crypto.SecretKey;
import org.w3c.dom.Document;

/**
 *
 * @author marc.vila.gomez
 */
public class MainXML {
    public static void main(String args[]) throws Exception {
        String xmlFile = "E:/ISDCM_Practica/security/encryption/xmlcypher/didlFilm1.xml";
        String encryptedFile = "E:/ISDCM_Practica/security/encryption/xmlcypher/didlFilm1_encrypted.xml";
        String decryptedFile = "E:/ISDCM_Practica/security/encryption/xmlcypher/didlFilm1_decrypted.xml";

        SecretKey secretKey = SecretKeyUtil.getSecretKey("AES");
        
        System.out.println("Reading document...");
        Document document = XMLUtil.getDocument(xmlFile);
        
        System.out.println("Encrypting document...");
        Document encryptedDoc = XMLUtil.encryptDocument(document, secretKey, XMLCipher.AES_128);
        XMLUtil.saveDocumentTo(encryptedDoc, encryptedFile);

        System.out.println("Decrypting document...");
        Document decryptedDoc = XMLUtil.decryptDocument(encryptedDoc, secretKey, XMLCipher.AES_128);
        XMLUtil.saveDocumentTo(decryptedDoc, decryptedFile);
    }
}
