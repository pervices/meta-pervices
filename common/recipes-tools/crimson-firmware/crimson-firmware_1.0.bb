DESCRIPTION = "Pervices SOC code"
AUTHOR = "Christopher Friedt <chris.f@pervices.com>"
SECTION = "common"
LICENSE = "GPLv3+"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"
DEPENDS = ""

SRC_URI = "git://github.com/pervices/firmware.git;protocol=git;branch=master-testing"
SRCREV = "35a09728259175758df454c416cbd77243446ed4"


S="${WORKDIR}/git"

inherit autotools
