DESCRIPTION = "Base image for Per Vices SDR platforms"
LICENSE = "MIT"
IMAGE_FEATURES:append = " dev-pkgs tools-sdk"
EXTRA_IMAGE_FEATURES:append = " package-management"
inherit core-image
inherit extrausers

##
# REQUIRED
##
IMAGE_INSTALL:append = "\
    bash \
    busybox \
    dtc \
    i2c-tools \
    kernel-modules \
    neon \
    vim \
    vim-vimrc \
    watchdog \
    zlib \
    "

IMAGE_INSTALL:append:stratix10 = "\
    unilibrsu-client \
    unilibrsu-lib \
    "

export IMAGE_BASENAME = "sdimage"