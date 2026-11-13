DESCRIPTION = "Base image for Per Vices SDR platforms"
LICENSE = "MIT"
IMAGE_FEATURES:append = " dev-pkgs tools-sdk"
EXTRA_IMAGE_FEATURES:append = " package-management"
TOOLCHAIN_HOST_TASK:append = "\
    nativesdk-python-cheetah \
    nativesdk-python-netserver \
    nativesdk-python-pprint \
    nativesdk-python-pickle \
    nativesdk-python-shell \
    nativesdk-orc nativesdk-swig \
    "
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

IMAGE_INSTALL:append:chestnut = "\
    unilibrsu-client \
    unilibrsu-lib \
    "

IMAGE_INSTALL:append:cyan = "\
    unilibrsu-client \
    unilibrsu-lib \
    "

export IMAGE_BASENAME = "sdimage"