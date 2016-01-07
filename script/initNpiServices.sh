#!/bin/bash

##########################################################
### V A R I A B L E S ####################################
##########################################################
SCRIPT_DIR=`pwd`"/";
TNPM_CLOUD_DIR=$SCRIPT_DIR"../../tnpm-cloud/";
TARGET_DIR=$TNPM_CLOUD_DIR"target/";
SERVICES_DIR=$TARGET_DIR"pack/services/";

BIN=$SERVICES_DIR"kafka/bin/";
ZOOKEEPER_START=$BIN"zookeeper-server-start.sh";
KAFKA_START=$BIN"kafka-server-start.sh";

CONF_DIR=$SERVICES_DIR"conf/kafka/";
ZOOKEEPER_CONF=$CONF_DIR"zookeeper.properties";
KAFKA_CONF=$CONF_DIR"kafka-server.properties";

PID="/tmp/initNpiServices.pid";
LOG="/tmp/initNpiServices.log";

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

function writePid {
	echo $1 $2 >> $PID;
}

function checkPointPidFile {
	echo `date` >> $PID;
}
##########################################################
### M A I N ##############################################
##########################################################
echo "About to start NPI services.";

echo "Check all required files.";
checkFileExist $ZOOKEEPER_START;
checkFileExist $ZOOKEEPER_CONF;

checkFileExist $KAFKA_START;
checkFileExist $KAFKA_CONF;

checkPointPidFile

echo "About to start Zookeeper.";
$ZOOKEEPER_START $ZOOKEEPER_CONF &
writePid "Zookeeper" $!;

echo "About to start Kafka.";
$KAFKA_START $KAFKA_CONF &
writePid "Kafka" $!;

echo "Done.";
exit 0;

