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
PACKAGES =+ "${PN}-client"
inherit useradd
USERADD_PACKAGES = "${PN} ${PN}-client"
USERADD_PARAM_${PN} = "-u 1200 -d /home/dev0 -r -s /bin/bash -P 'dev0' dev0"
GROUPADD_PARAM_${PN} = "-g 880 dev-grp0"
USERADD_PARAM_${PN}-client = "-u 1201 -d /home/client -r -s /bin/bash -P 'client' client"
GROUPADD_PARAM_${PN}-client = "-g 890 cli-grp0"
do_install () {
	install -d -m 0755 ${D}/home/dev0
	install -d -m 0755 ${D}/home/client
	install -d -m 0755 ${D}/home/root

	install -p -m 0644 default.bashrc ${D}/home/dev0/.bashrc
	install -p -m 0644 default.bash_profile ${D}/home/dev0/.bash_profile

	install -p -m 0644 default.bashrc ${D}/home/client/.bashrc
	install -p -m 0644 default.bash_profile ${D}/home/client/.bash_profile

	install -p -m 0644 default.bashrc ${D}/home/root/.bashrc
	install -p -m 0644 default.bash_profile ${D}/home/root/.bash_profile

	chown -R dev0 ${D}/home/dev0/
	chgrp -R dev-grp0 ${D}/home/dev0/

	chown -R client ${D}/home/client/
	chgrp -R cli-grp0 ${D}/home/client/
}

FILES_${PN} = "/home/dev0/ /home/root/"
FILES_${PN}-client = "/home/client/"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
