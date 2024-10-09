package com.example.app.utils;

public class Conversor {

    /**
     * Convierte un número decimal a un número en base N
     * <p>El número base debe estar entre 2 y 36</p>
     * <p>Ejemplo: 10, 2 -> "1010"</p>
     * @param baseNumber Número base
     * @param decNumber Número decimal
     * @return String
     * @throws IllegalArgumentException
     */
    public String toBaseN(byte baseNumber, long decNumber) {
        if (2 > baseNumber || baseNumber > 36) {
            String reason = "El número base '%i' no es válido, solo se permite un número entre 2 y 36";
            reason = String.format(reason, baseNumber);
            throw new IllegalArgumentException(reason);
        }

        int size = (int) Math.floor(Math.log(decNumber) / Math.log(baseNumber)) + 1;
        long[] cells = new long[size];
        int i = size -1;
        while (decNumber >= baseNumber) {
            cells[i] = decNumber % baseNumber;
            decNumber = decNumber / baseNumber;
            i--;
        }
        cells[0] = decNumber;

        String value = "";
        for (int j = 0; j < size; j++) {
            if (cells[j] < 10) {
                value += cells[j];
            } else {
                value += (char) (cells[j] + 87);
            }
        }

        return value;
    }

    /**
     * Convierte un número en base N a un número decimal
     * <p>El número base debe estar entre 2 y 36</p>
     * <p>Ejemplo: 2, "1010" -> 10</p>
     * @param baseNumber Número base
     * @param number Número en base N
     * @return long
     * @throws IllegalArgumentException
     */
    public long toBase10(byte baseNumber, String number) {
        if (2 > baseNumber || baseNumber > 36) {
            String reason = "El número base '%i' no es válido, solo se permite un número entre 2 y 36";
            reason = String.format(reason, baseNumber);
            throw new IllegalArgumentException(reason);
        }

        number = number.toLowerCase();
        long output = 0;
        int x = 0;
        int exp = number.length() - 1;

        for (int i = 0; i < number.length(); i++) {
            if (isNumeric(number)) {
                x = Integer.parseInt(String.valueOf(number.charAt(i)));
            } else {
                x = (int) number.charAt(i) - 87;
            }

            if (x >= baseNumber) {
                String reason = "El número '%s' no es válido para la base '%d'";
                reason = String.format(reason, number, baseNumber);
                throw new IllegalArgumentException(reason);
            }

            output += x * Math.pow(baseNumber, exp);
            exp--;
        }

        return output;
    }

    /**
     * Verifica si un caracter es numérico
     * @param x texto a verificar
     * @return boolean
     */
    public boolean isNumeric(String x) {
        boolean value = false;
        try {
            Long.parseLong(x);
            value = true;
        } catch (Exception e) {}

        return value;
    }
}