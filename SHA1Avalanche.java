import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Scanner;
public class SHA1Avalanche {
    static byte[] digest(String text) throws Exception {
        return MessageDigest.getInstance("SHA-1").digest(text.getBytes(StandardCharsets.UTF_8));
    }
    static String hex(byte[] bytes) {
        StringBuilder s = new StringBuilder();
        for (byte b : bytes)
            s.append(String.format("%02x", b & 0xff));
        return s.toString();
    }
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter original text: ");
        String first = sc.nextLine();
        System.out.print("Enter modified text: ");
        String second = sc.nextLine();
        byte[] a = digest(first);
        byte[] b = digest(second);
        int differentBits = 0;
        for (int i = 0; i < a.length; i++)
            differentBits += Integer.bitCount((a[i] ^ b[i]) & 0xff);
        System.out.println("Original SHA-1 : " + hex(a));
        System.out.println("Modified SHA-1 : " + hex(b));
        System.out.println("Digest length   : " + (a.length * 8) + " bits");
        System.out.println("Different bits  : " + differentBits + "/160");
        System.out.printf("Bit difference  : %.2f%%%n",
                          differentBits * 100.0 / 160);
        System.out.println("Digests match   : " +
                           MessageDigest.isEqual(a, b));
        sc.close();
    }
}


/*
Enter original text: Hello
Enter modified text: hello
Original SHA-1 : f7ff9e8b7bb2e09b70935a5d785e0cc5d9d0abf0
Modified SHA-1 : aaf4c61ddcc5e8a2dabede0f3b482cd9aea9434d
Digest length   : 160 bits
Different bits  : 75/160
Bit difference  : 46.88%
Digests match   : false
*/
