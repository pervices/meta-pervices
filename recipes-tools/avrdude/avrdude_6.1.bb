DESCRIPTION = "AVRdude for communicating with ATMEL microcontrollers"
AUTHOR = "Howard Pang <howardpang28@gmail.com>"
SECTION = "common"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM="file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"
DEPENDS = ""

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}${PV}:"

SRC_URI = "http://download.savannah.gnu.org/releases/avrdude/avrdude-6.1.tar.gz \
		file://fix-stdint.patch \ 
		"

S = "${WORKDIR}/avrdude-6.1"

SRC_URI[md5sum] = "9db8c25b935d34234b9b1ba16ad55fd5"
SRC_URI[sha256sum] = "9e98baca8e57cad402aaa1c7b61c8de750ed4f6fed577f7e4935db0430783d3b"

inherit autotools
