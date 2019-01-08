README for the assessment project. If you look at this file in Gitlab/Eclipse/IDEA, the Markdown formatting will be applied.

# Introduction #

This page describes the SoftConEx assessment project. 

The assessment project is used to ensure consistency in the recruiting process and to aid both sides - the future employee and SoftConEx - to get to know each other. 
The assessment is not a one-way street, it is supposed to help both sides in understanding whether the applicants' requirements match SoftConExs' requirements and vice versa.

## Important note ##

Software development is inherently difficult, especially when - which is the case here - somebody enters a project that has already been going on for a while (in our case, since 2005). 

It is virtually impossible to know everything first place. 

**Do not hesitate to ask any questions**. 

At SoftConEx we cultivate an open style of discussing problems and questions. Do not waste time and other resources when you've come to a dead end at your current task (be it assessment or "real" development) just because you don't want to ask too many questions.

# Requirements/Installation #

* Git
* OpenJDK version as defined per source/target level
* Maven min version as defined per maven enforcer plugin
* JUnit Tests should run without error
* Maven (mvn clean install) should produce a .jar file without errors (this includes that all tests run successfully)

```
mvn package
```

# Libraries used #

* *Commons Logging* from https://commons.apache.org/proper/commons-logging/
* *Dom4J* from https://dom4j.github.io/
* *JUnit* from http://junit.org/junit4/

# Coding #

## Custom class for list ##

## Logging ##

The assessment project uses the Commons Logging API. 

## toXML() / parse ##

The classes typically have a toXML() and a parse() method which will convert back and forward between a Java object and XML in a fairly simple way (through Dom4j). 

PriceTest has some samples for testing XML (de)serialization.

# Assessment #

## Calculation Model ##

A calculation model calculates a markup for a given price, *e.g.*

* If price is between 0 and 99 EUR, a markup of 10 EUR will be applied
* If price is between 100 and 199 EUR, a markup of 15 EUR will be applied
* If price is higher than 199 EUR, a markup of 8% will be applied

The implemented classes shall provide all necessary attributes and methods to perform this kind of logic.

In order to keep things simple, the Price class in the assessment project does not have a currency attribute.

## Classes ##

* *Price*: A simple price object (without currency)
* *PriceList*: A list of price objects
* *PriceRange*: A price range (price from/to)
* *CalculationModelDetail*: A calculation model detail which defines the markup for a given price range
* *CalculationModelDetailList*: A list of calculation model details
* *CalculationModel*: A calculation model holds an instance of CalculationModelDetailList 

The classes will have methods to convert objects to XML and to parse XML structures.

## Tasks ##

* Implement the toXml() and parse() methods in CalculationModelDetail, CalculationModelDetailList and CalculationModel

* Implement the equals() method in CalculationModelDetail
  * Add the necessary test methods to CalculationModelDetailTest. 
  * A sample implementation of a nontrivial equals() method can be found in the PriceRange class.
  
* Implement a new method sortByMinimumAscending() in CalculationModelDetailList. 
  * The sort logic will be similar to the sorting done in PriceList (already implemented) with one exception - a detail without priceRange (getPriceRange() == null) should always be the last element of a sorted list.
  
* Implement CalculationModelDetail#calculate completely
  * The TODOs are described inside the method body itself) and add the necessary test methods.
  
* Implement CalculationModel class completely, most importantly the #calculate method. 
  * Create JUnit Testcase with necessary test methods

## Note on firstPerCent ##

This defines whether percentage is applied first or absolute markup, e.g.

* *firstPerCent=true*: ( 100 + 10% ) + 10 = 120
* *firstPerCent=false*: ( 100 + 10 ) + 10% = 121

# git branching model and auto format

Please create a (feature) branch based on the master branch and commit/push into this feature branch.

Final work then is merged via a pull request into the master.

Please also make sure that the diffs are clean (regarding line feeds etc.). 
 
The code has been formatted using the default Eclipse Formatter. 

You can enforce auto-format via Eclipse  Preferences:

Java / Editor / Save Actions
* [/] Format Source Code
* [/] Organize Imports

# Final note

Note that this is an assessment project, so there might be - like in real life projects - mistakes.
Feel free to ask or to correct these, or handle it in any way you find appropriate.

When you have completed the tasks, you have possibly discovered things which could be improved.
Feel free to implement improvements.

