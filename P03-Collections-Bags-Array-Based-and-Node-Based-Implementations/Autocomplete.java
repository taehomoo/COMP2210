import java.util.Arrays;

/**
 * Autocomplete.
 */
public class Autocomplete {

   private Term[] terms;

	/**
	 * Initializes a data structure from the given array of terms.
	 * This method throws a NullPointerException if terms is null.
	 */
   public Autocomplete(Term[] terms) {
      if (terms == null) {
         throw new NullPointerException("Error");
      }
      this.terms = Arrays.<Term>copyOf(terms, terms.length);
      Arrays.sort(this.terms);
   }

	/** 
	 * Returns all terms that start with the given prefix, in descending order of weight. 
	 * This method throws a NullPointerException if prefix is null.
	 */
   public Term[] allMatches(String prefix) {
      if (prefix == null) {
         throw new NullPointerException("Error");
      }
      int length = prefix.length();
      int firstIndex = BinarySearch.firstIndexOf(terms, new Term(prefix, 0), 
         Term.byPrefixOrder(length));
      int lastIndex = BinarySearch.lastIndexOf(terms, new Term(prefix, 0), 
         Term.byPrefixOrder(length));
      int totalAmount = lastIndex - firstIndex + 1;
      Term[] finalArray = new Term[totalAmount];
      int j = 0;
      for (int i = firstIndex; i <= lastIndex; i++) {
         finalArray[j] = terms[i];
         j++;
      }
      Arrays.sort(finalArray, Term.byDescendingWeightOrder());
      return finalArray;
   }

}
