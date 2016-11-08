DESCRIPTION = "Pervices Crimson TNG FPGA image"
AUTHOR = "Shiqi Feng <shiqi.f@pervices.com>"
SECTION = "common"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM="file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"
DEPENDS = ""

SRC_URI = "file://soc_system.rbf \
	   file://update.sh \
	   file://update.dtb \
          "

FILES_${PN} = "home/root/pv_fpga/ ${base_libdir} ${sysconfdir}"
RDEPENDS_${PN} = "bash"

do_install() {
	install -d -m 0755 ${D}/home/root/pv_fpga/
	install -d -m 0755 ${D}${base_libdir}/firmware
	install -d -m 0755 ${D}${sysconfdir}/version
	install -m 0644 -D ${WORKDIR}/soc_system.rbf ${D}/home/root/pv_fpga/
	install -m 0755 -D ${WORKDIR}/update.sh ${D}/home/root/pv_fpga/
	install -m 0644 -D ${WORKDIR}/update.dtb ${D}/home/root/pv_fpga/
}
do_install_append() {
	ln -s /home/root/pv_fpga/soc_system.rbf ${D}${base_libdir}/firmware/soc_system.rbf
	ln -s /home/root/pv_fpga/update.dtb ${D}${base_libdir}/firmware/update.dtb
	echo "shipped-${PV}" > ${D}${sysconfdir}/version/${PN}
}
