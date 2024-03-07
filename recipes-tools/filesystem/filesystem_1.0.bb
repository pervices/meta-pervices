DESCRIPTION = "Pervices SDR Filesystem"
AUTHOR = "Shiqi Feng <shiqi.f@pervices.com>"
SECTION = "common"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM="file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"
DEPENDS_${PN} = "bash"
RDEPENDS_${PN} = "bash tcl"
SRC_URI_prepend += "file://motd \
                    file://udp_recvbuff.conf \
                    file://issue.net \
                    file://networking.service \
                    file://expand_parti_sd-README.txt \
                    file://expand_parti_sd.sh \
                   "

SRC_URI_crimson += "file://crimson/etc/udev/rules.d/99-local.rules \
                    file://crimson/lib/systemd/system/crimson-log.service \
                    file://crimson/lib/systemd/system/crimson-sensors.service \
                    file://crimson/lib/systemd/system/crimson-fanctl.service \
                    file://crimson/etc/crimson/logging \
                    file://crimson/etc/crimson/package-manager \
                    file://crimson/etc/crimson/make-tarball \
                    file://crimson/etc/crimson/sensors \
                    file://crimson/etc/crimson/crimson-update \
                    file://crimson/etc/crimson/crimson-smart-update \
                    file://crimson/etc/crimson/crimson-smart-add-address \
                    file://crimson/etc/crimson/crimson-smart-update-address \
                    file://crimson/etc/crimson/fanctl \
                    file://crimson/etc/sdr.conf \
                    file://crimson/etc/crimson/startup \
                    file://crimson/lib/systemd/system/crimson-startup.service \
                   "
SRC_URI_cyan += "file://cyan/etc/udev/rules.d/99-local.rules \
                 file://cyan/lib/systemd/system/cyan-log.service \
                 file://cyan/lib/systemd/system/cyan-sensors.service \
                 file://cyan/lib/systemd/system/cyan-fanctl.service \
                 file://cyan/lib/systemd/system/cyan-fpga-image-status.service \
                 file://cyan/lib/systemd/system/button-off.service \
                 file://cyan/lib/systemd/system/cyan-set-baud.service \
                 file://cyan/lib/systemd/system/cyan-startup.service \
                 file://cyan/lib/systemd/system/cyan-rf-fan-controller.service \
                 file://cyan/etc/cyan/logging \
                 file://cyan/etc/cyan/package-manager \
                 file://cyan/etc/cyan/make-tarball \
                 file://cyan/etc/cyan/sensors \
                 file://cyan/etc/cyan/cyan-update \
                 file://cyan/etc/cyan/fanctl \
                 file://cyan/etc/cyan/stty.settings \
                 file://cyan/etc/cyan/set_baud \
                 file://cyan/etc/cyan/startup \
                 file://cyan/usr/bin/button_press_off.sh \
                 file://cyan/usr/bin/execstop.sh \
                 file://cyan/usr/bin/cyan-rf-fan-controller.sh \
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
FILES_${PN} += "${bindir} ${sysconfdir} ${systemd_unitdir}/system ${base_libdir}"
SYSTEMD_SERVICE_${PN}_crimson = "networking.service crimson-fanctl.service crimson-startup.service"
SYSTEMD_SERVICE_${PN}_cyan = "networking.service cyan-set-baud.service cyan-fanctl.service button-off.service cyan-startup.service cyan-rf-fan-controller.service"
inherit systemd autotools

do_compile() {
}

do_install_prepend() {
	install -d -m 0755 ${D}${systemd_unitdir}/system/
	install -d -m 0755 ${D}${sysconfdir}/${MACHINE}/
	install -d -m 0755 ${D}${sysconfdir}/udev/rules.d/
	install -d -m 0755 ${D}${sysconfdir}/sysctl.d/
	install -d -m 0755 ${D}${bindir}/
	install -m 0755 -D ${WORKDIR}/motd ${D}${sysconfdir}/${MACHINE}/
	install -m 0644 -D ${WORKDIR}/issue.net ${D}${sysconfdir}/${MACHINE}/
	install -m 0644 -D ${WORKDIR}/networking.service ${D}${systemd_unitdir}/system/
	install -m 0644 -D ${WORKDIR}/udp_recvbuff.conf ${D}${sysconfdir}/sysctl.d/
	install -m 0644 -D ${WORKDIR}/expand_parti_sd-README.txt ${D}${sysconfdir}/${MACHINE}/
	install -m 0755 -D ${WORKDIR}/expand_parti_sd.sh ${D}${bindir}/
	ln -s /usr/bin/arm-poky-linux-gnueabi-gcc ${D}${bindir}/arm-linux-gnueabihf-gcc
}

do_install_crimson() {
	install -m 0644 -D ${WORKDIR}/crimson/lib/systemd/system/*.service ${D}${systemd_unitdir}/system/
	install -m 0744 -D ${WORKDIR}/crimson/etc/crimson/logging ${D}${sysconfdir}/crimson/
	install -m 0744 -D ${WORKDIR}/crimson/etc/crimson/package-manager ${D}${sysconfdir}/crimson/
	install -m 0744 -D ${WORKDIR}/crimson/etc/crimson/make-tarball ${D}${sysconfdir}/crimson/
	install -m 0744 -D ${WORKDIR}/crimson/etc/crimson/sensors ${D}${sysconfdir}/crimson/
	install -m 0744 -D ${WORKDIR}/crimson/etc/crimson/startup ${D}${sysconfdir}/crimson/
	install -m 0744 -D ${WORKDIR}/crimson/etc/crimson/crimson-update ${D}${sysconfdir}/crimson/
	install -m 0744 -D ${WORKDIR}/crimson/etc/crimson/crimson-smart-update ${D}${sysconfdir}/crimson/
	install -m 0744 -D ${WORKDIR}/crimson/etc/crimson/crimson-smart-add-address ${D}${sysconfdir}/crimson/
	install -m 0744 -D ${WORKDIR}/crimson/etc/crimson/crimson-smart-update-address ${D}${sysconfdir}/crimson/
	install -m 0744 -D ${WORKDIR}/crimson/etc/crimson/fanctl ${D}${sysconfdir}/crimson/
	install -m 0644 -D ${WORKDIR}/crimson/etc/udev/rules.d/* ${D}${sysconfdir}/udev/rules.d/
	install -m 0644 -D ${WORKDIR}/crimson/etc/sdr.conf ${D}${sysconfdir}/
}

do_install_cyan() {	
	install -m 0755 -D ${WORKDIR}/cyan/usr/bin/*.sh ${D}${bindir}
	install -m 0644 -D ${WORKDIR}/cyan/lib/systemd/system/*.service ${D}${systemd_unitdir}/system/
	install -m 0744 -D ${WORKDIR}/cyan/etc/cyan/logging ${D}${sysconfdir}/cyan/
	install -m 0744 -D ${WORKDIR}/cyan/etc/cyan/package-manager ${D}${sysconfdir}/cyan/
	install -m 0744 -D ${WORKDIR}/cyan/etc/cyan/make-tarball ${D}${sysconfdir}/cyan/
	install -m 0744 -D ${WORKDIR}/cyan/etc/cyan/sensors ${D}${sysconfdir}/cyan/
	install -m 0744 -D ${WORKDIR}/cyan/etc/cyan/cyan-update ${D}${sysconfdir}/cyan/
	install -m 0744 -D ${WORKDIR}/cyan/etc/cyan/fanctl ${D}${sysconfdir}/cyan/
	install -m 0644 -D ${WORKDIR}/cyan/etc/cyan/stty.settings ${D}${sysconfdir}/cyan/
	install -m 0744 -D ${WORKDIR}/cyan/etc/cyan/set_baud ${D}${sysconfdir}/cyan/
	install -m 0744 -D ${WORKDIR}/cyan/etc/cyan/startup ${D}${sysconfdir}/cyan/
	install -m 0644 -D ${WORKDIR}/cyan/etc/udev/rules.d/* ${D}${sysconfdir}/udev/rules.d/
}
