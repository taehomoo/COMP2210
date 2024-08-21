import java.util.Comparator;
public class Term implements Comparable<Term> {
   private String query;
   private long weight;
     
     
   /**
    * Initialize a term with the given query and weight.
    * This method throws a NullPointerException if query is null,
    * and an IllegalArgumentException if weight is negative.
    */
   public Term(String query, long weight) {
      if (query == null) {
         throw new NullPointerException("Error");
      }
      if (weight < 0) {
         throw new IllegalArgumentException("Error");
      }
      this.query = query;
      this.weight = weight;
   }

   /**
    * Compares the two terms in descending order of weight.
    */
   public static Comparator<Term> byDescendingWeightOrder() {
      return new DWO();
   }

   /**
    * Compares the two terms in ascending lexicographic order of query,
    * but using only the first length characters of query. This method
    * throws an IllegalArgumentException if length is less than or equal
    * to zero.
    */
   public static Comparator<Term> byPrefixOrder(int length) {
      if (length <= 0) {
         throw new IllegalArgumentException("Error");
      }
      return new BPO(length - 1);
   }

   /**
    * Compares this term with the other term in ascending lexicographic order
    * of query.
    */
   @Override
   public int compareTo(Term other) {
      if (this.query.compareTo(other.query) < 0) {
         return -1;
      }
      else if (this.query.compareTo(other.query) > 0) {
         return 1;
      }
      else {
         return 0;
      }
   }

   /**
    * Returns a string representation of this term in the following format:
    * query followed by a tab followed by weight
    */
   @Override
   public String toString(){
      String output = this.query + "\t" + this.weight;
      return output;
   }
   
   public String getQuery() {
      return query;
   }
   
   public long getWeight() {
      return weight;
   }
   
   public static class DWO implements Comparator<Term> {
      public int compare(Term t1, Term t2) {
         if (t1.weight < t2.weight) {
            return 1;
         }
         else if (t1.weight > t2.weight) {
            return -1;
         }
         else {
            return 0;
         }
      }
   }
   
   public static class BPO implements Comparator<Term> {
      private int length;
      public BPO (int lengthParam) {
         length = lengthParam;
      }
      public int compare(Term t1, Term t2) {
         String final1 = t1.query.substring(0, length);
         String final2 = t2.query.substring(0, length);
         return final1.compareTo(final2);
      }
   }

}
