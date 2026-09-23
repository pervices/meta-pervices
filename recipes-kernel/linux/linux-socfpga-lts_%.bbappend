# Append GSRD SoCFPGA device tree source include files
# As this is custom to Altera SoCFPGA GSRD, hence it is not suitable to be upstreamed to meta-intel-fpga

FILESEXTRAPATHS:prepend := "${THISDIR}/linux-socfpga-lts:"

DEPENDS = "u-boot-mkimage-native dtc-native"

SRC_URI:append:cyan = " file://fit_kernel_stratix10_htile.its file://stratix10.patch"
SRC_URI:append:cyan = " file://rsu.scc"

SRC_URI:append:chestnut = " file://fit_kernel_stratix10_htile.its file://stratix10.patch"
SRC_URI:append:chestnut = " file://rsu.scc"

inherit deploy

LINUXDEPLOYDIR = "${WORKDIR}/deploy-${PN}"
DTBDEPLOYDIR = "${DEPLOY_DIR_IMAGE}/devicetree"

do_deploy[depends] += "device-tree:do_deploy"

do_deploy:append() {
	# Stage required binaries for kernel.itb

	if [[ "${MACHINE}" == "cyan" ]] || [[ "${MACHINE}" == "chestnut" ]]; then
		# linux.dtb
		cp ${DTBDEPLOYDIR}/socfpga_stratix10_socdk.dtb ${B}
	fi

	# Generate and deploy kernel.itb
	if [[ "${MACHINE}" == "cyan" ]] || [[ "${MACHINE}" == "chestnut" ]]; then
		# kernel.its
		cp ${WORKDIR}/fit_kernel_stratix10_htile.its ${B}
		# Image
		cp ${LINUXDEPLOYDIR}/Image ${B}
		# Compress Image to lzma format
		if [ -e ${B}/Image.lzma ]; then
			rm ${B}/Image.lzma
		fi
		xz --format=lzma ${B}/Image
		# Generate kernel.itb
		mkimage -f ${B}/fit_kernel_stratix10_htile.its ${B}/kernel.itb
		# Deploy kernel.its, kernel.itb and Image.lzma
		install -m 744 ${B}/fit_kernel_stratix10_htile.its ${DEPLOYDIR}
		install -m 744 ${B}/kernel.itb ${DEPLOYDIR}
		install -m 744 ${B}/Image.lzma ${DEPLOYDIR}
		install -m 744 ${B}/socfpga_stratix10_socdk.dtb ${DEPLOYDIR}
	fi
}
