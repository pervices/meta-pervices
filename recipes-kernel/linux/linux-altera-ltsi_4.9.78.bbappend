FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${LINUX_VERSION}:"
SRC_URI += "file://defconfig \
            file://0001-Initial-commit-for-DTS-stuff.patch \
            file://0002-Remove-non-existing-modules-disable-everything-excep.patch \
            file://0003-Bugfix-new-chip-revisions-have-a-mask-of-0xF8.patch \
            file://0004-Update-DTS-with-4-MAX14830-and-gpio-pins.patch \
            file://0005-Enabled-more-modules-in-the-DTS.patch \
            file://0006-Fix-HPS_OSC-clock-frequency-is-125-MHz.patch \
            file://0007-Experimental-Add-MT25QU01G-QSPI-flash.patch \
            file://0008-Reduce-SPIM0-speed-don-t-use-MMC-highspeed-feature-r.patch \
"

do_configure_append() {
            cp ${WORKDIR}/defconfig ${WORKDIR}/linux-stratix10-standard-build/.config
}
