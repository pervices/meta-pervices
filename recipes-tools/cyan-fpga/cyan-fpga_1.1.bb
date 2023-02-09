DESCRIPTION = "Pervices Cyan  FPGA image"
AUTHOR = "Shiqi Feng <shiqi.f@pervices.com>"
SECTION = "common"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM="file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"
DEPENDS_${PN} = "bash"
RDEPENDS_${PN} = "bash"
SRC_URI = "file://soc_system.rpd \
	   file://update.sh \
	   file://get_chipid_temperature.sh \
	   file://jesd_rst_status.sh \
	   file://test_ddr.sh \
          "
FILES_${PN} += "${base_libdir}/firmware/ ${bindir}"

do_install() {
	install -d -m 0755 ${D}${base_libdir}/firmware/
	install -d -m 0755 ${D}${bindir}

	install -m 0644 -D ${WORKDIR}/soc_system.rpd ${D}${base_libdir}/firmware/
	install -m 0744 -D ${WORKDIR}/update.sh ${D}${base_libdir}/firmware/
	install -m 0755 -D ${WORKDIR}/get_chipid_temperature.sh ${D}${bindir}
	install -m 0755 -D ${WORKDIR}/jesd_rst_status.sh ${D}${bindir}
	install -m 0755 -D ${WORKDIR}/test_ddr.sh ${D}${bindir}


	chown -R root ${D}${base_libdir}/firmware
	chgrp -R 880 ${D}${base_libdir}/firmware
}
