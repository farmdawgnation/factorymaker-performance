package me.frmr.lift

import net.liftweb.util._
import net.liftweb.http._

object SimpleInjectorTester extends SimpleInjector {
  def funcName(): String = "abc123"

  @volatile var funcNameGeneratorVar: ()=>String = funcName _

  val funcNameGeneratorInjector = new Inject[()=>String](funcName _) {}

  val accesses = 1000000

  def test() {
    println("Testing performance of var access versus simple injector access.")

    println(s"Testing var access time for $accesses accesses.")

    println("Warming var.")
    (0 to 10).foreach { _ => funcNameGeneratorVar() }

    println("Testing var.")
    val varStart = System.currentTimeMillis()
    (0 to accesses).foreach { _ => funcNameGeneratorVar() }
    val varEnd = System.currentTimeMillis()

    println("Warming SimpleInjector.")
    (0 to 10).foreach { _ => funcNameGeneratorInjector.vend() }

    println("Testing SimpleInjector.")
    val fmStart = System.currentTimeMillis()
    (0 to accesses).foreach { _ => funcNameGeneratorInjector.vend() }
    val fmEnd = System.currentTimeMillis()

    println("\nResults:")
    println("Var test time: " + (varEnd - varStart) + " millis")
    println("SimpleInjector test time: " + (fmEnd - fmStart) + " millis\n\n")
  }
}
