DESCRIPTION = "Pervices SOC code"
AUTHOR = "Christopher Friedt <chris.f@pervices.com>"
SECTION = "common"
LICENSE = "GPLv3+"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"
DEPENDS = ""

SRC_URI = "git://github.com/cfriedtpv/firmware.git;protocol=git;branch=cfriedt-testing"
SRCREV = "1acf6de061f6eacd1993479f5845b94ef570b7d4"

S="${WORKDIR}/git"

inherit autotools
