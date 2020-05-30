package examples.resource

import zio._

object App {

  def main(args: Array[String]): Unit = {
    val queue = Queue.bounded[Int](requestedCapacity = 32)
    val result = for {
      q <- queue
      // release is a fn checking the result of acquire
      // if release do (_ => q.shutdown), it will throw zio.FiberFailure: Fiber failed. An interrupt was produced by #0.
      release = (successOrNot: Boolean) => ZIO.unit
      res = (v: Int) => ZManaged.make(q.offer(v))(release) 
      program = res(1) *> res(2) *> res(3)
      values <- program.use_(ZIO.unit) *> q.take *> q.take *> q.take
    } yield values
    val runtime = Runtime.default
    println(runtime.unsafeRun(result))
  }

}
