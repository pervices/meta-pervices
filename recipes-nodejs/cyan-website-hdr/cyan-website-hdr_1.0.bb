DESCRIPTION = "Pervices Cyan  webserver and GUI source code"
AUTHOR = "Shiqi Feng <shiqi.f@pervices.com>"
SECTION = "common"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM="file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"

SRC_URI = "git://github.com/pervices/webserver.git;protocol=git;branch=master \
           file://lib/systemd/system/cyan-website-hdr.service \
           file://lib/systemd/system/cyan-hdr.service \
           file://usr/bin/hdr_gpio_symlinks.sh \
          "
SRCREV = "master"
BRANCH = "master"

S = "${WORKDIR}/git/webserver-tate-HDR"

DEPENDS += " nodejs"

RDEPENDS_${PN} += " nodejs tcl"

inherit systemd npm-install-global allarch

FILES_${PN} += "${systemd_unitdir}/system ${sysconfdir}/cyan ${bindir}"

SYSTEMD_SERVICE_${PN} = "cyan-website-hdr.service cyan-hdr.service"

do_install_append() {
	install -d -m 0755 ${D}${systemd_unitdir}/system/
	install -d -m 0755 ${D}${sysconfdir}/cyan/
	install -d -m 0755 ${D}${bindir}
	install -m 0644 -D ${WORKDIR}/lib/systemd/system/cyan-website-hdr.service ${D}${systemd_unitdir}/system/
	install -m 0644 -D ${WORKDIR}/lib/systemd/system/cyan-hdr.service ${D}${systemd_unitdir}/system/
	install -m 0755 -D ${WORKDIR}/usr/bin/hdr_gpio_symlinks.sh ${D}${bindir}
	ln -s /usr/lib/node_modules/cyan-webserver-hdr/public/js/jquery-1.11.2.min.js ${D}${libdir}/node_modules/cyan-webserver-hdr/public/js/jquery.min.js
	chown -R root ${D}${libdir}/node_modules/cyan-webserver-hdr
	echo "${BRANCH}:${SRCREV}" > ${D}${sysconfdir}/cyan/${PN}
}
