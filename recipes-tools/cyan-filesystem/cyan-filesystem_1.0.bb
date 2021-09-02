DESCRIPTION = "Pervices Cyan  custom configuration files"
AUTHOR = "Shiqi Feng <shiqi.f@pervices.com>"
SECTION = "common"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM="file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"
DEPENDS_${PN} = "bash"
RDEPENDS_${PN} = "bash tcl"
SRC_URI = "file://etc/udev/rules.d/99-local.rules \
           file://lib/systemd/system/cyan-log.service \
           file://lib/systemd/system/cyan-networking.service \
           file://lib/systemd/system/cyan-sensors.service \
           file://lib/systemd/system/cyan-fanctl.service \
           file://lib/systemd/system/cyan-fpga-image-status.service \
           file://lib/systemd/system/button-off.service \
	   file://lib/systemd/system/cyan-set-baud.service \
           file://etc/cyan/logging \
           file://etc/cyan/package-manager \
           file://etc/cyan/motd \
           file://etc/sysctl.d/udp_recvbuff.conf \
           file://etc/cyan/issue.net \
           file://etc/cyan/make-tarball \
           file://etc/cyan/sensors \
           file://etc/cyan/cyan-update \
           file://etc/cyan/fanctl \
           file://etc/cyan/stty.settings \
           file://etc/cyan/set_baud \
           file://etc/cyan/startup \
           file://usr/bin/button_press_off.sh \
           file://usr/bin/execstop.sh \
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
SYSTEMD_SERVICE_${PN} = "cyan-networking.service cyan-set-baud.service cyan-fanctl.service"
#SYSTEMD_SERVICE_${PN} = "cyan-networking.service cyan-fpga-image-status.service cyan-fanctl.service "
#SYSTEMD_SERVICE_${PN} = "cyan-log.service cyan-sensors.service"

do_compile(){
}

do_install() {
	install -d -m 0755 ${D}${systemd_unitdir}/system/
	install -d -m 0755 ${D}${sysconfdir}/cyan/
	install -d -m 0755 ${D}${sysconfdir}/udev/rules.d/
	install -d -m 0755 ${D}${sysconfdir}/sysctl.d/
	install -d -m 0755 ${D}${bindir}/
	
	install -m 0755 -D ${WORKDIR}/usr/bin/*.sh #${D}${bindir}
	
	install -m 0644 -D ${WORKDIR}/lib/systemd/system/*.service ${D}${systemd_unitdir}/system/
	install -m 0755 -D ${WORKDIR}/etc/cyan/motd ${D}${sysconfdir}/cyan/
	install -m 0644 -D ${WORKDIR}/etc/cyan/issue.net ${D}${sysconfdir}/cyan/
	install -m 0744 -D ${WORKDIR}/etc/cyan/logging ${D}${sysconfdir}/cyan/
	install -m 0744 -D ${WORKDIR}/etc/cyan/package-manager ${D}${sysconfdir}/cyan/
	install -m 0744 -D ${WORKDIR}/etc/cyan/make-tarball ${D}${sysconfdir}/cyan/
	install -m 0744 -D ${WORKDIR}/etc/cyan/sensors ${D}${sysconfdir}/cyan/
	install -m 0744 -D ${WORKDIR}/etc/cyan/cyan-update ${D}${sysconfdir}/cyan/
	install -m 0744 -D ${WORKDIR}/etc/cyan/fanctl ${D}${sysconfdir}/cyan/
	install -m 0644 -D ${WORKDIR}/etc/cyan/stty.settings ${D}${sysconfdir}/cyan/
	install -m 0744 -D ${WORKDIR}/etc/cyan/set_baud ${D}${sysconfdir}/cyan/
	install -m 0744 -D ${WORKDIR}/etc/cyan/startup ${D}${sysconfdir}/cyan/
	
	install -m 0644 -D ${WORKDIR}/etc/udev/rules.d/* ${D}${sysconfdir}/udev/rules.d/
	install -m 0644 -D ${WORKDIR}/etc/sysctl.d/* ${D}${sysconfdir}/sysctl.d/
}

do_install_append() {
	ln -s /usr/bin/arm-poky-linux-gnueabi-gcc ${D}${bindir}/arm-linux-gnueabihf-gcc
	echo "installed-${PV}" >> ${D}${sysconfdir}/cyan/${PN}
}
