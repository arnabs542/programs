package com.walmart.ads.blitzcrank.client

object Client {

  def getItems() : List[String] = {
    List("raj")
  }

  def main(args: Array[String]): Unit = {
    println(s"Running main: output = ${getItems}")
    val map = Map("k1" -> "v1", "k2" -> "v2")
    val m = map + ("k3" -> "v3")
    println(map)
    println(m)
  }

}

