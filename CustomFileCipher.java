import java.nio.file.*;
import java.util.*;
public class CustomFileCipher {
    static byte[] crypt(byte[] data, int key, boolean encrypt) {
        byte[] result = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            int x = data[i] & 255;
            if (encrypt) {
                x = (x + key) & 255;
                x = ((x << 3) | (x >>> 5)) & 255;
                x ^= key;
            } else {
                x ^= key;
                x = ((x >>> 3) | (x << 5)) & 255;
                x = (x - key) & 255;
            }
            result[i] = (byte) x;
            key = (key * 7 + 13) & 255;
        }
        return result;
    }
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.print("1. Encrypt  2. Decrypt: ");
        String choice = sc.nextLine();
        if (!choice.equals("1") && !choice.equals("2")) return;
        System.out.print("File path: ");
        Path file = Paths.get(sc.nextLine());
        System.out.print("Key (1-255): ");
        int key = Integer.parseInt(sc.nextLine());
        if (key < 1 || key > 255)
            throw new IllegalArgumentException("Invalid key");
        byte[] input = Files.readAllBytes(file);
        boolean encrypt = choice.equals("1");
        byte[] output = crypt(input, key, encrypt);
        Path saved = Paths.get(file + (encrypt ? ".enc" : ".dec"));
        Files.write(saved, output);
        System.out.println("Saved: " + saved);
        if (encrypt)
            System.out.println("Correct recovery: " +
                Arrays.equals(input, crypt(output, key, false)));
        sc.close();
    }
}
