DESCRIPTION = "Recipe for adding users"
AUTHOR = "Shiqi Feng <shiqi.f@pervices.com>"
SECTION = "common"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM="file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"
DEPENDS = ""

SRC_URI = "file://default.bashrc \
           file://default.bash_profile \
           file://.ssh/authorized_keys \
	   file://default.minirc.dfl \
          "
EXCLUDE_FROM_WORLD = "1"
INHIBIT_DEFAULT_DEPS = "1"

ALLOW_EMPTY:${PN} = "1"

S = "${WORKDIR}"
PACKAGES =+ "${PN}-client"
inherit useradd
USERADD_PACKAGES = "${PN} ${PN}-client"
USERADD_PARAM:${PN} = "-u 1200 -m -d /home/dev0 -r -s /bin/bash -P 'dev0' dev0"
GROUPADD_PARAM:${PN} = "-g 880 dev-grp0"
USERADD_PARAM:${PN}-client = "-u 1201 -m -d /home/client -r -s /bin/bash -P 'client' client"
GROUPADD_PARAM:${PN}-client = "-g 890 cli-grp0"
RDEPENDS:${PN} += "shadow"
do_install () {
	install -d -m 0750 ${D}/home/dev0
	install -d -m 0700 ${D}/home/dev0/.ssh
	install -d -m 0750 ${D}/home/client
	install -d -m 0750 ${D}/home/root
	install -d -m 0700 ${D}/home/root/.ssh

	install -p -m 0640 default.bashrc ${D}/home/dev0/.bashrc
	install -p -m 0640 default.bash_profile ${D}/home/dev0/.bash_profile
	install -p -m 0600 .ssh/authorized_keys ${D}/home/dev0/.ssh/authorized_keys

	install -p -m 0640 default.bashrc ${D}/home/client/.bashrc
	install -p -m 0640 default.bash_profile ${D}/home/client/.bash_profile

	install -p -m 0640 default.bashrc ${D}/home/root/.bashrc
	install -p -m 0640 default.bash_profile ${D}/home/root/.bash_profile
	install -p -m 0600 .ssh/authorized_keys ${D}/home/root/.ssh/authorized_keys
	install -p -m 0640 default.minirc.dfl ${D}/home/root/.minirc.dfl
	sed -i 's/1;32m/1;33m/g' ${D}/home/root/.bashrc

	chown -R dev0 ${D}/home/dev0/
	chgrp -R dev-grp0 ${D}/home/dev0/

	chown -R client ${D}/home/client/
	chgrp -R cli-grp0 ${D}/home/client/
}
do_install:append () {
	install -d -m 0750 ${D}${sysconfdir}/sudoers.d
	echo "dev0 ALL=(ALL) ALL" > ${D}${sysconfdir}/sudoers.d/dev0
}

FILES:${PN} = "/home/dev0/ /home/root/ /etc/sudoers.d/"
FILES:${PN}-client = "/home/client/"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
