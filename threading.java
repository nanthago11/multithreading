package assignment;
import java.util.*;
import java.util.Scanner;
import java.util.Random;
public class Question3
{
public static int noOfDice; 
public static int noOfThrowsInEachDice[]; //stores how many times a particular dice has to be rolled
public static int totalTrialsOfDice = 0; 
public static int diceThrows[]; 
public static int counter = 0; 
public static int numberOfFaces = 6; //changing this you might use a octahedral dice or even make it a coin toss by making this 2.

public static void main(String[] args) {
System.out.print("How many dice do you want to roll?: ");
Scanner in = new Scanner(System.in);
noOfDice = in.nextInt();
noOfThrowsInEachDice = new int[noOfDice];
Random rand = new Random();
for(int i=0; i<noOfDice; i++)
{
  System.out.print("How many rolls for die no. " + (i+1) + "?: ");
  Scanner in1 = new Scanner(System.in);
  noOfThrowsInEachDice[i] = in1.nextInt(); //assigning number of trials
  totalTrialsOfDice += noOfThrowsInEachDice[i];
  System.out.println(noOfThrowsInEachDice[i]);
}

diceThrows = new int[totalTrialsOfDice]; 

for(int i=0; i<noOfDice; i++) //starting a thread for each dice
{
  Runnable oneRoller = new DiceRoller(noOfThrowsInEachDice[i]); 
  Thread t = new Thread(oneRoller);
  t.start();
}
try {
  Thread.currentThread().sleep(2000); 
} catch(Exception e) {
  System.out.println("This has to be handled");
}


countFrequencies(diceThrows); //counting all the frequencies
}

public static void countFrequencies(int[] diceThrows)
{
  int count[] = new int[numberOfFaces]; //counter array for each face's frequency.
  for(int i=0; i<diceThrows.length; i++)
  {
      count[diceThrows[i]-1]++;
  }
  for(int i=0; i<numberOfFaces; i++)
  {
      System.out.println("Number of " + (i+1) + "s is " + count[i]);
  }
}

public static synchronized void diceEntry(int dieValue) 
{
  //only one thread can enter this function at a time due to it being a synchronised one.
  diceThrows[counter] = dieValue;
  System.out.println (": current threadt id :" +
	      Thread.currentThread ().getId ());
  counter++;
}
}

class DiceRoller implements Runnable 
{
   public int noOfRolls;
  
   public DiceRoller(int noOfRolls)
   {
       this.noOfRolls = noOfRolls;
   }
  
   Random rand = new Random();
   public void run()
   {
       for(int i=0; i<noOfRolls; i++)
       {
           int rollResult = rand.nextInt(Question3.numberOfFaces) + 1;
           Question3.diceEntry(rollResult); //synchronized writing to the array of diceThrows
       }
   }
}
