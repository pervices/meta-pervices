DESCRIPTION = ""
AUTHOR = ""
SECTION = "common"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM="file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"
DEPENDS_${PN} = "zlib"
RDEPENDS_${PN} = "zlib"
#SRC_URI = "git://github.com/altera-opensource/intel-rsu.git;protocol=git;branch=master"
#SRCREV = "master"
#BRANCH = "master"
#S = "${WORKDIR}/git"
SRC_URI = "file://etc/librsu.rc \
           file://lib/librsu.so \
           file://usr/bin/rsu_client \
          "
FILES_${PN} += "/home/root/ ${D}${sysconfdir} ${D}${base_libdir} ${bindir}"

#do_compile() {
#        export ARCH=arm64
#        export CROSS_COMPILE=${CC}
#        export ZLIB_PATH=../../../zlib/1.2.8-r0/zlib-1.2.8/
#        cd ${WORKDIR}/git/lib
#        sed -i 's/\(CFLAGS := .*\)$/\1 -I\$\(ZLIB_PATH\)/g' makefile
#        oe_runmake
#        cd ../example
#        sed -i 's/\(LDFLAGS := .*\)$/\1 -L\$\(ZLIB_PATH\)/g' makefile
#        oe_runmake
#}

do_install() {
        install -d -m 0755 ${D}${sysconfdir}
        install -d -m 0755 ${D}${base_libdir}
        #install -d -m 0750 ${D}/home/root
	install -d -m 0755 ${D}${bindir}
        #install -m 0755 -D ${WORKDIR}/git/example/rsu_client ${D}/home/root/
        install -m 0755 -D ${WORKDIR}/usr/bin/rsu_client ${D}${bindir}
        #install -m 0755 -D ${WORKDIR}/git/lib/librsu.so ${D}${base_libdir}
        install -m 0755 -D ${WORKDIR}/lib/librsu.so ${D}${base_libdir}
        #install -m 0644 -D ${WORKDIR}/git/etc/qspi.rc ${D}${sysconfdir}/librsu.rc
        install -m 0644 -D ${WORKDIR}/etc/librsu.rc ${D}${sysconfdir}
}

do_package_qa() {
        echo "Success"
}
