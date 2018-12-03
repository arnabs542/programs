package com.raj.test

/**
  * Basic sanity tester for Client
  */
object ClientTest {

  def main(args: Array[String]): Unit = {
    var totalTime = 0L
    val count = 10
    for( a <- 1 to count) {
      val startTime = System.currentTimeMillis()
      //val items = testClient_with_handle_error(List("112855237"), "94086")
     val timeTaken = System.currentTimeMillis() - startTime;
      //println(s"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ Got records size = ${items.size}, Total time taken = ${timeTaken}")
      totalTime += timeTaken
    }
    println("t(avg) = " + totalTime/count)
  }

}
