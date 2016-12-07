DESCRIPTION = "Pervices Crimson TNG webserver and GUI source code"
AUTHOR = "Shiqi Feng <shiqi.f@pervices.com>"
SECTION = "common"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM="file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"

SRC_URI = "https://github.com/pervices/webserver/archive/master-testing.tar.gz"
SRC_URI[md5sum] = "283637edd1c409d6da2751c5ecc7f939"
SRC_URI[sha256sum] = "e173f9db61f68c02aadbdf43372b8855bd4a9877e51f421f677b30eaf6be8d6a"

S = "${WORKDIR}/webserver-master-testing"

inherit npm-install-global

do_install_append() {
	ln -s /usr/lib/node_modules/crimson_server/public/js/jquery-1.11.2.min.js ${D}${libdir}/node_modules/crimson_server/public/js/jquery.min.js
	chown -R root ${D}${libdir}/node_modules/crimson_server
}

BBCLASSEXTEND = "native nativesdk"
