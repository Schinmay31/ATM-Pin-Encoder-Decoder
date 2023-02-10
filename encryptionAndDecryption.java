import java.util.*; // Encription and Decryption of ATM Pin Using bit manipullation.

class encryptionAndDecryption {
    public class global {
        public static int one_condition = 1;
        public static int count_Zero = 0;
    }

    public static String toBinary(int a) {

        String bin = Integer.toBinaryString(a);
        return bin;
    }

    public static ArrayList<Character> leftShift(ArrayList<Character> list, String a) {

        for (int i = 0; i < a.length(); i++) {
            char temp = a.charAt(i);
            list.add(temp);
        }

        if (list.get(0) == '0') {
            global.one_condition = 0;
        }
        int x = 0;
        while (x != a.length()) {
            if (x == a.length() - 1) {
                list.set(x, '0');
                return list;
            }

            list.set(x, list.get(x + 1));
            x++;
        }

        return list;
    }

    public static String toEncryptedBinaryForEncoder(ArrayList<Character> EncryptedList) {
        String msg = "";
        for (int i = 0; i < EncryptedList.size(); i++) {
            msg += EncryptedList.get(i);
        }
        return msg;
    }

    public static String toEncryptedBinaryForDecoder(LinkedList<Character> list) {
        String msg = "";
        for (int i = 0; i < list.size(); i++) {
            msg += list.get(i);
        }
        return msg;
    }

    public static LinkedList<Character> rightShift(LinkedList<Character> receivedPinList, String dec, int one,
            int zero) {

        for (int i = 0; i < dec.length(); i++) {
            char temp = dec.charAt(i);
            receivedPinList.add(temp);
        }
        for (int i = 0; i < zero; i++) {
            receivedPinList.addFirst('0');
        }
        int x = dec.length() - 1;
        while (x >= 0) {
            if (x == 0) {
                if (one == 1) {
                    receivedPinList.set(0, '1');
                    x--;
                } else {
                    receivedPinList.set(0, '0');
                    x--;
                }

            } else {
                receivedPinList.set(x, receivedPinList.get(x - 1));
                x--;
            }
        }
        return receivedPinList;
    }

    public static int keyGenerator(int a, int b) {
        int x = a;
        x = (a * 10) + b;
        return x;
    }

    public static int keyAdder(int d, int k) {
        int x = d * 100;
        x += k;
        return x;
    }

    public static ArrayList<Integer> keyRemover(int r) {
        ArrayList<Integer> list = new ArrayList<>();
        int x = r % 100;
        r = r / 100;         
        list.add(r);
        list.add(x);

        return list;
    }

    public static void main(String arg[]) {
        Scanner sc = new Scanner(System.in);
        int num[] = {2815,2119,8818,4835,1123,9918,2341,6547,3471,4364};


        System.out.print("Enter Your 6 Digit ATM Pin : "); // Note : you cannot Enter 0 as starting of your Pin.
        int AtmPin = sc.nextInt();
        System.out.println();
        System.out.println("Original ATM Pin : " + AtmPin);
        //
        //
        // ATM Pin Encoder                   
        String a = toBinary(AtmPin);
        // System.out.println("Original Atm Pin Bit : " + a);
        ArrayList<Character> list = new ArrayList<>();

        ArrayList<Character> EncryptedList = leftShift(list, a);
        // System.out.println("Encrypted Signal Bit : " + EncryptedList);

        for (int i = 0; i < EncryptedList.size(); i++) {
            if (EncryptedList.get(i) == '1')
                break;
            else
                global.count_Zero++;

        }

        String c = toEncryptedBinaryForEncoder(EncryptedList);
        int decimal = Integer.parseInt(c, 2);

        int key = keyGenerator(global.one_condition, global.count_Zero);
        int decimalKey = keyAdder(decimal, key);
        // System.out.println("Encrypted Pin : " + decimalKey);
        // System.out.println();
        double Drand = Math.random()*9;
        int Irand = (int)Drand;
        int decimalWithRand = decimalKey+num[Irand];
        //  System.out.println(Irand);
        String decimalInHex = Integer.toHexString(decimalWithRand);
        // System.out.println(decimalInHex);
        String IrandInString=String.valueOf(Irand);

        decimalInHex += IrandInString;
        System.out.println("Encrypted Pin : "+decimalInHex); // this is to transmit over the internet.
        System.out.println();

        //
        //
        //
        //
        // ATM Pin Decoder
        // KEY : {1/0,number}; example: 12,07,18
        //
        //
        //
        //
        String recDecimalInHex = decimalInHex;
        System.out.println("Received Pin : " + recDecimalInHex);
        char dIdxInChar = recDecimalInHex.charAt(recDecimalInHex.length()-1);
        int dIdx = dIdxInChar - '0';
        // System.out.println(dIdx);
         
        StringBuilder sb = new StringBuilder(recDecimalInHex);
        sb.deleteCharAt(recDecimalInHex.length() - 1);
        // System.out.println(sb);
        String singleString = sb.toString();
        int recInDecimal=Integer.parseInt(singleString,16);
        // System.out.println(recInDecimal);  
        
        recInDecimal -= num[dIdx];
        // System.out.println(recInDecimal);






        int receivedPin = recInDecimal;
        
        ArrayList<Integer> ListOfKey = new ArrayList<>();
        ListOfKey = keyRemover(receivedPin);
        receivedPin = ListOfKey.get(0);
        int ReceivedKey = ListOfKey.get(1);
        int Rcount_Zero = ReceivedKey % 10;
        int Rone_condition = ReceivedKey / 10;

        LinkedList<Character> recivedPinList = new LinkedList<>();
        String b = toBinary(receivedPin);

        recivedPinList = rightShift(recivedPinList, b, Rone_condition, Rcount_Zero);
        String d = toEncryptedBinaryForDecoder(recivedPinList);

        int originalPin = Integer.parseInt(d, 2);
        System.out.println("Decrypted Pin : " + originalPin);

    }
}