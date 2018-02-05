#! /bin/sh

GREEN='\033[1;32m'
NC='\033[0m' # No Color

OPS_JAVA="java -jar -server -d64 -Dspring.profiles.active=vivo -Dlog4j.configuration=file:../config/log4j.properties -Xms1g -Xmx1g -XX:PermSize=512m -XX:MaxPermSize=512m -XX:+UseParallelOldGC -XX:ParallelGCThreads=3 -XX:-UseConcMarkSweepGC -XX:+DisableExplicitGC -XX:SurvivorRatio=5 -XX:GCTimeLimit=90 -XX:-UseAdaptiveSizePolicy -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=../logs/heapdump -Dfile.encoding=utf-8 -Xloggc:../logs/gc.log -XX:+PrintGCDateStamps -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=10 -XX:GCLogFileSize=1024K"

SERVER_NAME="outboundManager"
PROCESSOR_NAME="outboundManager"
PROCESSOR_OWNER_ID="obm"

cmd_stop()
{
    pid=`ps -ef | grep "$PROCESSOR_NAME" | grep "$PROCESSOR_OWNER_ID" | grep -v 'grep' | awk '{print $2}'`
    if [ $pid ]; then
        echo "Kill PID : "$pid
        kill -9 $pid
    fi
}

cmd_start()
{
    sleep 1
    PROCESS_COUNT=`ps -ef | grep "$PROCESSOR_NAME" | grep "$PROCESSOR_OWNER_ID" |grep -v 'grep' | awk '{print $2}' | wc | awk '{print $1}'`

    if [ "$PROCESS_COUNT" = "0" ]; then
        echo "Starts Server."
        nohup ./start.sh >> /dev/null &
    else
        echo $SERVER_NAME" Server shutting down fail. "
        exit 1
    fi

    sleep 2
    pid=`ps -ef | grep "$PROCESSOR_NAME" | grep "$PROCESSOR_OWNER_ID" | grep -v 'grep' | awk '{print $2}'`

    if [ -z $pid ]; then
        echo $SERVER_NAME" Server restarting failed."
        exit 1
    else
        echo $SERVER_NAME" Server successfully restarted. PID : "$pid
        exit 0
    fi
}

cmd_status()
{
    PROCESS_COUNT=`ps -ef | grep "$PROCESSOR_NAME" | grep "$PROCESSOR_OWNER_ID" | grep -v 'grep' | awk '{print $2}' | wc | awk '{print $1}'`
    if [ "$PROCESS_COUNT" = "0" ]; then
        echo "Stop.... OBM"
        exit 1
    else
        echo "Running.... OBM"
        exit 0
    fi
}

cmd_version()
{
    echo `java -jar /app/obm/bin/outboundManager.jar -version`
}

if [ ${1,,} = "start" ]; then
    cmd_start
elif [ ${1,,} = "restart" ]; then
    cmd_stop
    cmd_start
elif [ ${1,,} = "stop" ]; then
    cmd_stop
elif [ ${1,,} = "status" ]; then
    cmd_status
elif [ ${1,,} = "-version" ]; then
    cmd_version
else
    echo "Command not found"
fi



