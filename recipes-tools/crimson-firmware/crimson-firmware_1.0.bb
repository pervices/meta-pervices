DESCRIPTION = "Pervices Crimson TNG firmware source code"
AUTHOR = "Shiqi Feng <shiqi.f@pervices.com>"
SECTION = "common"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM="file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"
DEPENDS_${PN} = ""
RDEPENDS_${PN} = ""
SRC_URI = "git://github.com/pervices/firmware.git;protocol=git;branch=master-testing \
           file://lib/systemd/system/crimson-server.service \
          "
SRCREV = "7ca223894798ae28b232c0431e052807185fc625"

inherit systemd

INSANE_SKIP_${PN} = "ldflags"

FILES_${PN} += "${bindir} ${sysconfdir}/crimson ${systemd_unitdir}/system "

SYSTEMD_SERVICE_${PN} = "crimson-server.service \
                        "

do_compile() {
	make -C ${WORKDIR}/git all
}

do_install() {
	install -d -m 0755 ${D}${bindir}
	install -d -m 0755 ${D}${sysconfdir}/crimson/
	install -m 0755 -D ${WORKDIR}/git/out/bin/* ${D}${bindir}
}

do_install_append() {
	install -d -m 0755 ${D}${systemd_unitdir}/system/
	install -m 0644 -D ${WORKDIR}/lib/systemd/system/crimson-server.service ${D}${systemd_unitdir}/system/
	echo "installed-${PV}" >> ${D}${sysconfdir}/crimson/${PN}
}
