DESCRIPTION = "Pervices Crimson TNG custom configuration files"
AUTHOR = "Shiqi Feng <shiqi.f@pervices.com>"
SECTION = "common"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM="file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"
DEPENDS_${PN} = "bash"
RDEPENDS_${PN} = "bash"
SRC_URI = "file://etc/udev/rules.d/99-local.rules \
           file://lib/systemd/system/crimson-log.service \
           file://lib/systemd/system/crimson-networking.service \
           file://lib/systemd/system/crimson-sensors.service \
           file://lib/systemd/system/crimson-fanctl.service \
           file://etc/crimson/logging \
           file://etc/crimson/package-manager \
           file://etc/crimson/motd \
           file://etc/sysctl.d/udp_recvbuff.conf \
           file://etc/crimson/issue.net \
           file://etc/crimson/make-tarball \
           file://etc/crimson/sensors \
           file://etc/crimson/crimson-update \
           file://etc/crimson/fanctl \
           file://etc/sdr.conf \
          "
PAM_PLUGINS = "libpam-runtime \
               pam-plugin-access \
               pam-plugin-cracklib \
               pam-plugin-debug \
               pam-plugin-echo \
               pam-plugin-exec \
               pam-plugin-filter \
               pam-plugin-ftp \
               pam-plugin-issue \
               pam-plugin-listfile \
               pam-plugin-localuser \
               pam-plugin-mkhomedir \
               pam-plugin-namespace \
               pam-plugin-pwhistory \
               pam-plugin-rhosts \
               pam-plugin-stress \
               pam-plugin-succeed-if \
               pam-plugin-tally \
               pam-plugin-tally2 \
               pam-plugin-time \
               pam-plugin-timestamp \
               pam-plugin-umask \
               pam-plugin-wheel \
               pam-plugin-xauth \
              "
PACKAGECONFIG = "${@bb.utils.contains('DISTRO_FEATURES', 'pam', 'pam', '', d)}"
PACKAGECONFIG[pam] = "--with-libpam,--without-libpam,libpam,${PAM_PLUGINS}"
inherit systemd autotools
FILES_${PN} += "${bindir} ${sysconfdir} ${systemd_unitdir}/system ${base_libdir}"
SYSTEMD_SERVICE_${PN} = "crimson-networking.service crimson-fanctl.service"

do_install() {
	install -d -m 0755 ${D}${systemd_unitdir}/system/
	install -d -m 0755 ${D}${sysconfdir}/crimson/
	install -d -m 0755 ${D}${sysconfdir}/udev/rules.d/
	install -d -m 0755 ${D}${sysconfdir}/sysctl.d/
	install -d -m 0755 ${D}${bindir}/
	
	install -m 0644 -D ${WORKDIR}/lib/systemd/system/*.service ${D}${systemd_unitdir}/system/
	install -m 0755 -D ${WORKDIR}/etc/crimson/motd ${D}${sysconfdir}/crimson/
	install -m 0644 -D ${WORKDIR}/etc/crimson/issue.net ${D}${sysconfdir}/crimson/
	install -m 0744 -D ${WORKDIR}/etc/crimson/logging ${D}${sysconfdir}/crimson/
	install -m 0744 -D ${WORKDIR}/etc/crimson/package-manager ${D}${sysconfdir}/crimson/
	install -m 0744 -D ${WORKDIR}/etc/crimson/make-tarball ${D}${sysconfdir}/crimson/
	install -m 0744 -D ${WORKDIR}/etc/crimson/sensors ${D}${sysconfdir}/crimson/
	install -m 0744 -D ${WORKDIR}/etc/crimson/crimson-update ${D}${sysconfdir}/crimson/
	install -m 0744 -D ${WORKDIR}/etc/crimson/fanctl ${D}${sysconfdir}/crimson/
	install -m 0644 -D ${WORKDIR}/etc/udev/rules.d/* ${D}${sysconfdir}/udev/rules.d/
	install -m 0644 -D ${WORKDIR}/etc/sysctl.d/* ${D}${sysconfdir}/sysctl.d/
	install -m 0644 -D ${WORKDIR}/etc/sdr.conf ${D}${sysconfdir}/
}

do_install_append() {
	ln -s /usr/bin/arm-poky-linux-gnueabi-gcc ${D}${bindir}/arm-linux-gnueabihf-gcc
	echo "installed-${PV}" >> ${D}${sysconfdir}/crimson/${PN}
}
