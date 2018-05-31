# More Load Testing Set Up

Below are some helpful reminders about what needs to be set up each time we load test more for a new term. There is some set up that needs to take place in Peoplesoft as well as some updates that we'll need to make to more and course schedule service.

## course-schedule services changes
- Update the queries that gather student and class data. Queries are located in cs-service/jmeter/loadtesting.sql

## more changes
- The results of the queries should be saved in csv format in `more/jmeter/<env>` dir.

## jmeter script changes 
- Update the term var in the UAT User Defined Variables element.
- Adjust "test" thread groups and ramp up time values used to verify that the script works.

## peoplesoft changes
See [More Documentation](https://devops.app.vanderbilt.edu/confluence/display/ADIQU/More) in Confluence for how to perform the following

- Registration term dates need to be open.
- Students need to be term-activated for the term in the students query.

    - The term activation batch process will need to be run if student are not already term activated. The navigation is as follows. Check confluence for details on setting up the run control.

        > Records and Enrollment - Term Processing - Term Activation - Term Activation Batch Process

- Students need registration appointments or open enrollment needs to be set up.

## miscellany   
- Remember to turn on the perfmon agents on asu boxes to measure memory and cpu
    - for windows, use PuTTY client

- [Taurus](https://gettaurus.org/install/Installation/)
    - taurus is a command line tool that allows you to view the results of your test in real time without the overhead that comes with activating listeners. The tool creates a jtl file that can then be imported into Blazemeter sense for further analysis. 
    - to run the test: from your `taurus\bin` dir, run the following command:

        `bzt <filepath to your jmx file>`
            
    - multiple .jmx files can be run from the same command or you can can configure a .yaml file with the variables you want to modify.

        - refer to the [Confluence page on working with multiple .jmx files in Taurus](https://devops.app.vanderbilt.edu/confluence/display/ADIQU/Working+with+multiple+jmx+files)

- For larger load tests, i.e 1000 threads, it would be useful in increase the amount of memory that jMeter has access to. Find the following property in the `jmeter\bin\jmeter.bat`  

    - ```set HEAP=-Xms512m -Xmx1024m```
