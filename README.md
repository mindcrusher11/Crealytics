# Crealytics
Requirements:

Apache Spark version 1.6.0,
Scala 2.10,
Sbt 

#Please enter csvFilePath for input csv file and outPath for output file and testFilePath for sample test file path
configuration file is there in Resources folder with all details,any path can be given for configuration file.

spark_csv{
masterUrl= "local[4]"
appName = "readcsv"
readFormat = "com.databricks.spark.csv"
isReadCsvHeader = "true"
isCsvInferSchema = "true"
csvFilePath = "file:///home/gaur/test.csv"
csvDelimiter = ";"
colDateFormat = "yyyy-MM-dd"
isWriteCsvHeader = "true"
writeCsvDelimiter = ";"
outPath = "file:///home/gaur/test_out.csv"
outFileCount = "1"
testFilePath = "file:///home/gaur/Desktop/test_out.csv"
groupColumn = "date"
pivotColumn = "type"
writeFormat = "com.databricks.spark.csv"
}

Right now I am using databricks spark-csv package to perform read write operations over csv file.

Dependecies are listed in build.sbt file.

Run Application main method is also defined in one of file

Application can be run by using tests 

with command 'sbt test'

it will store output at path mentioned in configuration file.

Please change output and input path for csv file csvfilepath for input and outfilepath for output save.

Exceptiona handling and design patterns are pending.
