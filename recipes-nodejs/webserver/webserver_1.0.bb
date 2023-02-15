DESCRIPTION = "Pervices SDR webserver and GUI source code"
AUTHOR = "Shiqi Feng <shiqi.f@pervices.com>"
SECTION = "common"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM="file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"

SRC_URI_prepend += "git://github.com/pervices/webserver.git;protocol=https;branch=master \
                   "
SRC_URI_crimson += "file://crimson-webserver.service \
                    file://crimson-sdr.conf \
                   "
SRC_URI_cyan += "file://cyan-webserver.service \
                 file://cyan-sdr.conf \
                "
SRCREV = "master"
BRANCH = "master"
S = "${WORKDIR}/git/${MACHINE}"
RDEPENDS_${PN} = "nodejs"
FILES_${PN} += "${systemd_unitdir}/system ${sysconfdir}"
SYSTEMD_SERVICE_${PN}_crimson = "crimson-webserver.service"
#SYSTEMD_SERVICE_${PN}_cyan = "cyan-webserver.service"
inherit systemd npm-install-global allarch
do_install_append() {
	install -d -m 0755 ${D}${systemd_unitdir}/system/
	install -m 0644 -D ${WORKDIR}/${MACHINE}-webserver.service ${D}${systemd_unitdir}/system/
	install -m 0644 -D ${WORKDIR}/${MACHINE}-sdr.conf ${D}${sysconfdir}/sdr.conf
	ln -s /usr/lib/node_modules/webserver/public/js/jquery-1.11.2.min.js ${D}${libdir}/node_modules/webserver/public/js/jquery.min.js
	chown -R root ${D}${libdir}/node_modules/webserver
}
