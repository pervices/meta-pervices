DESCRIPTION = "Pervices Crimson TNG firmware source code"
AUTHOR = "Shiqi Feng <shiqi.f@pervices.com>"
SECTION = "common"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM="file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"
SRC_URI_crimson = "git://github.com/pervices/firmware.git;protocol=https;branch=master \
                   file://crimson-server.service \
                  "
SRC_URI_cyan = "git://github.com/pervices/firmware.git;protocol=https;branch=master-testing \
                file://post.service \
                file://cyan-server.service \
               "
SRCREV_crimson = "master"
SRCREV_cyan = "master-testing"

S = "${WORKDIR}/git"

inherit systemd
inherit autotools

INSANE_SKIP_${PN} = "ldflags"
FILES_${PN} += "${bindir} ${systemd_unitdir}/system ${D}${prefix}/src/debug/${PN}/${PV}-${PR}/git "

SYSTEMD_SERVICE_${PN}_crimson = "crimson-server.service"
SYSTEMD_SERVICE_${PN}_cyan = "cyan-server.service post.service"

do_configure_cyan() {
        cd ${WORKDIR}/git
        ./autogen.sh clean
        ./autogen.sh 
}

do_configure_crimson() {
        cd ${WORKDIR}/git
        ./autogen.sh clean
	./autogen.sh
        ./configure --prefix=/usr --host=arm-unknown-linux-gnueabihf
}

do_compile_crimson() {
	cd ${WORKDIR}/git
	make
	make DESTDIR=${WORKDIR}/git/ install
}

do_compile_cyan() {
        cd ${WORKDIR}/git
        make
        ${CC} script/memtool.c -o script/memtool
        ${CC} script/pll_check.c -o script/pll_check
        git describe --always --dirty --long >> ${PN}
}

do_install_crimson() {
	install -d -m 0755 ${D}${bindir}
	install -m 0755 -D ${WORKDIR}/git/server ${D}${bindir}
	install -m 0755 -D ${WORKDIR}/git/mem ${D}${bindir}
	install -m 0755 -D ${WORKDIR}/git/mcu ${D}${bindir}
	install -d -m 0755 ${D}${prefix}/src/debug/${PN}/${PV}-${PR}/git
	cp -r ${WORKDIR}/git ${D}${prefix}/src/debug/${PN}/${PV}-${PR}/
	install -d -m 0755 ${D}${systemd_unitdir}/system/
        install -m 0644 -D ${WORKDIR}/crimson-server.service ${D}${systemd_unitdir}/system/
}

do_install_cyan() {
        install -d -m 0755 ${D}${bindir}
        install -m 0755 -D ${WORKDIR}/git/server ${D}${bindir}
        install -m 0755 -D ${WORKDIR}/git/mem ${D}${bindir}
        install -m 0755 -D ${WORKDIR}/git/mcu ${D}${bindir}
        install -d -m 0755 ${D}${prefix}/src/debug/${PN}/${PV}-${PR}/git
        cp -r ${WORKDIR}/git ${D}${prefix}/src/debug/${PN}/${PV}-${PR}/
        install -m 0755 -D ${WORKDIR}/git/script/gpio_control ${D}${bindir}
        install -m 0755 -D ${WORKDIR}/git/script/memtool ${D}${bindir}
        install -m 0755 -D ${WORKDIR}/git/script/rfe_control ${D}${bindir}
        install -m 0755 -D ${WORKDIR}/git/script/prog_primer ${D}${bindir}
        install -m 0755 -D ${WORKDIR}/git/script/fpga_image_status ${D}${bindir}
        install -m 0755 -D ${WORKDIR}/git/script/pll_check ${D}${bindir}
        install -m 0755 -D ${WORKDIR}/git/script/buffer_lvl.sh ${D}${bindir}
        install -d -m 0755 ${D}${systemd_unitdir}/system/
        install -m 0644 -D ${WORKDIR}/lib/systemd/system/cyan-server.service ${D}${systemd_unitdir}/system/
        install -m 0644 -D ${WORKDIR}/lib/systemd/system/post.service ${D}${systemd_unitdir}/system/
}

do_package_qa() {
	echo "Success"
}
