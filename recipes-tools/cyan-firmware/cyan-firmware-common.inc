DESCRIPTION = "Pervices Cyan firmware source code"
AUTHOR = "Shiqi Feng <shiqi.f@pervices.com>"
SECTION = "common"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM="file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"
DEPENDS_${PN} = ""
RDEPENDS_${PN} = ""
#RCONFLICTS_${PN} = "cyan-firmware-4r4t-3g cyan-firmware-8r8t-1g"
#FILESPATH =. "${THISDIR}/files:"
SRC_URI = "git://github.com/pervices/firmware.git;protocol=https;branch=master-testing"
SRC_URI += "file://lib/systemd/system/cyan-server.service \
            file://usr/src/debug/cyan-firmware/update.sh \
            file://lib/systemd/system/cyan-post.service \
           "
SRCREV = "master-testing"
S = "${WORKDIR}/git"
inherit systemd
INSANE_SKIP_${PN} = "ldflags"
FILES_${PN} += "${bindir} ${sysconfdir}/cyan ${systemd_unitdir}/system ${D}${prefix}/src/debug/${PN}/${PV}-${PR}/git "
SYSTEMD_SERVICE_${PN} = "cyan-server.service cyan-post.service"

do_configure() {
	cd ${WORKDIR}/git
	./autogen.sh clean
	./autogen.sh 
}

do_compile() {
	cd ${WORKDIR}/git
	make
	${CC} script/memtool.c -o script/memtool
	${CC} script/pll_check.c -o script/pll_check
	git describe --always --dirty --long >> ${PN}
}

do_install() {
	install -d -m 0755 ${D}${bindir}
	install -d -m 0755 ${D}${sysconfdir}/cyan/
	install -m 0644 -D ${WORKDIR}/git/${PN} ${D}${sysconfdir}/cyan/${PN}
	install -m 0755 -D ${WORKDIR}/git/server ${D}${bindir}
	install -m 0755 -D ${WORKDIR}/git/mem ${D}${bindir}
	install -m 0755 -D ${WORKDIR}/git/mcu ${D}${bindir}
	install -d -m 0755 ${D}${prefix}/src/debug/${PN}/${PV}-${PR}/git
	cp -r ${WORKDIR}/git ${D}${prefix}/src/debug/${PN}/${PV}-${PR}/
	install -m 0755 -D ${WORKDIR}/usr/src/debug/cyan-firmware/update.sh ${D}${prefix}/src/debug/${PN}/${PV}-${PR}/git/
	install -m 0755 -D ${WORKDIR}/git/script/gpio_control ${D}${bindir}
	install -m 0755 -D ${WORKDIR}/git/script/memtool ${D}${bindir}
	install -m 0755 -D ${WORKDIR}/git/script/rfe_control ${D}${bindir}
	install -m 0755 -D ${WORKDIR}/git/script/prog_primer ${D}${bindir}
	install -m 0755 -D ${WORKDIR}/git/script/fpga_image_status ${D}${bindir}
	install -m 0755 -D ${WORKDIR}/git/script/pll_check ${D}${bindir}
	install -m 0755 -D ${WORKDIR}/git/script/buffer_lvl.sh ${D}${bindir}
	install -d -m 0755 ${D}${systemd_unitdir}/system/
	install -m 0644 -D ${WORKDIR}/lib/systemd/system/cyan-server.service ${D}${systemd_unitdir}/system/
	install -m 0644 -D ${WORKDIR}/lib/systemd/system/cyan-post.service ${D}${systemd_unitdir}/system/
}

do_package_qa() {
	echo "Success"
}
