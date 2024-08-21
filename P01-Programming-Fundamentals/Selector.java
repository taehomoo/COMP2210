import java.util.Arrays;

/**
* Defines a library of selection methods
* on arrays of ints.
*
* @author   Andrew Moon (atm0091@auburn.edu)
* @version  1/18/23
*
*/
public final class Selector {

   /**
    * Can't instantiate this class.
    *
    * D O   N O T   C H A N G E   T H I S   C O N S T R U C T O R
    *
    */
   private Selector() { }


   /**
    * Selects the minimum value from the array a. This method
    * throws IllegalArgumentException if a is null or has zero
    * length. The array a is not changed by this method.
    */
   public static int min(int[] a) {
      if (a == null || a.length == 0) {
         throw new IllegalArgumentException("Error");
      }
      int min = 0;
      for (int i = 0; i < a.length; i++) {
         if (i == 0) {
            min = a[i];
         }
         else if (a[i] < min) {
            min = a[i];
         }
      }
      return min;
   }


   /**
    * Selects the maximum value from the array a. This method
    * throws IllegalArgumentException if a is null or has zero
    * length. The array a is not changed by this method.
    */
   public static int max(int[] a) {
      if (a == null || a.length == 0) {
         throw new IllegalArgumentException("Error");
      }
      int max = 0;
      for (int i = 0; i < a.length; i++) {
         if (i == 0) {
            max = a[i];
         }
         else if (max < a[i]) {
            max = a[i];
         }
      }
      return max;
   }


   /**
    * Selects the kth minimum value from the array a. This method
    * throws IllegalArgumentException if a is null, has zero length,
    * or if there is no kth minimum value. Note that there is no kth
    * minimum value if k < 1, k > a.length, or if k is larger than
    * the number of distinct values in the array. The array a is not
    * changed by this method.
    */
   public static int kmin(int[] a, int k) {  
      int counter = 1;
      if (a == null || a.length == 0 || k < 1 || k > a.length) {
         throw new IllegalArgumentException("Error");
      }
      int count = 1;
      int[] copy = Arrays.copyOf(a, a.length);
      Arrays.sort(copy);
      for (int i = 0; i < copy.length; i++) {
         if (i + 1 < copy.length && copy[i] != copy[i + 1]) {
            count++;
         }
      }
      if (count < k) {
         throw new IllegalArgumentException("Error");
      }
      for (int i = 0; i < copy.length; i++) {
         if (i + 1 < copy.length && copy[i] == copy[i + 1]) {
            continue;
         }
         if (counter == k) {
            return copy[i];
         }
         counter++;
      }
      return -1;
   }


   /**
    * Selects the kth maximum value from the array a. This method
    * throws IllegalArgumentException if a is null, has zero length,
    * or if there is no kth maximum value. Note that there is no kth
    * maximum value if k < 1, k > a.length, or if k is larger than
    * the number of distinct values in the array. The array a is not
    * changed by this method.
    */
   public static int kmax(int[] a, int k) {
      if (a == null || a.length == 0 || k < 1 || k > a.length) {
         throw new IllegalArgumentException("Error");
      }
      int count = 1;
      int find = 1;
      int findCount = 1;
      int[] copy = Arrays.copyOf(a, a.length);
      Arrays.sort(copy);
      for (int i = 0; i < copy.length; i++) {
         if (i + 1 < copy.length && copy[i] != copy[i + 1]) {
            count++;
         }
      }
      if (count < k) {
         throw new IllegalArgumentException("Error");
      }
      for (int i = copy.length - 1; i >= 0; i--) {
         if (i > 0 && copy[i] == copy[i - 1]) {
            continue;
         }
         if (findCount == k) {
            return copy[i];
         }
         findCount++;
      }
      return -1;
   }


   /**
    * Returns an array containing all the values in a in the
    * range [low..high]; that is, all the values that are greater
    * than or equal to low and less than or equal to high,
    * including duplicate values. The length of the returned array
    * is the same as the number of values in the range [low..high].
    * If there are no qualifying values, this method returns a
    * zero-length array. Note that low and high do not have
    * to be actual values in a. This method throws an
    * IllegalArgumentException if a is null or has zero length.
    * The array a is not changed by this method.
    */
   public static int[] range(int[] a, int low, int high) {
      if (a == null || a.length == 0) {
         throw new IllegalArgumentException("Error");
      }
      int count = 0;
      for (int i : a) {
         if (i >= low && i <= high) {
            count++;
         }
      }
      int[] nA = new int[count];
      int nCount = 0;
      for (int j : a) {
         if (j >= low && j <= high) {
            nA[nCount] = j;
            nCount++;
         }
      }
      return nA;
   }


   /**
    * Returns the smallest value in a that is greater than or equal to
    * the given key. This method throws an IllegalArgumentException if
    * a is null or has zero length, or if there is no qualifying
    * value. Note that key does not have to be an actual value in a.
    * The array a is not changed by this method.
    */
   public static int ceiling(int[] a, int key) {
      int min = Integer.MAX_VALUE;
      if (a == null || a.length == 0) {
         throw new IllegalArgumentException("Error");
      }
      for (int i = 0; i < a.length; i++) {
         if (a[i] >= key) {
            if (a[i] < min) {
               min = a[i];
            }
         }
         else if (i == a.length - 1 && min == Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Error");
         }
      }
      return min;
   }


   /**
    * Returns the largest value in a that is less than or equal to
    * the given key. This method throws an IllegalArgumentException if
    * a is null or has zero length, or if there is no qualifying
    * value. Note that key does not have to be an actual value in a.
    * The array a is not changed by this method.
    */
   public static int floor(int[] a, int key) {
      int max = Integer.MIN_VALUE;
      if (a == null || a.length == 0) {
         throw new IllegalArgumentException("Error");
      }
      for (int i = 0; i < a.length; i++) {
         if (a[i] <= key) {
            if (a[i] > max) {
               max = a[i];
            }
         }
         else if (i == a.length - 1 && max == Integer.MIN_VALUE) {
            throw new IllegalArgumentException("Error");
         }
      }
      return max;
   }
}
