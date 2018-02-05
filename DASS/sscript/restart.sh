#!/bin/sh

SERVER_NAME="outboundManager"
HOSTNAME=`hostname`
PROCESSOR_NAME="outboundManager"

while echo $1 | grep ^- > /dev/null; do
    eval $( echo $1 | sed 's/-//g' | tr -d '\012')=$2
    shift
    shift
done

echo $SERVER_NAME" Server shutting down....."
pid=`ps -ef | grep "$PROCESSOR_NAME" | grep -v 'grep' | awk '{print $2}'`

if [ -z $pid ]; then
  echo $SERVER_NAME" Server Stopped."
else
  echo "Kill PID : "$pid
  kill -9 $pid
fi

sleep 1
PROCESS_COUNT=`ps -ef | grep "$PROCESSOR_NAME" | grep -v 'grep' | awk '{print $2}' | wc | awk '{print $1}'`
echo "Check Operating Server Count : "$PROCESS_COUNT

if [ "$PROCESS_COUNT" = "0" ]; then
  echo $SERVER_NAME" Server stopped."
  echo "Starts Server."
  nohup ./start.sh >> /dev/null &
else
  echo $SERVER_NAME" Server shutting down fail. "
  exit 1
fi

sleep 2
pid=`ps -ef | grep "$PROCESSOR_NAME" | grep -v 'grep' | awk '{print $2}'`

if [ -z $pid ]; then
  echo $SERVER_NAME" Server restarting failed."
else
  echo $SERVER_NAME" Server successfully restarted. PID : "$pid
fi
