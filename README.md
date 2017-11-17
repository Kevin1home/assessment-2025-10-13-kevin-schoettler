README for the assessment project. If you look at this file in BitBucket/Eclipse/IDEA, the Markdown formatting will be applied.

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
* Java 8 or higher
* Maven 3.3.9 or higher
* Eclipse Oxygen (4.7) or higher; IntelliJ IDEA 2017.2.5 is fine as well
* JUnit Tests should run without error
* Maven should produce a .jar file without errors (this includes that all tests run successfully)

```
mvn package
```

# Libraries used #

* *Commons IO* from https://commons.apache.org/proper/commons-io/
* *Commons Logging* from https://commons.apache.org/proper/commons-logging/
* *Dom4J* from https://dom4j.github.io/
* *JUnit* from http://junit.org/junit5/
* *XMLUnit* from http://www.xmlunit.org/

# Coding #

## Custom class for list ##

Don't use 

```
#!java
List<Price> list = new ArrayList<Price>();
```

but rather create a class extending ArrayList, like PriceList in the assessment project.

This allows to add methods to the SomeObjectList class which will be
readily available throughout the code without any other changes.

## Logging ##

The assessment project uses the Commons Logging API. 

The basic usage is really very simple. First instantiate a LOG object. This is done typically as a static final variable which can be used throughout the class:

```
#!java
private static final Log LOG = LogFactory.getLog(SimpleTest.class);
```

Logging a message in the assessment project is always done on the so-called INFO level:

```
#!java
LOG.info("Price: " + price);
```

Logging an exception is done on the ERROR level:

```
#!java
LOG.error(ex.getMessage(), ex);
```

*Important notes on logging:*

* Never use System.out.print to log messages on the console, but LOG.info
* Never use ex.printStackTrace() to log an exception, but LOG.error

*Important notes on exceptions:*

* Never "just" ignore Exceptions, always LOG on error, warn or info level
  * This is not 100% correct, there are exceptions like NumberFormatException which are rather user input errors than real exceptions

Reason is that silently ignoring exceptions can make things very difficult to debug in production.

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

Note: there is also a class diagram, see doc/task-calculationmodel.png

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

# Final note

Note that this is an assessment project, so there might be - like in real life projects - mistakes.
Feel free to ask or to correct these, or handle it in any way you find appropriate.

When you have completed the tasks, you have possibly discovered things which could be improved.
Feel free to implement improvements.

