DESCRIPTION = "Pervices Crimson TNG FPGA image"
AUTHOR = "Shiqi Feng <shiqi.f@pervices.com>"
SECTION = "common"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM="file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"
DEPENDS_${PN} = "bash"
RDEPENDS_${PN} = "bash"
SRC_URI = "file://soc_system.rbf \
	   file://update.sh \
	   file://update.dtb \
           file://preloader-mkpimage.bin \
          "
FILES_${PN} += "${base_libdir}/firmware/ ${sysconfdir}/crimson /preloader"

inherit deploy

do_install() {
	install -d -m 0755 ${D}${base_libdir}/firmware/
	install -d -m 0755 ${D}${sysconfdir}/crimson/

	install -m 0644 -D ${WORKDIR}/soc_system.rbf ${D}${base_libdir}/firmware/
	install -m 0744 -D ${WORKDIR}/update.sh ${D}${base_libdir}/firmware/
	install -m 0644 -D ${WORKDIR}/update.dtb ${D}${base_libdir}/firmware/

	chown -R root ${D}${base_libdir}/firmware
	chgrp -R 880 ${D}${base_libdir}/firmware

	install -d ${D}/preloader
	install -m 0644 ${WORKDIR}/preloader-mkpimage.bin ${D}/preloader/
}

do_install_append() {
	echo "shipped-${PV}" >> ${D}${sysconfdir}/crimson/${PN}
}

do_deploy() {
	install -d ${DEPLOYDIR}
	cp ${D}/preloader/preloader-mkpimage.bin ${DEPLOYDIR}
}
addtask deploy after do_install
