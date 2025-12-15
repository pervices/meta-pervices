DESCRIPTION = "Pervices filesystem overlay"
AUTHOR = "Shiqi Feng <shiqi.f@pervices.com>"
SECTION = "common"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM="file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"
DEPENDS_${PN} = "bash"
RDEPENDS:${PN} = "bash"
SRC_URI = "file://fsoverlay \
           file://fsoverlay.service \
          "
inherit systemd autotools
FILES:${PN} += "${sysconfdir} ${systemd_unitdir}/system /overlay"
#SYSTEMD_SERVICE:${PN} = "fsoverlay.service"

do_compile(){
}

do_install() {
	install -d -m 0755 ${D}${systemd_unitdir}/system/
	install -d -m 0755 ${D}${sysconfdir}/
	install -d -m 0755 ${D}/overlay/
	
	install -m 0644 -D ${WORKDIR}/fsoverlay.service ${D}${systemd_unitdir}/system/
	install -m 0755 -D ${WORKDIR}/fsoverlay ${D}${sysconfdir}/
}
