#FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"
SRCREV = "3a74d4fc90cb322a4784a3515bef7118c8f8c5ba"
SRC_URI = "git://github.com/systemd/systemd.git;protocol=https;branch=main"

# Need to copy over original recipe file srcs since we just essentially wiped SRC_URI
SRC_URI += " \
           file://touchscreen.rules \
           file://00-create-volatile.conf \
           file://init \
           file://run-ptest \
           file://0003-define-exp10-if-missing.patch \
           file://0004-Use-getenv-when-secure-versions-are-not-available.patch \
           file://0005-binfmt-Don-t-install-dependency-links-at-install-tim.patch \
           file://0006-configure-Check-for-additional-features-that-uclibc-.patch \
           file://0007-use-lnr-wrapper-instead-of-looking-for-relative-opti.patch \
           file://0008-nspawn-Use-execvpe-only-when-libc-supports-it.patch \
           file://0009-util-bypass-unimplemented-_SC_PHYS_PAGES-system-conf.patch \
           file://0010-implment-systemd-sysv-install-for-OE.patch \
           file://0011-nss-mymachines-Build-conditionally-when-HAVE_MYHOSTN.patch \
           file://0012-rules-whitelist-hd-devices.patch \
           file://0013-sysv-generator-add-support-for-executing-scripts-und.patch \
           file://0014-Make-root-s-home-directory-configurable.patch \
           file://0015-systemd-user-avoid-using-system-auth.patch \
           file://0016-Revert-rules-remove-firmware-loading-rules.patch \
           file://0017-Revert-udev-remove-userspace-firmware-loading-suppor.patch \
           file://0018-make-test-dir-configurable.patch \
           file://0019-remove-duplicate-include-uchar.h.patch \
           file://0020-check-for-uchar.h-in-configure.patch \
           file://0022-socket-util-don-t-fail-if-libc-doesn-t-support-IDN.patch \
           file://udev-re-enable-mount-propagation-for-udevd.patch \
           file://CVE-2016-7795.patch \
           file://validate-user.patch \
           file://Ensure-kdbus-isn-t-used-3501.patch \
"
SRC_URI:append_libc-uclibc = "\
           file://0002-units-Prefer-getty-to-agetty-in-console-setup-system.patch \
"
SRC_URI:append:qemuall = " file://0001-core-device.c-Change-the-default-device-timeout-to-2.patch"

do_install:append() {
	sed -i 's:#Storage=auto:Storage=volatile:g' ${D}${sysconfdir}/systemd/journald.conf
	sed -i 's:#RateLimitIntervalSec:RateLimitIntervalSec:g' ${D}${sysconfdir}/systemd/journald.conf
	sed -i 's:#RateLimitBurst=1000:RateLimitBurst=10000:g' ${D}${sysconfdir}/systemd/journald.conf
	sed -i 's:#SystemMaxUse=:SystemMaxUse=25M:g' ${D}${sysconfdir}/systemd/journald.conf
	sed -i 's:#SystemKeepFree=:SystemKeepFree=1G:g' ${D}${sysconfdir}/systemd/journald.conf
	sed -i 's:#SystemMaxFileSize=:SystemMaxFileSize=5M:g' ${D}${sysconfdir}/systemd/journald.conf
	sed -i 's:#SystemMaxFiles=100:SystemMaxFiles=100:g' ${D}${sysconfdir}/systemd/journald.conf
	sed -i 's:RuntimeMaxUse=64M:RuntimeMaxUse=50M:g' ${D}${sysconfdir}/systemd/journald.conf
	sed -i 's:#RuntimeKeepFree=:RuntimeKeepFree=1G:g' ${D}${sysconfdir}/systemd/journald.conf
	sed -i 's:#RuntimeMaxFileSize=:RuntimeMaxFileSize=5M:g' ${D}${sysconfdir}/systemd/journald.conf
	sed -i 's:#RuntimeMaxFiles=100:RuntimeMaxFiles=100:g' ${D}${sysconfdir}/systemd/journald.conf
	sed -i 's:#DefaultTimeoutStartSec=90:#DefaultTimeoutStartSec=6:g' ${D}${sysconfdir}/systemd/system.conf
	sed -i 's:#DefaultTimeoutStopSec=90:#DefaultTimeoutStopSec=6:g' ${D}${sysconfdir}/systemd/system.conf
	echo 'TimeoutStartSec=8' >> ${D}${sysconfdir}/systemd/system.conf
	echo 'TimeoutStopSec=8' >> ${D}${sysconfdir}/systemd/system.conf
}
