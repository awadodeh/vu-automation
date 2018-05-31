#!/bin/bash

####################################################################################
# This script runs a series of jmeter tests in non gui mode.
# A typical use case is to run the test for say, 100, 200, 300, and 500 threads. 
# The results of each test will be stored in separate directores. 
# The reulsts will include graphs from the Graphs Generator Listener (http://jmeter-plugins.org/wiki/GraphsGeneratorListener/)
# Additionally, graphs will be stored for various Perfmon (CPU/MEM), and JMXMon (JVM heap) listeners. 
#
# The main goal of this script is to automate the task of running several tests 
# and saving the results so you can spend your time anaylzing the results rahter 
# than saving/orgainizing them. 
#
# This also allows you to easily compare a given load to another load.  
#
###################################################################################



# This function Generates a PNG graph using the command line 
# graphing tool from jmeter plugins (http://jmeter-plugins.org/wiki/JMeterPluginsCMD/)
#
# The first arg should be the report type. Subsequent args are the report suffixes
#  a JTL file should exist for each suffix
#  a PNG graph will be generated for each suffix 
#
# example: "RunRepots PerfMon asucpu asumem"
#   Indicates that two PerfMon reports should be generated :
#      $OUTPUT_DIR/$threads_asucpu.jtl -> $OUTPUT_DIR/$threads_asucpu.png
#      $OUTPUT_DIR/$threads_asumem.jtl -> $OUTPUT_DIR/$threads_asumem.png
RunReports()
{
   reportType=$1
   shift

   while test ${#} -gt 0
   do
      for reportFormat in $REPORT_FORMATS; do
         #REPORT_CMD="java -jar $CMD_RUNNER --tool Reporter --generate-${reportFormat} $OUTPUT_DIR/${threads}_${1}.${reportFormat} --input-jtl $OUTPUT_DIR/${threads}_${1}.jtl --plugin-type $reportType"
         REPORT_CMD="$CMD_RUNNER --generate-${reportFormat} $OUTPUT_DIR/${threads}_${1}.${reportFormat} --input-jtl $OUTPUT_DIR/${threads}_${1}.jtl --plugin-type $reportType"
         echo $REPORT_CMD
         $REPORT_CMD
      done
      shift
   done
}

# This function combines the results of the various tests into a single CSV file. 
# The purpose is to create a single file that compares samples of interetst. 
# 
# The samplesToCompare variable contains a list of "interesting samples".
# The resutls for those samples for each of the tests are grouped together resulting in something like this;
# where sample1 and sample2 are the interesting samples and the test is running for 100, 200, and 300 threads
# this allows easy comparison of the interesting samples. 
#100,sample1.....
#200,sample1.....
#300,sample1.....
#
#100,sample2.....
#200,sample2.....
#300,sample2.....

Combine()
{
   rm -rf $tmp_dir
   mkdir -p $tmp_dir
   header=no

   for i in $(echo ${THREAD_COUNTS}| sed "s/,/ /g"); do 
      if [[ $header = "no" ]]; then
         header=yes
         result=`head -1 ${OUTPUT_BASE}/${i}/${i}_SynthesisReportGui.csv`
         echo "# threads",$result > $tmp_dir/h.csv
      fi
      
      for sample in "${samplesToCompare[@]}"
      do
         result=`grep "$sample" ${OUTPUT_BASE}/${i}/${i}_SynthesisReportGui.csv`
         echo $i,$result >> $tmp_dir/c.csv
      done
   done
   cat $tmp_dir/h.csv > $tmp_dir/cs.csv
   sort -t, -k2,2 -k1,1 $tmp_dir/c.csv >> $tmp_dir/cs.csv
   perl -pe 's/^700.*\n\K/ , , , , , , , , , , , ,\n/' $tmp_dir/cs.csv  > ${OUTPUT_BASE}/combined_stats.csv
}

# Parse command line options
GetOptions ()
{
USAGE="This script runs load tests in non gui mode

usage: more_docker.sh -d <duration> -t <thread counts>
ex: more_docker.sh -d 120 -t100,300,500

-d indicates the duration of each test in minutes. Defaults to 10 minutes.
-t comma separated list of thread counts. Defaults to 100,300,500.
-j name of the jmeter test file (defaults to more.jmx)
-r ramp up time (defaults to 30 seconds)
-m location of jmeter base dir (defaults to /opt/jmeter)
-h this help";

opt_d="600"
opt_t="100 300 500"
opt_j="more.jmx"
opt_r="30"
opt_m="/opt/jmeter"

  while getopts d:t:j:r:m:h arguments
  do
    case $arguments in
      d)  opt_d=$OPTARG;;
      t)  opt_t=$OPTARG;;
      j)  opt_j=$OPTARG;;
      r)  opt_r=$OPTARG;;
      m)  opt_m=$OPTARG;;

      h)  echo "\n$USAGE"
          exit 0;;

      \?)  echo "$USAGE"
           exit 1;;
   esac

  done
}

# Main
GetOptions $*

# These are used to generate graphs when the tests are complete. 
PERFMON_REPORTS="cpu mem"
JMX_REPORTS="regjvm ws1jvm"

# Indicates which reports to generate in RunReports();
REPORT_FORMATS="csv png"

# This is a list of total thread counts used in the test.  
# A test will be exectued for each number in this list. 
THREAD_COUNTS=${opt_t}

# Name of the jmeter test plan to run. 
TEST_JMX=${opt_j}

# base install of jmeter
JMETER_HOME=${opt_m}

# base command to run to execute jmeter in non-gui mode. 
JMETER_CMD_BASE="$JMETER_HOME/bin/jmeter -e -n -t $TEST_JMX"

# location of the CMDRunner jar which is used to generate reports when a test is complete. 
CMD_RUNNER="$JMETER_HOME/lib/ext/JMeterPluginsCMD.sh"
CURRENT_DATE=`date +%Y-%m-%d:%H:%M`

# Base location to store results 
#OUTPUT_BASE=/home/loadtest/more/jmeter/results
#tmp_dir=/home/tmp

OUTPUT_BASE=./results/$CURRENT_DATE
tmp_dir=/tmp/$CURRENT_DATE
samplesToCompare=('Enroll in Class', 'Drop Class', 'WD Enroll', 'WD Drop', 'Add To Cart', 'Admin CreateSchedule', 'Poll Drop', 'Poll Enroll', 'Q Drop', 'Q Enroll', 'Drop GetNotifications', 'Enroll GetNotifications', 'TOTAL');

## For each of the thread counts, run jmeter and generate reports. 
for threads in $(echo ${THREAD_COUNTS}| sed "s/,/ /g"); do 

   OUTPUT_DIR=$OUTPUT_BASE/$threads
   REPORT_OUTPUT_DIR=$OUTPUT_BASE/${threads}_report
   mkdir -p $REPORT_OUTPUT_DIR
   TEST_RESULTS=$OUTPUT_DIR/${threads}_`basename $TEST_JMX .jmx`.csv

   JMETER_CMD="$JMETER_CMD_BASE -l ${TEST_RESULTS} -Jp_rampUp=${opt_r} -Jp_duration=${opt_d} -Jp_total_threads=${threads} -Jp_output_dir=${OUTPUT_DIR} -o ${REPORT_OUTPUT_DIR}"

   echo ${JMETER_CMD}
   ${JMETER_CMD}

   RunReports PerfMon ${PERFMON_REPORTS}
   RunReports JMXMon ${JMX_REPORTS}
done

Combine
