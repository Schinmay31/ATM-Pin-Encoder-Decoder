import java.util.*; // Encription and Decryption of ATM Pin Using bit manipullation.

class encryption {
    public class global {
        public static boolean key = true;
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
            global.key = false;
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

    public static LinkedList<Character> rightShift(LinkedList<Character> receivedPinList, String dec) {

        for (int i = 0; i < dec.length(); i++) {
            char temp = dec.charAt(i);
            receivedPinList.add(temp);
        }
        for (int i = 0; i < global.count_Zero; i++) {
            receivedPinList.addFirst('0');
        }
        int x = dec.length() - 1;
        while (x >= 0) {
            if (x == 0) {
                if (global.key == true) {
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

    public static void main(String arg[]) {
        Scanner sc = new Scanner(System.in);

        int AtmPin = sc.nextInt();
        System.out.println("Original ATM Pin : " + AtmPin);

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
        // System.out.println(global.count_Zero);
        String c = toEncryptedBinaryForEncoder(EncryptedList);
        int decimal = Integer.parseInt(c, 2);
        System.out.println("Encrypted Pin: " + decimal);
        System.out.println();
        // ATM Pin Decoder
        int receivedPin = decimal;
        System.out.println("Received Pin : "+receivedPin);
        LinkedList<Character> recivedPinList = new LinkedList<>();
        String b = toBinary(receivedPin);

        recivedPinList = rightShift(recivedPinList, b);
        // System.out.println(recivedPinList);
        String d = toEncryptedBinaryForDecoder(recivedPinList);
        // System.out.println(d);

        int originalPin = Integer.parseInt(d, 2);
        System.out.println("Decrypted Pin : "+originalPin);
    }
}