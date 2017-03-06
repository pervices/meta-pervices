DESCRIPTION = "Pervices Crimson TNG Miscellaneous files"
AUTHOR = "Shiqi Feng <shiqi.f@pervices.com>"
SECTION = "common"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM="file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"
DEPENDS_${PN} = "bash"
RDEPENDS_${PN} = "bash"
SRC_URI = "file://preloader-mkpimage.bin \
           file://socfpga.dtb \
           file://u-boot.scr \
           file://update.sh \
	  "
FILES_${PN} += "/home/root/pv_miscellaneous/ ${sysconfdir}/crimson"

do_install() {
	install -d -m 0755 ${D}/home/root/pv_miscellaneous/
	install -d -m 0755 ${D}${sysconfdir}/crimson/

	install -m 0644 -D ${WORKDIR}/preloader-mkpimage.bin ${D}/home/root/pv_miscellaneous/
	install -m 0644 -D ${WORKDIR}/socfpga.dtb ${D}/home/root/pv_miscellaneous/
	install -m 0644 -D ${WORKDIR}/u-boot.scr ${D}/home/root/pv_miscellaneous/
	install -m 0755 -D ${WORKDIR}/update.sh ${D}/home/root/pv_miscellaneous/

	chown -R root ${D}/home/root/pv_miscellaneous/
        chgrp -R root ${D}/home/root/pv_miscellaneous/
}

do_install_append() {
	echo "shipped-${PV}" >> ${D}${sysconfdir}/crimson/${PN}
}
