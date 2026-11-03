import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.security.SecureRandom;
import java.util.*;
import javax.crypto.*;
import javax.crypto.spec.*;
public class SecureImage {
    static final int PORT = 5000;
    static final int MAX = 50 * 1024 * 1024;
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.print("Mode (key/send/receive): ");
        String mode = sc.nextLine().trim().toLowerCase();
        if (mode.equals("key")) {
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(256);
            System.out.println("AES key: " + Base64.getEncoder()
                    .encodeToString(kg.generateKey().getEncoded()));
            return;
        }
        if (!mode.equals("send") && !mode.equals("receive")) {
            System.out.println("Invalid mode");
            return;
        }
        System.out.print("Enter shared AES key (Base64): ");
        byte[] keyBytes = Base64.getDecoder().decode(sc.nextLine().trim());
        if (keyBytes.length != 32)
            throw new IllegalArgumentException("AES-256 needs a 32-byte key");
        SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
        Cipher aes = Cipher.getInstance("AES/GCM/NoPadding");
        if (mode.equals("send")) {
            System.out.print("Receiver IP (127.0.0.1 for same PC): ");
            String host = sc.nextLine().trim();
            System.out.print("Image file path: ");
            byte[] image = Files.readAllBytes(Path.of(sc.nextLine().trim()));
            if (image.length > MAX)
                throw new IOException("Image exceeds 50 MB");
            byte[] iv = new byte[12];
            new SecureRandom().nextBytes(iv);
            aes.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(128, iv));
            byte[] encrypted = aes.doFinal(image);
            try (Socket socket = new Socket(host, PORT);
                 DataOutputStream out =
                     new DataOutputStream(socket.getOutputStream())) {
                out.write(iv);
                out.writeInt(encrypted.length);
                out.write(encrypted);
            }
            System.out.println("Encrypted image sent successfully.");
        } else {
            System.out.print("Save received image as (e.g. received.png): ");
            Path output = Path.of(sc.nextLine().trim());
            try (ServerSocket server = new ServerSocket(PORT)) {
                System.out.println("Waiting for sender on port " + PORT + "...");
                try (Socket socket = server.accept();
                     DataInputStream in =
                         new DataInputStream(socket.getInputStream())) {
                    byte[] iv = new byte[12];
                    in.readFully(iv);
                    int length = in.readInt();
                    if (length < 16 || length > MAX + 16)
                        throw new IOException("Invalid encrypted image size");
                    byte[] encrypted = new byte[length];
                    in.readFully(encrypted);
                    aes.init(Cipher.DECRYPT_MODE, key,
                             new GCMParameterSpec(128, iv));
                    byte[] image = aes.doFinal(encrypted);
                    Files.write(output, image);

                    System.out.println("Verified and saved image: " + output);
                }
            } catch (AEADBadTagException ex) {
                System.out.println(
                    "REJECTED: Wrong key or modified image data!");
            }
        }
    }
}
