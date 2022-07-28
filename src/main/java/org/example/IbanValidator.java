package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class IbanValidator {

    private String fileName="date.txt";

    public  boolean isIbanValid(String iban) throws Exception {

        iban = iban.replace(" ", "").toUpperCase();

        checksNumberOfCharsByCode(iban);

        BigInteger x = new BigInteger(convertIbanToNumber(iban));

        return x.remainder(BigInteger.valueOf(97)).equals(BigInteger.ONE);

    }
    private void checksNumberOfCharsByCode(String iban) throws Exception {

        Map<String,Integer> countryCode = new HashMap<>();

        if(iban.length()<2){
            throw new InvalidIbanException("Wrong Iban!");
        }

        getCountryCodeAndIbanLength(countryCode);

        if (countryCode.get(iban.substring(0, 2)) == null) {
            throw new InvalidIbanException("Wrong Iban! Code Country does not exist.");
        } else if (countryCode.get(iban.substring(0, 2)) != iban.length()) {
            throw new InvalidIbanException("Wrong Iban! Length does not match.");
        }

    }
    private  void getCountryCodeAndIbanLength(Map<String,Integer> countryCode) throws Exception {

        URL url =  getClass().getClassLoader().getResource(fileName);

        if (url == null) {
            throw new InvalidIbanException("File not found!",new FileNotFoundException());
        } else {

            File file = new File(url.getPath());

            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {

                countryCode.put(scanner.next(), Integer.parseInt(scanner.next()));

                scanner.nextLine();
            }
            scanner.close();
        }
    }
    private String convertIbanToNumber(String iban) throws InvalidIbanException {

        StringBuilder number = new StringBuilder();

        iban = iban.substring(4) + iban.substring(0, 4);

        for (int i = 0; i < iban.length(); i++) {

            if (Character.isDigit(iban.charAt(i)))

                number.append(iban.charAt(i));

            else if (Character.isLetter(iban.charAt(i)))

                number.append(iban.charAt(i) - 55);

            else {
                throw new InvalidIbanException("Wrong Iban! Contain special character.");
            }
        }
        return number.toString();
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
