import java.security.*;
import java.util.*;
import javax.crypto.Cipher;
public class RSATiming {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter RSA key sizes (e.g. 1024 2048 3072): ");
        String[] keys = sc.nextLine().trim().split("\\s+");
        System.out.print("Enter message sizes in bits (e.g. 128 256 384): ");
        String[] messages = sc.nextLine().trim().split("\\s+");
        SecureRandom random = new SecureRandom();
        for (String k : keys) {
            int keyBits = Integer.parseInt(k);
            if (keyBits < 1024 || keyBits % 8 != 0) {
                System.out.println("Skipping invalid key size: " + keyBits);
                continue;
            }
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(keyBits);
            long start = System.nanoTime();
            KeyPair pair = generator.generateKeyPair();
            double keyMs = (System.nanoTime() - start) / 1e6;
            System.out.printf(
                "%nRSA key: %d bits | Key generation: %.3f ms%n",
                keyBits, keyMs
            );
            for (String m : messages) {
                int n = Integer.parseInt(m);
                int maxBits = (keyBits / 8 - 66) * 8;
                if (n <= 0 || n % 8 != 0 || n > maxBits) {
                    System.out.printf(
                        "Message %d bits: unsupported (maximum %d bits)%n",
                        n, maxBits
                    );
                    continue;
                }
                byte[] plain = new byte[n / 8];
                random.nextBytes(plain);
                plain[0] |= (byte) 0x80;
                Cipher rsa = Cipher.getInstance(
                    "RSA/ECB/OAEPWithSHA-256AndMGF1Padding"
                );
                start = System.nanoTime();
                rsa.init(Cipher.ENCRYPT_MODE, pair.getPublic());
                byte[] encrypted = rsa.doFinal(plain);
                double encMs = (System.nanoTime() - start) / 1e6;
                start = System.nanoTime();
                rsa.init(Cipher.DECRYPT_MODE, pair.getPrivate());
                byte[] decrypted = rsa.doFinal(encrypted);
                double decMs = (System.nanoTime() - start) / 1e6;
                System.out.printf(
                    "Message: %d bits | Encrypt: %.3f ms | Decrypt: %.3f ms | Correct: %b%n",
                    n, encMs, decMs,
                    Arrays.equals(plain, decrypted)
                );
            }
        }
        sc.close();
    }
}
