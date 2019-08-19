package com.raj.string

/**
  * @author rshekh1
  */
object Colorful {

  def main(args: Array[String]): Unit = {
    //println(colorful(263))
    val rhIdV2 = ""
    var a:Option[String] = None
    val rhIdOpt = Option(rhIdV2)
    rhIdOpt.map(x => {
      val values = x.split("_")
      if (values.length > 0) a = values.lift(0)
      println(a)
    } )
    /*if (rhIdV2 != null) {
      val values = rhIdV2.split("_")
      val l1 = values.lift(0)
      val l2 = values.lift(1)
      val l3 = values.lift(2)
      val l4 = values.lift(3)
      println(values)
      println(l1, l2, l3, l4)
    }*/
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
