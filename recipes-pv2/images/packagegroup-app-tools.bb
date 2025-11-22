
DESCRIPTION = "Packagegroup to provide necessary tools for pcu-video image"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit packagegroup

PIPEWIRE_TOOLS = " \
    pipewire \
    wireplumber \
    pipewire-pulse \
    pipewire-spa-tools \
    pipewire-tools \
    pipewire-v4l2 \
    pipewire-alsa \
"

RDEPENDS:${PN} = " \
    can-utils \
    coreutils \
    cpufrequtils \
    dosfstools \
    ethtool \
    evtest \
    e2fsprogs-mke2fs \
    e2fsprogs-resize2fs \
    fbset \
    fsl-rc-local \
    iproute2 iproute2-tc \
    iw \
    i2c-tools \
    kernel-tools-iio \
    kernel-tools-pci \
    kernel-tools-virtio \
    kernel-tools-vsock \
    libgpiod-tools \
    linuxptp \
    memtester \
    minicom \
    mmc-utils \
    mtd-utils \
    mtd-utils-ubifs \
    nano \
    parted \
    ${PIPEWIRE_TOOLS} \
    procps \
    ptpd \
    udev-extraconf \
    dbus-broker \
"
