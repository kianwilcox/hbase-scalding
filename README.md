hbase-scalding
==============

Project template for anyone wanting to write scalding jobs while keeping scalding as a dependency, instead of mixing in their own files into the scalding project. Also includes minimal support for HBase sources and sinks, and a few extensions to the pipe model we've found to be useful at StumbleUpon. 

Simply add a file in src/main/scala/jobs, import com.twitter.scalding._, and make sure the class inside extends Job.
If you'd like to use our extensions, import com.stumbleupon.scalding.extensions._ , 
and import com.stumbleupon.scalding.extensions.ScaldingWrapperConversions._ in your Job extension.

To run on hadoop, first run sbt assembly in the main project directory.
Then copy it to whatever machine you have hadoop running on.
Then run `hadoop jar hbase-scalding-assembly-0.1.0.jar jobs.YourJobClass --hdfs --any-other-command-line-args-here`
