dustjs-maven-plugin
===================

Maven plugin for the LinkedIn version of Dust.js

This plugin will precompile Dust.js templates into their javascript format for use in your projects.

When you need to use a Dust.js template, create the template and place it into the sourceDirectory configured in your projects pom file. 
The name of the template file will be the registered Dust.js template name used in the dust.render call.  For example, calendar_event_template.html
will register as calendar_event_template

This will place a corresponding .js file into the outputDirectory.

You can now just include this script onto your page and render it where needed.

    <script type="text/javascript" src="/scripts/dust-core-2.7.5.min.js"></script>
    <script type="text/javascript" src="/scripts/templates/dust/js/calendar_event_template.js"></script>
    
in javascript, after you have retrieved some sort of json object, objMyEvents in this case, call dust.render with the data you want to display:

    dust.render("calendar_event_template", objMyEvents, function(err, out) {
        $('#myEventsWrapper').html(out);
    });

Configuration options
---------------------

+ outputDirectory (File) - The directory for compiled javascript templates. 
+ sourceDirectory (File) - The source directory containing the template sources.
+ includes (String[]) - List of files to include. Specified as fileset patterns which are relative to the source directory. Default value is: { "\*\*/\*.html" }
+ excludes (String[]) - List of files to exclude. Specified as fileset patterns which are relative to the source directory.
+ force (boolean) - When true forces the Dust.js compiler to always compile the HTML templates. By default templates are only compiled when modified or the compliled javascript template does not exists. Default value is: false.
+ dustVersion (String) - Dust.js compiler version. Default value is dust-full-2.7.5.js

License
-------

License of the original source by Dan Monroe is unknown. Use and modify at your own risk.

Usage
-----

Install to your local maven repository:

    git clone https://github.com/patchpump/dustjs-maven-plugin.git
    cd dustjs-maven-plugin
    mvn clean install 

Alternatively add this github maven repository:

    <repositories>
        <repository>
            <id>dustjs-maven-plugin-mvn-repo</id>
            <url>https://raw.github.com/patchpump/dustjs-maven-plugin/mvn-repo/</url>
        </repository>
    </repositories>

Example pom.xml configuration:

    <plugin>
	    <groupId>patchpump.dust</groupId>
	    <artifactId>dust-maven-plugin</artifactId>
	    <version>2.7.5-PATCHPUMP-R4</version>
	    <configuration>
	        <sourceDirectory>src/main/webapp/templates/dust/src</sourceDirectory>
	        <outputDirectory>src/main/webapp/templates/dust/js</outputDirectory>
          <includes>
            <include>**/*.html</include>
          </includes>
	    </configuration>
	    <executions>
	        <execution>
	            <goals>
	                <goal>war</goal>
	            </goals>
	        </execution>
	    </executions>
	  </plugin>
