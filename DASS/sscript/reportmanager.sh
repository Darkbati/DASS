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
REPORT_EXPIRE_OPTION='-mtime +10'
ARCHIVE_EXPIRE_OPTION='-mtime +30'

REPORT_PATH=/app/$APP_USER/bin/backup_report
REPORT_PREFIX=$APP_NAME
REPORT_SUFFIX=report

TARGET_PATH=$REPORT_PATH
TARGET_PREFIX=$APP_NAME.$REPORT_SUFFIX
TARGET_SUFFIX=tar.gz
TARGET_FILE=$TARGET_PATH/$TARGET_PREFIX.$DATE.$TARGET_SUFFIX

###############################################################################
cd $LOG_PATH
backup=`find $REPORT_PATH/*.txt $REPORT_EXPIRE_OPTION | wc -l`
current=`ls -1 $REPORT_PATH/*.txt | wc -l`
if [ $backup -gt 0 -a $backup -lt $current ]; then

# Archive log files
# echo "tar cfz $TARGET_FILE --remove-file \`find $LOG_PREFIX.*.$LOG_SUFFIX $LOG_EXPIRE_OPTION \`"
# tar cfz $TARGET_FILE --remove-file `find $LOG_PREFIX.*.$LOG_SUFFIX $LOG_EXPIRE_OPTION`
echo "find $REPORT_PATH/*.txt $REPORT_EXPIRE_OPTION -exec tar cfz $TARGET_FILE --remove-file {} \;"
find $REPORT_PATH/*.txt $REPORT_EXPIRE_OPTION -exec tar cfz $TARGET_FILE --remove-file {} \;
fi

###############################################################################
# Remove tar files
cd $TARGET_PATH

echo "find $REPORT_PATH/*.txt $ARCHIVE_EXPIRE_OPTION -exec rm {} \;"
find $REPORT_PATH/*.txt $ARCHIVE_EXPIRE_OPTION -exec rm {} \;
###############################################################################
