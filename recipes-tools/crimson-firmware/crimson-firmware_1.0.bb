DESCRIPTION = "Pervices Crimson TNG firmware source code"
AUTHOR = "Shiqi Feng <shiqi.f@pervices.com>"
SECTION = "common"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM="file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"
DEPENDS_${PN} = ""
RDEPENDS_${PN} = ""
SRC_URI = "git://github.com/pervices/firmware.git;protocol=git;branch=master-testing \
           file://lib/systemd/system/crimson-server.service \
           file://usr/src/debug/${PN}/update.sh \
          "
SRCREV = "759942ef2d65f18ea4aaaae9096414e0a91a7529"

inherit systemd

INSANE_SKIP_${PN} = "ldflags"

FILES_${PN} += "${bindir} ${sysconfdir}/crimson ${systemd_unitdir}/system ${D}${prefix}/src/debug/${PN} "

SYSTEMD_SERVICE_${PN} = "crimson-server.service "

#do_compile() {
#	make -C ${WORKDIR}/git all
#}

do_install() {
	#install -d -m 0755 ${D}${bindir}
	install -d -m 0755 ${D}${sysconfdir}/crimson/
	#install -m 0755 -D ${WORKDIR}/git/out/bin/* ${D}${bindir}
	install -d -m 0755 ${D}${prefix}/src/debug/${PN}
	install -m 0755 -D ${WORKDIR}/usr/src/debug/${PN}/update.sh ${D}${prefix}/src/debug/${PN}/
	cp -r ${WORKDIR}/git/* ${D}${prefix}/src/debug/${PN}/
}

do_install_append() {
	install -d -m 0755 ${D}${systemd_unitdir}/system/
	install -m 0644 -D ${WORKDIR}/lib/systemd/system/crimson-server.service ${D}${systemd_unitdir}/system/
	echo "installed-${PV}" >> ${D}${sysconfdir}/crimson/${PN}
}
