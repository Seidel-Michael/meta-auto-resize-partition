SUMMARY = "Automatically resizes the partition to 100% of the available space on the first boot."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI += "file://auto-resize-partition.service file://auto-resize-partition.sh"

inherit systemd

SYSTEMD_SERVICE_${PN} = "auto-resize-partition.service"

do_install() {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/auto-resize-partition.service ${D}${systemd_system_unitdir}/auto-resize-partition.service

    install -d ${D}/usr/bin
    install -m 0744 ${WORKDIR}/auto-resize-partition.sh ${D}/usr/bin
}

RDEPENDS_${PN} += " bash parted e2fsprogs-resize2fs"


# Add to image Recipe

#update_auto_resize(){
#    sed -i 's#@DEVICE@#/dev/mmcblk2#g' ${IMAGE_ROOTFS}/usr/bin/auto-resize-partition.sh
#    sed -i 's#@PARTITION@#6#g' ${IMAGE_ROOTFS}/usr/bin/auto-resize-partition.sh
#    sed -i 's#@PARTITION_EXTENDED_ENABLED@#true#g' ${IMAGE_ROOTFS}/usr/bin/auto-resize-partition.sh
#    sed -i 's#@PARTITION_EXTENDED@#4#g' ${IMAGE_ROOTFS}/usr/bin/auto-resize-partition.sh
#    sed -i 's#@DISABLE_SERVICE_LOCATION@#/data/etc#g' ${IMAGE_ROOTFS}/usr/bin/auto-resize-partition.sh
#    sed -i 's#@DISABLE_SERVICE_LOCATION@#/data/etc#g' ${IMAGE_ROOTFS}/lib/systemd/system/auto-resize-partition.service
#}

# ROOTFS_POSTPROCESS_COMMAND += "update_auto_resize;"