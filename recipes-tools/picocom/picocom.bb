SUMMARY = "Lightweight and minimal dumb-terminal emulation program"
SECTION = "console/utils"
LICENSE = "GPLv2+"
HOMEPAGE = "http://code.google.com/p/picocom/"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=393a5ca445f6965873eca0259a17f833"

SRC_URI = "https://github.com/npat-efault/picocom.git"

SRC_URI[md5sum] = "2a3796204b1187358cf1cee14c6d6f22"
SRC_URI[sha256sum] = "46f5d46b17b34ebb8ba506e74f73a493fb8a6101d0674a7c3c0e173102c43e96"

EXTRA_OEMAKE = "'CC=${CC}' 'LD=${LD}' 'VERSION=${PV}' \
		'CFLAGS=${CFLAGS}' 'LDFLAGS=${LDFLAGS}' "

do_install () {
    install -d ${D}${bindir}
    install -m 0755 ${BPN} pcasc pcxm pcym pczm ${D}${bindir}/
}
