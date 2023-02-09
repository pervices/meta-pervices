DESCRIPTION = "Pervices Crimson TNG FPGA image"
AUTHOR = "Shiqi Feng <shiqi.f@pervices.com>"
SECTION = "common"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM="file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"
DEPENDS_${PN} = "bash"
RDEPENDS_${PN} = "bash"
SRC_URI = "file://fpga.rbf \
	   file://update.sh \
	   file://update.dtb \
           file://preloader-mkpimage.bin \
          "
FILES_${PN} += "${base_libdir}/firmware/ /preloader"

inherit deploy

do_install() {
	install -d -m 0755 ${D}${base_libdir}/firmware/

	install -m 0644 -D ${WORKDIR}/fpga.rbf ${D}${base_libdir}/firmware/
	install -m 0744 -D ${WORKDIR}/update.sh ${D}${base_libdir}/firmware/
	install -m 0644 -D ${WORKDIR}/update.dtb ${D}${base_libdir}/firmware/

	chown -R root ${D}${base_libdir}/firmware
	chgrp -R 880 ${D}${base_libdir}/firmware
	
	ln -s fpga.rbf ${D}${base_libdir}/firmware/soc_system.rbf
}

do_deploy() {
	install -d ${DEPLOYDIR}
	cp ${WORKDIR}/preloader-mkpimage.bin ${DEPLOYDIR}
	cp ${D}${base_libdir}/firmware/fpga.rbf ${DEPLOYDIR}/soc_system.rbf
}
addtask deploy after do_install
