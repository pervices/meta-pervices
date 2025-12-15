FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

# Add all the extra files we need
SRC_URI += " \
    file://u-boot.cmd \
    file://preloader-mkimage.bin \
    file://u-boot-crimson.img \
"

FILES:${PN} += "/boot"

# Run post_deploy after the normal deploy step
addtask post_deploy after do_deploy before do_package

do_compile:append() {
    # Pick the right arch here: "arm" OR "arm64"
    # (use the same one you used in the 2017.09 bbappend)
    uboot-mkimage -A arm64 -O linux -T script -C none -a 0 -e 0 \
        -d ${WORKDIR}/u-boot.cmd ${WORKDIR}/u-boot.scr
}

do_install:append() {
    install -d ${D}/boot/u-boot-scripts

    # U-Boot script
    install -m 0644 ${WORKDIR}/u-boot.scr \
        ${D}/boot/u-boot-scripts/u-boot.scr

    # Preloader & U-Boot images
    install -m 0644 ${WORKDIR}/preloader-mkimage.bin \
        ${D}/boot/preloader-mkimage.bin
    install -m 0644 ${WORKDIR}/u-boot-crimson.img \
        ${D}/boot/u-boot-crimson.img
    install -m 0644 ${WORKDIR}/u-boot-crimson-rtm10.img \
        ${D}/boot/u-boot-crimson-rtm10.img
#
#     # extlinux.conf so wic / IMAGE_BOOT_FILES can find it
#     install -m 0644 ${WORKDIR}/extlinux.conf \
#         ${D}/boot/extlinux.conf
}

do_post:deploy() {
    # Copy everything into the image deploy directory

    install -m 0644 ${D}/boot/u-boot-scripts/u-boot.scr \
        ${DEPLOY_DIR_IMAGE}/u-boot.scr

    install -m 0644 ${D}/boot/preloader-mkimage.bin \
        ${DEPLOY_DIR_IMAGE}/preloader-mkimage.bin

    install -m 0644 ${D}/boot/u-boot-crimson.img \
        ${DEPLOY_DIR_IMAGE}/u-boot-crimson.img

    install -m 0644 ${D}/boot/u-boot-crimson-rtm10.img \
        ${DEPLOY_DIR_IMAGE}/u-boot-crimson-rtm10.img

#     install -d ${DEPLOY_DIR_IMAGE}/extlinux

#     install -m 0644 ${D}/boot/extlinux.conf \
#         ${DEPLOY_DIR_IMAGE}/extlinux.conf

    # Optional combined image (like in your older recipe):
    # cat ${DEPLOY_DIR_IMAGE}/preloader-mkimage.bin \
    #     ${DEPLOY_DIR_IMAGE}/u-boot-crimson.img \
    #     > ${DEPLOY_DIR_IMAGE}/preloader-u-boot-arria5.img
}
