package com.psp.gestionproyecto.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class Logger {

    private static final String LOG_FILE = "login.log";
    private static final String ENCRYPTED_LOG_FILE = "login_cifradosimetrico.log";
    private static final String DECRYPTED_LOG_FILE = "login_descifradosimetrico.log";
    private static final String KEY_PRIVATE_FILE = "key.private";
    private static final String KEY_PUBLIC_FILE = "key.public";
    private static final String SIGNATURE_FILE = "login.signature";

    public void log(String username, String operation) {
        try {
            // Obtener la instancia de la clave secreta
            SecretKey secretKey = getSecretKey();

            // Inicializar el cifrador
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            // Obtener la fecha y hora actual formateada
            LocalDateTime now = LocalDateTime.now();
            String formattedDateTime = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            // Abrir el archivo de registro original y el archivo cifrado
            try (FileWriter fileWriter = new FileWriter(LOG_FILE, true);
                 PrintWriter printWriter = new PrintWriter(fileWriter);
                 CipherOutputStream cipherOutputStream = new CipherOutputStream(new FileOutputStream(ENCRYPTED_LOG_FILE, true), cipher)) {
                // Escribir en el archivo de registro original
                printWriter.println(username + " - " + formattedDateTime + " - " + operation);

                // Escribir en el archivo cifrado
                cipherOutputStream.write((username + " - " + formattedDateTime + " - " + operation + "\n").getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void decryptLogFile() {
        try {
            // Obtener la clave secreta
            SecretKey secretKey = getSecretKey();

            // Inicializar el cifrador
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            // Abrir los archivos cifrado y descifrado
            try (BufferedReader reader = new BufferedReader(new FileReader(ENCRYPTED_LOG_FILE));
                 PrintWriter writer = new PrintWriter(new FileWriter(DECRYPTED_LOG_FILE))) {
                // Leer y descifrar línea por línea
                String line;
                while ((line = reader.readLine()) != null) {
                    byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(line));
                    writer.println(new String(decryptedData));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generateAndSaveKeyPair() {
        try {
            // Generar par de claves pública y privada
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            // Guardar claves en archivos
            saveKeyToFile(keyPair.getPrivate(), KEY_PRIVATE_FILE);
            saveKeyToFile(keyPair.getPublic(), KEY_PUBLIC_FILE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public byte[] signLogFile() {
        try {
            // Leer la clave privada
            PrivateKey privateKey = readPrivateKey();

            // Inicializar objeto de firma
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKey);

            // Leer el contenido del archivo
            byte[] fileBytes = Files.readAllBytes(Paths.get(LOG_FILE));

            // Actualizar los datos a ser firmados
            signature.update(fileBytes);

            // Firmar el archivo
            return signature.sign();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean verifySignature(byte[] signature) {
        try {
            // Leer la clave pública
            PublicKey publicKey = readPublicKey();

            // Inicializar objeto de firma
            Signature sig = Signature.getInstance("SHA256withRSA");
            sig.initVerify(publicKey);

            // Leer el contenido del archivo
            byte[] fileBytes = Files.readAllBytes(Paths.get(LOG_FILE));

            // Actualizar los datos a ser verificados
            sig.update(fileBytes);

            // Verificar la firma
            return sig.verify(signature);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private SecretKey getSecretKey() throws Exception {
        // Verificar si ya existe una clave
        if (!new File(KEY_PRIVATE_FILE).exists()) {
            // Si no existe, generar una nueva clave
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            SecretKey secretKey = keyGenerator.generateKey();

            // Guardar la clave en el archivo
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(KEY_PRIVATE_FILE))) {
                out.writeObject(secretKey);
            }
        }

        // Leer la clave desde el archivo
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(KEY_PRIVATE_FILE))) {
            return (SecretKey) in.readObject();
        }
    }

    private void saveKeyToFile(Key key, String fileName) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            fos.write(key.getEncoded());
        }
    }

    private PrivateKey readPrivateKey() throws Exception {
        try (FileInputStream fis = new FileInputStream(KEY_PRIVATE_FILE)) {
            byte[] keyBytes = new byte[fis.available()];
            fis.read(keyBytes);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(keySpec);
        }
    }

    private PublicKey readPublicKey() throws Exception {
        try (FileInputStream fis = new FileInputStream(KEY_PUBLIC_FILE)) {
            byte[] keyBytes = new byte[fis.available()];
            fis.read(keyBytes);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(keySpec);
        }
    }
}