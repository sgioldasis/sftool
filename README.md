Sftool
=================

_Sftool_ allows you to import metadata from Salesforce (the schemas of the Salesforce record types), convert them to Avro and put it in your local filesystem, as Avro schema (avsc) files.  

It only needs access to the Enterprise WSDL of your Salesforce Organisation.

Features:

- Choose the type(s) of records you want to import
- Data types are inferred by looking at the Enterprise WSDL of your Salesforce Organisation
- Based on [salesforce2hadoop](https://github.com/datadudes/salesforce2hadoop)
- Built with the help of [wsdl2avro](https://github.com/datadudes/wsdl2avro) library.
- Built for the JVM, so works on any system that has Java 7 or greater installed

## Installation

You can find compiled binaries [here on Github](https://github.com/sgioldasis/sftool/releases). Just download, unpack and you're good to go.

If you want to build **Sftool** yourself, you need to have [Scala](http://www.scala-lang.org/download/install.html) 
and [SBT](http://www.scala-sbt.org/release/tutorial/Setup.html) installed. You can then build the "fat" jar as follows:

```bash
$ git clone https://github.com/sgioldasis/sftool.git
$ sbt assembly
```

The resulting jar file can be found in the `target/scala-2.11` directory.

## Usage

`sftool` is a command line application that is really simple to use. If you have Java 7 or higher installed, you 
can just use `java -jar sftool.jar` to run the application. To see what options are available, run:

```bash
$ java -jar sftool.jar --help
```

In order for _Sftool_ to understand the structure of your Salesforce data, it has to read the Enterprise WSDL 
of your Salesforce organisation. You can find out [here](https://developer.salesforce.com/docs/atlas.en-us.api.meta/api/sforce_api_quickstart_steps_generate_wsdl.htm) 
how to generate and download it for your organisation.

`sftool` has a single command for importing metadata from Salesforce: _getschemas_.

#### Get schemas

Use **getschemas** to import schemas from Salesforce. For each record type a schema file will be created on your local filesystem. You can get specific schemas like this:

```bash
$ java -jar sftool.jar getschemas --outpath /output/path --wsdl /path/to/enterprise.wsdl recordtype1 recordtype2 ...
```

You can also get all schemas if you don't provide any specific `recordtype` argument:

```bash
$ java -jar sftool.jar getschemas --outpath /output/path --wsdl /path/to/enterprise.wsdl
```

**Schemas output directory**

It also needs an _outPath_ where it will store all the imported schema files. Avro schemas will be stored under the provided output path. Schema for each record type will be stored under its own filename within this directory, which is the record type with a `.avsc` extention.

**Record types**

Provide `sftool` with the types of records you want to import from Salesforce. Specify the types by their Salesforce API names. Examples: `Account`, `Opportunity`, etc. Custom record types usually end with `__c`, for example: 
`Payment_Account__c`.

## Contributing

You're more than welcome to create issues for any bugs you find and ideas you have. Contributions in the form of pull requests are also very much appreciated!

## Authors

Sftool was created by:

- [Savas Gioldasis](https://github.com/sgioldasis) - [LinkedIn](https://www.linkedin.com/in/sgioldasis/)