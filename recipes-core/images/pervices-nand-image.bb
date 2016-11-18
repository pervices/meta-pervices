DESCRIPTION = "A barebone console-only image with minimal feature support for development/debug \
of hardware peripherals. This image inherits from Altera's GSRD image with additional packages \
to support SDR features."

IMAGE_FEATURES += " \
        debug-tweaks \
        dev-pkgs \
	tools-sdk \
        "

EXTRA_IMAGE_FEATURES += "package-management"

LICENSE = "MIT"

TOOLCHAIN_HOST_TASK_append = " nativesdk-python-cheetah \
    nativesdk-python-netserver nativesdk-python-pprint \
    nativesdk-python-pickle nativesdk-python-shell \
    nativesdk-orc nativesdk-swig"

##
# REQUIRED
##
IMAGE_INSTALL += "\
	autoconf \
	avrdude \
	base-files \
	base-passwd \
	bash \
	bison \
	boost \
	busybox \
	cmake \
	coreutils \
	crimson-filesystem \
	crimson-firmware \
	crimson-fpga \
	crimson-mcu \
	crimson-website \
	diffutils \
	dtc \
	e2fsprogs \ 
	elfutils \
	ethtool \
	file \
	findutils \
	flashrom \
	fuse \
	g++ \
	gawk \
	gcc \
	gdb \
	gdbserver \
	git \
	glibc \
	gnupg \
	gnutls \
	grep \
	gzip \
	i2c-tools \
	init-ifupdown \
	initscripts \
	iperf \
	iproute2 \
	iptables \
	iputils \
	kernel-modules \
	liba52 \
	libftdi \
	libgcrypt \
	libpcap \
	libudev \
	libusb1 \
	libusb-compat \
	libxml2 \
	lshw \
	make \
	memtester \
	minicom \
	mtd-utils \
	neon \
	netbase \
	net-tools \
	nmap \
	nodejs \
	openssh \
	openssh-sftp \
	openssh-sftp-server \
	openssl \
	oprofile \
	orc \
	patch \
	pciutils \
	perl \
	portmap \
	python-core \
	resolvconf \
	screen \
	sed \
	setserial \
	sshfs-fuse \
	strace \
	subversion \
	sudo \
	sysfsutils \
	systemd \
	sysvinit \
	tar \
	tcl \
	tcpdump \
	tzdata \
	udev \
	usbutils \
	util-linux \
	valgrind \
	vim \
	vim-vimrc \
	watchdog \
	wget \
	zlib \
	"

##
# OPTIONAL
##
CORE_IMAGE_EXTRA_INSTALL += "\
	glibc \
	ntpdate \
	python-argparse \
	python-cheetah \
	python-modules \
	"

inherit core-image
inherit extrausers
EXTRA_USERS_PARAMS = "\
	usermod -P root root; \
	usermod -s /bin/bash root; \
	useradd -P dev dev; \
	usermod -s /bin/bash dev; \
	"

export IMAGE_BASENAME = "pervices-nand-image"
