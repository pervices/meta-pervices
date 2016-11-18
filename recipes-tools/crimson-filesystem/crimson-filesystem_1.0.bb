DESCRIPTION = "Pervices Crimson TNG custom configuration files"
AUTHOR = "Shiqi Feng <shiqi.f@pervices.com>"
SECTION = "common"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM="file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"
DEPENDS = ""

SRC_URI = "file://99-local.rules \
           file://crimson-log.service \
           file://crimson-networking.service \
           file://crimson-server.service \
           file://crimson-website.service \
           file://logging \
           file://package-manager \
           file://motd \
           file://udp_recvbuff.conf \
           file://root.bashrc \
           file://root.bash_profile \
	   "
inherit systemd
FILES_${PN} += "${sysconfdir} ${systemd_unitdir}/system ${base_libdir} home/root/"
RDEPENDS_${PN} = "bash"
SYSTEMD_SERVICE_${PN} = "crimson-log.service crimson-website.service crimson-server.service crimson-networking.service"

do_install() {
	install -d -m 0755 ${D}${systemd_unitdir}/system/
	install -d -m 0755 ${D}${sysconfdir}/crimson/
	install -d -m 0755 ${D}${sysconfdir}/udev/rules.d/
	install -d -m 0755 ${D}${sysconfdir}/sysctl.d/
	install -d -m 0700 ${D}/home/root/
	install -m 0644 -D ${WORKDIR}/crimson-log.service ${D}${systemd_unitdir}/system/
	install -m 0644 -D ${WORKDIR}/crimson-server.service ${D}${systemd_unitdir}/system/
	install -m 0644 -D ${WORKDIR}/crimson-website.service ${D}${systemd_unitdir}/system/
	install -m 0644 -D ${WORKDIR}/crimson-networking.service ${D}${systemd_unitdir}/system/
	install -m 0744 -D ${WORKDIR}/logging ${D}${sysconfdir}/crimson/
	install -m 0744 -D ${WORKDIR}/package-manager ${D}${sysconfdir}/crimson/
	install -m 0755 -D ${WORKDIR}/motd ${D}${sysconfdir}/crimson/
	install -m 0644 -D ${WORKDIR}/99-local.rules ${D}${sysconfdir}/udev/rules.d/
	install -m 0644 -D ${WORKDIR}/udp_recvbuff.conf ${D}${sysconfdir}/sysctl.d/
	install -m 0600 -D ${WORKDIR}/root.bash_profile ${D}/home/root/.bash_profile
	install -m 0600 -D ${WORKDIR}/root.bashrc ${D}/home/root/.bashrc
}

do_install_append() {
	echo "installed-${PV}" >> ${D}${sysconfdir}/crimson/${PN}
}
