import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.Arrays;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

import java.util.stream.Collectors;

/**
 * Provides an implementation of the WordLadderGame interface. 
 *
 * @author Andrew Moon (atm0091@auburn.edu)
 */
public class Doublets implements WordLadderGame {

   // The word list used to validate words.
   // Must be instantiated and populated in the constructor.
   /////////////////////////////////////////////////////////////////////////////
   // DECLARE A FIELD NAMED lexicon HERE. THIS FIELD IS USED TO STORE ALL THE //
   // WORDS IN THE WORD LIST. YOU CAN CREATE YOUR OWN COLLECTION FOR THIS     //
   // PURPOSE OF YOU CAN USE ONE OF THE JCF COLLECTIONS. SUGGESTED CHOICES    //
   // ARE TreeSet (a red-black tree) OR HashSet (a closed addressed hash      //
   // table with chaining).
   /////////////////////////////////////////////////////////////////////////////
   
   private TreeSet<String> lexicon;
   private int hammingTotal;
   private ArrayList<String> neighbors;
   private int minLadCount = 0;
   private ArrayList<String> minWordLadder;
   private List<String> neighborList;
   private boolean check;
   private int hamNum;

   /**
    * Instantiates a new instance of Doublets with the lexicon populated with
    * the strings in the provided InputStream. The InputStream can be formatted
    * in different ways as long as the first string on each line is a word to be
    * stored in the lexicon.
    */
   public Doublets(InputStream in) {
      try {
         //////////////////////////////////////
         // INSTANTIATE lexicon OBJECT HERE  //
         //////////////////////////////////////
         lexicon = new TreeSet<String>();
         Scanner s =
            new Scanner(new BufferedReader(new InputStreamReader(in)));
         while (s.hasNext()) {
            String str = s.next();
            /////////////////////////////////////////////////////////////
            // INSERT CODE HERE TO APPROPRIATELY STORE str IN lexicon. //
            /////////////////////////////////////////////////////////////
            lexicon.add(str.toLowerCase());
            s.nextLine();
         }
         in.close();
      }
      catch (java.io.IOException e) {
         System.err.println("Error reading from InputStream.");
         System.exit(1);
      }
   }


   //////////////////////////////////////////////////////////////
   // ADD IMPLEMENTATIONS FOR ALL WordLadderGame METHODS HERE  //
   //////////////////////////////////////////////////////////////
   
   public int getWordCount() {
      return lexicon.size();
   }
   
   public boolean isWord(String str) {
      if (lexicon.contains(str)) {
         return true;
      }
      return false;
   }
   
   public int getHammingDistance(String str1, String str2) {
      hammingTotal = 0;
      if (str1.length() != str2.length()) {
         return -1;
      }
      for (int i = 0; i < str1.length(); i++) {
         if (str1.charAt(i) != str2.charAt(i)) {
            hammingTotal++;
         }
      }
      return hammingTotal;
   }
   
   public List<String> getNeighbors(String word) {
      neighbors = new ArrayList<String>();
      for (String s : lexicon) {
         if (getHammingDistance(s, word) == 1) {
            neighbors.add(s);
         }
      }
      return neighbors;
   }
   
   public boolean isWordLadder(List<String> sequence) {
      String temp = "";
      int count = 0;
      if (sequence.size() == 0) {
         return false;
      }
      for (String s : sequence) {
         if (!lexicon.contains(s)) {
            return false;
         }
         if (count == 0) {
            temp = s;
            count++;
            continue;
         }
         if (getHammingDistance(s, temp) != 1) {
            return false;
         }
         temp = s;
      }
      return true;
   }
   
   /**TO DO: Create a the part of the bfs that searches for the minLadder, even if 
   the hamming distance does not decrease after one word.*/
   public List<String> getMinLadder(String start, String end) {
     /*probably have to use getNeighbors method, and recursively
     find the neighbor that leads to the smallest amount of strings to
     get to the end word */
     
      minWordLadder = new ArrayList<String>();
      minWordLadder.add(start);
      neighborList = new ArrayList<String>();
      //String curr = "";
      hamNum = getHammingDistance(start, end);
      if (getHammingDistance(start, end) == 0) {
         return minWordLadder;
      }
      if (getHammingDistance(start, end) < 0) {
         minWordLadder.remove(minWordLadder.get(minWordLadder.size() - 1));
         return minWordLadder;
      }
      wordSearch(start, end, hamNum);
      if (minWordLadder.size() <= 1) {
         minWordLadder.remove(minWordLadder.get(minWordLadder.size() - 1));
      }
      return minWordLadder;
   }
   
   private void wordSearch(String start, String end, int hamNum) {
      check = false;
      neighborList = getNeighbors(start);
      for (String s : neighborList) {
         //loop through neighbors, see if any lead to end word
         if (s.equals(end)) {
            minWordLadder.add(s);
            check = true;
            return;
         }
         else if (getHammingDistance(s, end) < hamNum) {
            hamNum = getHammingDistance(s, end);
            minWordLadder.add(s);
            wordSearch(s, end, hamNum);
            if (check == false) {
               minWordLadder.remove(minWordLadder.get(minWordLadder.size() - 1));
            }
            else if (check == true) {
               return;
            }
         }
      }
   }
   
   
   /**private boolean hasNeighbors(String str) {
      return getNeighbors(str) >= 1;
   }**/
}