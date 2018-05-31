# More Jmeter Test

## Overview
The more jmeter test attempts to simulate the load of students enrolling for classes.  The test consists of four primary user groups:

### Student Enrollment  
This thread group simulates a student logging in and enrolling and dropping a class.  The test combines a random set of undergraduate students with classes.  This leads to may situations where the student connot enroll in the class.  Things like requisites and capicity reserves may make the enrollent fail.  The test makes no attempt to correct this situation, it's simply part of the test.   

### Anonyous 
This thread group simulates users searching for classes.  There is no login required to do this.  

### Student Login
This thread group simulates a student logging in and visiting the various pages of the registration (*more*) application.  Classes are searched for in a random way and the student schedule page is visited. These activities are performed in a loop. 
  

### Admin View
This thread group is almost exactly like the Student Login thread group except that the person logged in is an administrator rather than the student. 

## Setup 

### Ping Authentication
There is some set up needed to use Ping authentication in more. As currently configured, the more test looks in the user's home dir for `.\load_testing\login_credentials.csv` which should contain the user's vunetid and epassword. Vunet and epassword are needed since there is no longer a dummy password for our applications.

Additionally, the user's jmeter user properties file will need to be modified so that there is a property called `user.vunetid` set to the user's vunetid. This property supplies the vunetid of the user running the test so that jMeter knows where to pull the ping user config from that contains vunetid and epassword.

The file is found at `.\bin\user.properties`

### Which term to use?
Determine the term you want to run the tests for. 
You need to make sure that most undergrad students have a ```stdnt_car_term``` row for that term.
Depending on the time of year, they may or may not. 
If they don't, you have two options :

1. Find someone who knows how to term activate them.This would be a great thing to document. 
1. select a previous term (but be aware you'll have to deal with dropping classes and 
changing more  
ps settings to allow people to enroll for a term in a weird time. 

Once you settle on a term, update the environment user defined variables in the test plan. 

### Generate Sample Data
Generate the CSV files for the term you selected.  As indicated in the ```jmeter/*.sql``` files
use the SQL in ```cs-service/jmeter/load_testing.sql```.  It's more up to date with regard to all
the criteria required to find students and classes that are *enrollable*.

Once you have the files generated they should be placed in the appropriate ```jmeter/<env>``` directory.  They should also be named appropriately. 

### Remove Advisor (aka enrollment) holds
All undergraduates have an advisor hold (service indicator) placed on their record prior to enrollment. They are required to meet with their advisor to have the hold removed.  Enrollments will not succeed if a student has an enrollment hold. 

There is a thread group in the more jmeter script ```Remove Advisor (Enrollment) Holds``` that simply calls the *course-schedule-service* remove hold operation.  You can use this to remove the holds for some or all of your test students. 

## Running the Test
### Thread Counts
The test contains a set of User Defined Variables that determine the number of threads for each thread group.  There are two versions of these variables.  One is called *Testing Thread Counts* and it's purpose is to allow you to easily set the specific number of threads for each group.  The other is called *Thread Counts*.  It sets the same variables but as a percentage of the *maxThreads* global variable.   This is useful when you want to run the test with a different number of total threads but the same load distorbution across the thread groups.

### PerfMon Listeners
There are [PerfMon] listeners in the test that monitor CPU, memory, and heap of the various asu, sac, and JVM's.  For monitoring CPU and Memory, you need to have the PerfMon app running on the asu\*lt and sac\*lt boxes. If you have the ability to login to those boxes simply run the startAgent.sh script found in the [PerfMon] download.

You can also ask someone on the Academic dev team to run these if you don't have a login. 

All that's required is the PerfMon utility to be running on the box (it does not matter which user is running it). 
https://jmeter-plugins.org/wiki/PerfMon/]

### GUI vs Non GUI Mode
The gui mode is really just for creating and testing your test plan.  When running an actual test, you should use non gui mode. 

Blazemeter Taurus is incredilby helpful in determining if your test is running smoothly when kicked off in non-gui mode.

```more/jmeter/more_test.sh``` was created to make running in non GUI mode easier.

The script is designed to run _n_ tests with different thread counts for each.  For example, you could run tests with 100, 300, and 500 threads. 

Edit the more\_test.sh file and modify the THREAD_COUNTS variable to run tests with differnt thread counts.  

### Test Results
Error % for enrollment does not always mean an error. A certain percentage of enrollment is expected to fail due to things like prerequisites. This is expected behavior. 

[PerfMon]: https://jmeter-plugins.org/wiki/PerfMon/