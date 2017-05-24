package com.mycompany.encryption;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 *
 * @author marc.vila.gomez
 */
public class VideoUtil {    
    public static FileInputStream getVideo(String fileName) throws FileNotFoundException {
        return new FileInputStream(new File(fileName));
    }
    
    public static FileInputStream encryptVideo(SecretKey secretKey, FileInputStream initialFileInput, String fileEncryptedOutput, String algorithm) 
            throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        File outfile = new File(fileEncryptedOutput);
        if(!outfile.exists())
            outfile.createNewFile();
        FileInputStream encryptedInputFile = new FileInputStream(outfile);
        
        Cipher encipher = Cipher.getInstance(algorithm);
        encipher.init(Cipher.ENCRYPT_MODE, secretKey);
        CipherInputStream cis = new CipherInputStream(initialFileInput, encipher);
        FileOutputStream encryptedOutputFileStream = new FileOutputStream(outfile);

        int read;            
        while((read = cis.read())!=-1)
        {
            encryptedOutputFileStream.write((char)read);
            encryptedOutputFileStream.flush();
        }   
        encryptedOutputFileStream.close();
        
        return encryptedInputFile;
    }
    
    public static void decryptVideo(SecretKey secretKey, FileInputStream encryptedInputFile, String fileDecryptOutput, String algorithm) 
            throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        File decfile = new File(fileDecryptOutput);
        if(!decfile.exists())
            decfile.createNewFile();
        FileOutputStream decfos = new FileOutputStream(decfile);
        
        Cipher decipher = Cipher.getInstance(algorithm);
        decipher.init(Cipher.DECRYPT_MODE, secretKey);
        CipherOutputStream decryptedOutputFileStream = new CipherOutputStream(decfos,decipher);
        
        int read;
        while((read=encryptedInputFile.read())!=-1)
        {
            decryptedOutputFileStream.write(read);
            decryptedOutputFileStream.flush();
        }
        decryptedOutputFileStream.close(); 
    }    
}
