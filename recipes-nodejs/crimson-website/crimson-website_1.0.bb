DESCRIPTION = "Pervices Crimson TNG webserver and GUI source code"
AUTHOR = "Shiqi Feng <shiqi.f@pervices.com>"
SECTION = "common"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM="file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"

SRC_URI = "git://github.com/pervices/webserver.git;protocol=git;branch=master-testing \
           file://lib/systemd/system/crimson-website.service \
          "
SRCREV = "1aa775024abdff7481f973279959baa787b4f461"

S = "${WORKDIR}/git"

RDEPENDS_${PN} = "nodejs"

inherit systemd npm-install-global

FILES_${PN} += "${systemd_unitdir}/system \
               "
SYSTEMD_SERVICE_${PN} = "crimson-website.service \
                        "

do_install_append() {
	install -d -m 0755 ${D}${systemd_unitdir}/system/
	install -m 0644 -D ${WORKDIR}/lib/systemd/system/crimson-website.service ${D}${systemd_unitdir}/system/
	ln -s /usr/lib/node_modules/crimson-webserver/public/js/jquery-1.11.2.min.js ${D}${libdir}/node_modules/crimson-webserver/public/js/jquery.min.js
	chown -R root ${D}${libdir}/node_modules/crimson-webserver
}
