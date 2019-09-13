DESCRIPTION = "Pervices Cyan firmware source code"
AUTHOR = "Shiqi Feng <shiqi.f@pervices.com>"
SECTION = "common"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM="file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"
DEPENDS_${PN} = ""
RDEPENDS_${PN} = ""
SRC_URI = "git://github.com/pervices/firmware.git;protocol=git;branch=master-testing"
SRC_URI += "file://lib/systemd/system/cyan-server.service \
            file://usr/src/debug/${PN}/update.sh \
            file://usr/bin/uart-debugger.sh \
            file://lib/systemd/system/cyan-uart-debugger.service \
            file://lib/systemd/system/cyan-post.service \
            file://etc/cyan/stty.settings \
           "
SRCREV = "master-testing"

S = "${WORKDIR}/git"

inherit systemd
inherit autotools

INSANE_SKIP_${PN} = "ldflags"

FILES_${PN} += "${bindir} ${sysconfdir}/cyan ${systemd_unitdir}/system ${D}${prefix}/src/debug/${PN}/${PV}-${PR}/git "

SYSTEMD_SERVICE_${PN} = "cyan-server.service cyan-uart-debugger.service cyan-post.service"


do_compile() {
	cd ${WORKDIR}/git
	#sed -i 's/DVAUNT/DTATE/g' configure.ac
	sh autogen.sh
	./configure --prefix=/usr --host=arm-unknown-linux-gnueabihf
	make
	make DESTDIR=${WORKDIR}/git/ install
	${CC} script/memtool.c -o script/memtool
	${CC} script/pll_check.c -o script/pll_check
}

do_install() {
	install -d -m 0755 ${D}${bindir}
	install -d -m 0755 ${D}${sysconfdir}/cyan/
	install -m 0755 -D ${WORKDIR}/git/usr/bin/* ${D}${bindir}
	install -m 0755 -D ${WORKDIR}/usr/bin/uart-debugger.sh ${D}${bindir}
	install -d -m 0755 ${D}${prefix}/src/debug/${PN}/${PV}-${PR}/git
	rm -r ${WORKDIR}/git/usr
	cp -r ${WORKDIR}/git ${D}${prefix}/src/debug/${PN}/${PV}-${PR}/
	install -m 0755 -D ${WORKDIR}/usr/src/debug/${PN}/update.sh ${D}${prefix}/src/debug/${PN}/${PV}-${PR}/git/
	install -m 0755 -D ${WORKDIR}/git/script/gpio_control ${D}${bindir}
	install -m 0755 -D ${WORKDIR}/git/script/memtool ${D}${bindir}
	install -m 0755 -D ${WORKDIR}/git/script/rfe_control ${D}${bindir}
	install -m 0755 -D ${WORKDIR}/git/script/prog_primer ${D}${bindir}
	install -m 0755 -D ${WORKDIR}/git/script/fpga_image_status ${D}${bindir}
	install -m 0755 -D ${WORKDIR}/git/script/pll_check ${D}${bindir}
	install -m 0644 -D ${WORKDIR}/etc/cyan/stty.settings ${D}${sysconfdir}/cyan/
}

do_install_append() {
	install -d -m 0755 ${D}${systemd_unitdir}/system/
	install -m 0644 -D ${WORKDIR}/lib/systemd/system/cyan-server.service ${D}${systemd_unitdir}/system/
	install -m 0644 -D ${WORKDIR}/lib/systemd/system/cyan-uart-debugger.service ${D}${systemd_unitdir}/system/
	install -m 0644 -D ${WORKDIR}/lib/systemd/system/cyan-post.service ${D}${systemd_unitdir}/system/
	echo "installed-${PV}" >> ${D}${sysconfdir}/cyan/${PN}
}

do_package_qa() {
	echo "Success"
}
