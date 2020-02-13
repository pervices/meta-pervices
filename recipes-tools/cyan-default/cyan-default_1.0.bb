DESCRIPTION = "Pervices Cyan  default script"
AUTHOR = "Shiqi Feng <shiqi.f@pervices.com>"
SECTION = "common"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM="file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"
DEPENDS_${PN} = "bash"
RDEPENDS_${PN} = "bash"
SRC_URI = "file://etc/cyan/startup \
           file://lib/systemd/system/cyan-startup.service \
          "
inherit systemd autotools
FILES_${PN} += "${bindir} ${sysconfdir} ${systemd_unitdir}/system ${base_libdir}"
#SYSTEMD_SERVICE_${PN} = "cyan-startup.service"

do_compile(){
}

do_install() {
	install -d -m 0755 ${D}${systemd_unitdir}/system/
	install -d -m 0755 ${D}${sysconfdir}/cyan/
	
	install -m 0644 -D ${WORKDIR}/lib/systemd/system/*.service ${D}${systemd_unitdir}/system/
	install -m 0744 -D ${WORKDIR}/etc/cyan/startup ${D}${sysconfdir}/cyan/
}
