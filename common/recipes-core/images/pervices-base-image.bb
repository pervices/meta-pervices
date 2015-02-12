DESCRIPTION = "A barebone console-only image with minimal feature support for development/debug \
of hardware peripherals."

IMAGE_FEATURES += "splash ssh-server-openssh tools-sdk \
                   tools-debug tools-profile debug-tweaks \
                   dev-pkgs dbg-pkgs \
                  "

EXTRA_IMAGE_FEATURES += "package-management"

LICENSE = "MIT"

TOOLCHAIN_HOST_TASK_append = " nativesdk-python-cheetah \
    nativesdk-python-netserver nativesdk-python-pprint \
    nativesdk-python-pickle nativesdk-python-shell \
    nativesdk-orc nativesdk-swig"

CORE_IMAGE_EXTRA_INSTALL = "\
	i2c-tools \
	screen \
	vim \
	vim-vimrc \
	git \
	boost \
	cmake \
	python \
	python-cheetah \
	python-modules \
	python-argparse \
	htop \
	sshfs-fuse \
   	glib-2.0 \
    	orc \
    	libudev \
    	ntpdate \
    	iperf \
    	openssh-sftp \
    	openssh-sftp-server \
	lighttpd \
	lighttpd-module-cgi \
	flashrom \
	zlib \
	tar \
	libusb1 \
	libusb-compat \
	kernel-modules \
	netbase \
	busybox \
	base-passwd \
	base-files \
	sysvinit \
	initscripts \
	e2fsprogs \
	mtd-utils \
	gdb \
	gdbserver \
	bash \
	strace \
	openssh \
	openssl \
	elfutils \
	sysfsutils \
	usbutils \
	dtc \
	gawk \
	ethtool \
	grep \
	iputils \
	make \
	pciutils \
	portmap \
	sed \
	setserial \
	wget \
	autoconf \
	diffutils \
	perl \
	minicom \
	iptables \
	oprofile \
	net-tools \
	gator \
	openssh-sftp-server \
	util-linux \
	bison \
	gcc \
	g++ \
    	"

inherit core-image
