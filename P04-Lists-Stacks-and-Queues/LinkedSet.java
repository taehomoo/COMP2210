import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Provides an implementation of the Set interface.
 * A doubly-linked list is used as the underlying data structure.
 * Although not required by the interface, this linked list is
 * maintained in ascending natural order. In those methods that
 * take a LinkedSet as a parameter, this order is used to increase
 * efficiency.
 *
 * @author Dean Hendrix (dh@auburn.edu)
 * @author Andrew Moon (atm0091@auburn.edu)
 *
 */
public class LinkedSet<T extends Comparable<T>> implements Set<T> {

   //////////////////////////////////////////////////////////
   // Do not change the following three fields in any way. //
   //////////////////////////////////////////////////////////

   /** References to the first and last node of the list. */
   Node front;
   Node rear;

   /** The number of nodes in the list. */
   int size;

   /////////////////////////////////////////////////////////
   // Do not change the following constructor in any way. //
   /////////////////////////////////////////////////////////

   /**
    * Instantiates an empty LinkedSet.
    */
   public LinkedSet() {
      front = null;
      rear = null;
      size = 0;
   }


   //////////////////////////////////////////////////
   // Public interface and class-specific methods. //
   //////////////////////////////////////////////////

   ///////////////////////////////////////
   // DO NOT CHANGE THE TOSTRING METHOD //
   ///////////////////////////////////////
   /**
    * Return a string representation of this LinkedSet.
    *
    * @return a string representation of this LinkedSet
    */
   @Override
   public String toString() {
      if (isEmpty()) {
         return "[]";
      }
      StringBuilder result = new StringBuilder();
      result.append("[");
      for (T element : this) {
         result.append(element + ", ");
      }
      result.delete(result.length() - 2, result.length());
      result.append("]");
      return result.toString();
   }


   ///////////////////////////////////
   // DO NOT CHANGE THE SIZE METHOD //
   ///////////////////////////////////
   /**
    * Returns the current size of this collection.
    *
    * @return  the number of elements in this collection.
    */
   public int size() {
      return size;
   }

   //////////////////////////////////////
   // DO NOT CHANGE THE ISEMPTY METHOD //
   //////////////////////////////////////
   /**
    * Tests to see if this collection is empty.
    *
    * @return  true if this collection contains no elements, false otherwise.
    */
   public boolean isEmpty() {
      return (size == 0);
   }


   /**
    * Ensures the collection contains the specified element. Neither duplicate
    * nor null values are allowed. This method ensures that the elements in the
    * linked list are maintained in ascending natural order.
    *
    * @param  element  The element whose presence is to be ensured.
    * @return true if collection is changed, false otherwise.
    */
   public boolean add(T element) {
      if (element == null || contains(element)) {
         return false;
      }
      Node n = new Node(element);
      Node current = front;
      // if there is no other nodes
      if (isEmpty()) {
         front = n;
         rear = n;
         n.next = null;
      }
      // if node belongs in rear
      else if (element.compareTo(rear.element) > 0) {
         n.prev = rear;
         rear.next = n;
         rear = n;
         n.next = null;
      }
      // if node belongs in front
      else if (element.compareTo(front.element) < 0) {
         front.prev = n;
         n.next = front;
         front = n;
      }
      else {
         while (current != null) {
            if (current.element.compareTo(element) > 0) {
               n.next = current;
               current.prev.next = n;
               n.prev = current.prev;
               current.prev = n;
               break;
            }
            current = current.next;
         }
      }
      size++;
      return true;
   }

   /**
    * Ensures the collection does not contain the specified element.
    * If the specified element is present, this method removes it
    * from the collection. This method, consistent with add, ensures
    * that the elements in the linked lists are maintained in ascending
    * natural order.
    *
    * @param   element  The element to be removed.
    * @return  true if collection is changed, false otherwise.
    */
   public boolean remove(T element) {
      if (!contains(element) || element == null || isEmpty()) {
         return false;
      }
      Node current = front;
      while (current != null) {
         if (current.element.compareTo(element) == 0) {
            // if removing only existing node
            if (size == 1) {
               front = null;
               rear = null;
               size--;
               return true;
            }
            // if node removed is front
            else if (current == front) {
               front = front.next;
               front.prev = null;
               size--;
               return true;
            }
            // if node removed is rear
            else if (current.next == null) {
               current.prev.next = null;
               rear = current.prev;
               size--;
               return true;
            }
            else {
               current.prev.next = current.next;
               current.next.prev = current.prev;
               size--;
               return true;
            }
         }
         current = current.next;
      }
      return false;
   }


   /**
    * Searches for specified element in this collection.
    *
    * @param   element  The element whose presence in this collection is to be tested.
    * @return  true if this collection contains the specified element, false otherwise.
    */
   public boolean contains(T element) {
      Node current = front;
      while (current != null) {
         if (current.element.compareTo(element) == 0) {
            return true;
         }
         current = current.next;
      }
      return false;
   }


   /**
    * Tests for equality between this set and the parameter set.
    * Returns true if this set contains exactly the same elements
    * as the parameter set, regardless of order.
    *
    * @return  true if this set contains exactly the same elements as
    *               the parameter set, false otherwise
    */
   public boolean equals(Set<T> s) {
      Set value = complement(s);
      if (value.size() == 0 && s.size() == size) {
         return true;
      }
      return false;
   }


   /**
    * Tests for equality between this set and the parameter set.
    * Returns true if this set contains exactly the same elements
    * as the parameter set, regardless of order.
    *
    * @return  true if this set contains exactly the same elements as
    *               the parameter set, false otherwise
    */
   public boolean equals(LinkedSet<T> s) {
      if (complement(s).size() == 0 && s.size() == size) {
         return true;
      }
      return false;
   }


   /**
    * Returns a set that is the union of this set and the parameter set.
    *
    * @return  a set that contains all the elements of this set and the parameter set
    */
   public Set<T> union(Set<T> s){
      LinkedSet<T> returnLS = new LinkedSet<T>();
      Node current = front;
      while (current != null) {
         returnLS.add(current.element);
         current = current.next;
      }
      Iterator<T> iterator = s.iterator();
      while (iterator.hasNext()) {
         returnLS.add(iterator.next());
      }
      return returnLS;
   }


   /**
    * Returns a set that is the union of this set and the parameter set.
    *
    * @return  a set that contains all the elements of this set and the parameter set
    */
   public Set<T> union(LinkedSet<T> s){
      LinkedSet<T> returnLS = new LinkedSet<T>();
      Node thisCurrent = front;
      Node paramCurrent = s.front;
      // if parameter list is empty
      if (s.isEmpty()) {
         while (thisCurrent != null) {
            returnLS.constantAdd(thisCurrent.element);
            thisCurrent = thisCurrent.next;
         }
         return returnLS;
      }
      // if this list is empty
      else if (this.isEmpty()) {
         while (paramCurrent != null) {
            returnLS.constantAdd(paramCurrent.element);
            paramCurrent = paramCurrent.next;
         }
         return returnLS;
      }
      // adding elements from both lists in ascending order
      while (thisCurrent != null && paramCurrent != null) {
         if (thisCurrent.element.compareTo(paramCurrent.element) > 0) {
            returnLS.constantAdd(paramCurrent.element);
            paramCurrent = paramCurrent.next;
         }
         else if (thisCurrent.element.compareTo(paramCurrent.element) < 0) {
            returnLS.constantAdd(thisCurrent.element);
            thisCurrent = thisCurrent.next;
         }
         else if (thisCurrent.element.compareTo(paramCurrent.element) == 0) {
            returnLS.constantAdd(thisCurrent.element);
            thisCurrent = thisCurrent.next;
            paramCurrent = paramCurrent.next;
         }
      }
      if (thisCurrent == null) {
         while (paramCurrent != null) {
            returnLS.constantAdd(paramCurrent.element);
            paramCurrent = paramCurrent.next;
         }
      }
      else if (paramCurrent == null) {
         while (thisCurrent != null) {
            returnLS.constantAdd(thisCurrent.element);
            thisCurrent = thisCurrent.next;
         }
      }
      return returnLS;
   }


   /**
    * Returns a set that is the intersection of this set and the parameter set.
    *
    * @return  a set that contains elements that are in both this set and the parameter set
    */
   public Set<T> intersection(Set<T> s) {
      LinkedSet<T> returnLS = new LinkedSet<T>();
      Node current = front;
      while (current != null) {
         if (s.contains(current.element)) {
            returnLS.add(current.element);
         }
         current = current.next;
      }
      return returnLS;
   }

   /**
    * Returns a set that is the intersection of this set and
    * the parameter set.
    *
    * @return  a set that contains elements that are in both
    *            this set and the parameter set
    */
   public Set<T> intersection(LinkedSet<T> s) {
      LinkedSet<T> returnLS = new LinkedSet<T>();
      Node current = front;
      Node param = s.front;
      if (s.isEmpty()) {
         return returnLS;
      }
      if (this.isEmpty()) {
         return returnLS;
      }
      while (current != null && param != null) {
         if (current.element.compareTo(param.element) > 0) {
            param = param.next;
         }
         else if (current.element.compareTo(param.element) < 0) {
            current = current.next;
         }
         else if (current.element.compareTo(param.element) == 0){
            returnLS.constantAdd(current.element);
            current = current.next;
            param = param.next;
         }
      }
      return returnLS;
   }


   /**
    * Returns a set that is the complement of this set and the parameter set.
    *
    * @return  a set that contains elements that are in this set but not the parameter set
    */
   public Set<T> complement(Set<T> s) {
      LinkedSet<T> returnLS = new LinkedSet<T>();
      Node current = front;
      if (this.isEmpty()) {
         return returnLS;
      }
      // becuase it is a\b, if s is null all elements in this set will be returned
      if (s == null) {
         while (current != null) {
            returnLS.add(current.element);
            current = current.next;
         }
      }
      else {
         while (current != null) {
            if (!s.contains(current.element)) {
               returnLS.add(current.element);
            }
            current = current.next;
         }
      }
      return returnLS;
   }


   /**
    * Returns a set that is the complement of this set and
    * the parameter set.
    *
    * @return  a set that contains elements that are in this
    *            set but not the parameter set
    */
   public Set<T> complement(LinkedSet<T> s) {
      LinkedSet<T> returnLS = new LinkedSet<T>();
      Node current = front;
      Node param = s.front;
      if (this.isEmpty()) {
         return returnLS;
      }
      else if (s.isEmpty()) {
         while (current != null) {
            returnLS.constantAdd(current.element);
            current = current.next;
         }
      }
      while (current != null && param != null) {
         if (current.element.compareTo(param.element) > 0) {
            param = param.next;
         }
         else if (current.element.compareTo(param.element) < 0) {
            returnLS.constantAdd(current.element);
            current = current.next;
         }
         else if (current.element.compareTo(param.element) == 0){
            current = current.next;
            param = param.next;
         }
      }
      return returnLS;
   }


   /**
    * Returns an iterator over the elements in this LinkedSet.
    * Elements are returned in ascending natural order.
    *
    * @return  an iterator over the elements in this LinkedSet
    */
   public Iterator<T> iterator() {
      return new setIterator();
   }


   /**
    * Returns an iterator over the elements in this LinkedSet.
    * Elements are returned in descending natural order.
    *
    * @return  an iterator over the elements in this LinkedSet
    */
   public Iterator<T> descendingIterator() {
      return new descendingIteratorClass();
   }


   /**
    * Returns an iterator over the members of the power set
    * of this LinkedSet. No specific order can be assumed.
    *
    * @return  an iterator over members of the power set
    */
   public Iterator<Set<T>> powerSetIterator() {
      return new powerSetIteratorClass(size);
   }



   //////////////////////////////
   // Private utility methods. //
   //////////////////////////////

   // Feel free to add as many private methods as you need.
   
   private boolean constantAdd(T element) {
      if (element == null) {
         return false;
      }
      Node current = front;
      Node n = new Node(element);
      if (isEmpty()) {
         front = n;
         rear = n;
         rear.next = null;
         size++;
         return true;
      }
      else if (front.element.compareTo(element) > 0) {
         n.next = front;
         front.prev = n;
         front = n;
         size++;
         return true;
      }
      else if (rear.element.compareTo(element) < 0) {
         n.prev = rear;
         rear.next = n;
         rear = n;
         n.next = null;
         size++;
         return true;
      }
      return false;
   }

   ////////////////////
   // Nested classes //
   ////////////////////

   //////////////////////////////////////////////
   // DO NOT CHANGE THE NODE CLASS IN ANY WAY. //
   //////////////////////////////////////////////

   /**
    * Defines a node class for a doubly-linked list.
    */
   class Node {
      /** the value stored in this node. */
      T element;
      /** a reference to the node after this node. */
      Node next;
      /** a reference to the node before this node. */
      Node prev;
   
      /**
       * Instantiate an empty node.
       */
      public Node() {
         element = null;
         next = null;
         prev = null;
      }
   
      /**
       * Instantiate a node that containts element
       * and with no node before or after it.
       */
      public Node(T e) {
         element = e;
         next = null;
         prev = null;
      }
   }
   
   /**
   * Defines a class that creates an iterator over a linkedList
   */
   private class setIterator implements Iterator<T> {
      
      // Node iterator is created on
      private Node current = front;
      
      /**
      * checks if there is a next element in the linked set
      * @return current comparison to null
      */
      public boolean hasNext() {
         return current != null;
      }
      
       /**
       * moves current to next node
       * @return T object that is bound to node
       */
      public T next() {
         if (!hasNext()) {
            throw new NoSuchElementException();
         }
         else {
            T returnVal = current.element;
            current = current.next;
            return returnVal;
         }
      }
      
      public void remove(){
         throw new UnsupportedOperationException();
      }
      
   }
   
   private class descendingIteratorClass implements Iterator<T> {
      private Node current = rear;
      
      public boolean hasNext() {
         if (current != null) {
            return true;
         }
         return false;
      }
      public T next() {
         if (!hasNext()) {
            throw new NoSuchElementException();
         }
         else {
            T val = current.element;
            current = current.prev;
            return val;
         }
      }
      public void remove() {
         throw new UnsupportedOperationException();
      }
   }
   
   private class powerSetIteratorClass implements Iterator<Set<T>> {
      int current;
      int num;
      int numSets;
      Node n;
     
      public powerSetIteratorClass(int numE) {
         current = 0;
         num = numE;
         numSets = (int) Math.pow(2, num);
         n = front;
      }
      
      public boolean hasNext() {
         return current < numSets;
      }
   
      public Set<T> next() {
         Set<T> set = new LinkedSet<T>();
         String bS = Integer.toBinaryString(current);
         while (bS.length() < num) {
            bS = "0" + bS;
         }
         for (int a = 0; a < num; a++) {
            if (bS.charAt(a) == '1') {
               T element = n.element;
               set.add(element);
            }
            n = n.next;
         }
         n = front;
         current++;
         return set;
      }
      
      public void remove() {
         throw new UnsupportedOperationException();
      }
   
   }

}

/**
Notes I learn as I realize as I work through this project:

   - The methods with set parameters are different, higher time complexities because
   the set may not contain Node objects, so must use methods defined in set to 
   iterate or do whatever to the set parameter.
   
   - The methods with linkedset parameters are shorter time complexities, because
   you already know that it contains Nodes.
   
   - I probably have to create a different add method for methods using linked sets
   because the time complexity of the add methjod of a set is O(N), while
   a linked add could be O(1)
   
   - So hypothetically I'm pretty sure I can use the same method implementation
   whether or not the parameter is a set or linkedset, the difference is
   the better time complexity.
   
   - If I'm not mistaken, I think the reason I can make a constandAdd method, is
   because a set does not necessarily have natural ordering, therefore I can
   just add the items to a set.
   
   */
