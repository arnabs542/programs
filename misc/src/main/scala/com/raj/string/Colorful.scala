package com.raj.string

/**
  * @author rshekh1
  */
object Colorful {

  def main(args: Array[String]): Unit = {
    println(colorful(263))
  }

  def colorful(A: Int): Int  = {
    val digits = math.log10(A).toInt + 1
    val set = new collection.mutable.HashSet[Int]

    for (windowSize <- 1 to digits) {
      var num = A
      for (i <- 1 to digits - windowSize + 1) {
        val n = (num % (math.pow(10, windowSize))).toInt
        val res = n.toString.toList.map(_.asDigit).foldLeft(1)(_ * _)
        if (set.contains(res)) return 0
        set += res
        num /= 10
      }
    }
    1
  }


}
