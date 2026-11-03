import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.security.SecureRandom;
import java.util.*;
public class CustomFileCipher {
    static final int N = 8;
    static byte[] crypt(byte[] data, byte[] key, boolean encrypt) {
        if (!encrypt && data.length < N)
            throw new IllegalArgumentException("Invalid encrypted file");
        byte[] out = new byte[encrypt ? data.length + N : data.length - N];
        if (encrypt) {
            byte[] nonce = new byte[N];
            new SecureRandom().nextBytes(nonce);
            System.arraycopy(nonce, 0, out, 0, N);
        }
        byte[] nonce = encrypt ? out : data;
        int previous = nonce[0] & 255;
        for (int i = 0; i < (encrypt ? data.length : out.length); i++) {
            int k = (key[i % key.length] ^ nonce[i % N]) & 255;
            if (encrypt) {
                int x = (data[i] & 255) ^ k;
                x = (x + previous + i) & 255;
                int c = ((x << 3) | (x >>> 5)) & 255;
                out[N + i] = (byte)c;
                previous = c;
            } else {
                int c = data[N + i] & 255;
                int x = ((c >>> 3) | (c << 5)) & 255;
                x = (x - previous - i) & 255;
                out[i] = (byte)(x ^ k);
                previous = c;
            }
        }
        return out;
    }
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.print("1. Encrypt  2. Decrypt: ");
        String choice = sc.nextLine();
        if (!choice.equals("1") && !choice.equals("2")) return;
        System.out.print("File path: ");
        Path file = Path.of(sc.nextLine());
        System.out.print("Secret key: ");
        byte[] key = sc.nextLine().getBytes(StandardCharsets.UTF_8);
        if (key.length == 0)
            throw new IllegalArgumentException("Empty key");
        byte[] input = Files.readAllBytes(file);
        boolean encrypt = choice.equals("1");
        byte[] output = crypt(input, key, encrypt);
        Path saved = Path.of(file + (encrypt ? ".enc" : ".dec"));
        Files.write(saved, output);
        System.out.println("Saved: " + saved);
        if (encrypt)
            System.out.println("Correct recovery: " + Arrays.equals(input, crypt(output, key, false)));
        sc.close();
    }
}
