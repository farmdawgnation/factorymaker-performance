package me.frmr.lift

import net.liftweb.util._
import net.liftweb.http._

object FactoryTester extends Factory {
  def funcName(): String = "abc123"

  @volatile var funcNameGeneratorVar: ()=>String = funcName _

  val funcNameGeneratorFactoryMaker: FactoryMaker[()=>String] = new FactoryMaker[()=>String](funcName _) {}

  val accesses = 1000000

  def test() {
    println("Testing performance of var access vurses factory maker access.")

    println(s"Testing var access time for $accesses accesses.")

    println("Warming var.")
    (0 to 10).foreach { _ => funcNameGeneratorVar() }

    println("Testing var.")
    val varStart = System.currentTimeMillis()
    (0 to accesses).foreach { _ => funcNameGeneratorVar() }
    val varEnd = System.currentTimeMillis()

    println("Warming FactoryMaker.")
    (0 to 10).foreach { _ => funcNameGeneratorFactoryMaker.vend() }

    println("Testing FactoryMaker.")
    val fmStart = System.currentTimeMillis()
    (0 to accesses).foreach { _ => funcNameGeneratorFactoryMaker.vend() }
    val fmEnd = System.currentTimeMillis()

    println("\nResults:")
    println("Var test time: " + (varEnd - varStart) + " millis")
    println("FactoryMaker test time: " + (fmEnd - fmStart) + " millis\n\n")
  }
}
