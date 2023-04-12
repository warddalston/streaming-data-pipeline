package com.labs1904.hwe.exercises

import scala.annotation.tailrec

object StretchProblems {

  /*
  Checks if a string is palindrome.
 */
  @tailrec
  def isPalindrome(s: String): Boolean = {
//    s.reverse == s
    if (s.head != s.last) {
      return false
    }
    if (s.length <= 2) {
      return true
    }
    isPalindrome(s.slice(1, s.length - 1))
  }

  /*
For a given number, return the next largest number that can be created by rearranging that number's digits.
If no larger number can be created, return -1
   */
  @tailrec
  private def getNextBiggestInner(I: Seq[Int], stop: Int): Option[List[Int]] = {
  if (stop == I.size) {
    None
  } else {
    val swap: Int = I.slice(0, stop).indexWhere(_ > I(stop))
    swap match {
      case -1 => {
        getNextBiggestInner(I, stop + 1)
      }
      case _ => {
        Option(List(swap, stop))
      }
    }
  }
}

  def getNextBiggestNumber(i: Integer): Int = {
    if (i <= 11) {
      return -1
    }
    val intSeq: Seq[Int] = i.toString.map((s: Char) => s.toString.toInt).reverse
    val swap_exists = getNextBiggestInner(intSeq, 1)
    swap_exists match {
      case None => -1
      case Some(swap) => {
        val intSwapped = intSeq.updated(swap(0), intSeq(swap(1))).updated(swap(1), intSeq(swap(0)))
        val (front, back) = intSwapped.splitAt(swap(1))
        (front.sorted.reverse ++ back).reverse.mkString(sep="").toInt
      }
    }
  }

}
