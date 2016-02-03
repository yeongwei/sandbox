#/bin/bash

##
## Usage: testNpidZookeeper.sh [NPI_HOME]
##

if [ -z "${1}" ]; then 
	NPI_HOME="/home/sherpa/tnpm-cloud-script"
else
	NPI_HOME=${1}
fi

nohup $NPI_HOME/bin/npid2 start zookeeper

while [ -z "`ps -ef | grep zookeeper | grep -v grep`" ]; do
	echo "Delay for 5s"
	sleep 5;
done;

if [ -z "`echo stat | nc localhost 2181`" ]; then
	echo "FAILED"
	exit -1
else
	echo "PASSED"
fi

nohup $NPI_HOME/bin/npid2 stop zookeeper

while [ -f $NPI_HOME/var/zookeeper.pid ]; do
	echo "Delay for 5s"
	sleep 5;
done;

if [ -z "`echo stat | nc localhost 2181`" ]; then
	echo "PASSED"
else
	echo "FAILED"
	exit -1
fi

