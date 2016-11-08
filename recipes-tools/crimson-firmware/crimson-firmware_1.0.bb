DESCRIPTION = "Pervices Crimson TNG firmware source code"
AUTHOR = "Shiqi Feng <shiqi.f@pervices.com>"
SECTION = "common"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM="file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"
DEPENDS = ""

SRC_URI = "git://github.com/pervices/firmware.git;protocol=git;branch=master-testing"
SRCREV = "b64660f447904641ad7b9bffd9bc8a02412a3c6c"
INSANE_SKIP_${PN} = "ldflags"
FILES_${PN} = "${bindir} ${sysconfdir}"

do_compile() {
	make -C ${WORKDIR}/git all
}

do_install() {
	install -d -m 0755 ${D}${bindir}
	install -d -m 0755 ${D}${sysconfdir}/version
	install -m 0755 -D ${WORKDIR}/git/out/bin/* ${D}${bindir}
}

do_install_append() {
	echo "installed-${PV}" >> ${D}${sysconfdir}/version/${PN}
}
