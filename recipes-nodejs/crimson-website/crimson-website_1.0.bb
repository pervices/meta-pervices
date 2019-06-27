DESCRIPTION = "Pervices Crimson TNG webserver and GUI source code"
AUTHOR = "Shiqi Feng <shiqi.f@pervices.com>"
SECTION = "common"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM="file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"

SRC_URI = "git://github.com/pervices/webserver.git;protocol=git;branch=master \
           file://lib/systemd/system/crimson-website.service \
           file://.ci_info/gitversion \
          "

SRCREV = "master"
BRANCH = "master"

S = "${WORKDIR}/git/crimson"

RDEPENDS_${PN} = "nodejs"

inherit systemd npm-install-global

FILES_${PN} += "${systemd_unitdir}/system ${sysconfdir}/crimson"

SYSTEMD_SERVICE_${PN} = "crimson-website.service \
                        "

do_configure_append() {
    dir=$(pwd)
    cd ${WORKDIR}/git
    git checkout master
    cd $dir
}

do_install_append() {
	install -d -m 0755 ${D}${systemd_unitdir}/system/
	install -d -m 0755 ${D}${sysconfdir}/crimson/
	install -m 0644 -D ${WORKDIR}/lib/systemd/system/crimson-website.service ${D}${systemd_unitdir}/system/
	ln -s /usr/lib/node_modules/crimson-webserver/public/js/jquery-1.11.2.min.js ${D}${libdir}/node_modules/crimson-webserver/public/js/jquery.min.js
	chown -R root ${D}${libdir}/node_modules/crimson-webserver
	echo "${BRANCH}:${SRCREV}" > ${D}${sysconfdir}/crimson/${PN}
}
