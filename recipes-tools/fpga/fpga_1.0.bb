DESCRIPTION = "Pervices FPGA image"
AUTHOR = "Shiqi Feng <shiqi.f@pervices.com>"
SECTION = "common"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM="file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"
DEPENDS_${PN} = "bash"
RDEPENDS_${PN} = "bash"
SRC_URI_crimson = "file://soc_system_crimson.rbf \
                   file://crimson-update.sh \
                   file://update.dtb \
                  "
SRC_URI_cyan = "file://fpga.rpd \
                file://cyan-update.sh \
                file://get_chipid_temperature.sh \
                file://jesd_rst_status.sh \
                file://test_ddr.sh \
               "
SRC_URI_chestnut =  "file://fpga.rpd \
					file://cyan-update.sh \
                    file://get_chipid_temperature.sh \
                    file://jesd_rst_status.sh \
                    file://test_ddr.sh \
                    "
FILES_${PN}_crimson += "${base_libdir}/firmware/"
FILES_${PN}_cyan += "${base_libdir}/firmware/ ${bindir}"
FILES_${PN}_chestnut += "${base_libdir}/firmware/ ${bindir}"

inherit deploy

do_install_crimson() {
	install -d -m 0755 ${D}${base_libdir}/firmware/
	install -d ${DEPLOYDIR}

        install -m 0644 -D ${WORKDIR}/soc_system_crimson.rbf ${D}${base_libdir}/firmware/
	install -m 0744 -D ${WORKDIR}/crimson-update.sh ${D}${base_libdir}/firmware/update.sh
	install -m 0644 -D ${WORKDIR}/update.dtb ${D}${base_libdir}/firmware/

	chown -R root ${D}${base_libdir}/firmware
	chgrp -R 880 ${D}${base_libdir}/firmware
	
	ln -s soc_system_crimson.rbf ${D}${base_libdir}/firmware/soc_system.rbf
	cp soc_system_crimson.rbf ${DEPLOYDIR}/soc_system.rbf
}

do_install_cyan() {
	install -d -m 0755 ${D}${base_libdir}/firmware/
	install -d -m 0755 ${D}${bindir}

	install -m 0644 -D ${WORKDIR}/fpga.rpd ${D}${base_libdir}/firmware/
	install -m 0744 -D ${WORKDIR}/cyan-update.sh ${D}${base_libdir}/firmware/update.sh
	install -m 0755 -D ${WORKDIR}/get_chipid_temperature.sh ${D}${bindir}
	install -m 0755 -D ${WORKDIR}/jesd_rst_status.sh ${D}${bindir}
	install -m 0755 -D ${WORKDIR}/test_ddr.sh ${D}${bindir}

	chown -R root ${D}${base_libdir}/firmware
	chgrp -R 880 ${D}${base_libdir}/firmware
	
	ln -s fpga.rpd ${D}${base_libdir}/firmware/soc_system.rpd
}

do_install_chestnut() {
	install -d -m 0755 ${D}${base_libdir}/firmware/
	install -d -m 0755 ${D}${bindir}

	install -m 0644 -D ${WORKDIR}/fpga.rpd ${D}${base_libdir}/firmware/
	install -m 0744 -D ${WORKDIR}/cyan-update.sh ${D}${base_libdir}/firmware/update.sh
	install -m 0755 -D ${WORKDIR}/get_chipid_temperature.sh ${D}${bindir}
	install -m 0755 -D ${WORKDIR}/jesd_rst_status.sh ${D}${bindir}
	install -m 0755 -D ${WORKDIR}/test_ddr.sh ${D}${bindir}

	chown -R root ${D}${base_libdir}/firmware
	chgrp -R 880 ${D}${base_libdir}/firmware

	ln -s fpga.rpd ${D}${base_libdir}/firmware/soc_system.rpd
}

do_deploy() {
	install -d ${DEPLOYDIR}
}

addtask deploy after do_install
