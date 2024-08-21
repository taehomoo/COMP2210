import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class SelectorTest {


   /** Fixture initialization (common initialization
    *  for all tests). **/
   @Before public void setUp() {
   }


   /** A test that always fails. **/
   @Test public void kmaxArrayLength1Test() {
      int[] array = {1, 2, 3, 4, 5};
      int kValue = 2;
      int expected = 4;
      int actual = Selector.kmax(array, kValue);
      Assert.assertEquals(expected, actual);
   }

   @Test public void kMinArrayLength1Test() {
      int[] array = {3, 7, 3, 3, 1, 9, 1, 1, 1, 5};
      int kValue = 2;
      int expected = 3;
      int actual = Selector.kmin(array, kValue);
      Assert.assertEquals(expected, actual);
   }
}

