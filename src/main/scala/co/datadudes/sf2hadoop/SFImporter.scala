package co.datadudes.sf2hadoop

import better.files.{File, _}
import com.typesafe.scalalogging.LazyLogging
import org.apache.avro.Schema


class SFImporter(recordSchemas: Map[String, Schema],
                 basePath: String
                ) extends LazyLogging {

  val fsBasePath: String = if (basePath.endsWith("/")) basePath else basePath + "/"

  val baseDir: File = fsBasePath.dropRight(1)
    .toFile
    .createIfNotExists(asDirectory = true)

  def schemaImport(recordType: String): Unit = {
    val schema = recordSchemas(recordType)

    val avsc = (fsBasePath + recordType + ".avsc")
      .toFile
      .createIfNotExists()
      .overwrite(schema.toString(true))
  }

}
