DESCRIPTION = "Pervices SOC code"
AUTHOR = "Christopher Friedt <chris.f@pervices.com>"
SECTION = "common"
LICENSE = "GPLv3+"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"
DEPENDS = ""

SRC_URI = "git://github.com/cfriedtpv/firmware.git;protocol=git;branch=cfriedt-testing"
SRCREV = "2bb77c3c21048b7fba5a9e87ab47d5148fd64e97"


S="${WORKDIR}/git"

inherit autotools
