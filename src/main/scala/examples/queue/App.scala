package queue

import zio._
import scala.util.Random

object App {

  def main(args: Array[String]) {

    val queue = Queue.bounded[Int](requestedCapacity = 8)
    val sched = Schedule.recurs(3)
    val result = for {
      q <- queue
      p <- producer(q).repeat(sched).fork
      c <- consumer(q).repeat(sched).fork
      _ <- p.join
      _ <- c.join
    } yield ()
    val runtime = Runtime.default
    runtime.unsafeRun(result)
  }

  def producer(q: Queue[Int]) = for {
    t <- Task(Random.nextInt(5)+1).fork
    v <- t.join
    _ <- Task(println(s"$v is produced ..."))
    _ <- q.offer(v)
  } yield ()

  def consumer(q: Queue[Int]) = for {
    v <- q.take
    _ <- Task(println(s"consumer receives $v"))
  } yield v
}
