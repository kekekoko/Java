package com.Kerstin.JavaTutorial;
import java.util.Hashtable;
public class MarkovHashtable{
    Hashtable<String, String> preSuffixes = new Hashtable<String, String>();  
    public static void main (String[] args){
        MarkovHashtable bla = new MarkovHashtable();
        int length = args.length;
        final int last = length - 1;
        for (int i = 1; i < length; i++){
            if (i == 1){
                bla.preSuffixes.put("noValue " + args[i-1], args[i]);
            } else if (i == last){
                bla.preSuffixes.put(args[i - 2] + " " + args[i - 1], args[i]);
                bla.preSuffixes.put(args[i - 1] + " " + args[i], "noValue");
                bla.preSuffixes.put(args[i] + " noValue", "noValue noValue");
            } else {
                bla.preSuffixes.put(args[i - 2] + " " + args[i - 1], args[i]);
            }
        }
        String result = args[0];
        String lastValue = args[0];
        String secondLastValue = "noValue";
        String newValue;
        while (lastValue != "noValue"){
            newValue = bla.preSuffixes.get(secondLastValue + " " + lastValue);
            secondLastValue = lastValue;
            lastValue = newValue;
            if (newValue != "noValue"){
            	result += " " + newValue;
            }
        }
        System.out.print(result);
    }
}