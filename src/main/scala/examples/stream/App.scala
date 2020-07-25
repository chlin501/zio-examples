package examples.stream

import zio._
import zio.stream._

object App {

  def main(args: Array[String]) {
    // require "dev.zio" %% "zio-streams" % "1.0.0-RC18-2"
    val stream = Stream(1, 2, 3)
    val doubleIntStream  = stream.map(_*2)
    val sink = ZSink.sum[Int]
    val result = for {
      v <- doubleIntStream.run(sink)
      t1 <- Task(println(s"1 * 2 + 2 * 2 + 3 * 2 = $v")).fork
      _ <- t1.join
    } yield ()
    val runtime = Runtime.default
    runtime.unsafeRun(result)

  }


}
