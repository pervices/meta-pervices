SUMMARY = "Altera unified LibRSU (Remote System Update) library"
LICENSE = "MIT-0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=08e5b346f14e1877a2c2a2de82d2d4a1"

SRC_URI = "git://github.com/altera-fpga/librsu.git;protocol=https;branch=main;"

PV = "1.0+git${SRCPV}"
SRCREV = "7fbf28d0fffe9c599d3a05a3328985bbff366fb8"
DEPENDS = "zlib"

FILES:${PN} = " /usr/lib/* \
                /usr/include/* \
                /etc/* "

INSANE_SKIP:${PN} = "libdir"
INSANE_SKIP:${PN}-dbg = "libdir"

S = "${WORKDIR}/git"

inherit cmake

EXTRA_OECMAKE += "-DPLATFORM=linux-aarch64"

do_install(){
    cmake --install ${B} --prefix=${D} --component uniLibRSU_lib_Runtime
}