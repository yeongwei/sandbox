#!/bin/bash

findPID() {
	PIDs=(`ps -ef | grep -i "${1}/bootstrap.sh" | grep -v grep | grep -v gedit | grep -v killNpiFrameworks | awk '{print $2}'`)
	echo ${PIDs[*]}
}

checkKillStatus() {
	killed=0
	waitCount=10

	while [ ${waitCount} -ne 0 ]; do
		psResult=`ps -e | grep ${1}`
		echo "[DEBUG] ${psResult}"
		if [ -z "${psResult}" ]
		then
			killed=1
			echo "PID ${1} has been killed"
			break
		fi
		sleep 5
		waitCount=$(( $waitCount - 1))
	done

	if [ ${killed} -eq 0 ]; then
		echo "PID ${1} is still alive"
	fi
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
		kill -15 ${pid}
		checkKillStatus ${pid}
	done
}

FRAMEWORKS=("npi" "spark_hadoop" "kafka" "zk")

for fw in "${FRAMEWORKS[@]}"
do
	killCmd "${fw}"
done
