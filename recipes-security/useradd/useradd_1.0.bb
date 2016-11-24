DESCRIPTION = "Recipe for adding users in Crimson"
AUTHOR = "Shiqi Feng <shiqi.f@pervices.com>"
SECTION = "common"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM="file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"
DEPENDS = ""

SRC_URI = "file://default.bashrc \
           file://default.bash_profile \
          "

S = "${WORKDIR}"
PACKAGES =+ "${PN}-dev0"
inherit useradd
USERADD_PACKAGES = "${PN}-dev0"
USERADD_PARAM_${PN}-dev0 = "-u 1200 -d /home/dev0 -r -s /bin/bash -P 'dev0' dev0"
GROUPADD_PARAM_${PN}-dev0 = "-g 880 devs"
do_install () {
	install -d -m 0755 ${D}/home/dev0
	install -d -m 0755 ${D}/home/root

	install -p -m 0644 default.bashrc ${D}/home/dev0/.bashrc
	install -p -m 0644 default.bash_profile ${D}/home/dev0/.bash_profile

	install -p -m 0644 default.bashrc ${D}/home/root/.bashrc
	install -p -m 0644 default.bash_profile ${D}/home/root/.bash_profile

	chown -R dev0 ${D}/home/dev0/
	chgrp -R devs ${D}/home/dev0/
}

FILES_${PN}-dev0 = "/home/dev0/ /home/root/"

INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
