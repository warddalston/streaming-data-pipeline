package com.labs1904.hwe.exercises

import scala.annotation.tailrec

object StretchProblems {

  /*
  Checks if a string is palindrome.
 */
  def isPalindrome(s: String): Boolean = {
//    s.reverse == s
    if (s.head != s.last) {
      return false
    }
    if (s.length >= 2) {
      return true
    }
    isPalindrome(s.slice(1, s.length - 2))
  }

  /*
For a given number, return the next largest number that can be created by rearranging that number's digits.
If no larger number can be created, return -1
 */
  def getNextBiggestNumber(i: Integer): Int = {
    //TODO: Implement me!
    0
  }

}
