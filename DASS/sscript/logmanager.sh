#!/bin/bash

DATE=`date +%Y%m%d-%H%M%S`

APP_USER=obm
APP_NAME=obm_service
APP_HOST=OBM01

###############################################################################
# EXPIRE_OPTION
# day: -mtime +0	(24hr*1, set n-1)
# minute: -mmin +720	(12hr)
# -mtime N*24 time test
#   0 : 0h <= time < 24h
#   1 : 24h <= time < 48h
#   2 : 48h <= time < 72h
#  -2 : time < 48h
#  +2 : time >= 72h
LOG_EXPIRE_OPTION='-mtime +10'
ARCHIVE_EXPIRE_OPTION='-mtime +30'

LOG_PATH=/app/$APP_USER/logs
LOG_PREFIX=$APP_NAME
LOG_SUFFIX=log

TARGET_PATH=$LOG_PATH
TARGET_PREFIX=$APP_NAME
TARGET_SUFFIX=tar.gz
TARGET_FILE=$TARGET_PATH/$TARGET_PREFIX.log.$DATE.$TARGET_SUFFIX

###############################################################################
cd $LOG_PATH
# echo "ls -1 $LOG_PREFIX.*.$LOG_SUFFIX | wc -l"
# echo "find $LOG_PREFIX.*.$LOG_SUFFIX $LOG_EXPIRE_OPTION | wc -l"
backup=`find $LOG_PATH/$LOG_PREFIX.*.* $LOG_EXPIRE_OPTION | wc -l`
current=`ls -1 $LOG_PATH/$LOG_PREFIX.*.* | wc -l`
if [ $backup -gt 0 -a $backup -lt $current ]; then
# Remove log file
# echo "find $LOG_PREFIX.*.$LOG_SUFFIX $LOG_EXPIRE_OPTION -exec rm {} \;"
# find  $LOG_PREFIX.*.$LOG_SUFFIX $LOG_EXPIRE_OPTION -exec rm {} \;
# exit 0

# Archive log files
# echo "tar cfz $TARGET_FILE --remove-file \`find $LOG_PREFIX.*.$LOG_SUFFIX $LOG_EXPIRE_OPTION \`"
# tar cfz $TARGET_FILE --remove-file `find $LOG_PREFIX.*.$LOG_SUFFIX $LOG_EXPIRE_OPTION`
echo "find $LOG_PATH/$LOG_PREFIX.*.* $LOG_EXPIRE_OPTION -exec tar cfz $TARGET_FILE --remove-file {} \;"
find $LOG_PATH/$LOG_PREFIX.*.* $LOG_EXPIRE_OPTION -exec tar cfz $TARGET_FILE --remove-file {} \;
fi

###############################################################################
# Remove tar files
cd $TARGET_PATH

echo "find $LOG_PATH/$TARGET_PREFIX.log.*.$TARGET_SUFFIX $ARCHIVE_EXPIRE_OPTION -exec rm {} \;"
find $LOG_PATH/$TARGET_PREFIX.log.*.$TARGET_SUFFIX $ARCHIVE_EXPIRE_OPTION -exec rm {} \;
###############################################################################


