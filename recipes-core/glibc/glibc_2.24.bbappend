FILES_${PN} += "${base_libdir}/ld-linux-armhf.so.3 ${libdir}/ld-linux-armhf.so.3"
do_install_append() {
	ln -s ld-linux.so.3 ${D}${base_libdir}/ld-linux-armhf.so.3
	ln -s ../../lib/ld-linux.so.3 ${D}${libdir}/ld-linux-armhf.so.3
}
