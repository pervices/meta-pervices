SUMMARY = "A tool to load and stress a computer system"
HOMEPAGE = "http://kernel.ubuntu.com/~cking/stress-ng/"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=55ea9d559f985fb4834317d8ed6b9e58"

DEPENDS = "zlib libaio"

#SRC_URI = "http://kernel.ubuntu.com/~cking/tarballs/${BPN}/${BP}.tar.gz"
SRC_URI = "https://github.com/stressapptest/stressapptest/archive/refs/tags/v1.0.9.tar.gz"

SRC_URI[md5sum] = "d3a526c174c049dd7a1068dc74a62be2"
SRC_URI[sha256sum] = "2ba470587ad4f6ae92057d427c3a2a2756e5f10bd25cd91e62eaef55a40b30a1"

#CFLAGS += "-Wall -Wextra -DVERSION='"$(VERSION)"' -O2"

S = "${WORKDIR}/stressapptest-1.0.9"

inherit autotools

