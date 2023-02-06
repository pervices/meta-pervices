SUMMARY = "Intel Remote System Update Tool"
HOMEPAGE = "https://github.com/altera-opensource/intel-rsu"
SECTION = "intel-rsu"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://etc/qspi.rc;md5=11ed0a5e56ff53304b1e6192fde53286"

DEPENDS = "zlib"
RDEPENDS_${PN} = "bash"

PV = "1.0.0"

S = "${WORKDIR}/git"

SRC_URI = "git://github.com/altera-opensource/intel-rsu.git;protocol=http;branch=master \
           file://0001-intel-rsu-implement-automake-for-intel-rsu-package.patch \
           file://qspi.sh \
           "
SRCREV = "a9e07539bb5cda0305e84e0d62654bfb0fb28f01"

inherit autotools

EXTRA_OEMAKE = 'CROSS_COMPILE=${TARGET_PREFIX} CC="${CC} ${TOOLCHAIN_OPTIONS}"'
EXTRA_OEMAKE += 'HOSTCC="${BUILD_CC} ${BUILD_CFLAGS} ${BUILD_LDFLAGS}"'

do_install_append () {
    install -d ${D}/etc
    install -m 755 ${S}/etc/qspi.rc ${D}/etc/librsu.rc
    install -m 755 -d ${D}${includedir}
    install -m 755 ${S}/lib/*.h ${D}${includedir}
    install -m 0755 -D ${WORKDIR}/qspi.sh ${D}${bindir} 
}
