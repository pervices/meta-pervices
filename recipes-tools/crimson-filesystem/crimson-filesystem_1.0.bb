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
           file://udp_recvbuff.conf \
	   "
FILES_${PN} = "${sysconfdir} ${systemd_unitdir}/system ${base_libdir}"
do_install() {
	install -d -m 0755 ${D}${systemd_unitdir}/system
	install -d -m 0755 ${D}${sysconfdir}/init.d
	install -d -m 0755 ${D}${sysconfdir}/systemd/system/multi-user.target.wants
	install -d -m 0755 ${D}${sysconfdir}/udev/rules.d
	install -d -m 0755 ${D}${sysconfdir}/sysctl.d
	install -m 0644 -D ${WORKDIR}/crimson-log.service ${D}${systemd_unitdir}/system
	install -m 0644 -D ${WORKDIR}/crimson-server.service ${D}${systemd_unitdir}/system
	install -m 0644 -D ${WORKDIR}/crimson-website.service ${D}${systemd_unitdir}/system
	install -m 0644 -D ${WORKDIR}/crimson-networking.service ${D}${systemd_unitdir}/system
	install -m 0755 -D ${WORKDIR}/logging ${D}${sysconfdir}/init.d
	install -m 0755 -D ${WORKDIR}/package-manager ${D}${sysconfdir}/init.d
	install -m 0644 -D ${WORKDIR}/99-local.rules ${D}${sysconfdir}/udev/rules.d
	install -m 0644 -D ${WORKDIR}/udp_recvbuff.conf ${D}${sysconfdir}/sysctl.d
}

do_install_append() {
	ln -s /lib/systemd/system/crimson-log.service ${D}${sysconfdir}/systemd/system/multi-user.target.wants/crimson-log.service
	ln -s /lib/systemd/system/crimson-networking.service ${D}${sysconfdir}/systemd/system/multi-user.target.wants/crimson-networking.service
	ln -s /lib/systemd/system/crimson-server.service ${D}${sysconfdir}/systemd/system/multi-user.target.wants/crimson-server.service
	ln -s /lib/systemd/system/crimson-website.service ${D}${sysconfdir}/systemd/system/multi-user.target.wants/crimson-website.service
}
