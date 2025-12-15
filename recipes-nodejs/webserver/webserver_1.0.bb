DESCRIPTION = "Pervices SDR webserver and GUI source code"
AUTHOR = "Shiqi Feng <shiqi.f@pervices.com>"
SECTION = "common"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM="file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"

COMPATIBLE_MACHINE = '(crimson|cyan|stratix10|arria5)'

SRC_URI:prepend = "git://github.com/pervices/webserver.git;protocol=https;branch=master \
                   "
SRC_URI:crimson += "file://crimson-website.service \
                    file://crimson-sdr.conf \
                   "
SRC_URI:cyan += "file://cyan-website.service \
                 file://cyan-sdr.conf \
                "
SRCREV = "master"
BRANCH = "master"
S = "${WORKDIR}/git/${MACHINE}"
RDEPENDS:${PN} = "nodejs"
FILES:${PN} += "${systemd_unitdir}/system ${sysconfdir}"
SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE:${PN}:crimson = "crimson-website.service"
SYSTEMD_AUTO_ENABLE:${PN} = "enable"

#SYSTEMD_SERVICE:${PN}_${MACHINE} = "${MACHINE}-website.service"
#SYSTEMD_SERVICE:${PN}:cyan = "cyan-webserver.service"
inherit systemd npm allarch
do_install:append() {
	install -d -m 0755 ${D}${systemd_unitdir}/system/ ${D}${systemd_unitdir}/
	install -m 0644 -D ${WORKDIR}/${MACHINE}-website.service ${D}${systemd_unitdir}/system/
	#install -m 0644 -D ${WORKDIR}/${MACHINE}-sdr.conf ${D}${sysconfdir}/sdr.conf
	ln -s /usr/lib/node_modules/${MACHINE}-webserver/public/js/jquery-1.11.2.min.js ${D}${libdir}/node_modules/${MACHINE}-webserver/public/js/jquery.min.js
	chown -R root ${D}${libdir}/node_modules/${MACHINE}-webserver
}
