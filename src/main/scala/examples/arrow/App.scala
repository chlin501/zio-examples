package examples.arrow

import zio._
import zio.arrow.ZArrow

object App {
  def main(args: Array[String]): Unit = {
    def addOne = (_: Int) + 1
    def multiple2 = (_: Int) * 2
    val wrapped = ZArrow(addOne) >>> ZArrow(multiple2)
    val runtime = Runtime.default
    println(runtime.unsafeRun(wrapped.run(10)))
  }
}
