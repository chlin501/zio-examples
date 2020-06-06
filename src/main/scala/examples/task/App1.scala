package examples.task

import zio._
import zio.duration._
import scala.language.postfixOps

object App1 {

  def main(args: Array[String]): Unit = {
    val sched = Schedule.recurs(20) && Schedule.spaced(1 seconds)
    val result = for { 
      t1 <- Task{
        println("task1 start ...")
        Thread.sleep(3 * 1000)
        println("task1 do something ...")
        Thread.sleep(4 * 1000)
        println("task1 do another thing ...")
        Thread.sleep(2 * 1000)
        println("task1 clean up .")
        Thread.sleep(1 * 1000)
        println("task1 is still doing clean up ...")
        Thread.sleep(1 * 1000)
        println("task1 is still doing clean up ...")
        println("done!")
      }.fork
      t2 <- task(t1).repeat(sched).fork
      _ <- t1.join
      _ <- t2.join
    } yield ()
    val runtime = Runtime.default 
    runtime.unsafeRun(result)
  }

  def task(t: Fiber.Runtime[Throwable, Unit]) = for {
    s <- t.status
    _ <- console.putStrLn(s"task1's status ${s.toString}")
  } yield ()


}
