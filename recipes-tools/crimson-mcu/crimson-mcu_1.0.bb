DESCRIPTION = "Pervices Crimson TNG mcu hex files"
AUTHOR = "Shiqi Feng <shiqi.f@pervices.com>"
SECTION = "common"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM="file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"
DEPENDS_${PN} = "bash"
RDEPENDS_${PN} = "bash"
SRC_URI = "file://flash \
           file://burn.sh \
           file://buffer_lvl.sh \
           file://vaunt-rx.hex \
           file://VAUNT_RX-xboot-boot.hex \
           file://vaunt-tx.hex \
           file://VAUNT_TX-xboot-boot.hex \
           file://vaunt-synth.hex \
           file://VAUNT_SYNTH-xboot-boot.hex \
           file://.ci_info/gitversion \
           "
FILES_${PN} += "${base_libdir}/mcu/ ${sysconfdir}/crimson/"

do_install() {
	install -d -m 0755 ${D}${base_libdir}/mcu/
	install -d -m 0755 ${D}${sysconfdir}/crimson/

	install -m 0744 -D ${WORKDIR}/flash ${D}${base_libdir}/mcu/
	install -m 0744 -D ${WORKDIR}/burn.sh ${D}${base_libdir}/mcu/
    install -m 0744 -D ${WORKDIR}/buffer_lvl.sh ${D}${base_libdor}/mcu/
	install -m 0644 -D ${WORKDIR}/vaunt-rx.hex ${D}${base_libdir}/mcu/
	install -m 0644 -D ${WORKDIR}/vaunt-tx.hex ${D}${base_libdir}/mcu/
	install -m 0644 -D ${WORKDIR}/vaunt-synth.hex ${D}${base_libdir}/mcu/
	install -m 0644 -D ${WORKDIR}/VAUNT_RX-xboot-boot.hex ${D}${base_libdir}/mcu/
	install -m 0644 -D ${WORKDIR}/VAUNT_TX-xboot-boot.hex ${D}${base_libdir}/mcu/
	install -m 0644 -D ${WORKDIR}/VAUNT_SYNTH-xboot-boot.hex ${D}${base_libdir}/mcu/

	chown -R root ${D}${base_libdir}/mcu
	chgrp -R 880 ${D}${base_libdir}/mcu
}

do_install_append() {
	echo "shipped-${PV}" >> ${D}${sysconfdir}/crimson/${PN}
}
