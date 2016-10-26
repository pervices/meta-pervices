DESCRIPTION = "Pervices Crimson TNG Miscellaneous files"
AUTHOR = "Shiqi Feng <shiqi.f@pervices.com>"
SECTION = "common"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM="file://${COMMON_LICENSE_DIR}/BSD-3-Clause;md5=550794465ba0ec5312d6919e203a55f9"
DEPENDS = ""

SRC_URI = "file://make_sdimage.py \
           file://preloader-mkpimage.bin \
           file://socfpga.dtb \
           file://u-boot.scr \
	  "

FILES_${PN} = "home/root/pv_miscellaneous/"

do_install() {
	install -d -m 0755 ${D}/home/root/pv_Miscellaneous/
	install -m 0644 -D ${WORKDIR}/make_sdimage.py ${D}/home/root/pv_Miscellaneous/
	install -m 0644 -D ${WORKDIR}/preloader-mkpimage.bin ${D}/home/root/pv_Miscellaneous/
	install -m 0644 -D ${WORKDIR}/socfpga.dtb ${D}/home/root/pv_Miscellaneous/
	install -m 0644 -D ${WORKDIR}/u-boot.scr ${D}/home/root/pv_Miscellaneous/
}
