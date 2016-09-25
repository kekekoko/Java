package com.Kerstin.reddit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
public class Markov{
    //#270 [Intermediate] Generating Text with Markov Processes
    HashMap<String, ArrayList<String>> preSuffixes;
    Markov(String[] args){
        preSuffixes = new HashMap<String, ArrayList<String>>();
        int length = args.length;
        final int last = length - 1;
        for (int i = 1; i < length; i++){
            if (i == 1){
                this.mapValues(this.preSuffixes, "noValue " + args[i-1], args[i]);
            } else if (i == last){
                this.mapValues(this.preSuffixes, args[i - 2] + " " + args[i - 1], args[i]);
                this.mapValues(this.preSuffixes, args[i - 1] + " " + args[i], "noValue");
                this.mapValues(this.preSuffixes, args[i] + " noValue", "noValue noValue");
            } else {
            	this.mapValues(this.preSuffixes, args[i - 2] + " " + args[i - 1], args[i]);
            }
        }
        String result = args[0];
        String lastValue = args[0];
        String secondLastValue = "noValue";
        String newValue;
        Random randomValue = new Random();
        while (lastValue != "noValue"){
            int noOfValues = this.preSuffixes.get(secondLastValue + " " + lastValue).size();
            newValue = this.preSuffixes.get(secondLastValue + " " + lastValue).get(randomValue.nextInt(noOfValues));
            secondLastValue = lastValue;
            lastValue = newValue;
            if (newValue != "noValue"){
                result += " " + newValue;
            }
        }
        System.out.print(result);
    }
    public static void main (String args[]){
        new Markov(args);
    }
    private void mapValues(HashMap<String, ArrayList<String>> myHashMap, String myKey, String myValue){
        if (!myHashMap.containsKey(myKey)){
            myHashMap.put(myKey, new ArrayList<String>());
        }
        myHashMap.get(myKey).add(myValue);
    }
}