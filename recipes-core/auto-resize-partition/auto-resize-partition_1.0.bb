SUMMARY = "Automatically resizes the partition to 100% of the available space on the first boot."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI += "file://auto-resize-partition.service file://auto-resize-partition.sh"

inherit update-rc.d systemd

SYSTEMD_SERVICE_${PN} = "auto-resize-partition.service"
INITSCRIPT_NAME = "auto-resize-partition.sh"
INITSCRIPT_PARAMS = "defaults 80"

AUTO_RESIZE_DEVICE ??= "/dev/mmcblk0"
AUTO_RESIZE_PARTITION ??= "2"

do_install() {
    if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
		install -d ${D}${systemd_unitdir}
        install -m 0644 ${WORKDIR}/auto-resize-partition.service ${D}${systemd_unitdir}/

        install -d ${D}/usr/bin
        install -m 0744 ${WORKDIR}/auto-resize-partition.sh ${D}/usr/bin
        sed -i 's#@DEVICE@#${AUTO_RESIZE_DEVICE}#g' ${D}/usr/bin/auto-resize-partition.sh
        sed -i 's#@PARTITION@#${AUTO_RESIZE_PARTITION}#g' ${D}/usr/bin/auto-resize-partition.sh
	else
        install -d ${D}${sysconfdir}/init.d
        install -m 0755    ${WORKDIR}/auto-resize-partition.sh	${D}${sysconfdir}/init.d
        sed -i 's#@DEVICE@#${AUTO_RESIZE_DEVICE}#g' ${D}${sysconfdir}/init.d/auto-resize-partition.sh
        sed -i 's#@PARTITION@#${AUTO_RESIZE_PARTITION}#g' ${D}${sysconfdir}/init.d/auto-resize-partition.sh
    fi
}

FILES_${PN} += "/lib/systemd/system"


RDEPENDS_${PN} += " bash parted e2fsprogs-resize2fs"