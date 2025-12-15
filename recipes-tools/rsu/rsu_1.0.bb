SUMMARY = "Intel Remote System Update Tool"
HOMEPAGE = "https://github.com/altera-opensource/intel-rsu"
SECTION = "intel-rsu"
LICENSE = "GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://etc/qspi.rc;md5=4bc6d60efb3e09b02fee0d2ff2a52152"

DEPENDS = "zlib"
RDEPENDS:${PN} = "bash"

PV = "1.0.0"

S = "${WORKDIR}/git"

SRC_URI = "git://github.com/altera-opensource/intel-rsu.git;protocol=http;branch=master;protocol=https \
           file://0001-intel-rsu-implement-automake-for-intel-rsu-package.patch \
           file://qspi.sh \
           "
SRCREV = "master"

inherit autotools

EXTRA_OEMAKE = 'CROSS_COMPILE=${TARGET_PREFIX} CC="${CC} ${TOOLCHAIN_OPTIONS}"'
EXTRA_OEMAKE += 'HOSTCC="${BUILD_CC} ${BUILD_CFLAGS} ${BUILD_LDFLAGS}"'

do_install:append () {
    install -d ${D}/etc
    install -m 755 ${S}/etc/qspi.rc ${D}/etc/librsu.rc
    install -m 755 -d ${D}${includedir}
    install -m 755 ${S}/lib/*.h ${D}${includedir}
    install -m 0755 -D ${WORKDIR}/qspi.sh ${D}${bindir}
}
