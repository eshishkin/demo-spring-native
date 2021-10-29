#/bin/bash
rm -r result
rm log.txt
jmeter -n -t test_plan.jmx  -l log.txt -e -o result

#-p jmeter.properties