DESCRIPTION = "Pervices Crimson TNG webserver and GUI source code"
AUTHOR = "Shiqi Feng <shiqi.f@pervices.com>"
SECTION = "common"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM="file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"
DEPENDS = ""

SRC_URI = "git://github.com/pervices/webserver.git;protocol=git;branch=master-testing"
SRCREV = "8b8eefe90bb5c8e33d4ddd52cc8c9199fdc65c0d"
INSANE_SKIP_${PN} = "arch"
FILES_${PN} = "home/root/pv_web/ ${sysconfdir}"

do_install() {
	install -d -m 0755 ${D}/home/root/pv_web/
	install -d -m 0755 ${D}${sysconfdir}/version
	cp -r ${WORKDIR}/git/* ${D}/home/root/pv_web/
	find ${D}/home/root/pv_web -type f -exec chmod 644 {} \;
	find ${D}/home/root/pv_web -type d -exec chmod 755 {} \;
}

do_install_append() {
	echo "installed-${PV}" >> ${D}${sysconfdir}/version/${PN}
}
