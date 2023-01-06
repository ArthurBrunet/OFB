import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length != 4) {
            System.out.println("Usage: java -jar CBCProject.jar -d 4 1010 test.txt");
            return;
        }
        String mode = args[0];
        if (!mode.equals("-d") && !mode.equals("-e")) {
            System.out.println("Usage: java -jar CBCProject.jar -d 4 1010 test.txt");
            return;
        }
        int k = Integer.parseInt(args[1]);
        if(!args[1].matches("[0-9]+")) {
            System.out.println("La clé doit être un entier");
            return;
        }
        String v = args[2];
        if (!args[2].matches("[01]+")) {
            System.out.println("Le vecteur initiale doit être en binaire");
            return;
        }
        String fileName =  args[3];
        byte[] messageByte;
        if (mode.equals("-e")) {
            byte[] messageByteTemp = octetToBinaryArray(readFile(fileName));
            int size = (messageByteTemp.length % k) == 0 ? messageByteTemp.length : messageByteTemp.length + (k - (messageByteTemp.length % k));
            messageByte = new byte[size];
            System.arraycopy(messageByteTemp, 0, messageByte, 0, messageByteTemp.length);
        } else {
            messageByte = readFile(fileName);
        }
        byte[] vectorByte = StringToBinary(v);
        byte[] msgEnc = new byte[messageByte.length];
        for (int i = 0; i < messageByte.length ; i += k) {
            byte[] vTemp = Arrays.copyOfRange(chiffrement(vectorByte), 0, k);
            byte[] blocMessage = Arrays.copyOfRange(messageByte, i, i + k);
            for (int j = 0; j < blocMessage.length; j++) {
                int i1 = blocMessage[j] ^ vTemp[j];
                msgEnc[i + j] = (byte) i1;
            }
            vectorByte = padding(vTemp, v.length());
        }
        if (mode.equals("-e")) {
            saveFile("text.encrypted", msgEnc);
        }else {
            saveFile("text.decrypted",binaryToString(msgEnc).getBytes());
        }
    }

    private static byte[] readFile(String filename) throws IOException {
        try (FileInputStream fis = new FileInputStream(filename)) {
            byte[] bytes = new byte[fis.available()];
            fis.read(bytes);
            return bytes;
        }
    }
    private static void saveFile(String fileNameEncrypted, byte[] result) {
        try {
            FileOutputStream fos = new FileOutputStream(fileNameEncrypted);
            fos.write(result);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static byte[] chiffrement(byte[] vByte) {
        byte temp = vByte[0];
        for (int i = 0; i < vByte.length - 1; i++) {
            vByte[i] = vByte[i + 1];
        }
        vByte[vByte.length - 1] = temp;
        return vByte;
    }

    public static byte[] padding(byte[] vByte, int size) {
        byte[] vByteTemp = new byte[size];
        for (int i = size - vByte.length; i < vByte.length; i++) {
            vByteTemp[i] = vByte[i - (size - vByte.length)];
        }
        return vByteTemp;
    }
    public static byte[] octetToBinaryArray(byte[] bytes) {
        byte[] binary = new byte[bytes.length * 8];
        for (int i = 0; i < bytes.length; i++) {
            int val = bytes[i];
            for (int j = 0; j < 8; j++) {
                binary[i * 8 + j] = (byte) ((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
        }
        return binary;
    }

    public static String binaryToString(byte[] binary) {
        byte[] bytes = new byte[binary.length / 8];
        for (int i = 0; i < bytes.length; i++) {
            for (int j = 0; j < 8; j++) {
                bytes[i] = (byte) ((bytes[i] << 1) | binary[i * 8 + j]);
            }
        }
        return new String(bytes, StandardCharsets.UTF_8);
    }
    public static byte[] StringToBinary(String encryptedString){
        byte[] result = new byte[encryptedString.length()];
        for (int i = 0; i < encryptedString.length(); i++) {
            result[i] = (byte) Character.getNumericValue(encryptedString.charAt(i));
        }
        return result;
    }
}