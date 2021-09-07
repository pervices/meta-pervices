SUMMARY = "Lightweight and minimal dumb-terminal emulation program"
SECTION = "console/utils"
LICENSE = "GPLv2+"
HOMEPAGE = "http://code.google.com/p/picocom/"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=393a5ca445f6965873eca0259a17f833"

SRC_URI = "https://github.com/npat-efault/picocom.git"

SRC_URI[md5sum] = "8f3079cb080d20aaef16a3a387dc41b9"
SRC_URI[sha256sum] = "c174a9781f0cc636740e41de307a16e1888a92d94c4923681eb3c08eb6cc6fb2"


EXTRA_OEMAKE = "'CC=${CC}' 'LD=${LD}' 'VERSION=${PV}' \
		'CFLAGS=${CFLAGS}' 'LDFLAGS=${LDFLAGS}' "

do_install () {
    install -d ${D}${bindir}
    install -m 0755 ${BPN} pcasc pcxm pcym pczm ${D}${bindir}/
}
