package com.walmart.ads.blitzcrank.client

object Client {

  def getItems() : List[String] = {
    List("raj")
  }

  def main(args: Array[String]): Unit = {
    println(s"Running main: output = ${getItems}")
  }

}

