import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.*;
public class DSADemo {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter tender message: ");
        String message = sc.nextLine();
        KeyPairGenerator generator = KeyPairGenerator.getInstance("DSA");
        generator.initialize(2048);
        KeyPair keys = generator.generateKeyPair();
        Signature dsa = Signature.getInstance("SHA256withDSA");
        dsa.initSign(keys.getPrivate());
        dsa.update(message.getBytes(StandardCharsets.UTF_8));
        byte[] signature = dsa.sign();
        System.out.println("Digital Signature: " +
            Base64.getEncoder().encodeToString(signature));
        dsa.initVerify(keys.getPublic());
        dsa.update(message.getBytes(StandardCharsets.UTF_8));
        System.out.println("Original verified: " +
            dsa.verify(signature));
        System.out.print("Enter message for verification: ");
        String received = sc.nextLine();
        dsa.initVerify(keys.getPublic());
        dsa.update(received.getBytes(StandardCharsets.UTF_8));
        System.out.println("Received verified: " +
            dsa.verify(signature));
        sc.close();
    }
}

/*
Enter tender message: 50000
Digital Signature: MDwCHGYmcu5eDrTGo2rFq1dvEu9Lcfh45nIAzr0dht0CHDvYoTcuyZFpxtpPAr5Z+kboiCf0EFkHLe57ELM=
Original verified: true
Enter message for verification: 50001
Received verified: false
*/
