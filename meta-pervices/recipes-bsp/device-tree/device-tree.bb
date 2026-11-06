SUMMARY = "Altera SoCFPGA Development Kit devicetrees"
DESCRIPTION = "Devicetree addons for Altera SoCFPGA Development Kit examples"
SECTION = "bsp"

LICENSE = "MIT & GPL-2.0-only"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

KERNEL_INCLUDE = " \
        ${STAGING_KERNEL_DIR}/arch/${ARCH}/boot/dts \
        ${STAGING_KERNEL_DIR}/arch/${ARCH}/boot/dts/* \
        ${STAGING_KERNEL_DIR}/scripts/dtc/include-prefixes \
        "
inherit devicetree

PROVIDES = "virtual/dtb"

COMPATIBLE_MACHINE = "${MACHINE}"

SRC_URI:append:stratix10_htile = " \
	file://socfpga_stratix10_ghrd.dtsi \
	"

do_configure[depends] += "virtual/kernel:do_configure"

do_configure:append:stratix10_htile() {
    cp ${STAGING_KERNEL_DIR}/arch/${ARCH}/boot/dts/altera/socfpga_stratix10.dtsi ${WORKDIR}
    cp ${STAGING_KERNEL_DIR}/arch/${ARCH}/boot/dts/altera/socfpga_stratix10_socdk.dts ${WORKDIR}
	sed -i '/\#include \"socfpga_stratix10.dtsi\"/a \#include \"socfpga_stratix10_ghrd.dtsi\"\n' ${WORKDIR}/socfpga_stratix10_socdk.dts
}