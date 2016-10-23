DESCRIPTION = "Pervices mcu hex files"
AUTHOR = "Shiqi Feng <shiqi.f@pervices.com>"
SECTION = "common"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM="file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"
DEPENDS = ""

SRC_URI = "file://soc_system.rbf \
           "

FILES_${PN} = "home/root/pv_fpga/"

do_install() {
	install -d -m 0755 ${D}/home/root/pv_fpga/
	install -m 0644 -D ${WORKDIR}/soc_system.rbf ${D}/home/root/pv_fpga/
}
