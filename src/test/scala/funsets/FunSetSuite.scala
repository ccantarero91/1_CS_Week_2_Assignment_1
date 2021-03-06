package funsets

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

/**
  * This class is a test suite for the methods in object FunSets. To run
  * the test suite, you can either:
  *  - run the "test" command in the SBT console
  *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
  */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {

  /**
    * Link to the scaladoc - very clear and detailed tutorial of FunSuite
    *
    * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
    *
    * Operators
    *  - test
    *  - ignore
    *  - pending
    */

  /**
    * Tests are written using the "test" operator and the "assert" method.
    */
  // test("string take") {
  //   val message = "hello, world"
  //   assert(message.take(5) == "hello")
  // }

  /**
    * For ScalaTest tests, there exists a special equality operator "===" that
    * can be used inside "assert". If the assertion fails, the two values will
    * be printed in the error message. Otherwise, when using "==", the test
    * error message will only say "assertion failed", without showing the values.
    *
    * Try it out! Change the values so that the assertion fails, and look at the
    * error message.
    */
  // test("adding ints") {
  //   assert(1 + 2 === 3)
  // }


  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
    * When writing tests, one would often like to re-use certain values for multiple
    * tests. For instance, we would like to create an Int-set and have multiple test
    * about it.
    *
    * Instead of copy-pasting the code for creating the set into every test, we can
    * store it in the test class using a val:
    *
    * val s1 = singletonSet(1)
    *
    * However, what happens if the method "singletonSet" has a bug and crashes? Then
    * the test methods are not even executed, because creating an instance of the
    * test class fails!
    *
    * Therefore, we put the shared values into a separate trait (traits are like
    * abstract classes), and create an instance inside each test method.
    *
    */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
    val s4 = singletonSet(4)
  }

  /**
    * This test is currently disabled (by using "ignore") because the method
    * "singletonSet" is not yet implemented and the test would fail.
    *
    * Once you finish your implementation of "singletonSet", exchange the
    * function "ignore" by "test".
    */
  test("singletonSet(1) contains 1") {

    /**
      * We create a new instance of the "TestSets" trait, this gives us access
      * to the values "s1" to "s3".
      */
    new TestSets {
      /**
        * The string argument of "assert" is a message that is printed in case
        * the test fails. This helps identifying which assertion failed.
        */
      assert(contains(s1, 1), "Singleton")
    }
  }

  test("union contains all elements of each set") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }
  test("intersect only contains the elements that are in both sets") {
    new TestSets {
      val s = union(s1, s2)
      val t = union(s2, s3)
      val r = intersect(s, t)
      assert(!contains(r, 1), "intersect 1")
      assert(contains(r, 2), "intersect 2")
      assert(!contains(r, 3), "intersect 3")
    }
  }

  test("diff says to us when a value in the second set doesn't belong to the first one") {
    new TestSets {
      val s = union(union(s1, s2),s4)
      val t = union(s2, s3)
      val r = diff(s, t)
      assert(contains(r, 1), "diff 1")
      assert(!contains(r, 2), "diff 2")
      assert(!contains(r, 3), "diff 3")
      assert(contains(r, 4), "diff 4")
    }
  }

  test("filter function"){
    new TestSets {
      val s5 = singletonSet(5)
      val s = union(union(union(s1,s2),s3),s5)
      val t = filter(s, i => if(i%2 != 0) true else false)
      assert(contains(t,1), "filter odd 1")
      assert(contains(t,3), "filter odd 3")
    }
  }

  test("forall works") {
    new TestSets {
      val s5 = singletonSet(5)
      val sMinus1 = singletonSet(-1)
      val s = union(union(union(s1,s2),s3),s5)
      val sMinus = union(union(union(s1,s2),s3),sMinus1)
      assert(forall(s,x => x>0) === true, "forall => for all positive int it has to work")
      assert(forall(sMinus,x => x>0) === false, "forall => if a value is negative the function has to say false")
    }
  }

  test("exists works"){
    new TestSets {
      val s5 = singletonSet(5)
      val sMinus1 = singletonSet(-1)
      val s = union(union(union(s1,s2),s3),s5)
      val sMinus = union(union(union(s1,s2),s3),sMinus1)
      assert(exists(s,x => x<0) === false, "exists => for all positive int there is no one which is minor than zero")
      assert(exists(sMinus,x => x<0) === true, "exists => a value is negative so this should be true")
    }
  }

  test("map works"){
    new TestSets {
      val s = union(s1, s2)
      val t = union(singletonSet(2), singletonSet(4))
      assert(map(s,x=> x*2).toString() === t.toString(), "map 1")
    }
  }

}
