package task

import zio._

object App {

  def main(args: Array[String]): Unit = {
    val sched = Schedule.recurs(5)
    val result = for { 
      t <- task().repeat(sched).fork
      _ <- t.join
    } yield ()
    val runtime = Runtime.default 
    runtime.unsafeRun(result)
  }

  def task() = for {
    desc <- ZIO.descriptor
    _ <- console.putStrLn(desc.toString) 
  } yield ()


}
