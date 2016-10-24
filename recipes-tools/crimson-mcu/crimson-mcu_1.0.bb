DESCRIPTION = "Pervices Crimson TNG mcu hex files"
AUTHOR = "Shiqi Feng <shiqi.f@pervices.com>"
SECTION = "common"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM="file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"
DEPENDS = ""

SRC_URI = "file://flash.sh \
           file://rx.hex \
           file://RX-xboot-boot.hex \
           file://tx.hex \
           file://TX-xboot-boot.hex \
           file://synth.hex \
           file://SYNTH-xboot-boot.hex \
           "

FILES_${PN} = "home/root/pv_mcu/"

do_install() {
	install -d -m 0755 ${D}/home/root/pv_mcu/
	install -m 0755 -D ${WORKDIR}/flash.sh ${D}/home/root/pv_mcu/
	install -m 0644 -D ${WORKDIR}/rx.hex ${D}/home/root/pv_mcu/
	install -m 0644 -D ${WORKDIR}/tx.hex ${D}/home/root/pv_mcu/
	install -m 0644 -D ${WORKDIR}/synth.hex ${D}/home/root/pv_mcu/
	install -m 0644 -D ${WORKDIR}/RX-xboot-boot.hex ${D}/home/root/pv_mcu/
	install -m 0644 -D ${WORKDIR}/TX-xboot-boot.hex ${D}/home/root/pv_mcu/
	install -m 0644 -D ${WORKDIR}/SYNTH-xboot-boot.hex ${D}/home/root/pv_mcu/
}
