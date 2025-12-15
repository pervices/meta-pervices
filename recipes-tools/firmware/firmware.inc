DESCRIPTION = "Pervices Crimson TNG firmware source code"
AUTHOR = "Shiqi Feng <shiqi.f@pervices.com>"
SECTION = "common"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM="file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"
SRC_URI:crimson = "git://github.com/pervices/firmware.git;protocol=https;branch=master \
                   file://crimson-server.service \
                  "
SRC_URI:cyan = "git://github.com/pervices/firmware.git;protocol=https;branch=master \
                file://post.service \
                file://cyan-server.service \
               "
SRC_URI:chestnut =  "git://github.com/pervices/firmware.git;protocol=https;branch=master \
                    file://post.service \
                    file://chestnut-server.service \
                    "
SRCREV:crimson = "master"
SRCREV:cyan = "master"
SRCREV:chestnut = "master"

S = "${WORKDIR}/git"

inherit systemd
inherit autotools

CFLAGS:append = " -U_FORTIFY_SOURCE -D_FORTIFY_SOURCE=0"
CXXFLAGS:append = " -U_FORTIFY_SOURCE -D_FORTIFY_SOURCE=0"

INSANE_SKIP:${PN} = "ldflags buildpaths debug-files staticdev"
INSANE_SKIP:${PN}-src = "buildpaths debug-files staticdev"
INSANE_SKIP:${PN}-dev = "buildpaths debug-files staticdev"
INSANE_SKIP:${PN}-staticdev = "buildpaths debug-files staticdev"

FILES_${PN} += "${bindir} ${systemd_unitdir}/system ${D}${prefix}/src/debug/${PN}/${PV}-${PR}/git "

SYSTEMD_SERVICE_${PN}:crimson = "crimson-server.service"
SYSTEMD_SERVICE_${PN}:cyan = "cyan-server.service post.service"
SYSTEMD_SERVICE_${PN}:chestnut = "chestnut-server.service post.service"

do_configure:cyan() {
        cd ${WORKDIR}/git
        ./autogen.sh clean
        ./autogen.sh 
}

do_configure:chestnut() {
        cd ${WORKDIR}/git
        ./autogen.sh clean
        ./autogen.sh
}

do_configure:crimson() {
        cd ${WORKDIR}/git
        ./autogen.sh clean
	    ./autogen.sh
        ./configure --prefix=/usr --host=arm-unknown-linux-gnueabihf
}

do_compile:crimson() {
	cd ${WORKDIR}/git
	make
	make DESTDIR=${WORKDIR}/git/ install
}

do_compile:cyan() {
        cd ${WORKDIR}/git
        make
        ${CC} scripts/tate/memtool.c -o scripts/tate/memtool
        ${CC} scripts/tate/pll_check.c -o scripts/tate/pll_check -DTATE_NRNT
        git describe --always --dirty --long >> ${PN}
}

do_compile:chestnut() {
        cd ${WORKDIR}/git
        make
        ${CC} scripts/tate/memtool.c -o scripts/tate/memtool
        ${CC} scripts/tate/pll_check.c -o scripts/tate/pll_check -DLILY
        git describe --always --dirty --long >> ${PN}
}

do_install:crimson() {
	install -d -m 0755 ${D}${bindir}
	install -m 0755 -D ${WORKDIR}/git/server ${D}${bindir}
	install -m 0755 -D ${WORKDIR}/git/mem ${D}${bindir}
	install -m 0755 -D ${WORKDIR}/git/mcu ${D}${bindir}
	install -d -m 0755 ${D}${prefix}/src/debug/${PN}/${PV}-${PR}/git
	cp -r ${WORKDIR}/git ${D}${prefix}/src/debug/${PN}/${PV}-${PR}/
	install -d -m 0755 ${D}${systemd_unitdir}/system/
        install -m 0644 -D ${WORKDIR}/crimson-server.service ${D}${systemd_unitdir}/system/
}

do_install:cyan() {
        install -d -m 0755 ${D}${bindir}
        install -m 0755 -D ${WORKDIR}/git/server ${D}${bindir}
        install -m 0755 -D ${WORKDIR}/git/mem ${D}${bindir}
        install -m 0755 -D ${WORKDIR}/git/mcu ${D}${bindir}
        install -d -m 0755 ${D}${prefix}/src/debug/${PN}/${PV}-${PR}/git
        cp -r ${WORKDIR}/git ${D}${prefix}/src/debug/${PN}/${PV}-${PR}/
        install -m 0755 -D ${WORKDIR}/git/scripts/tate/gpio_control ${D}${bindir}
        install -m 0755 -D ${WORKDIR}/git/scripts/tate/memtool ${D}${bindir}
        install -m 0755 -D ${WORKDIR}/git/scripts/tate/rfe_control ${D}${bindir}
        install -m 0755 -D ${WORKDIR}/git/scripts/tate/prog_primer ${D}${bindir}
        install -m 0755 -D ${WORKDIR}/git/scripts/tate/fpga_image_status ${D}${bindir}
        install -m 0755 -D ${WORKDIR}/git/scripts/tate/pll_check ${D}${bindir}
        install -m 0755 -D ${WORKDIR}/git/scripts/tate/buffer_lvl.sh ${D}${bindir}
        install -m 0755 -D ${WORKDIR}/git/scripts/tate/jesd_gp.sh ${D}${bindir}
        install -m 0755 -D ${WORKDIR}/git/scripts/tate/jesd_debug.sh ${D}${bindir}
        install -m 0755 -D ${WORKDIR}/git/scripts/tate/pll_check.sh ${D}${bindir}
        install -d -m 0755 ${D}${systemd_unitdir}/system/
        install -m 0644 -D ${WORKDIR}/cyan-server.service ${D}${systemd_unitdir}/system/
        install -m 0644 -D ${WORKDIR}/post.service ${D}${systemd_unitdir}/system/
}

do_install:chestnut() {
        install -d -m 0755 ${D}${bindir}
        install -m 0755 -D ${WORKDIR}/git/server ${D}${bindir}
        install -m 0755 -D ${WORKDIR}/git/mem ${D}${bindir}
        install -m 0755 -D ${WORKDIR}/git/mcu ${D}${bindir}
        install -d -m 0755 ${D}${prefix}/src/debug/${PN}/${PV}-${PR}/git
        cp -r ${WORKDIR}/git ${D}${prefix}/src/debug/${PN}/${PV}-${PR}/
        install -m 0755 -D ${WORKDIR}/git/scripts/tate/gpio_control ${D}${bindir}
        install -m 0755 -D ${WORKDIR}/git/scripts/tate/memtool ${D}${bindir}
        install -m 0755 -D ${WORKDIR}/git/scripts/tate/rfe_control ${D}${bindir}
        install -m 0755 -D ${WORKDIR}/git/scripts/tate/prog_primer ${D}${bindir}
        install -m 0755 -D ${WORKDIR}/git/scripts/tate/fpga_image_status ${D}${bindir}
        install -m 0755 -D ${WORKDIR}/git/scripts/tate/pll_check ${D}${bindir}
        install -m 0755 -D ${WORKDIR}/git/scripts/tate/buffer_lvl.sh ${D}${bindir}
        install -m 0755 -D ${WORKDIR}/git/scripts/tate/jesd_gp.sh ${D}${bindir}
        install -m 0755 -D ${WORKDIR}/git/scripts/tate/jesd_debug.sh ${D}${bindir}
        install -m 0755 -D ${WORKDIR}/git/scripts/tate/pll_check.sh ${D}${bindir}
        install -d -m 0755 ${D}${systemd_unitdir}/system/
        install -m 0644 -D ${WORKDIR}/chestnut-server.service ${D}${systemd_unitdir}/system/
        install -m 0644 -D ${WORKDIR}/post.service ${D}${systemd_unitdir}/system/

        install -m 0644 -D ${WORKDIR}/chestnut-server.service ${D}/etc/systemd/system/multi-user.target.wants/chestnut-server.service
        install -m 0644 -D ${WORKDIR}/post.service ${D}/etc/systemd/system/multi-user.target.wants/post.service
}

do_package:qa() {
	echo "Success"
}
