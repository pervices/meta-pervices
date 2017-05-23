DESCRIPTION = "Pervices Crimson TNG default script"
AUTHOR = "Shiqi Feng <shiqi.f@pervices.com>"
SECTION = "common"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM="file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"
DEPENDS_${PN} = "bash"
RDEPENDS_${PN} = "bash"
SRC_URI = "file://etc/crimson/startup \
           file://lib/systemd/system/crimson-startup.service \
          "
inherit systemd autotools
FILES_${PN} += "${bindir} ${sysconfdir} ${systemd_unitdir}/system ${base_libdir}"
SYSTEMD_SERVICE_${PN} = "crimson-startup.service"
do_compile() {
}
do_install() {
	install -d -m 0755 ${D}${systemd_unitdir}/system/
	install -d -m 0755 ${D}${sysconfdir}/crimson/
	
	install -m 0644 -D ${WORKDIR}/lib/systemd/system/*.service ${D}${systemd_unitdir}/system/
	install -m 0744 -D ${WORKDIR}/etc/crimson/startup ${D}${sysconfdir}/crimson/
}
