
SUMMARY = "A image for PCU video v2 development"
LICENSE = "MIT"

inherit core-image extrausers

# printf "%q" $(mkpasswd -m sha256crypt fahel)
PASSWD = "\$5\$Hv7.3840I6SB21jN\$.xw0MzN1SbQFaKMhewg4GFMDetCHg4wBuuBxKkxNUa5"
EXTRA_USERS_PARAMS = "\
    usermod -p '${PASSWD}' root; \
    "

CLANGSDK = "1"

IMAGE_FEATURES += " \
    debug-tweaks \
    tools-profile \
    tools-sdk \
    package-management \
    ssh-server-openssh \
    splash \
    nfs-client \
    tools-testapps \
    hwcodecs \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'weston', \
       bb.utils.contains('DISTRO_FEATURES',     'x11', 'x11-base x11-sato', \
                                                       '', d), d)} \
"

CORE_IMAGE_EXTRA_INSTALL += " \
    packagegroup-core-full-cmdline \
    packagegroup-fsl-tools-audio \
    packagegroup-fsl-tools-gpu \
    packagegroup-fsl-tools-gpu-external \
    packagegroup-imx-isp \
    packagegroup-fsl-gstreamer1.0 \
    packagegroup-fsl-gstreamer1.0-full \
    packagegroup-app-tools \
    firmwared \
"

DEFAULT_TIMEZONE = "Asia/Shanghai"
ENABLE_BINARY_LOCALE_GENERATION = "1"
IMAGE_LINGUAS:append = " en-us en-gb es-us zh-cn"
GLIBC_GENERATE_LOCALES:append = " en_US.UTF-8 es_US.UTF-8 en_GB.UTF-8 zh_CN.UTF-8"
PACKAGECONFIG:append:pn-weston = " remoting"
PACKAGECONFIG:append:pn-flutter-engine = " profile debug"

IMAGE_INSTALL += " \
    chrony \
    coreutils \
    git cmake vim tree \
    pipewire-alsa \
    python3-paho-mqtt \
    python3-pygobject \
    python3-gpiod \
    python3-evdev \
    python3-numpy \
    python3-dbus \
    python3-sqlite3 \
    python3-netifaces \
    python3-posix-ipc \
    sox \
    curl \
    evtest \
    tzdata \
    v4l-utils \
    libv4l \
    ffmpeg \
    libva \
    libgpiod \
    tslib \
    net-tools \
    tcpdump \
    libubootenv \
    libubootenv-bin \
    htop \
    v4l-utils \
    i2c-tools \
    sqlite3 \
    journal-mount \
    gstreamer1.0-rtsp-server \
    gst-variable-rtsp-server \
    gstreamer1.0-plugins-good \
    gstreamer1.0-plugins-bad \
    glibc-utils glibc-gconv \
    tzdata-core tzdata-asia \
    flutter-auto flutter-pi flutter-samples-compass-app ivi-homescreen \
    fluttercommunity-plus-plugins-packages-battery-plus-battery-plus-example \
    my-flutter-webrtc \
"

