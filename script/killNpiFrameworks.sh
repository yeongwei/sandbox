#!/bin/bash

findPID() {
	PIDs=(`ps -ef | grep ${1} | grep -v grep | awk '{print $2}'`)
	echo ${PIDs[*]}
}

killCmd() {
	PIDs=(`findPID "${1}"`)
	echo "Search String: ${1}"
	if [ ${#PIDs[@]} -eq 0 ] 
	then
		echo "No PIDs to kill"
	fi
	for pid in "${PIDs[@]}"
	do
		echo "PID to kill: ${pid}"
		kill -9 ${pid}
	done
}

FRAMEWORKS=("zookeeper" "kafka" "hadoop" "npi")

for fw in "${FRAMEWORKS[@]}"
do
	killCmd "-i ${fw}"
done
