DESCRIPTION = "Pervices Crimson TNG filesystem overlay"
AUTHOR = "Shiqi Feng <shiqi.f@pervices.com>"
SECTION = "common"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM="file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"
DEPENDS_${PN} = "bash"
RDEPENDS_${PN} = "bash"
SRC_URI = "file://etc/crimson/fsoverlay \
           file://lib/systemd/system/crimson-fsoverlay.service \
          "
inherit systemd autotools
FILES_${PN} += "${sysconfdir} ${systemd_unitdir}/system /overlay"
SYSTEMD_SERVICE_${PN} = "crimson-fsoverlay.service"

do_install() {
	install -d -m 0755 ${D}${systemd_unitdir}/system/
	install -d -m 0755 ${D}${sysconfdir}/crimson/
	install -d -m 0755 ${D}/overlay
	
	install -m 0644 -D ${WORKDIR}/lib/systemd/system/crimson-fsoverlay.service ${D}${systemd_unitdir}/system/
	install -m 0755 -D ${WORKDIR}/etc/crimson/fsoverlay ${D}${sysconfdir}/crimson/
}
