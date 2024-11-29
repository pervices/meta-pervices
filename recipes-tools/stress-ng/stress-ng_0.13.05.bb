SUMMARY = "A tool to load and stress a computer system"
HOMEPAGE = "https://github.com/ColinIanKing/stress-ng"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

DEPENDS = "zlib libaio"

SRC_URI = "https://github.com/ColinIanKing/stress-ng/tarball/V0.13.05"
SRC_URI[md5sum] = "48ac92b114ccaafad53c687bf21abce1"
SRC_URI[sha256sum] = "9fe0af83b559442f8a395463c779723fe4c0e190772ca21de1ddb4e3debabc86"

CFLAGS += "-Wall -Wextra -DVERSION='"$(VERSION)"' -O2"

do_install_append() {
    install -d ${D}${bindir}
    install -m 755 ${S}/stress-ng ${D}${bindir}/stress-ng
}
