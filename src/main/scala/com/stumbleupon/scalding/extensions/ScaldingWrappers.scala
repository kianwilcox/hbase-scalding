package com.stumbleupon.scalding.extensions

import scala.util.matching.Regex
import cascading.pipe.Pipe
import cascading.tuple.Fields
import com.twitter.scalding._
import cascading.flow._

class PipeWrapper(input:Pipe) extends java.io.Serializable {
  import Dsl._
  import ScaldingWrapperConversions._
  
  def pipe = input
  
  
  // groups by the given fields, discarding any groups which match the predicate
  def discardGroupsWhere[A](f:Fields)(fn: A => Boolean)
      (implicit conv : TupleConverter[A]) : RichPipe = {
      conv.assertArityMatches(f)
      input.joinWithSmaller((f -> f),
      input.groupBy(f) { _.count((f -> new Fields('__count__.name)))(fn)}.filter(new Fields('__count__.name)) {
        count: Long => count == 0
      })
  }
  
  // groups by the given fields, discarding any groups which do not match the predicate
  def filterGroupsWhere[A](f:Fields)(fn: A => Boolean)
      (implicit conv : TupleConverter[A]) : RichPipe = {
      conv.assertArityMatches(f)
      input.joinWithSmaller((f -> f),
        input.groupBy(f) { _.count((f -> new Fields('__count__.name)))(fn)}.filter(new Fields('__count__.name)) {
          count: Long => count > 0
      })
  }
	
}

class GroupBuilderWrapper(val group:GroupBuilder) {
	
}

class JobWrapper(val job:Job) {
	
}

class StumbleJob(args:Args) extends Job(args) {

  
}

object ScaldingWrapperConversions {
  import Dsl._
  implicit def inputToPipeWrapper(input:Pipe) = new PipeWrapper(input)
  implicit def pipeWrapperToPipe(input:PipeWrapper) = input.pipe
  implicit def wrapGroupBuilder(group:GroupBuilder) = new GroupBuilderWrapper(group)
  implicit def groupBuilderWrapperToGroup(group:GroupBuilderWrapper) = group.group
  implicit def wrapJob(job:Job) = new JobWrapper(job)
  implicit def jobWrapperToJob(job:JobWrapper) = job.job
}