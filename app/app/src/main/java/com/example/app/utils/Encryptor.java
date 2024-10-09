package com.example.app.utils;

public class Encryptor {

    /**
     * Convierte un valor alfanumérico a un valor numérico
     * <p>El parametro puede contener '-' para indicar que es negativo</p>
     * <p>Ejemplo: "a" -> 10, "-b" -> -11</p>
     * @param key Valor alfanumérico
     * @return int
     * @throws IllegalArgumentException
     */
    public int alphaToValue(String key) {
        if ((!key.startsWith("-") && key.length() >= 2) || key.equals("-") || key.isEmpty()) {
            String reason = "El valor '%s' no es válido, solo se permite un único caracter (permite expresion negatiava)";
            reason = String.format(reason, key);
            throw new IllegalArgumentException(reason);
        }

        int value = 0;
        try {
            value = Integer.parseInt(key);
        } catch (Exception e) {
            char[] c = key.toCharArray();
            value = (int) c[c.length - 1] - 87;

            if (key.startsWith("-")) {
                value *= -1;
            }
        }

        return value;
    }

    /**
     * Encripta o desencripta un texto utilizando una clave
     * @param input Texto a encriptar
     * @param key Clave para encriptar
     * @return String
     */
    public String encrypt(String input, String[] key) {
        String value = "";
        int k = 0;
        char x;
        for (int i = 0; i < input.length(); i++) {
            x = (char) (((int) input.charAt(i)) + alphaToValue(key[k]));
            value += x;

            k++;
            if (k >= key.length) {
                k = 0;
            }
        }

        return value;
    }

    public String toHash(String input, String extraData, int id) {
        if (id <= 0 || input.isEmpty()) {
            String reason = "El id de encriptación no puede ser menor o igual a 0 y el texto no puede estar vacío";
            throw new IllegalArgumentException(reason);
        }

        int dirtyHash = 0;
        for (int i = 0; i < input.length(); i++) {
            if (String.valueOf(input.charAt(i)).equals("-")) {
                continue;
            }
            dirtyHash += Math.abs(alphaToValue(String.valueOf(input.charAt(i))));
        }

        if (!extraData.isEmpty()) {
            for (int i = 0; i < extraData.length(); i++) {
                dirtyHash += alphaToValue(String.valueOf(extraData.charAt(i)));
            }
        }

        Conversor conv = new Conversor();

        String hashKey = conv.toBaseN((byte) 16, Long.parseLong(String.valueOf(dirtyHash)));
        String hashValue = "";
        long x = 0;
        String dh = String.valueOf(dirtyHash);
        for (int i = 0; i < dh.length(); i++) {
            if ((dh.charAt(i) + "").equals("-")) {
                continue;
            }

            x = ((long) dh.charAt(i)) + alphaToValue(dh.charAt(i) + "") * id;
            hashValue += x;
        }

        String hash16 = conv.toBaseN((byte) 16, Long.parseLong(hashValue));

        String hashStak = hashKey + hashValue + dirtyHash + hash16;
        hashStak = encrypt(hashStak, hash16.split(""));

        String hash = "";
        for (int i = 0; i < hashStak.length(); i++) {
            if (conv.isNumeric(hashStak.charAt(i) + "")) {
                hash += hashStak.charAt(i);
            } else {
                hash += alphaToValue(hashStak.charAt(i) + "");
            }
        }

        return hash;
    }
}