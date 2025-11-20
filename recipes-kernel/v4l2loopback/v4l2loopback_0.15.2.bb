# Out-of-tree kernel module recipe for v4l2loopback
DESCRIPTION = "A kernel module to create V4L2 loopback devices"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRC_URI = "git://github.com/v4l2loopback/v4l2loopback.git;protocol=https;branch=main"
SRCREV = "c394f8fb2c168932055c2577247c42390198d7c9"

S = "${WORKDIR}/git"

inherit module

MODULES_INSTALL_TARGET = "install-all"
EXTRA_OEMAKE += "KERNEL_DIR=${STAGING_KERNEL_DIR}"
EXTRA_OEMAKE += "PREFIX=${D}${prefix}"

DEPENDS += "help2man-native"

PACKAGES += "${PN}-utils"
FILES:${PN}-utils = "${bindir}/v4l2loopback-ctl"

RDEPENDS:${PN}-utils += " \
    gstreamer1.0 \
    gstreamer1.0-plugins-base \
    gstreamer1.0-plugins-good \
    sudo \
    v4l-utils \
"

MAKE_TARGETS = "${PN}"
do_compile_utils() {
    cd ${B}
    oe_runmake utils
}

addtask do_compile_utils after do_compile before do_install

# Example: if users wish to pass module parameters at load time, create a modprobe conf file
# You can create a separate bbappend or a small recipe to drop a file into /etc/modprobe.d/

# ------------------------------------------------------------------
# Integration notes (add to your image and runtime configuration)
# ------------------------------------------------------------------
# 1) Add the recipe to your image:
#    IMAGE_INSTALL:append = " v4l2loopback"
#
# 2) Ensure kernel dev headers and module build support are enabled in your Yocto kernel config
#    - DEPENDS includes virtual/kernel so the kernel headers are available at build time.
#
# 3) Pin SRCREV if you want reproducible builds. Replace AUTOREV with the actual commit hash:
#    SRCREV = "abcdef1234567890..."
#
# 4) To auto-load the module at boot with custom parameters, add a small conf file: create a new recipe
#    (e.g. recipes-core/v4l2loopback/files/10-v4l2loopback.conf) and install it to /etc/modprobe.d/
#
#    Example content (10-v4l2loopback.conf):
#      options v4l2loopback devices=1 video_nr=10 card_label="mipi_cam" exclusive_caps=1
#
#    Or simply add a systemd unit (see below) to load module with modprobe parameters.
#
# 5) Example systemd unit to create and configure the device at boot (optional):
#
#    Create file: meta-yourlayer/recipes-core/v4l2loopback/files/load-v4l2loopback.service
#
#    [Unit]
#    Description=Load v4l2loopback module and configure device
#    After=local-fs.target
#
#    [Service]
#    Type=oneshot
#    ExecStart=/sbin/modprobe v4l2loopback devices=1 video_nr=10 card_label="mipi_cam" exclusive_caps=1
#    RemainAfterExit=yes
#
#    [Install]
#    WantedBy=multi-user.target
#
#    Then add a small recipe to install that file and enable the service via SELINUX/ROOTFS_POSTPROCESS
#
# 6) Usage example once module loaded:
#    modprobe v4l2loopback devices=1 video_nr=10 card_label="mipi_cam"
#    # Check device
#    ls -l /dev/video10
#
# 7) Typical GStreamer pipeline to feed the loopback device (on target):
#    gst-launch-1.0 v4l2src device=/dev/video3 ! video/x-raw,format=NV12,width=1280,height=720,framerate=30/1 \
#      ! videoconvert ! v4l2sink device=/dev/video10
#
# 8) Chromium can now open /dev/video10 as a normal webcam.

# ------------------------------------------------------------------
# Troubleshooting
# ------------------------------------------------------------------
# - If the recipe fails because the kernel build environment is not found, ensure your distro or image
#   provides kernel-devsrc or appropriate kernel headers (CONFIG_MODULES=y, and the kernel's build tree)
# - If compilation fails due to kernel API mismatches, pin the v4l2loopback commit that matches your
#   kernel version or apply small patches via SRC_URI to fix symbols.
# - To apply patches, add them to files/ and reference them from SRC_URI with file://patchname.patch
#
# ------------------------------------------------------------------
# License/Attribution: This recipe template is provided as a starting point. You may need to adjust
# paths, SRCREV and build flags according to your BSP/kernel and Yocto release (kirkstone / langdale / etc.).
# ------------------------------------------------------------------
