DESCRIPTION = "Pervices Cyan  mcu hex files"
AUTHOR = "Shiqi Feng <shiqi.f@pervices.com>"
SECTION = "common"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM="file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"
DEPENDS_${PN} = "bash"
RDEPENDS_${PN} = "bash"
SRC_URI = "file://flash \
           file://burn.sh \
           file://tate-rx.hex \
           file://TATE_RX-xboot-boot.hex \
           file://tate-tx.hex \
           file://TATE_TX-xboot-boot.hex \
           file://tate-synth.hex \
           file://TATE_SYNTH-xboot-boot.hex \
           "
FILES_${PN} += "${base_libdir}/mcu/"

do_install() {
	install -d -m 0755 ${D}${base_libdir}/mcu/

	install -m 0744 -D ${WORKDIR}/flash.sh ${D}${base_libdir}/mcu/
	install -m 0744 -D ${WORKDIR}/burn-on-host-only.sh ${D}${base_libdir}/mcu/
	install -m 0644 -D ${WORKDIR}/tate-rx.hex ${D}${base_libdir}/mcu/
	install -m 0644 -D ${WORKDIR}/tate-tx.hex ${D}${base_libdir}/mcu/
	install -m 0644 -D ${WORKDIR}/tate-synth.hex ${D}${base_libdir}/mcu/
	install -m 0644 -D ${WORKDIR}/TATE_RX-xboot-boot.hex ${D}${base_libdir}/mcu/
	install -m 0644 -D ${WORKDIR}/TATE_TX-xboot-boot.hex ${D}${base_libdir}/mcu/
	install -m 0644 -D ${WORKDIR}/TATE_SYNTH-xboot-boot.hex ${D}${base_libdir}/mcu/

	chown -R root ${D}${base_libdir}/mcu
	chgrp -R 880 ${D}${base_libdir}/mcu
}
