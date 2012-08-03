package jobs

import com.twitter.scalding._
import scala.collection.Iterable
import scala.collection.immutable.List
import java.util.Iterator
import cascading.tuple.Fields
import com.twitter.scalding._
import com.twitter.scalding.Args
import org.apache.hadoop.hbase.util.Bytes

/**
The purpose of this class is to test HBase -> Scala -> HBase serialization/desiralization
*/

class HBaseTests(args: Args) extends Job(args) {
  val table = new HBaseSource("test", "localhost", 'c1, Array("d"), Array('c2))
  val out = new HBaseSource("scalding_out", "localhost", 'c1, Array("test","test"), Array('cc2, 'times3))
  table.read
  .map('c1 -> 'c1) {c1: Array[Byte] => Bytes.toString(c1)}
  .map(('c2) -> ('cc2,'times3)) {
    t: Array[Byte] => {
      val c2 = t
      (Bytes.toString(c2), Bytes.toString(c2).toFloat * 3)
      }}
  .map('times3 -> 'times3) {times3: Float => times3 * 2}
  .project('c1, 'cc2, 'times3)
  .write(out)
}
