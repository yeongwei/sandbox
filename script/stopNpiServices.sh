#!/bin/bash

PID_FILE="/tmp/initNpiServices.pid";
ZOOKEEPER_TXT="Zookeeper";
KAKKA_TXT="Kafka";

##########################################################
### F U N C T I O N S ####################################
##########################################################
function checkFileExist {
	if [ -f $1 ]; then
		echo "Found $1";
	else
		echo "Not found $1";
		exit 1;
	fi
}

function getPid {
	if [ ! -z $1 ]; then
		echo `cat $PID_FILE | grep "$1" | awk '{print $2}'`;
	else
		echo "$1 is not a valid option.";
		exit 1;
	fi
}

function killPid {
	if [ ! -z $1 ] && [ ! -z $2 ]; then
		echo "Found $1 running with PID $2.";
		kill -9 $2;
		echo "Status is \"$!\"";
	else
		echo "Information in sufficient to kill PID.":
	fi
}

echo "About to stop NPI services.";

echo "Location NPI services PID file.";
checkFileExist $PID_FILE;

echo "About to stop Zookeeper.";
ZOOKEEPER_PID=$(getPid $ZOOKEEPER_TXT);

killPid $ZOOKEEPER_TXT $ZOOKEEPER_PID;

echo "About to stop Kafka.";
KAFKA_PID=$(getPid $KAKKA_TXT);

killPid $KAKKA_TXT $KAFKA_PID;

echo "About to remove PID file"
rm $PID_FILE;

echo "Done"
