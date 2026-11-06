DESCRIPTION = "Linux Kernel for Altera SoCFPGA"
LICENSE = "GPL-2.0-only"

FILESEXTRAPATHS:prepend := "${THISDIR}/linux-socfpga-lts:"

KERNEL_REPO ?= "git://github.com/altera-fpga/linux-socfpga.git"
KERNEL_PROT ?= "https"

# Kernel CONFIG_LOCALVERSION
LINUX_VERSION_EXTENSION ?= "-altera"

# Build branch name
LINUX_VERSION_PREFIX ?= "socfpga-"
LINUX_VERSION_SUFFIX ?= ""
LINUX_VERSION ?= "4.0"

SRCREV ?= "${AUTOREV}"
SRCREV_machine ?= "${SRCREV}"
PV = "${LINUX_VERSION}${LINUX_VERSION_SUFFIX}"
PV:append = "+git${SRCPV}"

KBRANCH ?= "${LINUX_VERSION_PREFIX}${LINUX_VERSION}${LINUX_VERSION_SUFFIX}"

SRC_URI = "${KERNEL_REPO};protocol=${KERNEL_PROT};branch=${KBRANCH}"

require recipes-kernel/linux/linux-yocto.inc

KBUILD_DEFCONFIG ?= "socfpga_defconfig"
KCONFIG_MODE ?= "--alldefconfig"

FILESEXTRAPATHS:prepend := "${THISDIR}/linux-socfpga-lts/configs:"

INSANE_SKIP:${PN}-src = "buildpaths"

SRC_URI:append = " file://gpio_sys.scc file://usb_adapter.scc "
SRC_URI:append:stratix10_htile = " file://rsu.scc"
SRC_URI:append:stratix10_htile = " file://fit_kernel_stratix10_htile.its file://stratix10.patch"

do_deploy:append() {
    # linux.dtb
    cp ${DTBDEPLOYDIR}/socfpga_stratix10_socdk.dtb ${B}

    cp ${WORKDIR}/fit_kernel_${MACHINE}.its ${B}

    cp ${LINUXDEPLOYDIR}/Image ${B}

    if [ -e ${B}/Image.lzma ]; then
        rm ${B}/Image.lzma
    fi

    xz --format=lzma ${B}/Image
    # Generate kernel.itb
    mkimage -f ${B}/fit_kernel_${MACHINE}.its ${B}/kernel.itb
    # Deploy kernel.its, kernel.itb and Image.lzma
    install -m 744 ${B}/fit_kernel_${MACHINE}.its ${DEPLOYDIR}
    install -m 744 ${B}/kernel.itb ${DEPLOYDIR}
    install -m 744 ${B}/Image.lzma ${DEPLOYDIR}
    install -m 744 ${B}/socfpga_stratix10_socdk.dtb ${DEPLOYDIR}
}