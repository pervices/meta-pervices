DESCRIPTION = "Base image for Per Vices SDR platforms"
LICENSE = "MIT"

EXTRA_IMAGE_FEATURES += "package-management"

inherit core-image
inherit extrausers

##
# REQUIRED
##
IMAGE_INSTALL:append = "\
	at \
	autoconf \
	bash \
	busybox \
	cmake \
	coreutils \
	cronie \
	cups \
	diffutils \
	dosfstools \
	dtc \
	dtbt \
	e2fsprogs \
	e2fsprogs-resize2fs \
	edac-utils \
	elfutils \
	ethtool \
	file \
	findutils \
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
	iproute2 \
	iptables \
	iputils \
	kernel-modules \
	kmod \
	liba52 \
	libftdi \
	libgcrypt \
	libpam \
	libpcap \
	libudev \
	libusb1 \
	libusb-compat \
	libxml2 \
	logrotate \
	make \
	memtester \
	man \
	minicom \
	mtd-utils \
	neon \
	netbase \
	net-tools \
	ntp \
	ntp-utils \
	openssh \
	openssh-misc \
	openssh-sftp \
	openssh-sftp-server \
	openssl \
	oprofile \
	orc \
	patch \
	parted \
	pciutils \
	perl \
	python-core \
	resolvconf \
	rsu \
	screen \
	sed \
	setserial \
	shadow \
	sshfs-fuse \
	strace \
	sudo \
	sysfsutils \
	systemd \
	tar \
	tcl \
	tcpdump \
	tzdata \
	udev \
	usbutils \
	util-linux \
	vim \
	vim-vimrc \
	watchdog \
	wget \
	zlib \
	"

##
# OPTIONAL
##

EXTRA_USERS_PARAMS = "usermod -P root root; \
                      usermod -s /bin/bash root; \
                      usermod -a -G dev-grp0 dev0; \
                      usermod -a -G cli-grp0 client; \
                     "

export IMAGE_BASENAME = "sdimage"
