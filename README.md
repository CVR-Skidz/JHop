# JHop
> An experimental search engine

JHop (Java Hop Engine) is an experimental search engine. JHop fulfills the following three operations which I the author consider 
qualify JHop as a search engine:

1. Information Acquisition - Web Crawling
2. Indexing & Ranking
3. Information Retrieval - Searching and displaying web pages

JHop allows a user to build individual indexes to seperate information, as opposed to searching one cumulative index. Using JHop 
requires the user to instruct JHop to cary out each operation - that is to crawl, index, and search. This can be done through the GUI, or command line
, both interfaces are accesible through the same jar.

## What's in this Repository

This repository contains the JHop program in a netbeans project. As such to compile the program one can use
the java compiler with Ant or directly load it into netbeans. 

- The source code is found under `src`
- All config files are found udner `config`
- All libraries used are found under `lib`
- All unit tests are found under `test`
- The embedded database use in JHop is found under `db`

JHop requires a JDK of 8 or newer, and also requires JavaFX. Hence if you are using a JDK which does not include
JavaFX you will require this additional dependency.

## Usage

JHop **does not** load an index by default. An index can be selected in the left hand menu. Additionally
you can add a new index, or drop the active one using the "Add" and "Drop" controls. The progress bar to the left indicates
when JHop is interacting with the database, when this is happening JHop will not process any more database commands.
(such as dropping an index whilst creating a new one). The active index is displayed in the bottom left, 
once an index is loaded the pages contained within the index will be listed here.

You can search the active index for terms in the search bar, clicking "Search" to execute. The number of 
results is displayed on the bottom of the interface. All results appear in the main window, clicking a result 
will load the page into JHop's web view. At any point you can search again, however the results page will only 
reappear if there are results found (consult the bottom status to see if there were 0 results).

### Creating an index

JHop crawls web pages to index terms. It is important to specify the correct query to attain the expected results.
When you "Add" a new index a seperate input dialog appears.  user need not tell JHop to 
index responses, but only give the required information to start crawling the web. This includes:

- The URL of the starting web page to crawl from.
- A query to indetify which HTML branches that contain the content to index.
- A hop limit (The number of links to follow from the starting page).

*Note that specifying a hop limit of 0 will not perform any "hops" but still indexes and crawls the starting page*

The supplied query is an important detail, based on the query supplied JHop will select the content to index - this is to allow
control of skipping irrelevant content such as adverts and blob data. This means that you will need to know how to specify an
appropriate query for the target domain.

*Note links to external sites are not followed, e.g. a site on a different domain than the starting page*

A query consits of an attribute and value. For instance the following element has an attribute of id and a value of content:

```
<div id="content"> </div>
```

A query identifies the element to start crawling from, and only elements that are listed in './include' or match the given query
are included in the crawl. A crawl will stop following a branch of elements in the DOM when both conditions are false.

For example given the following response the lines marked with \*'s will be crawled:

Query = class,content

Response = 

```
...
<body>
	<div class="container">
		<div class="content">
*			<p> ...
*			<ul>
*				<li>...
*			</ul>
*			<div class="content">
*				<p>...
*			</div>
			<div class="extra">
				<p>...
			</div>
		</div>
		<div class="extra">
		</div>
	</div>
</body>
```

If you create an index and find very few results, or none at all, it will be because the query is invalid (no matching elements)
or exists to far up the DOM that no included elements are direct childs of the first match.

In general an easy way to create a query is as follows:

1. Identify the included elements containing the desired content
2. Identify their direct parent
3. Identify an attribute and value contained in this parent
4. Supply these two tokens as the attribute and value

### Examples

A few example indexes have been supplied with JHop, they are described below:

- Coronavirus: extracts from wikipedia pages related to Coronavirus(es).
- Java Layout Managers: extracts from the Java documentation on Swing and AWT layout managers.

To test the functionality of JHop the following is an example query to index the python 3 documentation at the time of writing:

- URL: 		https://docs.python.org/3/library/array
- Attribute: 	class
- Value:	section
- Limit: 	{Up to the user, recommended: 5-20}

*A note on code blocks: `<code>` and `<pre>` tags are not included in the default tag list as many webpages contain duplicate text
content when they are included. However if you would like to view code blocks add the relevant tage to the `include` file, that
is pre or code (most likely both)*

## Attribution

JHop uses open source libraries (found in `lib`) provided by other authors. This includes:

- Apache Derby: Apache License 2.0
- JSoup: MIT License
- Junit: Eclipse Public License 2.0
- Hibernate ORM: LGPL 2.1

A copy of each of these licenses is attached under licenses, and were appropriate under NOTICES.

This software is distributed "as is" and comes with no warranty.
