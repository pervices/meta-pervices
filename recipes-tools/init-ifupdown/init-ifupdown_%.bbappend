SUMMARY = "Change usb0 from static to dhcp"

FILESEXTRAPATHS:prepend := "${THISDIR}/init-ifupdown-1.0:"


SRC_URI:append = " file://interfaces-cyan" 


do_install:append () {
    install -m 0644 ${WORKDIR}/interfaces ${D}${sysconfdir}/network/interfaces-cyan
}
