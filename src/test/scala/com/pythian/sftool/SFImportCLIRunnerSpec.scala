package com.pythian.sftool

import java.io.File

import com.sforce.soap.partner.sobject.SObject
import org.apache.avro.SchemaBuilder
import org.apache.commons.io.FileUtils
import org.specs2.mutable.SpecificationWithJUnit
import org.specs2.matcher
import org.specs2.matcher.FileMatchers
import better.files.{File => ScalaFile, _}

import scala.io.Source

class SFImportCLIRunnerSpec extends SpecificationWithJUnit with FileMatchers {

  "Command: 'getschemas --outpath schemas --wsdl salesforce/wsdl.xml Opportunity'" should {

    "produce a correct avsc file in schemas output folder" in {

      val command = "getschemas"
      val outpath = "src/test/resources/schemas"
      val wslpath = "src/test/resources/wsdl.xml"
      val recordType = "Opportunity"
      val validationpath = "src/test/resources"
      val extention = "avsc"

      val config = SFImportCLIRunner.Config(command, outpath, new File(wslpath), Seq(recordType))

      val outfilePath = outpath + "/" + recordType + "." + extention
      val validationfilePath = validationpath + "/" + recordType + "." + extention

      val outDir = outpath.toFile
      val outFile = outfilePath.toFile
      val validationFile = validationfilePath.toFile

      if (outDir.exists) outDir.delete()
      if (outFile.exists) outFile.delete()

      SFImportCLIRunner.handleCommand(config)

      outFile.isSameContentAs(validationFile)
    }
  }
}
