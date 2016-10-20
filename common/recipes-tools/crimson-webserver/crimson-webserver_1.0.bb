DESCRIPTION = "Pervices webserver and GUI code"
AUTHOR = "Shiqi Feng <shiqi.f@pervices.com>"
SECTION = "common"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM="file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"
DEPENDS = ""

SRC_URI = "git://github.com/pervices/webserver.git;protocol=git;branch=master"
SRCREV = "ba14ea77d1f91d6d93d80d67f2c983bfda8b75f8"
INSANE_SKIP_${PN} = "ldflags"
INSANE_SKIP_${PN} = "arch"

do_install() {
	install -d -m 0755 ${D}/home/root/pv_web/
	cp -r ${WORKDIR}/git/* ${D}/home/root/pv_web/
	find ${D}/home/root/pv_web -type f -exec chmod 644 {} \;
	find ${D}/home/root/pv_web -type d -exec chmod 755 {} \;
}
FILES_${PN} = "home/root/pv_web/"
