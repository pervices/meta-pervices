DESCRIPTION = "Pervices SOC code"
AUTHOR = "Shiqi Feng <shiqi.f@pervices.com>"
SECTION = "common"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM="file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"
DEPENDS = ""

SRC_URI = "git://github.com/pervices/firmware.git;protocol=git;branch=master"
SRCREV = "68089bcf55bf31d24270e3425f9e9ea17125593b"
INSANE_SKIP_${PN} = "ldflags"
FILES_${PN} = "${bindir}"

do_compile() {
	make -C ${WORKDIR}/git all
}

do_install() {
	install -d -m 0755 ${D}${bindir}
	install -m 0755 -D ${WORKDIR}/git/out/bin/* ${D}${bindir}
}
