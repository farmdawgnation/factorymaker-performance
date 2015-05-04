package me.frmr.lift

import net.liftweb.util._
import net.liftweb.http._

object PerformanceTester extends Factory {
  def main(args: Array[String]) {
    FactoryTester.test()
    SimpleInjectorTester.test()
  }
}
