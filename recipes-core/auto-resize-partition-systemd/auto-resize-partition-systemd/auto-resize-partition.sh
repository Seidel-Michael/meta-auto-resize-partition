#!/bin/bash

echo "Resizing Partition @DEVICE@p@PARTITION@ on first boot..."

umount @DEVICE@p@PARTITION@

if @PARTITION_EXTENDED_ENABLED@; then
   parted @DEVICE@ resizepart @PARTITION_EXTENDED@ 100%
fi

parted @DEVICE@ resizepart @PARTITION@ 100%
e2fsck -f -a @DEVICE@p@PARTITION@
resize2fs @DEVICE@p@PARTITION@
mount @DEVICE@p@PARTITION@

systemctl disable auto-resize-partition.service