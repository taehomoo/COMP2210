import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Defines a library of selection methods on Collections.
 *
 * @author  Andrew Moon (atm0091@auburn.edu)
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
    * Returns the minimum value in the Collection coll as defined by the
    * Comparator comp. If either coll or comp is null, this method throws an
    * IllegalArgumentException. If coll is empty, this method throws a
    * NoSuchElementException. This method will not change coll in any way.
    *
    * @param coll    the Collection from which the minimum is selected
    * @param comp    the Comparator that defines the total order on T
    * @return        the minimum value in coll
    * @throws        IllegalArgumentException as per above
    * @throws        NoSuchElementException as per above
    */
   public static <T> T min(Collection<T> coll, Comparator<T> comp) {
      if (coll == null || comp == null) {
         throw new IllegalArgumentException("Error");
      }
      if (coll.isEmpty()) {
         throw new NoSuchElementException("Error");
      }
      Iterator<T> itr = coll.iterator();
      T min = itr.next();
      while (itr.hasNext()) {
         T test = itr.next();
         if (comp.compare(test, min) < 0) {
            min = test;
         }
      }
      return min;
   }


   /**
    * Selects the maximum value in the Collection coll as defined by the
    * Comparator comp. If either coll or comp is null, this method throws an
    * IllegalArgumentException. If coll is empty, this method throws a
    * NoSuchElementException. This method will not change coll in any way.
    *
    * @param coll    the Collection from which the maximum is selected
    * @param comp    the Comparator that defines the total order on T
    * @return        the maximum value in coll
    * @throws        IllegalArgumentException as per above
    * @throws        NoSuchElementException as per above
    */
   public static <T> T max(Collection<T> coll, Comparator<T> comp) {
      if (coll == null || comp == null) {
         throw new IllegalArgumentException("Error");
      }
      if (coll.isEmpty()) {
         throw new NoSuchElementException("Error");
      }
      Iterator<T> itr = coll.iterator();
      T max = itr.next();
      while (itr.hasNext()) {
         T test = itr.next();
         if (comp.compare(test, max) > 0) {
            max = test;
         }
      }
      return max;
   }


   /**
    * Selects the kth minimum value from the Collection coll as defined by the
    * Comparator comp. If either coll or comp is null, this method throws an
    * IllegalArgumentException. If coll is empty or if there is no kth minimum
    * value, this method throws a NoSuchElementException. This method will not
    * change coll in any way.
    *
    * @param coll    the Collection from which the kth minimum is selected
    * @param k       the k-selection value
    * @param comp    the Comparator that defines the total order on T
    * @return        the kth minimum value in coll
    * @throws        IllegalArgumentException as per above
    * @throws        NoSuchElementException as per above
    */
   public static <T> T kmin(Collection<T> coll, int k, Comparator<T> comp) {
      if (coll == null || comp == null) {
         throw new IllegalArgumentException("Error");
      }
      if (coll.isEmpty()) {
         throw new NoSuchElementException("Error");
      }
      ArrayList<T> arr = new ArrayList<T>();
      Iterator<T> itr = coll.iterator();
      while (itr.hasNext()) {
         arr.add(itr.next());
      }
      if (k < 1 || k > arr.size()) {
         throw new NoSuchElementException("Error");
      }
      java.util.Collections.sort(arr, comp);
      //itr = arr.iterator();
      int count = 0;
      for (int i = 0; i < arr.size(); i++) {
         if (i + 1 < arr.size() && comp.compare(arr.get(i), arr.get(i + 1)) == 0) {
            continue;
         }
         else {
            count++;
         }
      }
      if (count < k) {
         throw new NoSuchElementException("Error");
      }
      int counter = 1;
      for (int i = 0; i < arr.size(); i++) {
         if (i + 1 < arr.size() && comp.compare(arr.get(i), arr.get(i + 1)) == 0) {
            continue;
         }
         else if (counter == k) {
            return arr.get(i);
         }
         counter++;
      }
      return null;
   }


   /**
    * Selects the kth maximum value from the Collection coll as defined by the
    * Comparator comp. If either coll or comp is null, this method throws an
    * IllegalArgumentException. If coll is empty or if there is no kth maximum
    * value, this method throws a NoSuchElementException. This method will not
    * change coll in any way.
    *
    * @param coll    the Collection from which the kth maximum is selected
    * @param k       the k-selection value
    * @param comp    the Comparator that defines the total order on T
    * @return        the kth maximum value in coll
    * @throws        IllegalArgumentException as per above
    * @throws        NoSuchElementException as per above
    */
   public static <T> T kmax(Collection<T> coll, int k, Comparator<T> comp) {
      if (coll == null || comp == null) {
         throw new IllegalArgumentException("Error");
      }
      if (coll.isEmpty()) {
         throw new NoSuchElementException("Error");
      }
      ArrayList<T> arr = new ArrayList<T>();
      Iterator<T> itr = coll.iterator();
      while (itr.hasNext()) {
         arr.add(itr.next());
      }
      if (k < 1 || k > arr.size()) {
         throw new NoSuchElementException("Error");
      }
      java.util.Collections.sort(arr, comp);
      java.util.Collections.reverse(arr);
      int count = 0;
      for (int i = 0; i < arr.size(); i++) {
         if (i + 1 < arr.size() && comp.compare(arr.get(i), arr.get(i + 1)) == 0) {
            continue;
         }
         else {
            count++;
         }
      }
      if (count < k) {
         throw new NoSuchElementException("Error");
      }
      int counter = 1; 
      for (int i = 0; i < arr.size(); i++) {
         if (i + 1 < arr.size() && comp.compare(arr.get(i), arr.get(i + 1)) == 0) {
            continue;
         }
         else if (counter == k) {
            return arr.get(i);
         }
         counter++;
      }
      return null;
   }


   /**
    * Returns a new Collection containing all the values in the Collection coll
    * that are greater than or equal to low and less than or equal to high, as
    * defined by the Comparator comp. The returned collection must contain only
    * these values and no others. The values low and high themselves do not have
    * to be in coll. Any duplicate values that are in coll must also be in the
    * returned Collection. If no values in coll fall into the specified range or
    * if coll is empty, this method throws a NoSuchElementException. If either
    * coll or comp is null, this method throws an IllegalArgumentException. This
    * method will not change coll in any way.
    *
    * @param coll    the Collection from which the range values are selected
    * @param low     the lower bound of the range
    * @param high    the upper bound of the range
    * @param comp    the Comparator that defines the total order on T
    * @return        a Collection of values between low and high
    * @throws        IllegalArgumentException as per above
    * @throws        NoSuchElementException as per above
    */
   public static <T> Collection<T> range(Collection<T> coll, T low, T high,
                                                     Comparator<T> comp) {
      if (coll == null || comp == null) {
         throw new IllegalArgumentException("Error");
      }
      if (coll.isEmpty()) {
         throw new NoSuchElementException("Error");
      }
      ArrayList<T> returnList = new ArrayList<T>();
      Iterator<T> itr = coll.iterator();
      while (itr.hasNext()) {
         T test = itr.next();
         if (comp.compare(test, low) >= 0 && comp.compare(test, high) <= 0) {
            returnList.add(test);
         }
      }  
      if (returnList.isEmpty()) {
         throw new NoSuchElementException("Error");
      }
      return returnList;
   }


   /**
    * Returns the smallest value in the Collection coll that is greater than
    * or equal to key, as defined by the Comparator comp. The value of key
    * does not have to be in coll. If coll or comp is null, this method throws
    * an IllegalArgumentException. If coll is empty or if there is no
    * qualifying value, this method throws a NoSuchElementException. This
    * method will not change coll in any way.
    *
    * @param coll    the Collection from which the ceiling value is selected
    * @param key     the reference value
    * @param comp    the Comparator that defines the total order on T
    * @return        the ceiling value of key in coll
    * @throws        IllegalArgumentException as per above
    * @throws        NoSuchElementException as per above
    */
   public static <T> T ceiling(Collection<T> coll, T key, Comparator<T> comp) {
      if (coll == null || comp == null) {
         throw new IllegalArgumentException("Error");
      }
      if (coll.isEmpty()) {
         throw new NoSuchElementException("Error");
      }
      Iterator<T> itr = coll.iterator();
      T ceiling = null;
      /** get first min value */
      while (itr.hasNext()) {
         T test = itr.next();
         if (comp.compare(test, key) >= 0) {
            ceiling = test;
            break;
         }
      }
      if (ceiling == null) {
         throw new NoSuchElementException("Error");
      }
      itr = coll.iterator();
      while (itr.hasNext()) {
         T test = itr.next();
         if (comp.compare(test, key) >= 0) {
            if (comp.compare(test, ceiling) < 0) {
               ceiling = test;
            }
         }
      }
      return ceiling;
   }


   /**
    * Returns the largest value in the Collection coll that is less than
    * or equal to key, as defined by the Comparator comp. The value of key
    * does not have to be in coll. If coll or comp is null, this method throws
    * an IllegalArgumentException. If coll is empty or if there is no
    * qualifying value, this method throws a NoSuchElementException. This
    * method will not change coll in any way.
    *
    * @param coll    the Collection from which the floor value is selected
    * @param key     the reference value
    * @param comp    the Comparator that defines the total order on T
    * @return        the floor value of key in coll
    * @throws        IllegalArgumentException as per above
    * @throws        NoSuchElementException as per above
    */
   public static <T> T floor(Collection<T> coll, T key, Comparator<T> comp) {
      if (coll == null || comp == null) {
         throw new IllegalArgumentException("Error");
      }
      if (coll.isEmpty()) {
         throw new NoSuchElementException("Error");
      }
      T floor = null;
      Iterator<T> itr = coll.iterator();
      while (itr.hasNext()) {
         T test = itr.next();
         if (comp.compare(test, key) <= 0) {
            floor = test;
            break;
         }
      }
      if (floor == null) {
         throw new NoSuchElementException("Error");
      }
      itr = coll.iterator();
      while (itr.hasNext()) {
         T test = itr.next();
         if (comp.compare(test, key) <= 0) {
            if (comp.compare(test, floor) > 0) {
               floor = test;
            }
         }
      }
      return floor;
   }

}
