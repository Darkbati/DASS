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