package com.Kerstin.reddit;
public class Degree{
	//#273 [Easy] Getting a degree
    public static void main (String[] args){
        Degree bla = new Degree();
        for (String arg: args){
            System.out.println(bla.convert(arg));
        }
    }
    private String convert(String inputValue){
        int i = 0;
        String resultString;
        float inputNumber;
        while (i < inputValue.length() & (Character.isDigit(inputValue.charAt(i)) | inputValue.charAt(i) == '.')){
            i++;
        }
        try {
            inputNumber = Float.parseFloat(inputValue.substring(0, i));
        } catch (NumberFormatException e){
            resultString = "Invalid input argument";
            return resultString;
        }
        char fromUnit = inputValue.charAt(i);
        char toUnit = inputValue.charAt(i+1);
        int outputNumber;
        switch (fromUnit){
        case 'r': 
            if (toUnit == 'd'){
                outputNumber = (int) Math.round((inputNumber * 180 / Math.PI));
                resultString = Integer.toString(outputNumber) + "d";
            } else resultString = "No candidate for conversion";
            break;
        case 'd':
            if (toUnit == 'r'){
                outputNumber = (int) Math.round((inputNumber * Math.PI / 180));
                resultString = Integer.toString(outputNumber) + "r";
            } else resultString = "No candidate for conversion";
            break;
        default: resultString = "invalid input argument";
        }
        return resultString;
    }
}