import java.math.BigInteger;
import java.util.Scanner;
public class DHMITM {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter prime p and generator g: ");
        BigInteger p = sc.nextBigInteger();
        BigInteger g = sc.nextBigInteger();
        System.out.print("Alice and Bob private keys: ");
        BigInteger a = sc.nextBigInteger();
        BigInteger b = sc.nextBigInteger();
        System.out.print("Eve's two private keys: ");
        BigInteger e1 = sc.nextBigInteger();
        BigInteger e2 = sc.nextBigInteger();
        BigInteger A = g.modPow(a, p);
        BigInteger B = g.modPow(b, p);
        BigInteger alice = B.modPow(a, p);
        BigInteger bob = A.modPow(b, p);
        System.out.println("\n--- NORMAL DH ---");
        System.out.println("Alice public: " + A);
        System.out.println("Bob public: " + B);
        System.out.println("Alice secret: " + alice);
        System.out.println("Bob secret: " + bob);
        System.out.println("Match: " + alice.equals(bob));
        BigInteger fakeB = g.modPow(e1, p);
        BigInteger fakeA = g.modPow(e2, p);
        BigInteger aliceMITM = fakeB.modPow(a, p);
        BigInteger eveAlice = A.modPow(e1, p);
        BigInteger bobMITM = fakeA.modPow(b, p);
        BigInteger eveBob = B.modPow(e2, p);
        System.out.println("\n--- MITM ATTACK ---");
        System.out.println("Eve sends to Alice: " + fakeB);
        System.out.println("Eve sends to Bob: " + fakeA);
        System.out.println("Alice secret: " + aliceMITM);
        System.out.println("Eve-Alice secret: " + eveAlice);
        System.out.println("Bob secret: " + bobMITM);
        System.out.println("Eve-Bob secret: " + eveBob);
        System.out.println("Eve matches Alice: " + eveAlice.equals(aliceMITM));
        System.out.println("Eve matches Bob: " + eveBob.equals(bobMITM));
        System.out.println("Alice matches Bob: " + aliceMITM.equals(bobMITM));
        System.out.println("\n--- MITIGATION ---");
        System.out.println("1. Digital signatures");
        System.out.println("2. Digital certificates");
        System.out.println("3. Authenticated DH using TLS");
        sc.close();
    }
}


/*
Enter prime p and generator g: 23 5
Alice and Bob private keys: 6 15
Eve's two private keys: 7 11

--- NORMAL DH ---
Alice public: 8
Bob public: 19
Alice secret: 2
Bob secret: 2
Match: true

--- MITM ATTACK ---
Eve sends to Alice: 17
Eve sends to Bob: 22
Alice secret: 12
Eve-Alice secret: 12
Bob secret: 22
Eve-Bob secret: 22
Eve matches Alice: true
Eve matches Bob: true
Alice matches Bob: false

--- MITIGATION ---
1. Digital signatures
2. Digital certificates
3. Authenticated DH using TLS
  */
