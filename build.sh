#!/bin/bash

DEFAULT_PV_RELEASE="dizzy"
DEFAULT_PV_MACHINE="rtm5"

PV_MACHINE="${DEFAULT_PV_MACHINE}"
PV_RELEASE="${DEFAULT_PV_RELEASE}"

die() {
	local r=$?
	if [ "" = "$*" ]; then
		echo "error (no message)"
	else
		echo "error: $*"
	fi
	if [ $r -eq 0 ]; then
		r=1
	fi
	exit $r
}

ncpus() {
	cat /proc/cpuinfo | grep -i processor | wc -l
}

pv_tag() {
	local mach="$1"
	local rel="$2"
	if [ "" = "${mach}" ]; then
		mach="${DEFAULT_PV_MACHINE}"
	fi
	if [ "" = "${rel}" ]; then
		mach="${DEFAULT_PV_RELEASE}"
	fi
	echo "pervices-${mach}-${rel}"
}

PV_DOCKER_TAG=""

usage() {
	echo "usage: sudo sh build.sh [options..]"
	echo ""
	echo "options:"
	echo ""
	echo "<-h|--help>           print usage"
	echo "<-m|--machine>  arg   build for specified machine (default: ${DEFAULT_PV_MACHINE})"
	echo "<-r|--release>  arg   build specified release     (default: ${DEFAULT_PV_RELEASE})"
	echo "<-t|--tag>      arg   use specified Docker tag    (default: pervices-[machine]-[release])"
}

while [ $# -ge 1 ]; do
	case "$1" in
	"-h" | "--help" )
		usage
		exit 0
		;;
	"-m" | "--PV_MACHINE" )
		shift
		PV_MACHINE="$1"
		;;
	"-r" | "--release" )
		shift
		PV_RELEASE="$1"
		;;
	"-t" | "--tag" )
		shift
		PV_DOCKER_TAG="$1"
		;;
	*)
		echo "unrecognized argument: '$1'"
		exit 2
		;;
	esac
	shift
done

touch / 2>/dev/null
if [ $? -ne 0 -a "0" != "${UID}" -a "0" != "${EUID}"  ]; then
	usage
	echo "root permissions required (UID: '${UID}', EUID: '${EUID}')"
	exit 1
fi

if [ "" = "${PV_DOCKER_TAG}" ]; then
	PV_DOCKER_TAG="$(pv_tag "${PV_MACHINE}" "${PV_RELEASE}")"
fi

case "${PV_RELEASE}" in
	"morty" )
		FIRMWARE_TAG="master-testing"
		RELEASES_TAG="master-testing"
		UHD_TAG="master-testing"
		WEBSERVER_TAG="master-testing"
		META_PERVICES_TAG="yocto2.2"
		
		POKY_TAG="morty"
		ALTERA_TAG="rel_angstrom-v2015.12-yocto2.0_17.08.01_pr"
		ANGSTROM_TAG="angstrom-v2016.12-yocto2.2"
		LINARO_TAG="${POKY_TAG}"
		NODEJS_TAG="${POKY_TAG}"
		NODEJS_CONTRIB_TAG="${POKY_TAG}"
		OE_TAG="${POKY_TAG}"
		SWU_TAG="${POKY_TAG}"
	;;

	"dizzy" )
		FIRMWARE_TAG="master-testing"
		RELEASES_TAG="master-testing"
		UHD_TAG="master-testing"
		WEBSERVER_TAG="master-testing"
		META_PERVICES_TAG="yocto1.7"
		
		POKY_TAG="dizzy"
		ALTERA_TAG="angstrom-v2014.12-yocto1.7"
		ANGSTROM_TAG="angstrom-v2014.12-yocto1.7"
		LINARO_TAG="${POKY_TAG}"
		NODEJS_TAG="jethro"
		NODEJS_CONTRIB_TAG="jethro"
		OE_TAG="${POKY_TAG}"
		SWU_TAG="daisy"
	;;

	*)
		echo "unsupported release specified: '${RELEASE}'"
		exit 3
	;;
esac

cat << EOF
================================================================================
PV_MACHINE         '${PV_MACHINE}'
PV_RELEASE         '${PV_RELEASE}'
PV_DOCKER_TAG      '${PV_DOCKER_TAG}'

FIRMWARE_TAG       '${FIRMWARE_TAG}'
RELEASES_TAG       '${RELEASES_TAG}'
UHD_TAG            '${UHD_TAG}'
WEBSERVER_TAG      '${WEBSERVER_TAG}'
META_PERVICES_TAG  '${META_PERVICES_TAG}'

POKY_TAG           '${POKY_TAG}'
ALTERA_TAG         '${ALTERA_TAG}'
ANGSTROM_TAG       '${ANGSTROM_TAG}'
LINARO_TAG         '${LINARO_TAG}'
NODEJS_TAG         '${NODEJS_TAG}'
NODEJS_CONTRIB_TAG '${NODEJS_CONTRIB_TAG}'
OE_TAG             '${OE_TAG}'
SWU_TAG            '${SWU_TAG}'
================================================================================
EOF

ARGS=" \
	--build-arg PV_RELEASE="${PV_RELEASE}" \
	--build-arg PV_MACHINE="${PV_MACHINE}" \
	--build-arg FIRMWARE_TAG="${FIRMWARE_TAG}" \
	--build-arg RELEASES_TAG="${RELEASES_TAG}" \
	--build-arg WEBSERVER_TAG="${WEBSERVER_TAG}" \
	--build-arg META_PERVICES_TAG="${META_PERVICES_TAG}" \
	--build-arg POKY_TAG="${POKY_TAG}" \
	--build-arg ALTERA_TAG="${ALTERA_TAG}" \
	--build-arg ANGSTROM_TAG="${ANGSTROM_TAG}" \
	--build-arg LINARO_TAG="${LINARO_TAG}" \
	--build-arg NODEJS_TAG="${NODEJS_TAG}" \
	--build-arg NODEJS_CONTRIB_TAG="${NODEJS_CONTRIB_TAG}" \
	--build-arg OE_TAG="${OE_TAG}" \
	--build-arg SWU_TAG="${SWU_TAG}" \
"

echo ""
for (( i = 5; i >= 0; i-- )); do
	printf "Building in ${i} (Ctrl+C to cancel)..\r"
	if [ 0 -ne $i ]; then
		sleep 1
	fi
done
echo ""
echo ""

true \
&& docker build \
	-t ${PV_DOCKER_TAG} \
	-f Dockerfile \
	${ARGS} \
	. \
|| die "failed to build docker image '${PV_DOCKER_TAG}'"

DRUN_CMD="docker run -v ${PWD}/build:/root/build --rm -it ${PV_DOCKER_TAG} "

cat << EOF

================================================================================
To use the docker container manually, enter the following:
sudo ${DRUN_CMD} bash
================================================================================

EOF

drun() {
	${DRUN_CMD} sh -c "$*"	
}

#rm -Rf build
mkdir -p build

echo "Preparing configuration.."
if [ "yocto1.7" = "${META_PERVICES_TAG}" ]; then
	drun " \
			mkdir -p /root/build/conf \
		" \
	&& drun " \
			cp \
				/root/poky/meta-pervices/conf/bblayers.conf.sample \
				/root/build/conf/bblayers.conf \
		" \
	&& drun " \
			cp \
				/root/poky/meta-pervices/conf/local.conf.sample \
				/root/build/conf/local.conf \
		" \
	&& drun " \
			sed \
				-i \
				-e 's|path-to-poky|/root|g' \
				/root/build/conf/bblayers.conf \
		" \
	&& drun " \
			sed \
				-i \
			-e 's|tools-profile||g' \
				/root/build/conf/local.conf \
		" \
	|| die "failed to prepare configuration"
elif [ "yocto2.2" = "${META_PERVICES_TAG}" ]; then
	drun " \
			mkdir -p /root/build/conf \
		" \
	&& drun " \
			cp \
				/root/poky/meta-pervices/sample-configuration/bblayers.conf.sample \
				/root/build/conf/bblayers.conf \
		" \
	&& drun " \
			cp \
				/root/poky/meta-pervices/sample-configuration/local.conf.sample \
				/root/build/conf/local.conf \
		" \
	&& drun " \
			sed \
				-i \
				-e 's|path-to-poky|/root/poky|g' \
				/root/build/conf/bblayers.conf \
		" \
	|| die "failed to prepare configuration"
else
	die "unsupported META_PERVICES_TAG ${META_PERVICES_TAG}"
fi

echo "Building Bootloader.."
drun " \
	cd /root/poky \
		&& . ./oe-init-build-env /root/build \
		&& export BITBAKE_THREADS=$(( 2 * $(ncpus) )) \
		&& export PARALLEL_MAKE=-j$(( $(ncpus) - 1 )) \
		&& bitbake virtual/bootloader \
	" \
|| die "failed to build Bootloader"

echo "Building Kernel Image.."
drun " \
	cd /root/poky \
		&& . ./oe-init-build-env /root/build \
		&& export BITBAKE_THREADS=$(( 2 * $(ncpus) )) \
		&& export PARALLEL_MAKE=-j$(( $(ncpus) - 1 )) \
		&& bitbake virtual/kernel \
	" \
|| die "failed to build Kernel Image"

echo "Building SD-Card Image.."
drun " \
	cd /root/poky \
		&& . ./oe-init-build-env /root/build \
		&& export BITBAKE_THREADS=$(( 2 * $(ncpus) )) \
		&& export PARALLEL_MAKE=-j$(( $(ncpus) - 1 )) \
		&& bitbake pervices-sd-image \
	" \
|| die "failed to build SD Card Image"

echo "Building NAND Image.."
drun " \
	cd /root/poky \
		&& . ./oe-init-build-env /root/build \
		&& export BITBAKE_THREADS=$(( 2 * $(ncpus) )) \
		&& export PARALLEL_MAKE=-j$(( $(ncpus) - 1 )) \
		&& bitbake pervices-nand-image \
	" \
|| die "failed to build NAND Image"

echo 'SUCCESS!'
