# Post process the systemd udev rules in /lib/udev/rules.d/99-systemd.rules

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://backport-v234-e266c06-v230.patch"


pkg_postinst_${PN}() {

sed -i 's/tty\[a-zA-Z\]\*/&|ttyS0\*/' $D/lib/udev/rules.d/99-systemd.rules

}
