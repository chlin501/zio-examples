package examples.resource

import zio._

object App {

  def main(args: Array[String]): Unit = {
    val result = for {
      listOfIntRef <- Ref.make[List[Int]](Nil)
      res = (v: Int) => ZManaged.make(listOfIntRef.update(v :: _))(_ => listOfIntRef.update(v :: _))
      program = res(1) *> res(2) *> res(3)
      values <- program.use_(ZIO.unit) *> listOfIntRef.get
    } yield values
    val runtime = Runtime.default
    println(runtime.unsafeRun(result))
  }

}
