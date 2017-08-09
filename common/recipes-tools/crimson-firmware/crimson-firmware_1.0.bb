DESCRIPTION = "Pervices SOC code"
AUTHOR = "Shiqi Feng <shiqi.f@pervices.com>"
SECTION = "common"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM="file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"
DEPENDS = ""

SRC_URI = "git://github.com/cfriedtpv/firmware.git;protocol=git;branch=cfriedt-testing"
SRCREV = "cecc95e8ac03877e0c58d99faf74851124f6e623"
INSANE_SKIP_${PN} = "ldflags"
FILES_${PN} = "${bindir}"

do_compile() {
	make -C ${WORKDIR}/git all CC="'${CC}'"
}

do_install() {
	install -d -m 0755 ${D}${bindir}
	install -m 0755 -D ${WORKDIR}/git/out/bin/* ${D}${bindir}
}
