DESCRIPTION = "Pervices SOC code"
AUTHOR = "Christopher Friedt <chris.f@pervices.com>"
SECTION = "common"
LICENSE = "GPLv3+"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"
DEPENDS = ""

SRC_URI = "git://github.com/cfriedtpv/firmware.git;protocol=git;branch=cfriedt-testing"
SRCREV = "91acb0cf5b9e561f2f4cb5b48ad3a586d46a3e3b"


S="${WORKDIR}/git"

inherit autotools
