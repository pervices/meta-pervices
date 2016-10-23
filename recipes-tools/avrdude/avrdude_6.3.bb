DESCRIPTION = "AVRdude for communicating with ATMEL microcontrollers"
AUTHOR = "Shiqi Feng <shiqi.f@pervices.com>"
SECTION = "common"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM="file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"
DEPENDS = ""

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

SRC_URI = "http://download.savannah.gnu.org/releases/avrdude/avrdude-6.3.tar.gz \
	   file://fix-stdint.patch \ 
	  "

S = "${WORKDIR}/avrdude-6.3"

SRC_URI[md5sum] = "58bb42049122cf80fe4f4d0ce36d92ee"
SRC_URI[sha256sum] = "0f9f731b6394ca7795b88359689a7fa1fba818c6e1d962513eb28da670e0a196"

inherit autotools
