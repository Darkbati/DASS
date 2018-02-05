java -jar -server -d64 \
    -Dspring.profiles.active=vivo \
    -Dlog4j.configuration=file:/app/obm/config/log4j.properties \
    -Dservice.config=/app/obm/config/config.vivo.properties \
    -Ddefault.path=/app/obm/bin/ \
    -Ddefault.config.path=../config/ \
    -Xms200m -Xmx200m -XX:PermSize=100m -XX:MaxPermSize=100m \
    -XX:+UseParallelOldGC -XX:ParallelGCThreads=3 -XX:-UseConcMarkSweepGC -XX:+UseAdaptiveSizePolicy \
    -XX:+DisableExplicitGC -XX:SurvivorRatio=5 -XX:GCTimeLimit=90 \
    -XX:+UseParallelGC -XX:MaxNewSize=50m -XX:NewSize=50m \
    -XX:MaxTenuringThreshold=0 -XX:CMSInitiatingOccupancyFraction=60 \
    -XX:+HeapDumpOnOutOfMemoryError \
    -XX:HeapDumpPath=/app/obm/logs/heapdump \
    -Dfile.encoding=utf-8 \
    /app/obm/bin/outboundManager.jar \