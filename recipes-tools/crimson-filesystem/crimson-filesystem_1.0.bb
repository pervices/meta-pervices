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
           file://lib/systemd/system/crimson-server.service \
           file://lib/systemd/system/crimson-website.service \
           file://etc/crimson/logging \
           file://etc/crimson/package-manager \
           file://etc/crimson/motd \
           file://etc/sysctl.d/udp_recvbuff.conf \
           file://etc/crimson/issue.net \
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
FILES_${PN} += "${sysconfdir} ${systemd_unitdir}/system ${base_libdir}"
SYSTEMD_SERVICE_${PN} = "crimson-log.service crimson-website.service crimson-server.service crimson-networking.service"

do_install() {
	install -d -m 0755 ${D}${systemd_unitdir}/system/
	install -d -m 0755 ${D}${sysconfdir}/crimson/
	install -d -m 0755 ${D}${sysconfdir}/udev/rules.d/
	install -d -m 0755 ${D}${sysconfdir}/sysctl.d/

	install -m 0644 -D ${WORKDIR}/lib/systemd/system/*.service ${D}${systemd_unitdir}/system/
	install -m 0744 -D ${WORKDIR}/etc/crimson/* ${D}${sysconfdir}/crimson/
	chmod go+x ${D}${sysconfdir}/crimson/motd
	install -m 0644 -D ${WORKDIR}/etc/udev/rules.d/* ${D}${sysconfdir}/udev/rules.d/
	install -m 0644 -D ${WORKDIR}/etc/sysctl.d/* ${D}${sysconfdir}/sysctl.d/
}

do_install_append() {
	echo "installed-${PV}" >> ${D}${sysconfdir}/crimson/${PN}
}
