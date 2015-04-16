DESCRIPTION = "Crimson Server, Memory, and UART Tools"
AUTHOR = "Howard Pang <howardpang28@gmail.com>"
SECTION = "common"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM="file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"
DEPENDS = ""

#SRCREV = "56ba5c4550737c485f4dd3a8c1f76797296df0dd"
#SRC_URI = "git://github.com/pervices/meta-pervices.git;protocol=https;branch=master"

SRCREV = "8cd608f42698dcb1eb2d001cd7816d12e43ac6b1"
SRC_URI = "git://github.com/pervices/firmware.git;protocol=https;branch=master"

# This is a hack to bypass a build flow without .configure (inherit autotools)
MY_LDFLAGS = "-lm ${LDFLAGS}"

OUTDIR = "out/bin"
INSTALLDIR = "/usr/bin"
S = "${WORKDIR}/git"

do_compile() {
	make -C ${S} -f Makefile  clean
	make LDFLAGS='${MY_LDFLAGS}' -C ${S} -f Makefile
}

do_install() {
	install -d -m 0755 ${D}${INSTALLDIR}/
	install -m 0755 ${S}/${OUTDIR}/server ${D}${INSTALLDIR}/
	install -m 0755 ${S}/${OUTDIR}/mcu    ${D}${INSTALLDIR}/
	install -m 0755 ${S}/${OUTDIR}/mem    ${D}${INSTALLDIR}/
}
