SUMMARY = "Per Vices SDR devicetrees"
DESCRIPTION = "Devicetree customisation for Per Vices SDRs"
SECTION = "bsp"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

KERNEL_INCLUDE = " \
    ${STAGING_KERNEL_DIR}/arch/${ARCH}/boot/dts \
    ${STAGING_KERNEL_DIR}/arch/${ARCH}/boot/dts/* \
    ${STAGING_KERNEL_DIR}/scripts/dtc/include-prefixes \
    "
inherit devicetree

PROVIDES = "virtual/dtb"

COMPATIBLE_MACHINE = "(cyan|chestnut)"

SRC_URI:append:cyan = " \
    file://socfpga_stratix10_ghrd.dtsi \
    "
SRC_URI:append:chestnut = " \
    file://socfpga_stratix10_ghrd.dtsi \
    "

do_configure[depends] += "virtual/kernel:do_configure"

do_configure:append:cyan() {
    # DTB generation
    cp ${STAGING_KERNEL_DIR}/arch/${ARCH}/boot/dts/altera/socfpga_stratix10.dtsi ${WORKDIR}
    cp ${STAGING_KERNEL_DIR}/arch/${ARCH}/boot/dts/altera/socfpga_stratix10_socdk.dts ${WORKDIR}
    # Include custom dtsi in DTB generation
    sed -i '/\#include \"socfpga_stratix10.dtsi\"/a \#include \"socfpga_stratix10_ghrd.dtsi\"\n' ${WORKDIR}/socfpga_stratix10_socdk.dts
}

do_configure:append:chestnut() {
    # DTB generation
    cp ${STAGING_KERNEL_DIR}/arch/${ARCH}/boot/dts/altera/socfpga_stratix10.dtsi ${WORKDIR}
    cp ${STAGING_KERNEL_DIR}/arch/${ARCH}/boot/dts/altera/socfpga_stratix10_socdk.dts ${WORKDIR}
    # Include custom dtsi in DTB generation
    sed -i '/\#include \"socfpga_stratix10.dtsi\"/a \#include \"socfpga_stratix10_ghrd.dtsi\"\n' ${WORKDIR}/socfpga_stratix10_socdk.dts
}
