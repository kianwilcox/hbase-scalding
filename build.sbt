import AssemblyKeys._

name := "hbase-scalding"

organization := "StumbleUpon"

version := "0.1.0"

scalaVersion := "2.9.2"


resolvers += "Apache HBase" at "https://repository.apache.org/content/repositories/releases"
	
resolvers += "Sonatype OSS Repo" at "https://oss.sonatype.org/content/repositories/releases"
	
resolvers += "Concurrent Maven Repo" at "http://conjars.org/repo"
  
resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
  
resolvers += "Twitter Maven Repo" at "http://maven.twttr.com"

resolvers += "Maven Repository" at "http://mvnrepository.com/artifact/"

resolvers += ("releases" at
    "http://oss.sonatype.org/content/repositories/releases")

resolvers += ("snapshots" at
    "http://oss.sonatype.org/content/repositories/snapshots")
    
resolvers += "Sonatype OSS Repo" at "https://oss.sonatype.org/content/groups/scala-tools"

mainClass := Some("com.twitter.scalding.Tool")

  libraryDependencies+= "org.specs2" %% "specs2" % "1.11" % "test"

	libraryDependencies+= "cascading" % "cascading-core" % "2.0.2"

	libraryDependencies+= "cascading" % "cascading-local" % "2.0.2"

	libraryDependencies+= "cascading" % "cascading-hadoop" % "2.0.2"

	libraryDependencies+= "com.twitter" %% "scalding" % "0.7.3"

	libraryDependencies+= "commons-lang" % "commons-lang" % "2.4"

	libraryDependencies+= "io.netty" % "netty" % "[3.4.6.Final]"
	
  libraryDependencies += "org.apache.hbase" % "hbase" % "0.94.0"

  parallelExecution in Test := false

  seq(assemblySettings: _*)


mergeStrategy in assembly <<= (mergeStrategy in assembly) { (old) =>
  {
    case _ => MergeStrategy.last // leiningen build files
  }
}

excludedFiles in assembly := { (bases: Seq[File]) =>
  bases.filterNot(_.getAbsolutePath.contains("seshet")) flatMap { base => 
    //Exclude all log4j.properties from other peoples jars
    ((base * "*").get collect {
      case f if f.getName.toLowerCase == "log4j.properties" => f
    }) ++ 
    //Exclude the license and manifest from the exploded jars
    ((base / "META-INF" * "*").get collect {
      case f => f
    })
  }
}
