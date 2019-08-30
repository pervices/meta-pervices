DESCRIPTION = "Pervices Crimson TNG firmware source code"
AUTHOR = "Shiqi Feng <shiqi.f@pervices.com>"
SECTION = "common"
PR="r0"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM="file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"
DEPENDS_${PN} = ""
RDEPENDS_${PN} = ""
SRC_URI = "file://build/"
SRC_URI += "file://lib/systemd/system/crimson-server.service"
SRC_URI += "file://usr/src/debug/${PN}/update.sh"
SRC_URI += "file://.ci_info/gitversion"

S = "${WORKDIR}/build"

inherit systemd
inherit autotools

INSANE_SKIP_${PN} = "ldflags"

FILES_${PN} += "${bindir} ${sysconfdir}/crimson ${systemd_unitdir}/system ${D}${prefix}/src/debug/${PN}/${PV}-${PR}/build "

SYSTEMD_SERVICE_${PN} = "crimson-server.service "

do_install() {
	install -d -m 0755 ${D}${bindir}
	install -d -m 0755 ${D}${sysconfdir}/crimson/
	install -d -m 0755 ${D}${prefix}/src/debug/${PN}/${PV}-${PR}/build
	
    install -m 0755 -D ${WORKDIR}/build/usr/bin/* ${D}${bindir}
	
    rm -rf ${WORKDIR}/build/usr
	cp -r ${WORKDIR}/build ${D}${prefix}/src/debug/${PN}/${PV}-${PR}/
	
    install -m 0755 -D ${WORKDIR}/usr/src/debug/${PN}/update.sh ${D}${prefix}/src/debug/${PN}/${PV}-${PR}/build/
}

do_install_append() {
	install -d -m 0755 ${D}${systemd_unitdir}/system/
	install -m 0644 -D ${WORKDIR}/lib/systemd/system/crimson-server.service ${D}${systemd_unitdir}/system/
	echo "installed-${PV}" >> ${D}${sysconfdir}/crimson/${PN}
}

do_package_qa() {
	echo "Success"
}
