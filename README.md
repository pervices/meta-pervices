Yocto BSP Layer for Per Vices Platforms
========================================================

Maintainer: Howard Pang <howard.p@pervices.com></br>
The source repository is located at: https://github.com/pervices/meta-pervices

## Getting Sources
Get the 1.5 Dora branch of the Poky release from Yocto.
```
git clone -b dora git://git.yoctoproject.org/poky.git
pushd poky
```
Clone the desired branch of the meta-altera layer.
```
git clone -b angstrom-v2013.12-yocto1.5 git://git.rocketboards.org/meta-altera.git
```
Clone the meta-angstrom layer that Altera uses since they have been using Angstrom prior to Poky, and remove broken package.
```
git clone -b angstrom-v2013.12-yocto1.5  https://github.com/Angstrom-distribution/meta-angstrom.git
rm -rf meta-altera/recipes-images/
```
Clone the desired branch of the meta-pervices layer.
```
git clone https://github.com/pervices/meta-pervices.git
```
Clone the respective 1.5 Dora branch of the linaro OE layer.
```
git clone -b dora git://git.linaro.org/openembedded/meta-linaro.git
```
Clone the openembedded core
```
git clone -b dora https://github.com/openembedded/meta-openembedded.git
git clone -b dora git://git.openembedded.org/openembedded-core
```
Source the Open embedded script for variables. Everything inside your ./build folder is the output of the build.
Global configuration files and build settings are also located in here.
```
cd ../
source poky/oe-init-build-env ./build
```

## Modifying ./build/conf/bblayers.conf
Include the extra layers that we included above into our build by adding them to the bblayers.conf file.
Replace the current BBLAYERS variable with the one specified below. Note: /path/to/poky/ shown below should be the path to the ./poky folder
```
BBLAYERS ?= " \
  /home/hpang/yocto/poky/meta \
  /home/hpang/yocto/poky/meta-yocto \
  /home/hpang/yocto/poky/meta-yocto-bsp \
  /home/hpang/yocto/poky/meta-altera \
  /home/hpang/yocto/poky/meta-linaro/meta-linaro-toolchain \
  /home/hpang/yocto/poky/meta-pervices/common \
  /home/hpang/yocto/poky/meta-openembedded/meta-oe \
  "
```

## Modifying ./build/conf/local.conf
All changes below pertain to modifying local.conf to include critical and useful compilation/architecture configurations. Some of these
might already exist. In the case they do exist, just ensure that it is correct with the specified values shown below.</br>
First specify the FPGA.
```
MACHINE = "socfpga_arria5"
```
We are building the poky distribution vs. the altera angstrom standard.
```
DISTRO ?= "poky"
```
Uncomment out the BB_NUMBER_THREADS and PARALLEL_MAKE to build faster
```
BB_NUMBER_THREADS ?= "8"
PARALLEL_MAKE ?= "-j 8"
```
Specify the packager to use, IPK seems to error out when do_rootfs executes, so force to use RPM by uncommenting
```
PACKAGE_CLASSES ?= "package_rpm"
```
There further more seems to be some inode size issues when building the file system for .ext3, so only make tar.gz output
```
IMAGE_FSTYPES = "tar.gz"
```
Adds a bunch of required packages for systemd and usbhost.
```
DISTRO_FEATURES_append = " systemd usbhost"
```
Use the linaro toolchain that we included as a layer, that we pulled from Linaro's GIT.
```
PREFERRED_VERSION_gcc                            ?= "linaro-4.8%"
PREFERRED_VERSION_gcc-cross                      ?= "linaro-4.8%"
PREFERRED_VERSION_gcc-cross-initial              ?= "linaro-4.8%"
PREFERRED_VERSION_gcc-cross-intermediate         ?= "linaro-4.8%"
PREFERRED_VERSION_gcc-cross-canadian             ?= "linaro-4.8%"
PREFERRED_VERSION_gcc-crosssdk                   ?= "linaro-4.8%"
PREFERRED_VERSION_gcc-crosssdk-initial           ?= "linaro-4.8%"
PREFERRED_VERSION_gcc-crosssdk-intermediate      ?= "linaro-4.8%"
PREFERRED_VERSION_gcc-runtime                    ?= "linaro-4.8%"
PREFERRED_VERSION_nativesdk-gcc-runtime          ?= "linaro-4.8%"
PREFERRED_VERSION_libgcc                         ?= "linaro-4.8%"
PREFERRED_VERSION_nativesdk-libgcc               ?= "linaro-4.8%"
PREFERRED_VERSION_gcc-cross-canadian-${TRANSLATED_TARGET_ARCH} ?= "linaro-4.8%"
```

Add support for hard-float to the compiler
```
DEFAULTTUNE = "cortexa9hf-neon"
```

## Fixing Linaro Toolchain Path
Fix the path of linaro snapshots in the file by replacing the SRC_URI line:
./poky/meta-linaro/meta-linaro-toolchain/recipes-devtools/gcc/gcc-linaro-4.8.inc
```
SRC_URI = "https://snapshots.linaro.org/openembedded/sources/gcc-${PV}-${RELEASE}.tar.xz \
```

## Compiling Source Code
Build the bootloader

1. `bitbake virtual/bootloader`
2. Load and save the kernel .config file provided by Per Vices, the current config file is located at ///releases/crimson/sdcard/kernel.config 
   `bitbake -c menuconfig virtual/kernel`
3. `bitbake virtual/kernel`
4. `bitbake pervices-base-image`

The Build configuration should look like this:
```
Build Configuration:
BB_VERSION        = "1.20.0"
BUILD_SYS         = "x86_64-linux"
NATIVELSBSTRING   = "Arch-rolling"
TARGET_SYS        = "arm-poky-linux-gnueabi"
MACHINE           = "socfpga_arria5"
DISTRO            = "poky"
DISTRO_VERSION    = "1.5.4"
TUNE_FEATURES     = "armv7a vfp neon callconvention-hard cortexa9"
TARGET_FPU        = "vfp-neon"
meta              
meta-yocto        
meta-yocto-bsp    = "dora:e035c59d7d331ddaeff19b069169567af5e6f8ac"
meta-altera       = "angstrom-v2013.12-yocto1.5:1090ffbbee1f3b6e45647591d369423ef9b2e41e"
meta-linaro-toolchain = "dora:2d06310e96959f3bf90f25a3fdd0f8dd0ed7a0b2"
common            = "master:23c385994d8a2b98a8771abd5e698621354e00e0"
meta-oe           = "dora:e75ae8f50af3effe560c43fc63cfd1f39395f011"
```

## Building SDCard Image
To make the sdcard you will need the following:

* make_sdcard.py - make sdcard script (Per Vices provided)
* pervices-base-image-socfpga_arria5.tar.gz - rootfs (bitbake built)
* preloader-mkpimage.bin - preloader (quartus generated)
* socfpga.dtb - device tree blob (quartus generated)
* soc_system.rbf - hardware FPGA image (quartue generated)
* u-boot.scr - u-boot init script (Per Vices provided)
* u-boot-socfpga_arria5.img - u-boot image (bitbake built - bitbake virtual/bootloader)
* zImage - kernel image (bitbake built - bitbake virtual/kernel)

Navigate the to the output directory at
```
./build/tmp/deploy/images/socfpga_arria5
```
Extract the generated rootfs (file system)
```
mkdir rootfs
cd rootfs
sudo tar -zxf ../pervices-base-image-socfpga_arria5.tar.gz
cd ../
```
Make a new directory to store all the files you need to make the sdcard image
```
mkdir 2015-03-17-1604
```
Put all of those files in the folder you generated, and execute the following within that folder:
```
sudo ./make_sdimage.py -f -P preloader-mkpimage.bin,u-boot-socfpga_arria5.img,num=3,format=raw,size=50M,type=A2 -P rootfs/*,num=2,format=ext3,size=2000M -P zImage,u-boot.scr,soc_system.rbf,socfpga.dtb,num=1,format=vfat,size=500M -s 4G -n sdcard.img
```
Plug in your sdcard and determine the mount point /dev/sdX
```
sudo dd if=sdcard.img of=/dev/sdX
```
