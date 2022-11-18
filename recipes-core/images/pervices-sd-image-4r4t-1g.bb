DESCRIPTION = "A barebone console-only image with minimal feature support for development/debug \
of hardware peripherals. This image inherits from Altera's GSRD image with additional packages \
to support SDR features."

IMAGE_FEATURES += " \
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
	at \
	at-doc \
	autoconf \
	autoconf-doc \
	avrdude \
	avrdude-doc \
	base-files \
	base-passwd \
	base-passwd-doc \
	bash \
	bash-doc \
	bison \
	bison-doc \
	boost \
	busybox \
	cmake \
	cmake-doc \
	coreutils \
	coreutils-doc \
	cyan-filesystem \
	cyan-firmware-4r4t-1g \
	cyan-fpga \
	cyan-mcu \
	cyan-default \
	cronie \
	cronie-doc \
	cups \
	cups-doc \
	diffutils \
	diffutils-doc \
	dosfstools \
	dtc \
	dtbt \
	e2fsprogs \
	e2fsprogs-resize2fs \
	e2fsprogs-doc \
	edac-utils \
	elfutils \
	ethtool \
	ethtool-doc \
	file \
	file-doc \
	findutils \
	findutils-doc \
	flashrom \
	flashrom-doc \
	fuse \
	fuse-doc \
	fpga-overlay \
	g++ \
	gawk \
	gawk-doc \
	gcc \
	gcc-doc \
	gdb \
	gdb-doc \
	gdbserver \
	git \
	git-doc \
	glibc \
	glibc-doc \
	gnupg \
	gnupg-doc \
	gnutls \
	gnutls-doc \
	grep \
	grep-doc \
	gzip \
	gzip-doc \
	i2c-tools \
	i2c-tools-doc \
	init-ifupdown \
	initscripts \
	iperf \
	iperf-doc \
	iproute2 \
	iproute2-doc \
	iptables \
	iptables-doc \
	iputils \
	iputils-doc \
	kernel-modules \
	kmod \
	liba52 \
	libftdi \
	libgcrypt \
	libgcrypt-doc \
	libpam \
	libpam-doc \
	libpcap \
	libpcap-doc \
	libudev \
	libusb1 \
	libusb-compat \
	libxml2 \
	libxml2-doc \
	lshw \
	lshw-doc \
	lsof \
	make \
	make-doc \
	memtester \
	memtester-doc \
	man \
	man-doc \
	minicom \
	minicom-doc \
	mtd-utils \
	mtd-utils-doc \
	neon \
	neon-doc \
	netbase \
	netbase-doc \
	net-tools \
	net-tools-doc \
	nmap \
	nmap-doc \
	nodejs \
	nodejs-doc \
	openssh \
	openssh-doc \
	openssh-misc \
	openssh-sftp \
	openssh-sftp-server \
	openssl \
	openssl-doc \
	oprofile \
	oprofile-doc \
	orc \
	patch \
	patch-doc \
	parted \
	pciutils \
	pciutils-doc \
	perl \
	perl-doc \
	picocom \
	polkit \
	portmap \
	portmap-doc \
	python-core \
	resolvconf \
	rsu \
	screen \
	screen-doc \
	sed \
	sed-doc \
	setserial \
	setserial-doc \
	shadow \
	shadow-doc \
	sshfs-fuse \
	sshfs-fuse-doc \
	strace \
	strace-doc \
	stress \
	stress-ng \
	stressapptest \
	subversion \
	subversion-doc \
	sudo \
	sudo-doc \
	sysfsutils \
	sysfsutils-doc \
	systemd \
	systemd-doc \
	tar \
	tar-doc \
	tcl \
	tcl-doc \
	tcpdump \
	tcpdump-doc \
	tzdata \
	udev \
	useradd \
	useradd-client \
	usbutils \
	usbutils-doc \
	util-linux \
	util-linux-doc \
	valgrind \
	vim \
	vim-doc \
	vim-vimrc \
	watchdog \
	watchdog-doc \
	wget \
	wget-doc \
	wireshark \
	zlib \
	zlib-doc \
	"

##
# OPTIONAL
##
CORE_IMAGE_EXTRA_INSTALL += "\
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
	usermod -a -G dev-grp0 dev0; \
	usermod -a -G cli-grp0 client; \
	"

export IMAGE_BASENAME = "pervices-sd-image"
