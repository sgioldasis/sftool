package com.pythian.sftool

import java.io.File

import AvroUtils._
import co.datadudes.wsdl2avro.WSDL2Avro
import com.typesafe.scalalogging.LazyLogging

object SFImportCLIRunner extends App with LazyLogging {

  case class Config(command: String = "",
                    schemaOutPath: String = "",
                    sfWSDL: File = new File("."),
                    records: Seq[String] = Seq())

  val parser = new scopt.OptionParser[Config]("sftool") {
    head("sftool", "1.2")
    cmd("getschemas") required() action { (_, c) => c.copy(command = "getschemas") } text "Extract Salesforce record schemas as Avro files"
    note("\n")
    opt[String]('o', "outpath") required() action { (x, c) => c.copy(schemaOutPath = x) } text "Path for generated output files"
    opt[File]('w', "wsdl") required() valueName "<file>" action { (x, c) => c.copy(sfWSDL = x) } text "Path to Salesforce Enterprise WSDL"
    arg[String]("<record>...") optional() unbounded() action { (x, c) => c.copy(records = c.records :+ x) } text "List of Salesforce record types to import"
    help("help") text "prints this usage text"
  }

  parser.parse(args, Config()) match {
    case Some(config) => handleCommand(config)

    case None => System.exit(1)
  }

  def handleCommand(config: Config): Unit = {
    if (config.command.isEmpty || (config.command.trim != "getschemas")) {
      println("You need to enter a valid command (getschemas)")
    } else {
      val schemas = WSDL2Avro.convert(config.sfWSDL.getCanonicalPath, filterSFInternalFields)
      val importer = new SFImporter(schemas, config.schemaOutPath)

      if (config.command == "getschemas") {

        val records = if (config.records.isEmpty) schemas.keys else config.records
        records.foreach { recordType => importer.schemaImport(recordType) }
        println("Schema files were exported to folder '" + config.schemaOutPath + "'")

      }
    }
  }

}
