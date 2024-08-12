DESCRIPTION = "Pervices mcu hex files"
AUTHOR = "Shiqi Feng <shiqi.f@pervices.com>"
SECTION = "common"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM="file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"
DEPENDS_${PN} = "bash"
RDEPENDS_${PN} = "bash glibc"
SRC_URI_prepend = "file://burn.sh"
SRC_URI_crimson += "file://flash-crimson \
                    file://vaunt-rx.hex \
                    file://VAUNT_RX-xboot-boot.hex \
                    file://vaunt-tx.hex \
                    file://VAUNT_TX-xboot-boot.hex \
                    file://vaunt-synth.hex \
                    file://VAUNT_SYNTH-xboot-boot.hex \
                   "
SRC_URI_cyan += "file://flash-cyan \
                 file://tate-rx.hex \
                 file://TATE_RX-xboot-boot.hex \
                 file://tate-tx.hex \
                 file://TATE_TX-xboot-boot.hex \
                 file://tate-synth.hex \
                 file://TATE_SYNTH-xboot-boot.hex \
                "
SRC_URI_chestnut += "file://flash-chestnut \
                    file://lily-rx.hex \
                    file://LILY_RX-xboot-boot.hex \
                    file://lily-tx.hex \
                    file://LILY_TX-xboot-boot.hex \
                    file://lily-synth.hex \
                    file://LILY_SYNTH-xboot-boot.hex \
                    "
FILES_${PN} += "${base_libdir}/mcu/"
INSANE_SKIP_${PN} = "ldflags"

do_install_prepend() {
	install -d -m 0755 ${D}${base_libdir}/mcu/
	install -m 0744 -D ${WORKDIR}/flash-${MACHINE} ${D}${base_libdir}/mcu/flash
	install -m 0744 -D ${WORKDIR}/burn.sh ${D}${base_libdir}/mcu/
}

do_install_crimson() {
	install -m 0644 -D ${WORKDIR}/vaunt-rx.hex ${D}${base_libdir}/mcu/
	install -m 0644 -D ${WORKDIR}/vaunt-tx.hex ${D}${base_libdir}/mcu/
	install -m 0644 -D ${WORKDIR}/vaunt-synth.hex ${D}${base_libdir}/mcu/
	install -m 0644 -D ${WORKDIR}/VAUNT_RX-xboot-boot.hex ${D}${base_libdir}/mcu/
	install -m 0644 -D ${WORKDIR}/VAUNT_TX-xboot-boot.hex ${D}${base_libdir}/mcu/
	install -m 0644 -D ${WORKDIR}/VAUNT_SYNTH-xboot-boot.hex ${D}${base_libdir}/mcu/
}

do_install_cyan() {
        install -m 0644 -D ${WORKDIR}/tate-rx.hex ${D}${base_libdir}/mcu/
        install -m 0644 -D ${WORKDIR}/tate-tx.hex ${D}${base_libdir}/mcu/
        install -m 0644 -D ${WORKDIR}/tate-synth.hex ${D}${base_libdir}/mcu/
        install -m 0644 -D ${WORKDIR}/TATE_RX-xboot-boot.hex ${D}${base_libdir}/mcu/
        install -m 0644 -D ${WORKDIR}/TATE_TX-xboot-boot.hex ${D}${base_libdir}/mcu/
        install -m 0644 -D ${WORKDIR}/TATE_SYNTH-xboot-boot.hex ${D}${base_libdir}/mcu/
}

do_install_chestnut() {
        install -m 0644 -D ${WORKDIR}/lily-rx.hex ${D}${base_libdir}/mcu/
        install -m 0644 -D ${WORKDIR}/lily-tx.hex ${D}${base_libdir}/mcu/
        install -m 0644 -D ${WORKDIR}/lily-synth.hex ${D}${base_libdir}/mcu/
        install -m 0644 -D ${WORKDIR}/LILY_RX-xboot-boot.hex ${D}${base_libdir}/mcu/
        install -m 0644 -D ${WORKDIR}/LILY_TX-xboot-boot.hex ${D}${base_libdir}/mcu/
        install -m 0644 -D ${WORKDIR}/LILY_SYNTH-xboot-boot.hex ${D}${base_libdir}/mcu/
}

do_install_append() {
	chown -R root ${D}${base_libdir}/mcu
	chgrp -R 880 ${D}${base_libdir}/mcu
}
