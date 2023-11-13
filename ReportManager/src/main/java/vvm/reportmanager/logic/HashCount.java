package vvm.reportmanager.logic;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashCount {
    /**
     * байт-массив преобразует в Hash
     * @param bytes байт-массив для преобразования в Hash
     * @return возвращает строку с hash
     */
    private String bytesToHex(byte[] bytes) {
        var builder = new StringBuilder();
        for (var b : bytes) {
            builder.append(String.format("%02x", b & 0xff));
        }
        return builder.toString();
    }

    /**
     * Считает hash файла
     * @param filename файл для которго рассчитывается hash
     * @return возвращает строку с hash числом
     */
    public String getMd5HashForFile(String filename) {
        try {
            var md = MessageDigest.getInstance("SHA-256");
            var buffer = new byte[8192];
            try (var is = Files.newInputStream(Paths.get(filename))) {
                int read;
                while ((read = is.read(buffer)) > 0) {
                    md.update(buffer, 0, read);
                }
            }
            byte[] digest = md.digest();
            return bytesToHex(digest);
        } catch (NoSuchAlgorithmException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Считает hash для переданной строки
     * @param str строка для расчета hash числа
     * @return возвращает строку с hash числом
     */
    public String alterHash(String str){
        String newstr = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(str.getBytes());
            byte[] digest = md.digest();
            newstr = bytesToHex(digest);
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
        return newstr;
    }
}
