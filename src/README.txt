This program handles biotab generation for biospecimen and auxiliary biotabs.

Program Execution
This program must be executed with a directory path to the configuration properties file. For example:
java -jar tcga_bcr_biotab.jar -propPath C:\\TDE\\tcga-bcr-biotab\\src\\main\\resources\\config.properties

It is important to clear the output directory before generation, otherwise the biotab files will be appended
with the newly processed XML data. This is usually not the desired outcome.

Config Properties
The Config Properties file contains details about file locations, database connections, and other general settings.
It also maintains a link to the biotab properties file. See the file for more details.

Biotab Properties
The biotab properties controls settings for actually producing the biotabs, such as exclusions.
See the file for more details.

