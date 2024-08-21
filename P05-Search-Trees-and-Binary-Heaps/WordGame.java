import java.io.File;
import java.util.TreeSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.Scanner;
import java.lang.Math;
import java.util.Arrays;
import java.util.ArrayList;

public class WordGame implements WordSearchGame {

   private String[][] board;
   private int numRows;
   private int numCols;
   private static final int NUM_NEIGHBORS = 8;
   private TreeSet<String> lexicon;
   private boolean[][] visited;
   private int order;
   private String buildingWord;
   private SortedSet<String> scorableWords;
   private ArrayList<Position> visitedPath;
   private ArrayList<Integer> numList;
   private String buildingWord2;
   //private int dfs2Calls = 1;
   private boolean check = false;

   public void loadLexicon(String fileName) {
      lexicon = new TreeSet<String>();
      if (fileName == null) {
         throw new IllegalArgumentException("File does not exist");
      }
      File file = new File(fileName);
      /**if (!file.canRead()) {
         throw new IllegalArgumentException("File cannot be opened");
      }*/
      try {
         Scanner scan = new Scanner(file);
         while (scan.hasNext()) {
            String current = scan.next();
            current = current.toUpperCase();
            lexicon.add(current);
         }
      }
      
      catch (java.io.FileNotFoundException e) {
         throw new IllegalArgumentException("File not found");
      }
   }
   
   public void setBoard(String[] letterArray) {
      if (letterArray == null) {
         throw new IllegalArgumentException("Null parameter");
      }
      double check = Math.sqrt(letterArray.length);
      int checkOne = (int)Math.sqrt(letterArray.length);
      if (check != checkOne) {
         throw new IllegalArgumentException("Parameter not square");
      }
      numRows = (int)Math.sqrt(letterArray.length);
      numCols = (int)numRows;
      board = new String[numRows][numCols];
      int n = 0;
      for (int i = 0; i < numRows; i++) {
         for (int j = 0; j < numCols; j++) {
            board[i][j] = letterArray[n];
            n++;
         }
      }
   }
   
   public void setBoard() {
      board = new String[4][4];
      String[] array = {"E", "E", "C", "A", "A", "L", "E", "P", "H",
          "N", "B", "O", "Q", "T", "T", "Y"};
      int n = 0;
      for (int i = 0; i < numRows; i++) {
         for (int j = 0; j < numCols; j++) {
            board[i][j] = array[n];
            n++;
         }
      }
   }
   
   public String getBoard() {
      String output = "";
      for (int i = 0; i < numRows; i++) {
         for (int j = 0; j < numCols; j++) {
            if (j == numCols - 1) {
               output += board[i][j];
            }
            else {
               output += board[i][j] + " ";
            }
         }
         if (i == numRows - 1) {
            break;
         }
         else {
            output += "\n";
         }
      }
      return output;
   }
   
   public SortedSet<String> getAllScorableWords(int minimumWordLength) {
      if (minimumWordLength < 1) {
         throw new IllegalArgumentException("Error");
      }
      if (lexicon == null) {
         throw new IllegalStateException("Error");
      }
      // instantiate visitedPath, scorableWords, and buildingWord that will be applied
      visitedPath = new ArrayList<Position>();
      scorableWords = new TreeSet<String>();
      buildingWord = "";
      // looping through 2d board
      for (int i = 0; i < numRows; i++) {
         for (int j = 0; j < numCols; j++) {
            // start building word off current string at position
            buildingWord = board[i][j];
            // if word itself is already a valid word
            if (isValidWord(buildingWord) && buildingWord.length() >= minimumWordLength) {
               scorableWords.add(buildingWord);
            }
            // if this current buildingWord is a valid prefix
            if (isValidPrefix(buildingWord)) {
            // create a position at this point, to pass to the dfs
               Position p = new Position (i, j);
               // mark this spot as a visited spot in this new path
               visitedPath.add(p);
               // call the dfs
               dfs(p, minimumWordLength);
               /** by the time dfs returns, there will only be this original position
               in the visitedPath, which I then remove, leaving an empty visitedPath, so that
               the next time dfs is called, even though the visitedPath gets marked, all cells
               are still false because visitedPath is empty*/
               visitedPath.remove(p);
            }
         }
      }
      return scorableWords;
   }
   
   private void dfs(Position position, int minLen) {
     // unvisits cells except for the path taken so far (stored in visitedPath)
      unvisitAll();
      markPathTaken();
     // loops through all neighbors
      for (Position neighbor : position.neighbors()) {
         // if neighbor is not visited
         if (!isVisited(neighbor)) {
            // mark neighbor as visited
            visit(neighbor); 
            // if this string + current building word is a valid prefix...
            if (isValidPrefix(buildingWord + board[neighbor.x][neighbor.y])) {
               // add the string to the building word, and add this position to the visited path
               buildingWord += board[neighbor.x][neighbor.y];
               visitedPath.add(neighbor);
               // if the building word has created a valid word, add it.
               if (isValidWord(buildingWord) && buildingWord.length() >= minLen) {
                  scorableWords.add(buildingWord);
               }
               // call dfs again for the neighbors of this new position to continue word building
               dfs(neighbor, minLen);
               /** if all neighbors of this position return as false, remove this position
               from the visitedPath, and remove this string from the buildingWord. If the word
               was a valid word with this position added, it would have already been added to
               scorableWords, so it is safe to remove it completely from the buildingWord variable*/
               visitedPath.remove(neighbor);
               int endIndex = buildingWord.length() - board[neighbor.x][neighbor.y].length();
               buildingWord = buildingWord.substring(0, endIndex);
            }
            
         }
      }
   /** By the end of the recursive calls, only 1 poistion will be left in visitedPath, which is removed in 
   getAllScorableWords call anyways. Think this is to double check to mark all the cells as unvisited*/
   
      unvisitAll();
      markPathTaken();
   }
   
   public int getScoreForWords(SortedSet<String> words, int minimumWordLength) {
      int total = 0;
      for (String word : words) {
         int value = word.length() - minimumWordLength;
         total += value + 1;
      }
      return total;
   }
   
   public boolean isValidWord(String wordToCheck) {
      if (wordToCheck == null) {
         throw new IllegalArgumentException("Null word to check");
      }
      if (lexicon == null) {
         throw new IllegalStateException("Lexicon not loaded");
      }
      wordToCheck = wordToCheck.toUpperCase();
      if (lexicon.contains(wordToCheck)) {
         return true;
      }
      return false;
   }
    
   public boolean isValidPrefix(String prefixToCheck) {
      if (prefixToCheck == null) {
         throw new IllegalArgumentException("Null prefix to check");
      }
      if (lexicon == null) {
         throw new IllegalStateException("Lexicon not loaded");
      }
      prefixToCheck = prefixToCheck.toUpperCase();
      String word = lexicon.ceiling(prefixToCheck);
      if (word != null) {
         return word.startsWith(prefixToCheck);
      }
      return false;
   }
   
   public List<Integer> isOnBoard(String wordToCheck) {
      if (wordToCheck == null) {
         throw new IllegalArgumentException("Null param");
      }
      if (lexicon == null) {
         throw new IllegalStateException("Lexicon not loaded");
      }
      visitedPath = new ArrayList<Position>();
      wordToCheck = wordToCheck.toUpperCase();
      numList = new ArrayList<Integer>();
      buildingWord2 = "";
      // loop through 2d array
      for (int i = 0; i < numRows; i++) {
         for (int j = 0; j < numCols; j++) {
            if (board[i][j].equals(wordToCheck)) {
               numList.add((i * numRows) + j);
               return numList;
            }
            // if cell matches first letter of wordToCheck
            if (wordToCheck.startsWith(board[i][j])) {
               Position p = new Position(i, j);
               visitedPath.add(p);
               buildingWord2 = board[i][j];
               dfs2(p, wordToCheck);
               if (!wordToCheck.equals(buildingWord2)) {
                  visitedPath.remove(p);
               }
               else {
                  for (Position pos : visitedPath) {
                     numList.add((pos.x * numRows) + pos.y);
                     //dfs2Calls = 1;
                  }
               }
            }
         }
      }
      //dfs2Calls = 1;
      return numList;
   } 
   
   private void dfs2(Position p, String wordToCheck) {
      unvisitAll();
      markPathTaken();
      for (Position neighbor : p.neighbors()) {
         if (!isVisited(neighbor)) {
            visit(neighbor);  
            if (wordToCheck.startsWith(buildingWord2 + board[neighbor.x][neighbor.y])) {
               buildingWord2 += board[neighbor.x][neighbor.y];
               visitedPath.add(neighbor);
               dfs2(neighbor, wordToCheck);
               if (wordToCheck.equals(buildingWord2)) {
                  return;
               }
               else {
                  visitedPath.remove(neighbor);
                  int endIndex = buildingWord2.length() - board[neighbor.x][neighbor.y].length();
                  buildingWord2 = buildingWord2.substring(0, endIndex);
               }
            }  
         }
      }
      unvisitAll();
      markPathTaken();
   }
   
   private void unvisitAll() {
   
      visited = new boolean[numRows][numCols];
      for (boolean[] row : visited) {
         Arrays.fill(row, false);
      }
   }
   
   private void markPathTaken() {
      for (int i = 0; i < visitedPath.size(); i++) {
         visit(visitedPath.get(i));
      }
   }
   private class Position {
      int x;
      int y;
   
      /** Constructs a Position with coordinates (x,y). */
      public Position(int x, int y) {
         this.x = x;
         this.y = y;
      }
   
      /** Returns a string representation of this Position. */
      @Override
      public String toString() {
         return "(" + x + ", " + y + ")";
      }
   
      /** Returns all the neighbors of this Position. */
      public Position[] neighbors() {
         Position[] nbrs = new Position[NUM_NEIGHBORS];
         int count = 0;
         Position p;
         // generate all eight neighbor positions
         // add to return value if valid
         for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
               if (!((i == 0) && (j == 0))) {
                  p = new Position(x + i, y + j);
                  if (isValid(p)) {
                     nbrs[count++] = p;
                  }
               }
            }
         }
         return Arrays.copyOf(nbrs, count);
      }
   }

   private boolean isValid(Position p) {
      return (p.x >= 0) && (p.x < numRows) && 
            (p.y >= 0) && (p.y < numCols);
   }
      

   private boolean isVisited(Position p) {
      return visited[p.x][p.y];
   }

   private void visit(Position p) {
      visited[p.x][p.y] = true;
   }

   /**private void process(Position p) {
      board[p.x][p.y] = order++;
   }*/

}