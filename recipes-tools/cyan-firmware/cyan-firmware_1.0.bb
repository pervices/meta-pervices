DESCRIPTION = "Pervices Cyan firmware source code"
AUTHOR = "Shiqi Feng <shiqi.f@pervices.com>"
SECTION = "common"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM="file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"
DEPENDS_${PN} = ""
RDEPENDS_${PN} = ""
SRC_URI = "git://github.com/pervices/firmware.git;protocol=https;branch=master-testing"
SRC_URI += "file://lib/systemd/system/cyan-server.service \
            file://usr/src/debug/${PN}/update.sh \
            file://lib/systemd/system/cyan-post.service \
            file://poky.patch \
           "
SRCREV = "master-testing"

S = "${WORKDIR}/git"

inherit systemd
inherit autotools

INSANE_SKIP_${PN} = "ldflags"

FILES_${PN} += "${bindir} ${sysconfdir}/cyan ${systemd_unitdir}/system ${D}${prefix}/src/debug/${PN}/${PV}-${PR}/git "

SYSTEMD_SERVICE_${PN} = "cyan-server.service cyan-post.service"


do_compile() {
	cd ${WORKDIR}/git
	#echo ${CC} > slog
	#echo ${CXX} >> slog
	#echo ${SDKPATHINSTALL} >> slog
	#echo ${SDKPATH} >> slog
	./autotate.sh TATE_NRNT RTM5 R4 T4 S1000
	#sh autogen.sh clean
	#sh autogen.sh
	#./configure --prefix=/usr --host=arm-unknown-linux-gnueabihf PRODUCT=TATE_NRNT HW_REV=RTM4 NRX=R4 NTX=T4 MAX_RATE=S1000
	#make
	#make DESTDIR=${WORKDIR}/git/ install
	${CC} script/memtool.c -o script/memtool
	${CC} script/pll_check.c -o script/pll_check
}

do_install() {
	install -d -m 0755 ${D}${bindir}
	install -d -m 0755 ${D}${sysconfdir}/cyan/
	install -m 0755 -D ${WORKDIR}/git/server ${D}${bindir}
	install -m 0755 -D ${WORKDIR}/git/mem ${D}${bindir}
	install -m 0755 -D ${WORKDIR}/git/mcu ${D}${bindir}
	install -d -m 0755 ${D}${prefix}/src/debug/${PN}/${PV}-${PR}/git
	cp -r ${WORKDIR}/git ${D}${prefix}/src/debug/${PN}/${PV}-${PR}/
	install -m 0755 -D ${WORKDIR}/usr/src/debug/${PN}/update.sh ${D}${prefix}/src/debug/${PN}/${PV}-${PR}/git/
	install -m 0755 -D ${WORKDIR}/git/script/gpio_control ${D}${bindir}
	install -m 0755 -D ${WORKDIR}/git/script/memtool ${D}${bindir}
	install -m 0755 -D ${WORKDIR}/git/script/rfe_control ${D}${bindir}
	install -m 0755 -D ${WORKDIR}/git/script/prog_primer ${D}${bindir}
	install -m 0755 -D ${WORKDIR}/git/script/fpga_image_status ${D}${bindir}
	install -m 0755 -D ${WORKDIR}/git/script/pll_check ${D}${bindir}
	install -m 0755 -D ${WORKDIR}/git/script/buffer_lvl.sh ${D}${bindir}
}

do_install_append() {
	install -d -m 0755 ${D}${systemd_unitdir}/system/
	install -m 0644 -D ${WORKDIR}/lib/systemd/system/cyan-server.service ${D}${systemd_unitdir}/system/
	install -m 0644 -D ${WORKDIR}/lib/systemd/system/cyan-post.service ${D}${systemd_unitdir}/system/
	cd ${WORKDIR}/git && git describe --tags --always --dirty --long >> ${D}${sysconfdir}/cyan/${PN}
}

do_package_qa() {
	echo "Success"
}
