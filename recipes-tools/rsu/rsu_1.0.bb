DESCRIPTION = ""
AUTHOR = ""
SECTION = "common"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM="file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"
DEPENDS_${PN} = "zlib"
RDEPENDS_${PN} = ""
SRC_URI = "git://github.com/altera-opensource/intel-rsu.git;protocol=git;branch=master"

SRCREV = "master"

BRANCH = "master"

S = "${WORKDIR}/git"

#INSANE_SKIP_${PN} = "ldflags"

#FILES_${PN} += "${bindir} ${sysconfdir}/cyan ${systemd_unitdir}/system ${D}${prefix}/src/debug/${PN}/${PV}-${PR}/git "

#SYSTEMD_SERVICE_${PN} = "cyan-server.service"


do_compile() {
	export ZLIB_PATH=../../../zlib/1.2.8-r0/zlib-1.2.8/
	cd ${WORKDIR}/git/lib
	sed -i 's/\(CFLAGS := .*\)$/\1 -I\$\(ZLIB_PATH\)/g' makefile
	oe_runmake
	cd ../example
	sed -i 's/\(LDFLAGS := .*\)$/\1 -L\$\(ZLIB_PATH\)/g' makefile
	oe_runmake
}

do_install() {
}

do_install_append() {
}

do_package_qa() {
	echo "Success"
}
