# Yocto 2.2 Morty BSP Layer for Per Vices SDRs

[![PerVices](https://www.pervices.com/wp-content/uploads/elementor/thumbs/pv-logo-with-name-sans-serif-web-pf8rchrnf73tnf5j64959qjol29xs2fn0ztskr2uyk.png)](https://pervices.com)  
This branch is not an official release branch and is only used for developing and testing purposes.  
You can use this branch to build an SD card image that will work for both Crimson and Cyan from scrach.  
It's also very easy to customize the image to fit your end applications.  

## Before you start

If you are not familiar with Yocto, you can refer to [Yocto Quick Start Guide], [Yocto Project Reference Manual], and [Yocto Project Mega Manual] to help setup the required software components.  
This guide assumes you already have all the required packages installed to start using Yocto.  
**Note this manual only works for Yocto 2.2**.  
**Note this manual is currently not compatible with all versions of Crimson, please contact Per Vices before trying to generate a new image**.  
**Note You need at least 50GB of disk space before attempting to build an SD card image**  

## Common Steps for both Crimson and Cyan

Step 1 Clone the poky repository

```sh
git clone -b morty git://git.yoctoproject.org/poky.git
```

Step 2 Clone the meta-linaro repository
```sh
git -C poky clone -b morty git://git.linaro.org/openembedded/meta-linaro.git
```

Step 3 Clone the meta-openembedded repository
```sh
git -C poky clone -b morty https://github.com/openembedded/meta-openembedded.git
``` 

Step 4 Clone the meta-altera repository
```sh
git -C poky clone -b angstrom-v2016.12-yocto2.2 https://github.com/pervices/meta-altera.git
```

Step 5 Clone the meta-angstrom repository
```sh
git -C poky clone -b angstrom-v2016.12-yocto2.2 https://github.com/Angstrom-distribution/meta-angstrom.git
```

Step 6 Clone the meta-nodejs repository
```sh
git -C poky clone -b morty https://github.com/imyller/meta-nodejs.git
```

Step 7 Clone the meta-pervices repository
```sh
git -C poky clone -b master https://github.com/pervices/meta-pervices.git
```

Step 8 Setup the build environment
```sh
source poky/oe-init-build-env ./build/
```

Step 9 Copy over the common configuration file
```sh
cp ../poky/meta-pervices/build-config/bblayers.conf conf/
```

## For Crimson only

Step 10 Copy over the configuration file for Crimson
```sh
cp ../poky/meta-pervices/build-config/crimson-rtm9.conf conf/local.conf
```

Optional: You may want to edit the following lines in local.conf based on the number of available CPU cores and threads
```sh
BB_NUMBER_THREADS ?= "8"
PARALLEL_MAKE ?= "-j 8"
```

Step 11 Build the SD card image for Crimson
```sh
bitbake crimson-rtm9
```

Step 12 Retrieve the SD card image

Once the build is finished successfully, the SD card image can be retrieved from the following path:
```sh
tmp/deploy/images/crimson/sdimage-crimson.wic
```

## For Cyan only

Step 10 Copy over the configuration file for Cyan
```sh
cp ../poky/meta-pervices/build-config/cyan.conf conf/local.conf
```

Optional: You may want to edit the following lines in local.conf based on the number of available CPU cores and threads
```sh
BB_NUMBER_THREADS ?= "8"
PARALLEL_MAKE ?= "-j 8"
```

Step 11 Build the SD card image for Cyan
```sh
bitbake 4r4t-1g-rtm5
```
Step 12 Retrieve the SD card image

Once the build is finished successfully, the SD card image can be retrieved from the following path:
```sh
tmp/deploy/images/cyan/sdimage-cyan-4r4t-1g-rtm5.wic
```
[Yocto Quick Start Guide]: <https://docs.yoctoproject.org/2.2/yocto-project-qs/yocto-project-qs.html>
[Yocto Project Reference Manual]: <https://docs.yoctoproject.org/2.2/ref-manual/ref-manual.html>
[Yocto Project Mega Manual]: <https://docs.yoctoproject.org/2.2/mega-manual/mega-manual.html>
