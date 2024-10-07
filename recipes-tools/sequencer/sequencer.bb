# Originally developed by Analog Device adapted by Per Vices for use with Per Vices SDRs
# See https://www.analog.com/en/products/adm1266.html#documentation

DESCRIPTION = "Pervices SDR sequencer update utility"
SECTION = "common"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM="file://${S}/license.pdf;md5=e3b019d66eb248e73c9b1e5361c4a128"
SRC_URI_cyan = "git://github.com/pervices/sequencer.git;protocol=https;branch=master"
SRCREV_cyan = "master"
PV = "1.0"
S = "${WORKDIR}/git"
EXTRA_OEMAKE = "'CC=${CC}' 'LD=${CC}' 'LDFLAGS=${LDFLAGS}'"

do_install_cyan() {
        install -d -m 0755 ${D}${bindir}
        install -m 0755 -D ${WORKDIR}/git/adm1266_load_fw_config ${D}${bindir}
        install -m 0755 -D ${WORKDIR}/git/adm1266_blackbox ${D}${bindir}
        install -m 0755 -D ${WORKDIR}/git/adm1266_monitor ${D}${bindir}
        install -d -m 0755 ${D}${prefix}/src/debug/${PN}/${PV}-${PR}/git
        cp -r ${WORKDIR}/git ${D}${prefix}/src/debug/${PN}/${PV}-${PR}/
}

do_package_qa() {
        echo "Success"
}
