#!/bin/bash
### BEGIN INIT INFO
# Provides:          auto-resize-partition
# Required-Start:    mountall
# Required-Stop: 
# Default-Start:     S
# Default-Stop:
# Short-Description: Automatically resizes the partition to 100% of the available space on the first boot.
# Description:
### END INIT INFO

echo "Resizing Partition @DEVICE@p@PARTITION@ on first boot..."
parted @DEVICE@  resizepart @PARTITION@ 100%
resize2fs @DEVICE@p@PARTITION@

if [[ -f "/lib/systemd/auto-resize-partition.service" ]]; then
    systemctl disable auto-resize-partition.service
else
    update-rc.d auto-resize.partition.sh remove
fi
