SUMMARY = "Lightweight and minimal dumb-terminal emulation program"
SECTION = "console/utils"
LICENSE = "GPLv2+"
HOMEPAGE = "http://code.google.com/p/picocom/"
LIC_FILES_CHKSUM = "file://LICENSE.txt"

SRC_URI = "https://github.com/npat-efault/picocom.git"


EXTRA_OEMAKE = "'CC=${CC}' 'LD=${LD}' 'VERSION=${PV}' \
		'CFLAGS=${CFLAGS}' 'LDFLAGS=${LDFLAGS}' "

do_install () {
    install -d ${D}${bindir}
    install -m 0755 ${BPN} pcasc pcxm pcym pczm ${D}${bindir}/
}
