DESCRIPTION = "Pervices Crimson TNG firmware source code"
AUTHOR = "Shiqi Feng <shiqi.f@pervices.com>"
SECTION = "common"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM="file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"
DEPENDS_${PN} = ""
RDEPENDS_${PN} = ""
SRC_URI = "git://github.com/pervices/firmware.git;protocol=git;branch=master"
SRC_URI += "file://lib/systemd/system/crimson-server.service"
SRC_URI += "file://usr/src/debug/${PN}/update.sh"
SRCREV = "master"

S = "${WORKDIR}/git"

inherit systemd
inherit autotools

INSANE_SKIP_${PN} = "ldflags"

FILES_${PN} += "${bindir} ${sysconfdir}/crimson ${systemd_unitdir}/system ${D}${prefix}/src/debug/${PN}/${PV}-${PR}/git "

SYSTEMD_SERVICE_${PN} = "crimson-server.service "


do_compile() {
	cd ${WORKDIR}/git
	sh autogen.sh
	./configure --prefix=/usr --host=arm-unknown-linux-gnueabihf
	make
	make DESTDIR=${WORKDIR}/git/ install
}

do_install() {
	install -d -m 0755 ${D}${bindir}
	install -d -m 0755 ${D}${sysconfdir}/crimson/
	install -m 0755 -D ${WORKDIR}/git/usr/bin/* ${D}${bindir}
	install -d -m 0755 ${D}${prefix}/src/debug/${PN}/${PV}-${PR}/git
	rm -r ${WORKDIR}/git/usr
	cp -r ${WORKDIR}/git ${D}${prefix}/src/debug/${PN}/${PV}-${PR}/
	install -m 0755 -D ${WORKDIR}/usr/src/debug/${PN}/update.sh ${D}${prefix}/src/debug/${PN}/${PV}-${PR}/git/
}

do_install_append() {
	install -d -m 0755 ${D}${systemd_unitdir}/system/
	install -m 0644 -D ${WORKDIR}/lib/systemd/system/crimson-server.service ${D}${systemd_unitdir}/system/
	echo "installed-${PV}" >> ${D}${sysconfdir}/crimson/${PN}
}

do_package_qa() {
	echo "Success"
}
