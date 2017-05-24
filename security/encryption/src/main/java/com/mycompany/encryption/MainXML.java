package com.mycompany.encryption;

import com.sun.org.apache.xml.internal.security.encryption.XMLCipher;
import java.io.FileInputStream;
import java.util.Scanner;
import javax.crypto.SecretKey;
import org.w3c.dom.Document;

/**
 *
 * @author marc.vila.gomez
 */
public class MainXML {
    private static final String xmlFile = "E:/ISDCM_Practica/security/encryption/xmlcypher/didlFilm1.xml";
    private static final String encryptedXMLFile = "E:/ISDCM_Practica/security/encryption/xmlcypher/didlFilm1_encrypted.xml";
    private static final String decryptedXMLFile = "E:/ISDCM_Practica/security/encryption/xmlcypher/didlFilm1_decrypted.xml";
        
    private static final String videoFile = "E:/ISDCM_Practica/security/encryption/xmlcypher/video.mp4";
    private static final String encryptedVideoFile = "E:/ISDCM_Practica/security/encryption/xmlcypher/video_encrypted.mp4";
    private static final String decryptedVideoFile = "E:/ISDCM_Practica/security/encryption/xmlcypher/video_decrypted.mp4";
    
    public static void main(String args[]) throws Exception {
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        System.out.println("Introduzca un numero (0, 1)");
        System.out.println("0 - Realizar encriptado / desencriptado de XML");
        System.out.println("1 - Realizar encriptado / desencriptado de video");
        int option = reader.nextInt(); // Scans the next token of the input as an int.
        
        if (option == 0) {
            System.out.println("Reading document...");
            Document document = XMLUtil.getDocument(xmlFile);

            System.out.println("Encrypting document...");
            SecretKey secretKey = SecretKeyUtil.getSecretKey("AES");
            Document encryptedDoc = XMLUtil.encryptDocument(document, secretKey, XMLCipher.AES_128);
            XMLUtil.saveDocumentTo(encryptedDoc, encryptedXMLFile);

            System.out.println("Decrypting document...");
            Document decryptedDoc = XMLUtil.decryptDocument(encryptedDoc, secretKey, XMLCipher.AES_128);
            XMLUtil.saveDocumentTo(decryptedDoc, decryptedXMLFile);
        } else if (option == 1) {            
            System.out.println("Reading video...");
            FileInputStream initialFile = VideoUtil.getVideo(videoFile);

            System.out.println("Encrypting video...");
            SecretKey secretKey = SecretKeyUtil.getSecretKey("AES");
            FileInputStream encryptedFile = VideoUtil.encryptVideo(secretKey, initialFile, encryptedVideoFile, "AES");

            System.out.println("Decrypting video...");
            VideoUtil.decryptVideo(secretKey, encryptedFile, decryptedVideoFile, "AES");
        }
    }
}
